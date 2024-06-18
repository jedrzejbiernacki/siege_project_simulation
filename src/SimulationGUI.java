import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationGUI extends JFrame {
    public final Board board;
    private final JPanel boardPanel;
    private final JButton startButton;
    private final JTextArea outputArea;
    private final Army defender;
    private final Army attacker;
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
    private final ImageIcon catapultIcon;
    private final ImageIcon horsemanIcon;
    private final ImageIcon medicIcon;
    private final ImageIcon leaderIcon;
    private final ImageIcon ramIcon;
    private final ImageIcon kingIcon;
    private final JLabel statsLabel;


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
        catapultIcon = loadImageIcon("catapult.jpg");
        ramIcon = loadImageIcon("ram.jpg");
        medicIcon = loadImageIcon("medic.jpg");
        leaderIcon = loadImageIcon("leader.jpg");
        kingIcon = loadImageIcon("king.jpg");
        horsemanIcon = loadImageIcon("horseman.jpg");
        board = new Board(100, 100, 10, 1) {

        };
        board.fields = board.boardType(1, 100, 100);
        attacker = new Army(false,100,9,board);
        defender = new Army(true,150,5,board);

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

        statsLabel = new JLabel("Statistics:");
        statsLabel.setPreferredSize(new Dimension(200, 100));

        add(boardPanel, BorderLayout.CENTER);
        add(startButton, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.SOUTH);
        add(statsLabel, BorderLayout.EAST);

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
                    label.setBorder(null);
                } else if (field instanceof Mud) {
                    label.setIcon(mudIcon);
                    label.setBorder(null);
                } else if (field instanceof Rocks) {
                    label.setIcon(rocksIcon);
                    label.setBorder(null);
                } else if(field instanceof Wall&&((Wall) field).getHealth()>0) {
                    label.setIcon(wallIcon);
                    label.setBorder(null);
                } else if (field instanceof Gate) {
                    label.setIcon(gateIcon);
                    label.setBorder(null);
                }
                else if(field instanceof Wall&&((Wall) field).getHealth()<=0){
                    label.setIcon(grassIcon);
                    label.setBorder(null);
                }
            }
        }
        for(int i = 0;i<defender.getAlive_soldiers().size();i++){
            updateSoldierPosition(defender.getAlive_soldiers().get(i));
        }
        for(int i = 0;i<attacker.getAlive_soldiers().size();i++){
            updateSoldierPosition(attacker.getAlive_soldiers().get(i));
        }
        boardPanel.revalidate();
        boardPanel.repaint();
        updateStatsLabel();
    }
    private void updateStatsLabel() {
        int totalDefenders = defender.getAlive_soldiers().size();
        int totalAttackers = attacker.getAlive_soldiers().size();
        int totalAliveDefenders = 0;
        int totalAliveAttackers = 0;

        int aliveDefenderKnights = 0;
        int aliveDefenderArchers = 0;
        int aliveDefenderCatapults = 0;
        int aliveDefenderHorsemen = 0;
        int aliveDefenderMedics = 0;
        int aliveDefenderLeaders = 0;
        int aliveDefenderRams = 0;
        int aliveDefenderKings = 0;

        int aliveAttackerKnights = 0;
        int aliveAttackerArchers = 0;
        int aliveAttackerCatapults = 0;
        int aliveAttackerHorsemen = 0;
        int aliveAttackerMedics = 0;
        int aliveAttackerLeaders = 0;
        int aliveAttackerRams = 0;
        int aliveAttackerKings = 0;

        for (Soldier soldier : defender.getAlive_soldiers()) {
            if (soldier.getHealth() > 0) {
                totalAliveDefenders++;
                if (soldier instanceof Knight) aliveDefenderKnights++;
                else if (soldier instanceof Archer) aliveDefenderArchers++;
                else if (soldier instanceof Catapult) aliveDefenderCatapults++;
                else if (soldier instanceof Horseman) aliveDefenderHorsemen++;
                else if (soldier instanceof Medic) aliveDefenderMedics++;
                else if (soldier instanceof Leader) aliveDefenderLeaders++;
                else if (soldier instanceof Ram) aliveDefenderRams++;
                else if (soldier instanceof King) aliveDefenderKings++;
            }
        }

        for (Soldier soldier : attacker.getAlive_soldiers()) {
            if (soldier.getHealth() > 0) {
                totalAliveAttackers++;
                if (soldier instanceof Knight) aliveAttackerKnights++;
                else if (soldier instanceof Archer) aliveAttackerArchers++;
                else if (soldier instanceof Catapult) aliveAttackerCatapults++;
                else if (soldier instanceof Horseman) aliveAttackerHorsemen++;
                else if (soldier instanceof Medic) aliveAttackerMedics++;
                else if (soldier instanceof Leader) aliveAttackerLeaders++;
                else if (soldier instanceof Ram) aliveAttackerRams++;
                else if (soldier instanceof King) aliveAttackerKings++;
            }
        }

        String statsText = "<html>Statistics:<br>"  +
                "Alive Defenders: " + totalAliveDefenders + "<br>" +
                " - Knights: " + aliveDefenderKnights + "<br>" +
                " - Archers: " + aliveDefenderArchers + "<br>" +
                " - Catapults: " + aliveDefenderCatapults + "<br>" +
                " - Horsemen: " + aliveDefenderHorsemen + "<br>" +
                " - Medics: " + aliveDefenderMedics + "<br>" +
                " - Leaders: " + aliveDefenderLeaders + "<br>" +
                " - Rams: " + aliveDefenderRams + "<br>" +
                " - Kings: " + aliveDefenderKings + "<br>"  +
                "Alive Attackers: " + totalAliveAttackers + "<br>" +
                " - Knights: " + aliveAttackerKnights + "<br>" +
                " - Archers: " + aliveAttackerArchers + "<br>" +
                " - Catapults: " + aliveAttackerCatapults + "<br>" +
                " - Horsemen: " + aliveAttackerHorsemen + "<br>" +
                " - Medics: " + aliveAttackerMedics + "<br>" +
                " - Leaders: " + aliveAttackerLeaders + "<br>" +
                " - Rams: " + aliveAttackerRams + "<br>" +
                " - Kings: " + aliveAttackerKings + "<br>" +
                "</html>";

        statsLabel.setText(statsText);
    }

    /**
     *
     * @param soldier
     */
    private void updateSoldierPosition(Soldier soldier) {
        JLabel label = boardLabels[soldier.getY_position()][soldier.getX_position()];
        Border attackerBorder = BorderFactory.createLineBorder(Color.RED, 1);
        Border defenderBorder = BorderFactory.createLineBorder(Color.BLUE, 1);
        if(soldier.getArmyType()){
            if (soldier.getHealth() <= 0) {
                Field field = board.fields[soldier.getY_position()][soldier.getX_position()];
                if (field instanceof Grass) {
                    label.setIcon(grassIcon);
                    label.setBorder(null);
                } else if (field instanceof Mud) {
                    label.setIcon(mudIcon);
                    label.setBorder(null);
                }
            } else if (soldier instanceof Knight) {
                label.setIcon(knightIcon);
            } else if (soldier instanceof Archer) {
                label.setIcon(archerIcon);
            } else if (soldier instanceof Catapult) {
                label.setIcon(catapultIcon);
            } else if (soldier instanceof King) {
                label.setIcon(kingIcon);
            } else if (soldier instanceof Ram) {
                label.setIcon(ramIcon);
            } else if (soldier instanceof Medic) {
                label.setIcon(medicIcon);
            } else if (soldier instanceof Leader) {
                label.setIcon(leaderIcon);
            } else if (soldier instanceof Horseman) {
                label.setIcon(horsemanIcon);
            }
            label.setBorder(defenderBorder);
        }
        else{if (soldier.getHealth() <= 0) {
            Field field = board.fields[soldier.getY_position()][soldier.getX_position()];
            if (field instanceof Grass) {
                label.setIcon(grassIcon);
            } else if (field instanceof Mud) {
                label.setIcon(mudIcon);
            }
        } else if (soldier instanceof Knight) {
            label.setIcon(knightIcon);
        } else if (soldier instanceof Archer) {
            label.setIcon(archerIcon);
        } else if (soldier instanceof Catapult) {
            label.setIcon(catapultIcon);
        } else if (soldier instanceof King) {
            label.setIcon(kingIcon);
        } else if (soldier instanceof Ram) {
            label.setIcon(ramIcon);
        } else if (soldier instanceof Medic) {
            label.setIcon(medicIcon);
        } else if (soldier instanceof Leader) {
            label.setIcon(leaderIcon);
        } else if (soldier instanceof Horseman) {
            label.setIcon(horsemanIcon);
        }
            label.setBorder(attackerBorder);

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

        SwingWorker<Void, String> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                int time = 0;


                while (!(attacker.isEmpty()) && (defender.getAlive_soldiers().get(0) instanceof King)) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    for (int i = 0; i < attacker.getAlive_soldiers().size(); i++) {
                        Soldier a = attacker.getAlive_soldiers().get(i);
                        System.out.println(a.getArmyType());
                        if (a instanceof Catapult) {
                            if(!scanForWallOrGate(a)){
                                moveCatapultTowardsWall(a);
                            }

                        } else if (a instanceof Ram) {
                            if(!scanForWallOrGate(a)){
                                moveRamTowardsGate(a);
                            }

                        } else if(a instanceof Medic){
                            List<Soldier> allays = new ArrayList<>();
                            for(int j = 0;j<attacker.getAlive_soldiers().size();j++){
                                if(j!=i){
                                    allays.add(attacker.getAlive_soldiers().get(j));
                                }
                            }
                            if(!scanForEnemies(a,allays)){
                                moveSoldierTowardsEnemy(a, defender.getAlive_soldiers().get(0));
                            }
                        }
                        else{
                            if(!scanForEnemies(a,defender.getAlive_soldiers())){
                                moveSoldierTowardsEnemy(a, defender.getAlive_soldiers().get(0));
                            }
                        }

                    }
                    time+=1;
                    for (int i = 0; i < defender.getAlive_soldiers().size(); i++) {
                        Soldier a = defender.getAlive_soldiers().get(i);
                        System.out.println(a.getArmyType());
                        if(!scanForEnemies(a,attacker.getAlive_soldiers())){
                            moveSoldierTowardsEnemy(a, attacker.getAlive_soldiers().get(0));
                        }
                    }

                    System.out.println(attacker.getAlive_soldiers().size());
                    System.out.println(defender.getAlive_soldiers().size());


                    if(time==1){
                        for(int i = 0;i<board.getHeight();i++){
                            for(int j = 0;j<board.getWidth();j++){
                                if(board.fields[i][j]instanceof Gate){
                                    board.fields[i][j] = new Grass(i,j);
                                }
                            }
                        }
                    }
                    attacker.check_for_dead();
                    defender.check_for_dead();
                    System.out.println(defender.getAlive_soldiers().get(0).getHealth());
                    updateBoard();
                }
                return null;
            }
                @Override
                protected void done () {
                    startButton.setEnabled(true);
                }
            };
        startButton.setEnabled(false);
        worker.execute();
        }


    /**
     *
     * @param s1
     * @param s2
     * @return
     */
    private int calculateDistance(Soldier s1, Soldier s2) {
        return Math.abs(s1.getX_position() - s2.getX_position()) + Math.abs(s1.getY_position() - s2.getY_position());
    }

    /**
     *
     * @param soldier
     * @param enemies
     * @return
     */
    private boolean scanForEnemies(Soldier soldier,List<Soldier> enemies){
        for (Soldier enemy : enemies) {
            double distance = Math.sqrt(Math.pow(soldier.getX_position() - enemy.getX_position(), 2) +
                    Math.pow(soldier.getY_position() - enemy.getY_position(), 2));
            if (distance <= soldier.getTriggerRange()){
                if(distance<=soldier.getRange()){
                    soldier.attack(enemy);
                    return true;
                }

            }
        }
        return false;
    }

    /**
     *
     * @param mover
     * @param enemy
     */
    private void moveSoldierTowardsEnemy(Soldier mover, Soldier enemy) {
        int currentX = mover.getX_position();
        int currentY = mover.getY_position();
        int enemyX = enemy.getX_position();
        int enemyY = enemy.getY_position();
        int movementSpeed = mover.getMovement();
            for (int step = 0; step < movementSpeed; step++) {
                if(!enemy.getArmyType()){
                    if(scanForEnemies(mover,attacker.getAlive_soldiers())){
                        return;
                    }
                }else if(enemy.getArmyType()){
                    if(scanForEnemies(mover,defender.getAlive_soldiers())){
                        return;
                    }
                }

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


        updateBoard();
        }

    /**
     *
     * @param soldier
     * @return
     */
    private boolean scanForWallOrGate(Soldier soldier){
        if(soldier instanceof Catapult) {
            for (int i = 0; i < soldier.getRange(); i++) {
                Field field = board.fields[soldier.getY_position()][soldier.getX_position() - i];
                if (field instanceof Wall){
                    soldier.attack(field);
                    return true;
                }
            }
        }
        else if(soldier instanceof Ram){
            for (int i = 0; i < soldier.getRange(); i++) {
                Field field = board.fields[soldier.getY_position()][soldier.getX_position() - i];
                if (field instanceof Gate){
                    soldier.attack(field);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param catapult
     */
        private void moveCatapultTowardsWall(Soldier catapult){
            int currentX = catapult.getX_position();
            int currentY = catapult.getY_position();


            int k = 0;
            if(scanForWallOrGate(catapult)||currentX-catapult.getMovement()<0){
                return;
            }
            for(int j = 0;j<catapult.getMovement();j++){
                if(isValidMove(currentX-j,currentY)){
                    currentX = currentX-j;
                }
                else{
                    if(k==-1){
                        k=1;
                    }
                    else{
                        k=-1;
                    }
                    currentY=currentY+k;
                }
            }
            catapult.setX_position(currentX);
            catapult.setY_position(currentY);
            updateBoard();
        }

    /**
     *
     * @param catapult
     */
    private void moveRamTowardsGate(Soldier catapult){
        int currentX = catapult.getX_position();
        int currentY = catapult.getY_position();
        int k = 0;
        if(scanForWallOrGate(catapult)||currentX-catapult.getMovement()<0){
            return;
        }
            for(int j = 0;j<catapult.getMovement();j++){
                if(isValidMove(currentX-j,currentY)){
                    currentX = currentX-j;
                }
                else{
                    if(k==-1){
                        k=1;
                    }
                    else{
                        k=-1;
                    }
                    currentY=currentY+k;

                }
            }




        catapult.setX_position(currentX);
        catapult.setY_position(currentY);
        catapult.setMovement(catapult.getMovement()+1);
        updateBoard();
    }


    /**
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isValidMove(int x, int y) {
        return x >= 0 && y >= 0 && x < board.height && y < board.width &&
                !(board.fields[y][x] instanceof Wall) && !(board.fields[y][x] instanceof Rocks)&&!(board.fields[y][x] instanceof Gate)&&!isSoldier(x,y);
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isSoldier(int x, int y){
        for(int i = 0;i<defender.getAlive_soldiers().size();i++){
            Soldier a = defender.getAlive_soldiers().get(i);
            if(a.getX_position()==x&&a.getY_position()==y){
                return true;
            }
        }
        for(int i = 0;i<attacker.getAlive_soldiers().size();i++){
            Soldier a = attacker.getAlive_soldiers().get(i);
            if(a.getX_position()==x&&a.getY_position()==y){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param fileName
     * @return
     */
    private ImageIcon loadImageIcon(String fileName) {
        URL imageURL = getClass().getResource(fileName);
        if (imageURL != null) {            return new ImageIcon(imageURL);
        } else {
            throw new RuntimeException("Image file not found: " + fileName);
        }
    }
    private void getCountOfSoldier(){
        int knightsA = 0;
        int knightsD = 0;
        int archersA = 0;
        int archersD = 0;
        int horsemanA = 0;
        int horsemanD=0;
        int medicA = 0;
        int medicD = 0;
        int catapult = 0;
        int ram = 0;
        int leaderA = 0;
        int leaderD = 0;
        for(int i = 0;i<defender.getAlive_soldiers().size();i++){
            if(defender.getAlive_soldiers().get(i)instanceof Knight){
                knightsD++;
            }
            else if(defender.getAlive_soldiers().get(i)instanceof Archer){
                archersD++;
            }
            else if(defender.getAlive_soldiers().get(i)instanceof Leader){
                leaderD++;
            }
            else if(defender.getAlive_soldiers().get(i)instanceof Medic){
                medicD++;
            }
            else if(defender.getAlive_soldiers().get(i)instanceof Horseman){
                horsemanD++;
            }
        }
        for(int i = 0;i<attacker.getAlive_soldiers().size();i++){
            if(attacker.getAlive_soldiers().get(i)instanceof Knight){
                knightsD++;
            }
            else if(attacker.getAlive_soldiers().get(i)instanceof Archer){
                archersD++;
            }
            else if(attacker.getAlive_soldiers().get(i)instanceof Leader){
                leaderD++;
            }
            else if(attacker.getAlive_soldiers().get(i)instanceof Medic){
                medicD++;
            }
            else if(attacker.getAlive_soldiers().get(i)instanceof Horseman){
                horsemanD++;
            } else if (attacker.getAlive_soldiers().get(i) instanceof Catapult) {
                catapult++;
            }
            else if (attacker.getAlive_soldiers().get(i) instanceof Ram) {
                ram++;
            }
        }

    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {

        /*SimulationGUI simulationGUI = new SimulationGUI();
        Army defender = new Army(true,50,8,simulationGUI.board);
        for(int i = 0;i<defender.getAlive_soldiers().size();i++){
            if(defender.getAlive_soldiers().get(i)instanceof King){
                System.out.println(i);
            }
        }*/




        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SimulationGUI();
            }

        });


    }
}
