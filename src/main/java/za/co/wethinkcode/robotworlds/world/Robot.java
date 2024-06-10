package za.co.wethinkcode.robotworlds;

/**
 * The Robot class represents a robot in the world.
 */
public class Robot {
    private final int MAX_SHIELDS = 10;
    private final int MAX_SHOTS = 100;

    private final String name;
    private String status;
    private int shields;
    private int shots;
    private Position position;
    private Direction currentDirection;


    /**
     * Constructs a Robot object with the specified name, position, and direction.
     *
     * @param name The name of the robot.
     * @param position The initial position of the robot.
     * @param direction The initial direction of the robot.
     */
    public Robot(String name, Position position, Direction direction) {
        this.name = name;
        this.status = "NORMAL";
        this.shields = MAX_SHIELDS;
        this.shots = MAX_SHOTS;
        this.position = position;
        this.currentDirection = direction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String newStatus) {
        status = newStatus;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction direction) {
        this.currentDirection = direction;
    }

    public String getName() {
        return name;
    }

    public int getShields() {
        return shields;
    }

    public int getShots() {
        return shots;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Updates the position of the robot based on the current direction and the specified number of steps.
     *
     * @param numSteps The number of steps to move the robot.
     * @return True if the new position is valid and the update was successful, false otherwise.
     */
    public boolean updatePosition(int numSteps) {
        int newX = position.getX();
        int newY = position.getY();

        switch (currentDirection) {
            case NORTH:
                newY += numSteps;
                break;
            case SOUTH:
                newY -= numSteps;
                break;
            case WEST:
                newX -= numSteps;
                break;
            case EAST:
                newX += numSteps;
                break;
        }

        Position newPosition = new Position(newX, newY);
        if (isValidPosition(newPosition)) {
            position = newPosition;
            return true;
        }
        return false;
    }

    private boolean isValidPosition(Position newPosition) {
        return newPosition.getX() >= -200 && newPosition.getX() <= 100 &&
                newPosition.getY() >= -200 && newPosition.getY() <= 100;
    }

    /**
     * Updates the shields of the robot after being hit.
     *
     * @param hit The damage received by the robot.
     */
    public void updateShields(int hit) {
        if (shields > 0) {
            shields -= hit;
        }
    }

    /**
     * Updates the shots of the robot after firing shots.
     *
     * @param shotsFired The number of shots fired by the robot.
     */
    public void updateShots(int shotsFired) {
        if (shots > 0) {
            shots -= shotsFired;
        }
    }

    /**
     * Resets the robot to its initial state.
     */
    public void reset() {
        position = new Position(0, 0);
        currentDirection = Direction.NORTH;
        status = "NORMAL";
        shields = MAX_SHIELDS;
        shots = MAX_SHOTS;
    }

    /**
     * Returns a string representation of the robot's current state.
     *
     * @return A string containing the position, name, and status of the robot.
     */
    @Override
    public String toString() {
        return "[" + position.getX() + "," + position.getY() + "] " + name + "> " + status;
    }
}
