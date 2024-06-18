import java.util.*;

import static java.lang.Math.sqrt;



public class Simulation {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        Horseman a = new Horseman(1,1); //Soldier 1
        Archer b = new Archer(2,2); //Soldier 2

//        while(true) {
//            a.attack(b);
//
//            if(b.getHealth() < 0) {
//                System.out.println("Soldier 2 is dead.");
//                break;
//            }
//            b.attack(a);
//            if (a.getHealth() < 0) {
//                System.out.println("Soldier 1 is dead.");
//                break;
//            }
//        }
        boolean over = false;
        while(true) {

            for (int i = 15; i != 0; i--) {
                if (a.getRange() >= i) {
                    a.attack(b);
                    if(b.getHealth() < 0) {
                        System.out.println("Soldier 2 is dead.");
                        over = true;
                        break;
                    }
                }
                if (b.getRange() >= i) {
                    b.attack(a);
                    if (a.getHealth() < 0) {
                        System.out.println("Soldier 1 is dead.");
                        over = true;
                        break;
                    }
                }
            }
            if(over) break;
        }
    }
}


abstract class Soldier {
    private int health;
    private int power;
    private int movement;
    private int range;

    public int x_position;
    public int y_position;
    private boolean defender;
    private int triggerRange;

    /**
     *
     * @param newHealth
     */
    protected void setHealth(int newHealth) {
        this.health = newHealth;
    }

    /**
     *
     * @param newPower
     */
    protected void setPower(int newPower) {
        this.power = newPower;
    }

    /**
     *
     * @param newMovement
     */
    protected void setMovement(int newMovement) {
        this.movement = newMovement;
    }

    /**
     *
     * @param defender
     */
    protected void setDefender(boolean defender){
        this.defender = defender;
    }

    /**
     *
     * @param newRange
     */
    protected void setRange(int newRange) {
        this.range = newRange;
    }

    /**
     *
     * @param newX_position
     */
    protected void setX_position(int newX_position) {
        this.x_position = newX_position;
    }

    /**
     *
     * @param newY_position
     */
    protected void setY_position(int newY_position) {
        this.y_position = newY_position;
    }

    /**
     *
     * @return
     */
    protected int getHealth() {
        return this.health;
    }

    /**
     *
     * @return
     */
    protected int getPower() {
        return this.power;
    }

    /**
     *
     * @return
     */
    protected int getMovement() {
        return this.movement;
    }

    /**
     *
     * @return
     */
    protected int getRange() {
        return this.range;
    }

    /**
     *
     * @return
     */
    protected boolean getArmyType(){
        return this.defender;
    }

    /**
     *
     * @return
     */
    protected int getX_position() {
        return this.x_position;
    }

    /**
     *
     * @return
     */
    protected int getY_position() {
        return this.y_position;
    }

    /**
     *
     * @return
     */
    protected boolean isAlive() {
        return this.health > 0;
    }

    /**
     *
     * @param target
     */

    public void attack(Soldier target) {

    }

    /**
     *
     * @param target
     */
    public void attack(Field target){

    }

    /**
     *
     * @return
     */
    public int getTriggerRange(){
        return this.triggerRange;
    }

    /**
     *
     * @param triggerRange
     */
    public void setTriggerRange(int triggerRange){
        this.triggerRange = triggerRange;
    }
}

class Knight extends Soldier implements AttackCommand{
    public Knight(int x, int y) {
        this.setPower(27);
        this.setHealth(130);
        this.setMovement(5);
        this.setRange(4);
        this.setX_position(y);
        this.setY_position(x);
        this.setTriggerRange(25);
    }

    /**
     *
     * @param enemy
     */
    @Override
    public void attack(Soldier enemy) {
        enemy.setHealth(enemy.getHealth()-this.getPower());
    }
}
class King extends Soldier implements AttackCommand{


    public King(Board board) {
        this.setPower(50);
        this.setHealth(800);
        this.setMovement(0);
        this.setRange(2);
        this.setX_position(0);
        this.setY_position(board.getHeight()/2);
    }

    /**
     *
     * @param enemy
     */
    @Override
    public void attack(Soldier enemy) {
        enemy.setHealth(enemy.getHealth()-this.getPower());
    }
}
class Ram extends Soldier{
    public Ram(int x, int y) {
        this.setPower(2*getMovement());
        this.setHealth(250);
        this.setMovement(2);
        this.setRange(2);
        this.setX_position(y);
        this.setY_position(x);
        this.setDefender(false);
    }



