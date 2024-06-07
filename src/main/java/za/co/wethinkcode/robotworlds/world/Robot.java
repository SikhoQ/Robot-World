package za.co.wethinkcode.robotworlds.world;


import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;

import java.util.List;

public class Robot {
    private final int MAX_SHIELDS = 10;
    private final int MAX_SHOTS = 100;

    private final String name;
    private String status;
    private int shields;
    private int shots;
    private Position position;
    private Direction currentDirection;
    private final IWorld world;

    public Robot(String name, Position position, Direction direction, IWorld world) {
        this.name = name;
        this.status = "NORMAL";
        this.shields = MAX_SHIELDS;
        this.shots = MAX_SHOTS;
        this.position = position;
        this.currentDirection = direction;
        this.world = world;
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

    /* change updatePosition to return String instead of boolean
     * returns "Done" if successful update
     * else "Obstructed"
     */
    public String updatePosition(int numSteps) {
        int xCoord = position.getX();
        int yCoord = position.getY();

        Position newPosition = position;
        switch (currentDirection) {
            // position update for north direction
            case NORTH:
                for (int y = yCoord + 1; y <= (yCoord + numSteps); y++) {
                    newPosition = new Position(xCoord, y);
                    if (isValidPosition(newPosition)) {
                        // obstacles
                        List<Obstacle> obstacles = world.getObstacles();
                        for (Obstacle obstacle: obstacles) {
                            if (obstacle.blocksPosition(newPosition)) {
                                // obstructed by obstacle
                                position = new Position(xCoord, y - 1);
                                return "Obstacle";
                            }
                        }
                        // robots
                        List<Robot> robots = world.getRobots();
                        for (Robot robot: robots) {
                            if (robot.blocksPosition(newPosition)) {
                                // obstructed by robot
                                position = new Position(xCoord, y - 1);
                                return "Robot";
                            }
                        }
                    } else {
                        // obstructed by wall
                        position = new Position(xCoord, y - 1);
                        return "Wall";
                    }
                }
                break;
            case SOUTH:
                for (int y = yCoord - 1; y >= (yCoord - numSteps); y--) {
                    newPosition = new Position(xCoord, y);
                    if (isValidPosition(newPosition)) {
                        // obstacles
                        List<Obstacle> obstacles = world.getObstacles();
                        for (Obstacle obstacle: obstacles) {
                            if (obstacle.blocksPosition(newPosition)) {
                                position = new Position(xCoord, y + 1);
                                return "Obstructed";
                            }
                        }
                        // robots
                        List<Robot> robots = world.getRobots();
                        for (Robot robot: robots) {
                            if (robot.blocksPosition(newPosition)) {
                                position = new Position(xCoord, y + 1);
                                return "Obstructed";
                            }
                        }
                    } else {
                        position = new Position(xCoord, y + 1);
                        return "Obstructed";
                    }
                }
                break;
            case WEST:
                for (int x = xCoord - 1; x >= (xCoord - numSteps); x--) {
                    newPosition = new Position(x, yCoord);
                    if (isValidPosition(newPosition)) {
                        // obstacles
                        List<Obstacle> obstacles = world.getObstacles();
                        for (Obstacle obstacle: obstacles) {
                            if (obstacle.blocksPosition(newPosition)) {
                                position = new Position(x + 1, yCoord);
                                return "Obstructed";
                            }
                        }
                        // robots
                        List<Robot> robots = world.getRobots();
                        for (Robot robot: robots) {
                            if (robot.blocksPosition(newPosition)) {
                                position = new Position(x + 1, yCoord);
                                return "Obstructed";
                            }
                        }
                    } else {
                        position = new Position(x + 1, yCoord);
                        return "Obstructed";
                    }
                }
                break;
            case EAST:
                for (int x = xCoord + 1; x <= (xCoord + numSteps); x++) {
                    newPosition = new Position(x, yCoord);
                    if (isValidPosition(newPosition)) {
                        // obstacles
                        List<Obstacle> obstacles = world.getObstacles();
                        for (Obstacle obstacle: obstacles) {
                            if (obstacle.blocksPosition(newPosition)) {
                                position = new Position(x - 1, yCoord);
                                return "Obstructed";
                            }
                        }
                        // robots
                        List<Robot> robots = world.getRobots();
                        for (Robot robot: robots) {
                            if (robot.blocksPosition(newPosition)) {
                                position = new Position(x - 1, yCoord);
                                return "Obstructed";
                            }
                        }
                    } else {
                        position = new Position(x - 1, yCoord);
                        return "Obstructed";
                    }
                }
                break;
        }
        position = newPosition;
        return "Done";
    }

    private boolean isValidPosition(Position newPosition) {
        int minimumX = world.getWorldEdges().getMinimumX();
        int maximumX = world.getWorldEdges().getMaximumX();
        int minimumY = world.getWorldEdges().getMinimumY();
        int maximumY = world.getWorldEdges().getMaximumY();
        return newPosition.getX() >= minimumX && newPosition.getX() <= maximumX &&
                newPosition.getY() >= minimumY && newPosition.getY() <= maximumY;
    }

    private boolean blocksPosition(Position position) {
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
