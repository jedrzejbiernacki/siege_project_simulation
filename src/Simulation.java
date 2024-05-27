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

    private void setHealth(int newHealth) {
        this.health = newHealth;
    }
    private void setPower(int newPower) {
        this.power = newPower;
    }
    private void setMovement(int newMovement) {
        this.movement = newMovement;
    }
    private void setRange(int newRange) {
        this.range = newRange;
    }
    private void setX_position(int newX_position) {
        this.x_position = newX_position;
    }
    private void setY_position(int newY_position) {
        this.y_position = newY_position;
    }
    private int getHealth() {
        return this.health;
    }
    private int getPower() {
        return this.power;
    }
    private int getMovement() {
        return this.movement;
    }
    private int getRange() {
        return this.range;
    }
    private int getX_position() {
        return this.x_position;
    }
    private int getY_position() {
        return this.y_position;
    }
}

class Knight extends Soldier{

}