    public void increase_movement() {
        this.setMovement(this.getMovement()+2);
    }

    /**
     *
     * @param enemy
     */
    public void attack(Gate enemy) {
        enemy.setHealth(enemy.getHealth()-this.getPower());
    }
}
class Catapult extends Soldier{
    public Catapult(int x, int y) {
        this.setPower(400);
        this.setHealth(250);
        this.setMovement(4);
        this.setRange(15);
        this.setX_position(y);
        this.setY_position(x);
        this.setDefender(false);
    }

    /**
     *
     * @param enemy
     */
    public void attack(Wall enemy) {
        enemy.setHealth(enemy.getHealth()-this.getPower());
    }
}
class Archer extends Soldier implements AttackCommand{
    public Archer(int x, int y) {
        this.setPower(17);
        this.setHealth(100);
        this.setMovement(6);
        this.setRange(15);
        this.setX_position(y);
        this.setY_position(x);
        this.setTriggerRange(25);
    }

    /**
     *
     * @param enemy
     */
    @Override
    public void attack(Soldier enemy) {
        enemy.setHealth(enemy.getHealth()-this.getPower());
    }

}
class Leader extends Soldier implements AttackCommand{
    private int auraRange;
    public Leader(int x,int y){
        this.setPower(50);
        this.setHealth(200);
        this.setMovement(5);
        this.setRange(2);
        this.setX_position(y);
        this.setY_position(x);
        this.auraRange = 5;
        this.setTriggerRange(25);
    }

    /**
     *
     * @param enemy
     */
    @Override
    public void attack(Soldier enemy) {
        enemy.setHealth(enemy.getHealth()-this.getPower());
    }
}
class Medic extends Soldier implements AttackCommand{
    public Medic(int x,int y){
        this.setPower(-20);
        this.setHealth(80);
        this.setMovement(6);
        this.setRange(3);
        this.setX_position(y);
        this.setY_position(x);
        this.setTriggerRange(25);
    }

    /**
     *
     * @param ally
     */
    @Override
    public void attack(Soldier ally) {
        ally.setHealth(ally.getHealth()-this.getPower());
    }
}
class Horseman extends Soldier implements AttackCommand{
    public Horseman(int x, int y) {
        this.setPower(37);
        this.setHealth(150);
        this.setMovement(10);
        this.setRange(6);
        this.setX_position(y);
        this.setY_position(x);
        this.setTriggerRange(25);
    }

    /**
     *
     * @param enemy
     */
    @Override
    public void attack(Soldier enemy) {
        enemy.setHealth(enemy.getHealth()-this.getPower());
    }
}


abstract class Field {
    private boolean blocks;
    private int x_position;
    private int y_position;
    private double movement_modifier;

    /**
     *
     * @param blocks
     */
    protected void setBlocks(boolean blocks) {
        this.blocks = blocks;
    }

    /**
     *
     * @param x_position
     */
    protected void setX_position(int x_position) {
        this.x_position = x_position;
    }

    /**
     *
     * @param y_position
     */
    protected void setY_position(int y_position) {
        this.y_position = y_position;
    }

    /**
     *
     * @param modifier
     */
    protected void setMovement_modifier(double modifier) {
        this.movement_modifier = modifier;
    }

    /**
     *
     * @return
     */
    protected int getX_position() {
        return this.x_position;
    }

    /**
     *
     * @return
     */
    protected int getY_position() {
        return this.y_position;
    }

    /**
     *
     * @return
     */
    protected boolean getBlocks() {
        return blocks;
    }

    /**
     *
     * @return
     */
    protected double getMovement_modifier() {
        return this.movement_modifier;
    }
}

class Rocks extends Field {
    /**
     *
     * @param x
     * @param y
     */
    Rocks(int x, int y) {
        this.setBlocks(true);
        this.setX_position(x);
        this.setY_position(y);
        this.setMovement_modifier(0);
    }
}
class Mud extends Field {
    /**
     *
     * @param x
     * @param y
     */
    Mud(int x, int y) {
        this.setBlocks(false);
        this.setX_position(x);
        this.setY_position(y);
        this.setMovement_modifier(0.5);
    }
}
class Grass extends Field{
    /**
     *
     * @param x
     * @param y
     */
    Grass(int x,int y){
        this.setBlocks(false);
        this.setX_position(x);
        this.setY_position(y);
        this.setMovement_modifier(1);
    }
}
class Gate extends Field{
    private int health;

