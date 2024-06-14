package za.co.wethinkcode.robotworlds.robot;

import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.world.configuration.Config;

public class SimpleBot {

    private final String name;
    private String status;
    private Position position;
    private Direction direction;
    protected int shields;
    private final int MAX_SHIELDS = 10;
    private Gun gun;
    private final int reloadTime; // in milliseconds
    private final int repairTime; // in milliseconds
    private int PORT;

    public SimpleBot(String name, Position position, Direction direction, int PORT) {
        this.name = name;
        this.status = "NORMAL";
        this.shields = MAX_SHIELDS;
        this.position = position;
        this.direction = direction;
        this.PORT = PORT;

        Config config = Config.readConfiguration();
        this.reloadTime = config.getReload() * 1000; // Convert seconds to milliseconds
        this.repairTime = config.getRepair() * 1000; // Convert seconds to milliseconds
    }

    public String getStatus() {
        return status;
    }

    public long getReloadTime() {
        return this.reloadTime;
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

    public void setGun(int maximumShots) {
        this.gun = new Gun(maximumShots);
    }

    public Gun getGun() {
        return gun;
    }

    public Position getPosition() {
        return position;
    }

    public int getPORT() {
        return PORT;
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

    public boolean blocksPosition(Position position) {
        return this.getPosition().equals(position);
    }

    public void updateShields() {
        if (shields > 0) {
            shields--;
        }
    }

    public void resetRobot() {
        position = new Position(0, 0);
        direction = Direction.NORTH;
        status = "NORMAL";
        shields = MAX_SHIELDS;
    }

    public void repair() {
        try {
            Thread.sleep(repairTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        this.shields = MAX_SHIELDS;
    }

    @Override
    public String toString() {
        return "[" + position.getX() + "," + position.getY() + "] " + name + "> " + status;
    }
}
