import java.util.*;

import static java.lang.Math.sqrt;

public class Simulation {
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

    protected void setHealth(int newHealth) {
        this.health = newHealth;
    }
    protected void setPower(int newPower) {
        this.power = newPower;
    }
    protected void setMovement(int newMovement) {
        this.movement = newMovement;
    }
    protected void setDefender(boolean defender){
        this.defender = defender;
    }
    protected void setRange(int newRange) {
        this.range = newRange;
    }
    protected void setX_position(int newX_position) {
        this.x_position = newX_position;
    }
    protected void setY_position(int newY_position) {
        this.y_position = newY_position;
    }
    protected int getHealth() {
        return this.health;
    }
    protected int getPower() {
        return this.power;
    }
    protected int getMovement() {
        return this.movement;
    }
    protected int getRange() {
        return this.range;
    }
    protected boolean getArmyType(){
        return this.defender;
    }
    protected int getX_position() {
        return this.x_position;
    }
    protected int getY_position() {
        return this.y_position;
    }
    protected boolean isAlive() {
        return this.health > 0;
    }

    public void attack(Soldier target) {}
}

class Knight extends Soldier implements AttackCommand{
    public Knight(int x, int y) {
        this.setPower(27);
        this.setHealth(130);
        this.setMovement(5);
        this.setRange(2);
        this.setX_position(x);
        this.setY_position(y);
    }
    @Override
    public void attack(Soldier enemy) {
        enemy.setHealth(enemy.getHealth()-this.getPower());
    }
}
class King extends Soldier implements AttackCommand{


    public King(Board board) {
        this.setPower(15);
        this.setHealth(300);
        this.setMovement(0);
        this.setRange(2);
        this.setX_position(0);
        this.setY_position(board.getHeight()/2);
    }
    @Override
    public void attack(Soldier enemy) {
        enemy.setHealth(enemy.getHealth()-this.getPower());
    }
}
class Ram extends Soldier{
    public Ram(int x, int y) {
        this.setPower(2*getMovement());
        this.setHealth(250);
        this.setMovement(3);
        this.setRange(2);
        this.setX_position(x);
        this.setY_position(y);
        this.setDefender(false);
    }

    public void attack(Gate enemy) {
        enemy.setHealth(enemy.getHealth()-this.getPower());
    }
}
class Catapult extends Soldier{
    public Catapult(int x, int y) {
        this.setPower(150);
        this.setHealth(250);
        this.setMovement(2);
        this.setRange(15);
        this.setX_position(x);
        this.setY_position(y);
        this.setDefender(false);
    }

    public void attack(Wall enemy) {
        enemy.setHealth(enemy.getHealth()-this.getPower());
    }
}
class Archer extends Soldier implements AttackCommand{
    public Archer(int x, int y) {
        this.setPower(17);
        this.setHealth(100);
        this.setMovement(6);
        this.setRange(10);
        this.setX_position(x);
        this.setY_position(y);
    }
    @Override
    public void attack(Soldier enemy) {
        enemy.setHealth(enemy.getHealth()-this.getPower());
    }

}
class Leader extends Soldier implements AttackCommand{
    private int auraRange;
    public Leader(int x,int y,int auraRange){
        this.setPower(50);
        this.setHealth(200);
        this.setMovement(5);
        this.setRange(2);
        this.setX_position(x);
        this.setY_position(y);
        this.auraRange = auraRange;
    }
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
        this.setX_position(x);
        this.setY_position(y);
    }
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
        this.setRange(3);
        this.setX_position(x);
        this.setY_position(y);
    }
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
    protected void setBlocks(boolean blocks) {
        this.blocks = blocks;
    }
    protected void setX_position(int x_position) {
        this.x_position = x_position;
    }
    protected void setY_position(int y_position) {
        this.y_position = y_position;
    }
    protected void setMovement_modifier(double modifier) {
        this.movement_modifier = modifier;
    }
    protected int getX_position() {
        return this.x_position;
    }
    protected int getY_position() {
        return this.y_position;
    }
    protected boolean getBlocks() {
        return blocks;
    }
    protected double getMovement_modifier() {
        return this.movement_modifier;
    }
}

class Rocks extends Field {
    Rocks(int x, int y) {
        this.setBlocks(true);
        this.setX_position(x);
        this.setY_position(y);
        this.setMovement_modifier(0);
    }
}
class Mud extends Field {
    Mud(int x, int y) {
        this.setBlocks(false);
        this.setX_position(x);
        this.setY_position(y);
        this.setMovement_modifier(0.5);
    }
}
class Grass extends Field{
    Grass(int x,int y){
        this.setBlocks(false);
        this.setX_position(x);
        this.setY_position(y);
        this.setMovement_modifier(1);
    }
}
class Gate extends Field{
    private int health;

