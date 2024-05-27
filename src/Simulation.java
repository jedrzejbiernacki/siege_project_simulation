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

    protected void setHealth(int newHealth) {
        this.health = newHealth;
    }
    protected void setPower(int newPower) {
        this.power = newPower;
    }
    protected void setMovement(int newMovement) {
        this.movement = newMovement;
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
    protected int getX_position() {
        return this.x_position;
    }
    protected int getY_position() {
        return this.y_position;
    }
}

class Knight extends Soldier{
    public Knight() {
        this.setPower(27);
        this.setHealth(130);
        this.setMovement(5);
        this.setRange(2);
    }

    public void attack(Soldier enemy) {
        enemy.setHealth(enemy.getHealth()-this.getPower());
    }
}
class Archer extends Soldier{
    public Archer() {
        this.setPower(17);
        this.setHealth(100);
        this.setMovement(6);
        this.setRange(10);
    }
    public void attack(Soldier enemy) {
        enemy.setHealth(enemy.getHealth()-this.getPower());
    }

}
class Horseman extends Soldier{
    public Horseman() {
        this.setPower(37);
        this.setHealth(150);
        this.setMovement(10);
        this.setRange(3);
    }
    public void attack(Soldier enemy) {
        enemy.setHealth(enemy.getHealth()-this.getPower());
    }
}