package za.co.wethinkcode.robotworlds.robot;


import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.world.Obstacle;

import java.util.List;

public class Robot {

    private final String name;
    private String status;
    private Position position;
    private Direction direction;
    private int shields;
    private int shots;
    private final int MAX_SHIELDS = 10;
    private final int MAX_SHOTS = 100;


    public Robot(String name, Position position, Direction direction) {
        this.name = name;
        this.status = "NORMAL";
        this.shields = MAX_SHIELDS;
        this.shots = MAX_SHOTS;
        this.position = position;
        this.direction = direction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String newStatus) {
        status = newStatus;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
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

    private boolean isValidPosition(Position newPosition, IWorld world) {
        List<Robot> robots = world.getRobots();
        List<Obstacle> obstacles = world.getObstacles();
        int i = 0;
        while (i < Math.max(robots.size(), obstacles.size())) {
            if ((i < obstacles.size()) && obstacles.get(i).blocksPosition(newPosition)) {
                System.out.println("isValidPosition");
                return false;
            }
            i++;
        }
        return newPosition.getX() >= -200 && newPosition.getX() <= 100 &&
                newPosition.getY() >= -200 && newPosition.getY() <= 100;
    }

    public boolean blocksPosition(Position position) {
        return this.getPosition().equals(position);
    }

    public void updateShields(int hit) {
        if (shields > 0) {
            shields -= hit;
        }
    }

    public void updateShots(int shotsFired) {
        if (shots > 0) {
            shots -= shotsFired;
        }
    }

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