    /**
     *
     * @param x
     * @param y
     */
    Gate(int x,int y){
        this.setBlocks(true);
        this.setX_position(x);
        this.setY_position(y);
        this.setMovement_modifier(0);
        this.setHealth(500);
    }

    /**
     *
     * @param health
     */
    public void setHealth(int health){
        this.health = health;
    }

    /**
     *
     * @return
     */
    public int getHealth(){
        return this.health;
    }
}
class Wall extends Field{
    private int health;

    /**
     *
     * @param x
     * @param y
     */
    Wall(int x,int y){
        this.setBlocks(true);
        this.setX_position(x);
        this.setY_position(y);
        this.setMovement_modifier(0);
        this.setHealth(1500);
    }

    /**
     *
     * @param health
     */
    public void setHealth(int health){
        this.health = health;
    }

    /**
     *
     * @return
     */
    public int getHealth(){
        return this.health;
    }
}
abstract class Board{
    public static  int width;
    public static int height;
    protected int iterations;
    protected int type;
    protected Field[][] fields;

    /**
     *
     * @param width
     * @param height
     * @param iterations
     * @param type
     */
    public Board(int width,int height,int iterations,int type){
        this.height=height;
        this.width=width;
        this.iterations=iterations;
        this.type=type;
        this.fields = new Field[width][height];
    }

    /**
     *
     * @return
     */
    public int getWidth(){
        return width;
    }

    /**
     *
     * @return
     */
    public int getHeight(){
        return height;
    }

    /**
     *
     * @param type
     * @param width
     * @param height
     * @return
     */
    public Field[][] boardType(int type,int width,int height){
        if(width<100||height<100){
            System.out.println("Minimalne wymiary planszy to 100x100");
            return null;
        }

        switch (type) {
            case 1:
                return boardType1(type,width,height);

            case 2:
                return boardType2(type,width,height);

            case 3:
                return boardType3(type,width,height);

            default:
                System.out.println("Podaj numer od 1 do 3");
                return null;

        }
    }

    /**
     *
     * @param type
     * @param width
     * @param height
     * @return
     */
    public Field[][] initialize(int type,int width,int height){
        for(int i = 0;i<height;i++){
            for(int j = 0;j<width;j++){
                fields[i][j] = new Grass(i,j);
            }
        }
        return fields;
    }

    /**
     *
     * @param type
     * @param width
     * @param height
     * @return
     */
    public Field[][] boardType1(int type,int width,int height){
        fields = initialize(type,width,height);
        Random rand = new Random();
        for (int i = 0; i < width / 4; i++) {
            fields[height / 4][i] = new Wall(height/4,i);
            fields[3 * height / 4][i] = new Wall(height*3/4,i);
        }
        for(int i = height/8+1;i<height/4;i++){
            fields[i][width/4] = new Wall(i,width/4);
            fields[i+5*height/8][width/4] = new Wall(i+5*height/8,width/4);
        }
        for(int i = width/4;i<width*5/12;i++){
            fields[height / 8][i] = new Wall(height/8,i);
            fields[7*height/8][i] = new Wall(height*7/8,i);
        }
        for(int i = height/8+1;i<height/4;i++){
            fields[i][width*5/12] = new Wall(i,width*5/12);
            fields[i+5*height/8][width*5/12] = new Wall(i+height*5/8,width*5/12);
        }
        for(int i = width/4;i<width*5/12;i++){
            fields[height / 4][i] = new Wall(height/4,i);
            fields[3 * height / 4][i] = new Wall(height*3/4,i);
        }
        for(int i = height/4;i<height*3/4;i++){
            fields[i][width/4] = new Wall(i,width/4);
        }
        fields[height/2][width/4] = new Gate(height/2,width/4);
        fields[height/2-1][width/4] = new Gate(height/2-1,width/4);
        for(int i = 0;i<width*height/75;i++){
            int y = rand.nextInt(width*5/12+2,width-3);
            int x = rand.nextInt(1,height-1);
            if(!(fields[x+1][y]instanceof Rocks||fields[x-1][y]instanceof Rocks||fields[x][y-1]instanceof Rocks||fields[x][y+1]instanceof Rocks||fields[x+1][y+1]instanceof Rocks||fields[x+1][y-1]instanceof Rocks||fields[x-1][y-1]instanceof Rocks||fields[x-1][y+1]instanceof Rocks)){
                fields[x][y] = new Rocks(x,y);
            }
        }
        for(int i = 0;i<width*height/50;i++){
            int y = rand.nextInt(width*5/12+2,width-3);
            int x = rand.nextInt(1,height-1);
            if(!(fields[x][y]instanceof Rocks)) fields[x][y] = new Mud(x,y);
        }

        return fields;
    }

