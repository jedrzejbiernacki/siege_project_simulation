import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

public class SimulationGUI extends JFrame {
    private final Board board;
    private final JPanel boardPanel;
    private final JButton startButton;
    private final JTextArea outputArea;
    private Knight a;
    private Archer b;
    private final JLabel[][] boardLabels;
    private final ImageIcon grassIcon;
    private final ImageIcon mudIcon;
    private final ImageIcon rocksIcon;
    private final ImageIcon wallIcon;
    private final ImageIcon gateIcon;
    private final ImageIcon knightIcon;
    private final ImageIcon archerIcon;

    public SimulationGUI() {
        setTitle("Simulation");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Load images
        grassIcon = loadImageIcon("grass.jpg");
        mudIcon = loadImageIcon("mud.jpg");
        rocksIcon = loadImageIcon("Rock.jpg");
        wallIcon = loadImageIcon("wall.jpg");
        gateIcon = loadImageIcon("gate.jpg");
        knightIcon = loadImageIcon("Knight.jpg");
        archerIcon = loadImageIcon("archer.jpg");

        board = new Board(100, 100, 10, 1) {
            // Provide implementation for abstract methods if any
        };
        board.fields = board.boardType(1, 100, 100); // Choose board type 1 for simplicity

        boardPanel = new JPanel(new GridLayout(board.height, board.width));
        boardLabels = new JLabel[board.height][board.width];
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
                JLabel label = new JLabel();
                if (field instanceof Grass) {
                    label.setIcon(grassIcon);
                } else if (field instanceof Mud) {
                    label.setIcon(mudIcon);
                } else if (field instanceof Rocks) {
                    label.setIcon(rocksIcon);
                } else if (field instanceof Wall) {
                    label.setIcon(wallIcon);
                } else if (field instanceof Gate) {
                    label.setIcon(gateIcon);
                }
                boardLabels[i][j] = label;
                boardPanel.add(label);
            }
        }
    }

    private void updateBoard() {
        for (int i = 0; i < board.height; i++) {
            for (int j = 0; j < board.width; j++) {
                Field field = board.fields[i][j];
                JLabel label = boardLabels[i][j];
                if (field instanceof Grass) {
                    label.setIcon(grassIcon);
                } else if (field instanceof Mud) {
                    label.setIcon(mudIcon);
                } else if (field instanceof Rocks) {
                    label.setIcon(rocksIcon);
                } else if (field instanceof Wall) {
                    label.setIcon(wallIcon);
                } else if (field instanceof Gate) {
                    label.setIcon(gateIcon);
                }
            }
        }
        updateSoldierPosition(a);
        updateSoldierPosition(b);
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private void updateSoldierPosition(Soldier soldier) {
        JLabel label = boardLabels[soldier.getX_position()][soldier.getY_position()];
        if(soldier.getHealth() <= 0) {
            Field field = board.fields[soldier.getX_position()][soldier.getY_position()];
            if (field instanceof Grass) {
                label.setIcon(grassIcon);
            } else if (field instanceof Mud) {
                label.setIcon(mudIcon);
            }
        } else if (soldier.getArmyType()) {
            label.setIcon(knightIcon);
        } else {
            label.setIcon(archerIcon);
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
            protected void process(List<String> chunks) {
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
            if (soldier.getRange() >= calculateDistance(soldier, enemy)) {
                break;
            } else {
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

    private ImageIcon loadImageIcon(String fileName) {
        URL imageURL = getClass().getResource(fileName);
        if (imageURL != null) {            return new ImageIcon(imageURL);
        } else {
            throw new RuntimeException("Image file not found: " + fileName);
        }
    }

    public static void main(String[] args) {


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SimulationGUI();
            }
        });
    }
}
