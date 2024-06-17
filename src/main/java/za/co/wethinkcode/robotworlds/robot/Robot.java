package za.co.wethinkcode.robotworlds.robot;

import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.ConfigUtility;

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

    public String getMake() {
        return make;
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

    public Gun getGun() {
        return gun;
    }

    public Position getPosition() {
        return position;
    }

    public long getRepairTime() {
        return ConfigUtility.getRepair();
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
        shields = ConfigUtility.getShields();
    }

    public void repair() {
        this.shields = ConfigUtility.getShields();
    }

    @Override
    public String toString() {
        return "[" + position.getX() + "," + position.getY() + "] " + name + "> " + status;
    }
}
