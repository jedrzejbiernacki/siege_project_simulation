import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationGUI extends JFrame {
    private final Board board;
    private final JPanel boardPanel;
    private final JButton startButton;
    private final JTextArea outputArea;

    public SimulationGUI() {
        setTitle("Simulation");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        board = new Board(100, 100, 10, 1) {

        };
        board.fields = board.boardType(1, 100, 100); // Choose board type 1 for simplicity


        boardPanel = new JPanel(new GridLayout(board.height, board.width));
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
                    panel.setBackground(Color.RED);
                }
                boardPanel.add(panel);
            }
        }
    }

    private void startSimulation() {

        Horseman a = new Horseman(1, 1); // Soldier 1
        Archer b = new Archer(2, 2); // Soldier 2

        boolean over = false;
        while (true) {
            for (int i = 15; i != 0; i--) {
                if (a.getRange() >= i) {
                    a.attack(b);
                    if (b.getHealth() < 0) {
                        outputArea.append("Soldier 2 is dead.\n");
                        over = true;
                        break;
                    }
                }
                if (b.getRange() >= i) {
                    b.attack(a);
                    if (a.getHealth() < 0) {
                        outputArea.append("Soldier 1 is dead.\n");
                        over = true;
                        break;
                    }
                }
            }
            if (over) break;
        }
    }

    public static void main(String[] args) {
        new SimulationGUI();
    }
}