    /**
     *
     * @param type
     * @param width
     * @param height
     * @return
     */
    public Field[][] boardType2(int type,int width,int height){
        fields = initialize(type,width,height);
        Random rand = new Random();

        for (int i = 0; i < width / 4+1; i++) {
            fields[height / 4][i] = new Wall(height/4,i);
            fields[3 * height / 4][i] = new Wall(height*3/4,i);
            fields[height/4-1][i] = new Mud(height/4-1,i);
            fields[height/4-2][i]=new Mud(height/4-2,i);
            fields[height/4-3][i] = new Mud(height/4-3,i);
            fields[3*height/4+1][i] = new Mud(3*height/4+1,i);
            fields[3*height/4+2][i]=new Mud(3*height/4+2,i);
            fields[3*height/4+3][i] = new Mud(3*height/4+3,i);
        }
        for(int i = height/4;i<height*3/4+1;i++){
            int j = i-3;
            int k = i+3;
            fields[i][width/4] = new Wall(i,width/4);
            fields[i][width/4+1] = new Mud(i,width/4+1);
            fields[i][width/4+2] = new Mud(i,width/4+2);
            fields[i][width/4+3] = new Mud(i,width/4+3);
            fields[j][width/4+1] = new Mud(j,width/4+1);
            fields[j][width/4+2] = new Mud(j,width/4+2);
            fields[j][width/4+3] = new Mud(j,width/4+3);
            fields[k][width/4+1] = new Mud(k,width/4+1);
            fields[k][width/4+2] = new Mud(k,width/4+2);
            fields[k][width/4+3] = new Mud(k,width/4+3);
        }
        fields[height/2][width/4] = new Gate(height/2,width/4);
        fields[height/2-1][width/4] = new Gate(height/2-1,width/4);
        for(int i = 0;i<width*height/25;i++){
            int y = rand.nextInt(width/4+4,width-3);
            int x = rand.nextInt(1,height-1);
            if(!(fields[x+1][y]instanceof Rocks||fields[x-1][y]instanceof Rocks||fields[x][y-1]instanceof Rocks||fields[x][y+1]instanceof Rocks||fields[x+1][y+1]instanceof Rocks||fields[x+1][y-1]instanceof Rocks||fields[x-1][y-1]instanceof Rocks||fields[x-1][y+1]instanceof Rocks)){
                fields[x][y] = new Rocks(x,y);
            }

        }
        for(int i = 0;i<width*height/150;i++){
            int y = rand.nextInt(width/4+4,width-3);
            int x = rand.nextInt(1,height-1);
            if(!(fields[x][y]instanceof Rocks)) fields[x][y] = new Mud(x,y);
        }
        return fields;
    }

