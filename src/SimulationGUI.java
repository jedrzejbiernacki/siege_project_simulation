import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

public class SimulationGUI extends JFrame {
    private final Board board;
    private final JPanel boardPanel;
    private final JButton startButton;
    private final JTextArea outputArea;
    private Knight a;
    private Archer b;
    private final JPanel[][] boardPanels;

    public SimulationGUI() {
        setTitle("Simulation");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        board = new Board(100, 100, 10, 1) {
            // Provide implementation for abstract methods if any
        };
        board.fields = board.boardType(1, 100, 100); // Choose board type 1 for simplicity

        boardPanel = new JPanel(new GridLayout(board.height, board.width));
        boardPanels = new JPanel[board.height][board.width];
        initializeBoard();

        startButton = new JButton("Start Simulation");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSimulation();
            }
        });

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(boardPanel, BorderLayout.CENTER);
        add(startButton, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void initializeBoard() {
        for (int i = 0; i < board.height; i++) {
            for (int j = 0; j < board.width; j++) {
                Field field = board.fields[i][j];
                JPanel panel = new JPanel();
                if (field instanceof Grass) {
                    panel.setBackground(new Color(38, 162, 77));
                } else if (field instanceof Mud) {
                    panel.setBackground(new Color(108, 52, 31));
                } else if (field instanceof Rocks) {
                    panel.setBackground(Color.GRAY);
                } else if (field instanceof Wall) {
                    panel.setBackground(Color.DARK_GRAY);
                } else if (field instanceof Gate) {
                    panel.setBackground(Color.ORANGE);
                }
                boardPanels[i][j] = panel;
                boardPanel.add(panel);
            }
        }
    }

    private void updateBoard() {
        for (int i = 0; i < board.height; i++) {
            for (int j = 0; j < board.width; j++) {
                Field field = board.fields[i][j];
                JPanel panel = boardPanels[i][j];
                if (field instanceof Grass) {
                    panel.setBackground(new Color(38, 162, 77));
                } else if (field instanceof Mud) {
                    panel.setBackground(new Color(108, 52, 31));
                } else if (field instanceof Rocks) {
                    panel.setBackground(Color.GRAY);
                } else if (field instanceof Wall) {
                    panel.setBackground(Color.DARK_GRAY);
                } else if (field instanceof Gate) {
                    panel.setBackground(Color.ORANGE);
                }
            }
        }
        updateSoldierPosition(a);
        updateSoldierPosition(b);
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private void updateSoldierPosition(Soldier soldier) {
        Field field = board.fields[soldier.getX_position()][soldier.getY_position()];
        JPanel panel = boardPanels[soldier.getX_position()][soldier.getY_position()];
        if(soldier.getHealth()<=0){
        if (field instanceof Grass) {
            panel.setBackground(new Color(38, 162, 77));
        } else if (field instanceof Mud) {
            panel.setBackground(new Color(108, 52, 31));
        }
        }
        else if (soldier.getArmyType()) {
            panel.setBackground(Color.BLUE);
        } else {
            panel.setBackground(Color.RED);
        }
    }

    private void startSimulation() {
        a = new Knight(30, 70); // Soldier 1
        a.setDefender(false);
        b = new Archer(70, 30); // Soldier 2
        b.setDefender(true);
        updateBoard();
        SwingWorker<Void, String> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                boolean over = false;
                while (!over) {
                    try {
                        Thread.sleep(1000); // Pause for a while to visualize the steps
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    moveSoldierTowardsEnemy(a, b);
                    moveSoldierTowardsEnemy(b, a);

                    if (a.getRange() >= calculateDistance(a, b)) {
                        a.attack(b);
                        publish("Soldier 1 attacks Soldier 2. Soldier 2 health: " + b.getHealth());
                        if (b.getHealth() <= 0) {
                            publish("Soldier 2 is dead.");
                            
                            updateBoard();
                            over = true;
                            break;
                        }
                    }

                    if (b.getRange() >= calculateDistance(b, a)) {
                        b.attack(a);
                        publish("Soldier 2 attacks Soldier 1. Soldier 1 health: " + a.getHealth());
                        if (a.getHealth() <= 0) {
                            publish("Soldier 1 is dead.");
                            over = true;
                            break;
                        }
                    }
                }
                return null;
            }

            @Override
            protected void process(java.util.List<String> chunks) {
                for (String message : chunks) {
                    outputArea.append(message + "\n");
                }
                updateBoard();
            }

            @Override
            protected void done() {
                startButton.setEnabled(true);
            }
        };

        startButton.setEnabled(false);
        worker.execute();
    }

    private int calculateDistance(Soldier s1, Soldier s2) {
        return Math.abs(s1.getX_position() - s2.getX_position()) + Math.abs(s1.getY_position() - s2.getY_position());
    }

    private void moveSoldierTowardsEnemy(Soldier soldier, Soldier enemy) {
        for (int i = 0; i < soldier.getMovement(); i++) {
            if(soldier.getRange() >= calculateDistance(soldier, enemy)){
                break;
            }
            else{
            int startX = soldier.getX_position();
            int startY = soldier.getY_position();
            int targetX = startX;
            int targetY = startY;
            int bestDistance = calculateDistance(soldier, enemy);

            int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for (int[] direction : directions) {
                int newX = startX + direction[0];
                int newY = startY + direction[1];
                if (isValidMove(newX, newY) && !(board.fields[newX][newY] instanceof Rocks)) {
                    Soldier nextPosition = new Horseman(newX, newY);
                    int newDistance = calculateDistance(nextPosition, enemy);
                    int nextX = newX + direction[0];
                    int nextY = newY + direction[1];
                    if (isValidMove(nextX, nextY) && !(board.fields[nextX][nextY] instanceof Rocks)) {
                        int newNewDistance = calculateDistance(new Horseman(nextX, nextY), enemy);
                        if (newNewDistance < newDistance) {
                            newDistance = newNewDistance;
                            newX = nextX;
                            newY = nextY;
                        }
                    }
                    if (newDistance < bestDistance) {
                        bestDistance = newDistance;
                        targetX = newX;
                        targetY = newY;
                    }
                }
            }

        // Debugging prints
        System.out.println("Soldier " + soldier.getClass().getSimpleName() + " moving from (" + startX + ", " + startY + ") to (" + targetX + ", " + targetY + ")");

        // Move soldier if the target position is different
        if (startX != targetX || startY != targetY) {
            soldier.setX_position(targetX);
            soldier.setY_position(targetY);
        }
    }
    }
    updateBoard();
    }

    private boolean isValidMove(int x, int y) {
        return x >= 0 && y >= 0 && x < board.height && y < board.width &&
                !(board.fields[x][y] instanceof Wall) && !(board.fields[x][y] instanceof Rocks);
    }

    public static void main(String[] args) {
        new SimulationGUI();
    }
}
