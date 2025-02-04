package za.co.wethinkcode.robotworlds.command;

import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.robot.Gun;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.world.*;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The LookCommand class is responsible for handling the action of looking around in all four directions.
 * It extends the Command class and provides the implementation for the execute method.
 */
public class LookCommand extends Command {
    public LookCommand() {
        super("look", null);
    }

    /**
     * Executes the LookCommand.
     *
     * @param target The robot that is executing the command.
     * @param world The world in which the robot is located.
     * @return A ServerResponse object containing the result, data, and state of the command execution.
     */
    @Override
    public ServerResponse execute(Robot target, IWorld world) {
        Map<Object, Position> north = lookInDirection("NORTH", target, world);
        Map<Object, Position> south = lookInDirection("SOUTH", target, world);
        Map<Object, Position> east = lookInDirection("EAST", target, world);
        Map<Object, Position> west = lookInDirection("WEST", target, world);

        String result = "OK";
        Map<String, Object> data = new HashMap<>();
        List<Map<String, Object>> objects = new ArrayList<>();

        if (!north.isEmpty()) {
            for (Map.Entry<Object, Position> entry: north.entrySet()) {
                Map<String, Object> object = getObjectMap(target, entry, "NORTH");
                objects.add(object);
            }
        }
        if (!south.isEmpty()) {
            for (Map.Entry<Object, Position> entry: south.entrySet()) {
                Map<String, Object> object = getObjectMap(target, entry, "SOUTH");
                objects.add(object);
            }
        }
        if (!east.isEmpty())  {
            for (Map.Entry<Object, Position> entry: east.entrySet()) {
                Map<String, Object> object = getObjectMap(target, entry, "EAST");
                objects.add(object);
            }
        }
        if (!west.isEmpty()) {
            for (Map.Entry<Object, Position> entry: west.entrySet()) {
                Map<String, Object> object = getObjectMap(target, entry, "WEST");
                objects.add(object);
            }
        }

        Map<String, Object> state = new HashMap<>();
        state.put("position", target.getPosition());
        state.put("direction", target.getDirection());
        state.put("shields", target.getShields());
        state.put("shots", Gun.getNumberOfShots());
        state.put("status", target.getStatus());

        data.put("objects", objects);

        return new ServerResponse(result, data, state);
    }

    /**
     * Looks in a specific direction for objects in the world.
     *
     * @param direction The direction in which to look.
     * @param target The robot that is executing the command.
     * @param world The world in which the robot is located.
     * @return A map of objects and their positions in the specified direction.
     */
    private Map<Object, Position> lookInDirection(String direction, Robot target, IWorld world) {
        Position robotPosition = target.getPosition();
        int robotX = robotPosition.getX();
        int robotY = robotPosition.getY();
        int visibility = world.getVisibility();
        Map<Object, Position> objects = new HashMap<>();

        if (direction.equalsIgnoreCase("EAST")) {
            for (int x = robotX + 1; x <= (robotX + visibility); x++) {
                Object object = detectObject(direction, world, x, robotY);
                if (object != null) {
                    objects.put(object, new Position(x, robotY));
                }
            }
        }
        else if (direction.equalsIgnoreCase("WEST")) {
            for (int x = robotX - 1; x >= (robotX - visibility); x--) {
                Object object = detectObject(direction, world, x, robotY);
                if (object != null) {
                    objects.put(object, new Position(x, robotY));
                }
            }
        }
        else if (direction.equalsIgnoreCase("SOUTH")) {
            for (int y = robotY - 1; y >= (robotY - visibility); y--) {
                Object object = detectObject(direction, world, robotX, y);
                if (object != null) {
                    objects.put(object, new Position(robotX, y));
                }
            }
        }
        else if (direction.equalsIgnoreCase("NORTH")) {
            for (int y = robotY + 1; y <= (robotY + visibility); y++) {
                Object object = detectObject(direction, world, robotX, y);
                if (object != null) {
                    objects.put(object, new Position(robotX, y));
                }
            }
        }
        return objects;
    }

    /**
     * Detects an object at a specific position in the world.
     *
     * @param direction The direction in which to detect the object.
     * @param world The world in which the robot is located.
     * @param x The x-coordinate of the position to detect.
     * @param y The y-coordinate of the position to detect.
     * @return The detected object or null if no object is found.
     */
    private Object detectObject(String direction, IWorld world, int x, int y) {
        List<Obstacle> obstacles = world.getObstacles();
        Map<Integer, Robot> robots = world.getRobots();
        Edge worldEdges = world.getWorldEdges();

        for (Obstacle obstacle: obstacles) {
            int obstacleX = obstacle.getBottomLeftX();
            int obstacleY = obstacle.getBottomLeftY();
            if ((x >= obstacleX && x <= obstacleX + 4) && (y >= obstacleY && y <= obstacleY + 4)) {
                return obstacle;
            }
        }

        for (Map.Entry<Integer, Robot> entry: robots.entrySet()) {
            Robot robot = entry.getValue();
            if (robot.getPosition().equals(new Position(x, y))) {
                return robot;
            }
        }

        if (direction.equalsIgnoreCase("EAST") && x == worldEdges.getMaximumX() ||
                direction.equalsIgnoreCase("WEST") && x == worldEdges.getMinimumX() ||
                direction.equalsIgnoreCase("NORTH") && x == worldEdges.getMaximumY() ||
                direction.equalsIgnoreCase("SOUTH") && x == worldEdges.getMinimumY()) {
            return worldEdges;
        }

        return null;
    }

    /**
     * Creates a map representing an object detected in a specific direction.
     *
     * @param target The robot that is executing the command.
     * @param entry The entry containing the detected object and its position.
     * @param direction The direction in which the object was detected.
     * @return A map representing the detected object.
     */
    private static Map<String, Object> getObjectMap(Robot target, Map.Entry<Object, Position> entry, String direction) {
        Map<String, Object> object = new HashMap<>();
        String type = "";
        Object detectedObject = entry.getKey();
        if (detectedObject.getClass().equals(SquareObstacle.class)) {
            type = "OBSTACLE";
        }
        else if (detectedObject.getClass().equals(Robot.class)) {
            type = "ROBOT";
        }
        else if (detectedObject.getClass().equals(Edge.class)) {
            type = "EDGE";
        }
        int distance = switch (direction) {
            case "NORTH" -> entry.getValue().getY() - target.getPosition().getY();
            case "SOUTH" -> {
                if (type.equals("OBSTACLE"))
                    yield target.getPosition().getY() - (entry.getValue().getY() + 4);
                else
                    yield target.getPosition().getY() - entry.getValue().getY();
            }
            case "WEST" -> {
                if (type.equals("OBSTACLE"))
                    yield target.getPosition().getX() - (entry.getValue().getX() + 4);
                else
                    yield target.getPosition().getX() - entry.getValue().getX();
            }
            case "EAST" -> entry.getValue().getX() - target.getPosition().getX();
            default -> 0;
        };

        object.put("direction", direction);
        object.put("type", type);
        object.put("distance", distance);
        return object;
    }
}