    /**
     *
     * @param type
     * @param width
     * @param height
     * @return
     */
    public Field[][] boardType3(int type,int width,int height) {
        fields = initialize(type, width, height);
        Random rand = new Random();

        for (int i = 0; i < width / 4 + 1; i++) {
            fields[height / 4][i] = new Wall(height / 4, i);
            fields[3 * height / 4][i] = new Wall(height * 3 / 4, i);
        }
        for (int i = height / 4; i < height * 3 / 4 + 1; i++) {
            int j = i - 3;
            int k = i + 3;
            fields[i][width / 4] = new Wall(i, width / 4);

        }
        fields[height / 2][width / 4] = new Gate(height / 2, width / 4);
        fields[height / 2 - 1][width / 4] = new Gate(height / 2 - 1, width / 4);
        for (int i = 0; i < width * height / 50; i++) {
            int y = rand.nextInt(width / 4 + 4, width - 3);
            int x = rand.nextInt(1, height - 1);
            if (!(fields[x + 1][y] instanceof Rocks || fields[x - 1][y] instanceof Rocks || fields[x][y - 1] instanceof Rocks || fields[x][y + 1] instanceof Rocks || fields[x + 1][y + 1] instanceof Rocks || fields[x + 1][y - 1] instanceof Rocks || fields[x - 1][y - 1] instanceof Rocks || fields[x - 1][y + 1] instanceof Rocks)) {
                fields[x][y] = new Rocks(x, y);
            }
        }
        for(int i = 0;i<width*height/10;i++){
            int y = rand.nextInt(width/4+4,width-3);
            int x = rand.nextInt(1,height-1);
            if(!(fields[x][y]instanceof Rocks)) fields[x][y] = new Mud(x,y);
        }
        fields[height/4][2] = new Grass(height/4,2);
        return fields;
    }
}


class Army {
    private List<Soldier> alive_soldiers;
    int medicCountA = 0;
    int medicCountD=0;

