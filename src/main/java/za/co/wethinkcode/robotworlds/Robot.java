package za.co.wethinkcode.robotworlds;

<<<<<<< HEAD
public class Robot {
    private final Position TOP_LEFT = new Position(-100,200);
    private final Position BOTTOM_RIGHT = new Position(100,-200);

    public static final Position CENTRE = new Position(0,0);

    private Position position;
    private Direction currentDirection;
    private String status;
    private String name;

    // to replay commands, robot must handle each command in the array list
    // store commands in list, filtering out non-movement (store movement in array)
    // put logic inside handlecommand

    public Robot(String name) {
        this.name = name;
        this.status = "Ready";
        this.position = CENTRE;
        this.currentDirection = Direction.UP;
    }

    public String getStatus() {
        return this.status;
    }

    public Direction getCurrentDirection() {
        return this.currentDirection;
    }

    public Position getPosition() {
        return this.position;
=======

public class Robot {
    private final int MAX_SHIELDS = 10;
    private final int MAX_SHOTS = 100;

    private final String name;
    private String status;
    private int shields;
    private int shots;
    private Position position;
    private Direction currentDirection;


    public Robot(String name, Position position) {
        this.name = name;
        this.status = "Ready";
        this.shields = MAX_SHIELDS;
        this.shots = MAX_SHOTS;
        this.position = position;
        this.currentDirection = Direction.NORTH;

    }

    public String getStatus() {
        return status;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction direction) {
        this.currentDirection = direction;
>>>>>>> origin/main-clone
    }

    public String getName() {
        return name;
    }

<<<<<<< HEAD
    public void setStatus(String status) {
        this.status = status;
    }

    public void setDirection(Direction direction) {
        this.currentDirection = direction;
    }

    public boolean updatePosition(int nrSteps){
        int newX = this.position.getX();
        int newY = this.position.getY();

        if (Direction.UP.equals(this.currentDirection)) {
            newY += nrSteps;
        }

        else if (Direction.DOWN.equals(this.currentDirection)) {
            newY -= nrSteps;
        }

        else if (Direction.LEFT.equals(this.currentDirection)) {
            newX -= nrSteps;
        }

        else if (Direction.RIGHT.equals(this.currentDirection)){
            newX += nrSteps;
        }

        Position newPosition = new Position(newX, newY);
        if (newPosition.isIn(TOP_LEFT,BOTTOM_RIGHT)){
            this.position = newPosition;
=======
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
>>>>>>> origin/main-clone
            return true;
        }
        return false;
    }

<<<<<<< HEAD
    @Override
    public String toString() {
        return "[" + this.position.getX() + "," + this.position.getY() + "] "
                + this.name + "> " + this.status;
=======
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
        status = "Ready";
        shields = MAX_SHIELDS;
        shots = MAX_SHOTS;
    }

    @Override
    public String toString() {
        return "[" + position.getX() + "," + position.getY() + "] " + name + "> " + status;
>>>>>>> origin/main-clone
    }
}
