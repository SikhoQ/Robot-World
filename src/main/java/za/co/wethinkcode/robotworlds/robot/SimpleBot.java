package za.co.wethinkcode.robotworlds.robot;

import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.world.configuration.ConfigUtility;

public class SimpleBot {

    private final String name;
    private String status;
    private Position position;
    private Direction direction;
    protected int shields;
    private final int MAX_SHIELDS = 10;
    private Gun gun;
    private final int reloadTime;
    private final int repairTime;
    private final int PORT;
    protected ConfigUtility configUtility;

    public SimpleBot(String name, Position position, Direction direction, int PORT) {
        this.name = name;
        this.status = "NORMAL";
        this.shields = MAX_SHIELDS;
        this.position = position;
        this.direction = direction;
        this.PORT = PORT;

        this.configUtility = ConfigUtility.readConfiguration();
        this.reloadTime = configUtility.getReload();
        this.repairTime = configUtility.getRepair();
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

    public long getRepairTime() {
        return configUtility.getRepair();
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
        this.shields = MAX_SHIELDS;
    }

    @Override
    public String toString() {
        return "[" + position.getX() + "," + position.getY() + "] " + name + "> " + status;
    }
}