    /**
     *
     * @param defenders
     * @param number
     * @param strength
     * @param board
     */
    Army(boolean defenders, int number, int strength, Board board) {

        if (strength > 10 || strength < 1) {
            System.out.println("Strength number should be between 1 and 10!");
            System.exit(0);
        }
        this.alive_soldiers = new ArrayList<>();
        Random rand = new Random();
        if (!defenders) {
            for (int i = 0; i != number; i++) {
                int roll = rand.nextInt(100);
                int threshold1 = 60 + strength;
                int threshold2 = threshold1 + 20 - strength;
                int threshold3 = threshold2 + 10 - strength / 2;
                int threshold4 = threshold3 + 5 - strength / 4; //Okreslanie sily tworzonej jednostki na podstawie zmiennej strength

                if (i == 0) {
                    if (strength < 3) {

                    } else if (strength < 5) {
                        int y = (int) Math.floor( Math.random() * (100-90) + 90 );
                        int x = board.getHeight()/2+5;
                        Soldier a = new Catapult(x, y);
                        this.alive_soldiers.add(a);
                    } else if (strength < 7) {
                        int y = (int) Math.floor( Math.random() * (100-90) + 90 );
                        int x = board.getHeight()/2+5;
                        Soldier a = new Catapult(x, y);
                        y = (int) Math.floor( Math.random() * (100-90) + 90 );
                        x = board.getHeight()/2;
                        Soldier b = new Ram(x, y);
                        this.alive_soldiers.add(a);
                        this.alive_soldiers.add(b);
                    } else if (strength < 10) {
                        int y = (int) Math.floor( Math.random() * (100-90) + 90 );
                        int x = board.getHeight()/2+5;
                        Soldier a = new Catapult(x, y);
                        y = (int) Math.floor( Math.random() * (100-90) + 90 );
                        x = board.getHeight()/2;
                        Soldier b = new Ram(x, y);
                        y = (int) Math.floor( Math.random() * (100-90) + 90 );
                        x = board.getHeight()/2-5;
                        Soldier c = new Catapult(x, y);
                        this.alive_soldiers.add(a);
                        this.alive_soldiers.add(b);
                        this.alive_soldiers.add(c);
                    } else {
                        int y = (int) Math.floor( Math.random() * (100-90) + 90 );
                        int x = board.getHeight()/2+5;
                        Soldier a = new Catapult(x, y);
                        y = (int) Math.floor( Math.random() * (100-90) + 90 );
                        x = board.getHeight()/2;
                        Soldier b = new Ram(x, y);
                        y = (int) Math.floor( Math.random() * (100-90) + 90 );
                        x = board.getHeight()/2-5;
                        Soldier c = new Catapult(x, y);
                        y = (int) Math.floor( Math.random() * (100-90) + 90 );
                        x = board.getHeight()/2+8;
                        Soldier d = new Catapult(x, y);
                        this.alive_soldiers.add(a);
                        this.alive_soldiers.add(b);
                        this.alive_soldiers.add(c);
                        this.alive_soldiers.add(d);
                    }
                }

                if (roll < threshold1) {
                    int y = (int) Math.floor( Math.random() * (100-90) + 90 );
                    int x = (int) Math.floor( Math.random() * (100-0) + 0 );
                    Soldier a = new Knight(x, y);
                    alive_soldiers.add(a);
                } else if (roll < threshold2) {
                    int y = (int) Math.floor( Math.random() * (100-90) + 90 );
                    int x = (int) Math.floor( Math.random() * (100-0) + 0 );
                    Soldier a = new Archer(x, y);
                    alive_soldiers.add(a);
                } else if (roll < threshold3) {
                    int y = (int) Math.floor( Math.random() * (100-90) + 90 );
                    int x = (int) Math.floor( Math.random() * (100-0) + 0 );
                    Soldier a = new Horseman(x, y);
                    alive_soldiers.add(a);
                } else if (roll < threshold4&&medicCountA!=1) {
                    int y = (int) Math.floor( Math.random() * (100-90) + 90 );
                    int x = (int) Math.floor( Math.random() * (100-0) + 0 );
                    Soldier a = new Medic(x, y);
                    medicCountA++;
                    alive_soldiers.add(a);
                } else {
                    int y = (int) Math.floor( Math.random() * (100-90) + 90 );
                    int x = (int) Math.floor( Math.random() * (100-0) + 0 );
                    Soldier a = new Leader(x, y);
                    alive_soldiers.add(a);
                }
            }
            for(int i = 0;i<alive_soldiers.size();i++){
                alive_soldiers.get(i).setDefender(false);
            }
        } else {
            for (int i = 0; i != number; i++) {
                int roll = rand.nextInt(100);
                int threshold1 = 60 + strength;
                int threshold2 = threshold1 + 20 - strength;
                int threshold3 = threshold2 + 10 - strength / 2;
                int threshold4 = threshold3 + 5 - strength / 4; //Okreslanie sily tworzonej jednostki na podstawie zmiennej strength

                if(i == 0) {
                    Soldier king = new King(board);
                    this.alive_soldiers.add(king);
                }


                if (roll < threshold1) {
                    int y = rand.nextInt(20);
                    int x = rand.nextInt(3*board.getHeight()/4+1 - board.getHeight()/4)+ board.getHeight()/4;;
                    Soldier a = new Knight(x, y);
                    alive_soldiers.add(a);
                } else if (roll < threshold2) {
                    int y = rand.nextInt(20);
                    int x = rand.nextInt(3*board.getHeight()/4+1 - board.getHeight()/4)+ board.getHeight()/4;;
                    Soldier a = new Archer(x, y);
                    alive_soldiers.add(a);
                } else if (roll < threshold3) {
                    int y = rand.nextInt(20);
                    int x = rand.nextInt(3*board.getHeight()/4+1 - board.getHeight()/4)+ board.getHeight()/4;;
                    Soldier a = new Horseman(x, y);
                    alive_soldiers.add(a);
                } else if (roll < threshold4&&medicCountD!=1) {
                    int y = rand.nextInt(20);
                    int x = rand.nextInt(3*board.getHeight()/4+1 - board.getHeight()/4)+ board.getHeight()/4;;
                    Soldier a = new Medic(x, y);
                    medicCountD++;
                    alive_soldiers.add(a);

                } else {
                    int y = rand.nextInt(20);
                    int x = rand.nextInt(3*board.getHeight()/4+1 - board.getHeight()/4)+ board.getHeight()/4;;
                    Soldier a = new Leader(x, y);
                    alive_soldiers.add(a);
                }
            }
            for(int i = 0;i<alive_soldiers.size();i++){
                alive_soldiers.get(i).setDefender(true);
            }
        }
    }

    /**
     *
     * @return
     */
    public List<Soldier> getAlive_soldiers() {
        return this.alive_soldiers;
    }


    public void check_for_dead() {
        this.alive_soldiers.removeIf(soldier -> !soldier.isAlive());
    }


    public void check_army() {
        for (int i = 0; i != this.alive_soldiers.size(); i++) {
            System.out.println("Soldier " + i); //Debug tool
        }
    }


    /**
     *
     * @return
     */
    public boolean isEmpty(){
        if(alive_soldiers.isEmpty()){
            return true;
        }
        return false;
    }


}

