package za.co.wethinkcode.robotworlds;


public class Robot {
    private final int MAX_SHIELDS = 10;
    private final int MAX_SHOTS = 100;

    private final String name;
    private String status;
    private int shields;
    private int shots;
    private Position position;
    private Direction currentDirection;


    public Robot(String name, Position position, Direction direction) {
        this.name = name;
        this.status = "Ready";
        this.shields = MAX_SHIELDS;
        this.shots = MAX_SHOTS;
        this.position = position;
        this.currentDirection = direction;
    }

    public String getStatus() {
        return status;
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

    public void updateShields(int hit) {
        shields -= hit;
        if (shields < 0) {
            shields = 0;
        }
    }

    public void updateShots(int shotsFired) {
        shots -= shotsFired;
        if (shots < 0) {
            shots = 0;
        }
    }

    public void reset() {
        position = new Position(0, 0);
        currentDirection = Direction.NORTH;
        status = "NORMAL";
        shields = MAX_SHIELDS;
        shots = MAX_SHOTS;
    }

    @Override
    public String toString() {
        return "[" + position.getX() + "," + position.getY() + "] " + name + "> " + status;
    }
}
