package za.co.wethinkcode.robotworlds.robot;


import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.world.IWorld;

public class SimpleBot implements Robot {

    private final String name;
    private String status;
    private Position position;
    private Direction direction;
    private int shields;
    private int shots;
    private final int MAX_SHIELDS = 10;
    private final int MAX_SHOTS = 100;


    public SimpleBot(String name, Position position, Direction direction) {
        this.name = name;
        this.status = "NORMAL";
        this.shields = MAX_SHIELDS;
        this.shots = MAX_SHOTS;
        this.position = position;
        this.direction = direction;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String newStatus) {
        status = newStatus;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getShields() {
        return shields;
    }

    @Override
    public int getShots() {
        return shots;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
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

    @Override
    public boolean blocksPosition(Position position) {
        return this.getPosition().equals(position);
    }

    @Override
    public void updateShields(int hit) {
        if (shields > 0) {
            shields -= hit;
        }
    }

    @Override
    public void updateShots(int shotsFired) {
        if (shots > 0) {
            shots -= shotsFired;
        }
    }

    @Override
    public void reset() {
        position = new Position(0, 0);
        direction = Direction.NORTH;
        status = "NORMAL";
        shields = MAX_SHIELDS;
        shots = MAX_SHOTS;
    }

    @Override
    public String toString() {
        return "[" + position.getX() + "," + position.getY() + "] " + name + "> " + status;
    }
}