    Gate(int x,int y){
        this.setBlocks(true);
        this.setX_position(x);
        this.setY_position(y);
        this.setMovement_modifier(0);
        this.setHealth(500);
    }
    public void setHealth(int health){
        this.health = health;
    }
    public int getHealth(){
        return this.health;
    }
}
class Wall extends Field{
    private int health;

    Wall(int x,int y){
        this.setBlocks(true);
        this.setX_position(x);
        this.setY_position(y);
        this.setMovement_modifier(0);
        this.setHealth(1500);
    }
    public void setHealth(int health){
        this.health = health;
    }
    public int getHealth(){
        return this.health;
    }
}
abstract class Board{
    protected int width;
    protected int height;
    protected int iterations;
    protected int type;
    protected Field[][] fields;

    public Board(int width,int height,int iterations,int type){
        this.height=height;
        this.width=width;
        this.iterations=iterations;
        this.type=type;
        this.fields = new Field[width][height];
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }

    public Field[][] boardType(int type,int width,int height){
        if(width<100||height<100){
            System.out.println("Minimalne wymiary planszy to 100x100");
            return null;
        }

        switch (type) {
            case 1:
                return boardType1(type,width,height);

            case 2:
                return boardType1(type,width,height);

            case 3:
                return boardType1(type,width,height);

            default:
                System.out.println("Podaj numer od 1 do 3");
                return null;

        }
    }
    public Field[][] initialize(int type,int width,int height){
        for(int i = 0;i<height;i++){
            for(int j = 0;j<width;j++){
                fields[i][j] = new Grass(i,j);
            }
        }
        return fields;
    }
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
        return fields;
    }
}

class MovementLogic {
    public static void move_attacker(Soldier unit, Field[][] board) {

        // Decide next move based on relative position of unit B
        if (unit.getX_position() < Army.defenders_average_position[0] && !(board[unit.getX_position() + 1][unit.getY_position()]instanceof Rocks)) {
            unit.x_position++;
        } else if (unit.getX_position() > Army.defenders_average_position[0] && !(board[unit.getX_position() - 1][unit.getY_position()]instanceof Rocks)) {
            unit.x_position--;
        } else if (unit.getY_position() < Army.defenders_average_position[1] && !(board[unit.getX_position()][unit.getY_position() + 1]instanceof Rocks)) {
            unit.y_position++;
        } else if (unit.getY_position() > Army.defenders_average_position[1] && !(board[unit.getX_position()][unit.getY_position() - 1]instanceof Rocks )) {
            unit.y_position--;
        }
    }

    public static boolean check_for_enemies(Soldier unit, Army army) {
        for (int i = 0; i != army.getAlive_soldiers().size(); i++) {
            if (unit.getRange() >= sqrt((army.getAlive_soldiers().get(i).x_position - unit.x_position)*(army.getAlive_soldiers().get(i).x_position - unit.x_position) + (army.getAlive_soldiers().get(i).y_position - unit.y_position)*(army.getAlive_soldiers().get(i).y_position - unit.y_position))) {
                unit.attack(army.getAlive_soldiers().get(i));
                army.check_for_dead();
                return true;
            }
        }
        return false;
    }
}

class Army {
    private List<Soldier> alive_soldiers;
    private boolean defenders;
    public static int[] defenders_average_position;

    Army(boolean defenders) {
        this.defenders = defenders;
        alive_soldiers = new ArrayList<>();
    }

    public List<Soldier> getAlive_soldiers() {
        return this.alive_soldiers;
    }

    public void add_soldier(Soldier unit) {
        this.alive_soldiers.add(unit);
    }

    public void check_for_dead() {
        Iterator<Soldier> iterator = this.alive_soldiers.iterator();

        while(iterator.hasNext()) {
            if (!iterator.next().isAlive()) {
                iterator.remove();
            }
        }

    }

    public void check_army() {
        for (int i = 0; i != this.alive_soldiers.size(); i++) {
            System.out.println("Soldier " + i);
        }
    }

    public static void find_defenders(Army defenders) {
        defenders_average_position = new int[2];
        int x = 0, y = 0;
        for (int i = 0; i != defenders.alive_soldiers.size(); i++) {
            x = defenders.alive_soldiers.get(i).x_position;
            y = defenders.alive_soldiers.get(i).y_position;
        }
        defenders_average_position[0] = x/defenders.alive_soldiers.size();
        defenders_average_position[1] = y/defenders.alive_soldiers.size();
    }

}

