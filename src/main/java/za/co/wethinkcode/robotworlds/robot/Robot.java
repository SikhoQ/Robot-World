package za.co.wethinkcode.robotworlds.robot;

import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.ConfigUtility;

/**
 * The Robot class represents a robot with specific attributes such as name, make, status, position,
 * direction, shields, gun, and timing configurations for reloading and repairing.
 */
public class Robot {
    private final String name;
    private final String make;
    private String status;
    private Position position;
    private Direction direction;
    protected int shields;
    private final Gun gun;
    private final int reloadTime;
    private final int repairTime;
    private final int PORT;

    /**
     * Constructs a Robot object with the specified attributes.
     *
     * @param makeAndName an array containing the make and name of the robot.
     * @param maxShots the maximum number of shots the robot's gun can hold.
     * @param position the initial position of the robot.
     * @param direction the initial direction the robot is facing.
     * @param PORT the port number associated with the robot.
     */
    public Robot(String[] makeAndName, int maxShots, Position position, Direction direction, int PORT) {
        this.name = makeAndName[0];
        this.make = makeAndName[1];
        this.status = "NORMAL";
        this.shields = ConfigUtility.getShields();
        this.gun = new Gun(maxShots);
        this.position = position;
        this.direction = direction;
        this.PORT = PORT;
        this.reloadTime = ConfigUtility.getReload();
        this.repairTime = ConfigUtility.getRepair();
    }

    /**
     * Gets the make of the robot.
     *
     * @return the make of the robot.
     */
    public String getMake() {
        return make;
    }

    /**
     * Gets the current status of the robot.
     *
     * @return the current status of the robot.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Gets the reload time of the robot.
     *
     * @return the reload time of the robot.
     */
    public long getReloadTime() {
        return this.reloadTime;
    }

    /**
     * Sets the status of the robot.
     *
     * @param newStatus the new status of the robot.
     */
    public void setStatus(String newStatus) {
        status = newStatus;
    }

    /**
     * Gets the direction the robot is facing.
     *
     * @return the direction the robot is facing.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets the direction the robot is facing.
     *
     * @param direction the new direction the robot is facing.
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Gets the name of the robot.
     *
     * @return the name of the robot.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the current shield level of the robot.
     *
     * @return the current shield level of the robot.
     */
    public int getShields() {
        return shields;
    }

    /**
     * Gets the gun the robot is equipped with.
     *
     * @return the gun the robot is equipped with.
     */
    public Gun getGun() {
        return gun;
    }

    /**
     * Gets the current position of the robot.
     *
     * @return the current position of the robot.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Gets the repair time of the robot.
     *
     * @return the repair time of the robot.
     */
    public long getRepairTime() {
        return ConfigUtility.getRepair();
    }

    /**
     * Gets the port number associated with the robot.
     *
     * @return the port number associated with the robot.
     */
    public int getPORT() {
        return PORT;
    }

    /**
     * Updates the position of the robot based on the number of steps and the world configuration.
     *
     * @param numSteps the number of steps to move.
     * @param world the world in which the robot is moving.
     * @return "Done" if the move is successful, otherwise "Obstructed".
     */
    public String updatePosition(int numSteps, IWorld world) {
        int newX = position.getX();
        int newY = position.getY();
        boolean goingBack = numSteps < 0;
        numSteps = Math.abs(numSteps);

        for (int step = 1; step <= numSteps; step++) {
            switch (direction) {
                case NORTH:
                    newY = (goingBack) ? (newY - 1) : (newY + 1);
                    break;
                case SOUTH:
                    newY = (goingBack) ? (newY + 1) : (newY - 1);
                    break;
                case WEST:
                    newX = (goingBack) ? (newX + 1) : (newX - 1);
                    break;
                case EAST:
                    newX = (goingBack) ? (newX - 1) : (newX + 1);
                    break;
            }
            Position newPosition = new Position(newX, newY);
            if (world.isNewPositionAllowed(newPosition)) {
                position = newPosition;
            } else {
                return "Obstructed";
            }
        }
        return "Done";
    }

    /**
     * Checks if the robot blocks a given position.
     *
     * @param position the position to check.
     * @return true if the robot blocks the position, otherwise false.
     */
    public boolean blocksPosition(Position position) {
        return this.getPosition().equals(position);
    }

    /**
     * Updates the shield level of the robot by decreasing it by one if it is greater than zero.
     */
    public void updateShields() {
        if (shields > 0) {
            shields--;
        }
    }

    /**
     * Resets the robot's position, direction, status, and shield level to default values.
     */
    public void resetRobot() {
        position = new Position(0, 0);
        direction = Direction.NORTH;
        status = "NORMAL";
        shields = ConfigUtility.getShields();
    }

    /**
     * Repairs the robot by setting the shield level to the maximum value.
     */
    public void repair() {
        this.shields = ConfigUtility.getShields();
    }

    /**
     * Returns a string representation of the robot, including its position, name, and status.
     *
     * @return a string representation of the robot.
     */
    @Override
    public String toString() {
        return "[" + position.getX() + "," + position.getY() + "] " + name + "> " + status;
    }
}
