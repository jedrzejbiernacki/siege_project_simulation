public class Simulation {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}

abstract class Soldier {
    private int health;
    private int power;
    private int movement;
    private int range;

    private int x_position;
    private int y_position;
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
class Ram extends Soldier{
    public Ram(int x, int y) {
        this.setPower(50+50*getMovement());
        this.setHealth(250);
        //tu trzeba zeby sie rozpedzal
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

    Gate(int x,int y,int health){
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

    Wall(int x,int y,int health){
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
