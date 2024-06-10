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
    private Army defender;
    private Army attacker;
    private King king;
    private Catapult c;
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
        setSize(1024,960);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        grassIcon = loadImageIcon("grass.JPG");
        mudIcon = loadImageIcon("mud.jpg");
        rocksIcon = loadImageIcon("rock.jpeg");
        wallIcon = loadImageIcon("wall.jpg");
        gateIcon = loadImageIcon("gate.jpg");
        knightIcon = loadImageIcon("knight.jpg");
        archerIcon = loadImageIcon("archer.jpg");

        board = new Board(100, 100, 10, 1) {

        };
        board.fields = board.boardType(1, 100, 100);

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
        } else if (!soldier.getArmyType()) {
            label.setIcon(knightIcon);
        } else {
            label.setIcon(archerIcon);
        }
    }

    private void startSimulation() {
        /*a = new Knight(70, 30); // Soldier 1
        a.setDefender(false);
        b = new Archer(30, 70); // Soldier 2
        b.setDefender(true);
        c = new Catapult(90,30);
        updateBoard();
        SwingWorker<Void, String> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                boolean over = false;
                while (!over) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    moveSoldierTowardsEnemy(a, b);
                    moveSoldierTowardsEnemy(b, a);
                    moveCatapultTowardsWall(c);

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
                    if (c.getRange() >= calculateDistance(c, a)) {

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
        worker.execute();*/
        attacker = new Army(false,50,5);
        defender = new Army(true,50,5);
        king = new King(board);
        while(!(attacker.isEmpty())||!(king.isAlive())){
            for(int i = 0;i<attacker.getAlive_soldiers().size();i++){
                Soldier a = attacker.getAlive_soldiers().get(i);
                if(a instanceof Catapult){
                    moveCatapultTowardsWall(a);
                    ((Catapult) a).attack();
                }
                else if(a instanceof Ram){
                    moveRamTowardsGate(a);
                }
                else{
                    moveSoldierTowardsEnemy(a,king);
                }

            }
        }

    }


    private int calculateDistance(Soldier s1, Soldier s2) {
        return Math.abs(s1.getX_position() - s2.getX_position()) + Math.abs(s1.getY_position() - s2.getY_position());
    }
    private void scanForEnemies(Soldier soldier,List<Soldier> enemies){
        for(int i = 0;i<enemies.size();i++){

        }
    }

    private void moveSoldierTowardsEnemy(Soldier mover, Soldier enemy) {
        int currentX = mover.getX_position();
        int currentY = mover.getY_position();
        int enemyX = enemy.getX_position();
        int enemyY = enemy.getY_position();
        int movementSpeed = mover.getMovement();
        if(mover.getRange()>=calculateDistance(mover,enemy)){
            return;
        }
        else{
            for (int step = 0; step < movementSpeed; step++) {

                int[] dx = {1, -1, 0, 0};
                int[] dy = {0, 0, 1, -1};
                int bestX = currentX;
                int bestY = currentY;
                int minDistance = Integer.MAX_VALUE;

                for (int i = 0; i < 4; i++) {
                    int newX = currentX + dx[i];
                    int newY = currentY + dy[i];
                    if (isValidMove(newX, newY)) {
                        int distance = Math.abs(newX - enemyX) + Math.abs(newY - enemyY);
                        if (distance < minDistance) {
                            minDistance = distance;
                            bestX = newX;
                            bestY = newY;
                        }
                    }
                }
                if (minDistance == Integer.MAX_VALUE) {
                    break;
                }
                currentX = bestX;
                currentY = bestY;
            }
            mover.setX_position(currentX);
            mover.setY_position(currentY);
        }

        updateBoard();
        }
        private void moveCatapultTowardsWall(Soldier catapult){
            int currentX = catapult.getX_position();
            int currentY = catapult.getY_position();

            for(int i = 0;i<catapult.getRange();i++){
                Field field = board.fields[currentX-i][catapult.getY_position()];
                if(field instanceof Wall){
                    return;
                }
                else{
                    if(isValidMove(currentX-1,catapult.getY_position())){
                        if(isValidMove(currentX-2,catapult.getY_position())){
                            currentX = catapult.getX_position()-2;
                        }
                        else{
                            currentX = catapult.getX_position()-1;
                        }

                    }
                    else {
                        currentY+=1;

                    }

                }

            }
            catapult.setX_position(currentX);
            catapult.setY_position(currentY);
        }
    private void moveRamTowardsGate(Soldier catapult){
        int currentX = catapult.getX_position();
        int currentY = catapult.getY_position();

        for(int i = 0;i<catapult.getRange();i++){
            Field field = board.fields[currentX-i][catapult.getY_position()];
            if(field instanceof Gate){
                return;
            }
            else{
                if(isValidMove(currentX-1,catapult.getY_position())){
                    if(isValidMove(currentX-2,catapult.getY_position())){
                        currentX = catapult.getX_position()-2;
                    }
                    else{
                        currentX = catapult.getX_position()-1;
                    }

                }
                else {
                    currentY+=1;

                }

            }

        }
        catapult.setX_position(currentX);
        catapult.setY_position(currentY);
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
