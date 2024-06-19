package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.TitleCaseConverter;
import za.co.wethinkcode.robotworlds.maze.RandomMaze;
import za.co.wethinkcode.robotworlds.robot.Gun;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.ConfigUtility;

import java.util.*;


public class TextWorld implements IWorld {
    private Position position = CENTRE;
    private final List<Obstacle> obstacles;
    private Direction heading;
    private final Position TOP_LEFT;
    private final Position BOTTOM_RIGHT;
    private final Edge worldEdges;
    private final Map<Integer, Robot> robots;
    private int worldHeight;
    private int worldWidth;
    private int visibility;
    private int reload;
    private int repair;
    private int shields;

    public TextWorld() {
        obstacles = new RandomMaze().getObstacles();
        heading = Direction.NORTH;
        robots = new HashMap<>();
        setupWorld();
        int worldX = worldWidth / 2;
        int worldY = worldHeight / 2;
        TOP_LEFT = new Position(-worldX,worldY);
        BOTTOM_RIGHT = new Position(worldX,-worldY);
        worldEdges = new Edge(TOP_LEFT, BOTTOM_RIGHT);
    }

    private void setupWorld() {
        if (ConfigUtility.loadConfiguration() != null) {
            worldWidth = ConfigUtility.getWorldSize().getWidth();
            worldHeight = ConfigUtility.getWorldSize().getHeight();
            visibility = ConfigUtility.getVisibility();
            reload = ConfigUtility.getReload();
            repair = ConfigUtility.getRepair();
        } else {
            worldWidth = 40;
            worldHeight = 80;
            visibility = 20;
            reload = 5;
            repair = 5;
            shields = 5;
        }
    }

    @Override
    public int getReload() {
        return reload;
    }

    @Override
    public int getRepair() {
        return repair;
    }

    @Override
    public int getShields() {
        return shields;
    }

    @Override
    public int getVisibility() {
        return visibility;
    }

    @Override
    public Edge getWorldEdges() {
        return worldEdges;
    }

    @Override
    public void showObstacles() {
        if (!obstacles.isEmpty()) {
            System.out.println("There are some obstacles:");
            Obstacle[] obstacleArray = obstacles.toArray(new Obstacle[obstacles.size() - 1]);
            for (Obstacle obstacle: obstacleArray) {
                int x = obstacle.getBottomLeftX();
                int y = obstacle.getBottomLeftY();
                System.out.println("- At position "+x+","+y+" (to "+(x+4)+","+(y+4)+")");
            }
        }
    }

    @Override
    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    @Override
    public boolean isAtEdge() {
        return position.getX() == -100 || position.getX() == 100 ||
                position.getY() == -200 || position.getY() == 200;
    }

    @Override
    public void reset() {
        position = CENTRE;
        heading = Direction.NORTH;
    }

    @Override
    public boolean isNewPositionAllowed(Position position) {
        for (Obstacle obstacle: obstacles) {
            if (obstacle.blocksPosition(position)) {
                return false;
            }
        }
        for (Map.Entry<Integer, Robot> entry: robots.entrySet()) {
            if (entry.getValue().blocksPosition(position)) {
                return false;
            }
        }
        return position.isIn(TOP_LEFT, BOTTOM_RIGHT);
    }

    @Override
    public Direction getCurrentDirection() {
        return heading;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void updateDirection(boolean turnRight) {
        if (turnRight) {
            heading = Direction.EAST;
        }
        else {
            heading = Direction.WEST;
        }
    }

    @Override
    public UpdateResponse updatePosition(int nrSteps) {
        int newX = position.getX();
        int newY = position.getY();

        if (Direction.NORTH.equals(heading)) {
            newY += nrSteps;
        }

        else if (Direction.SOUTH.equals(heading)) {
            newY -= nrSteps;
        }

        else if (Direction.WEST.equals(heading)) {
            newX -= nrSteps;
        }

        else if (Direction.EAST.equals(heading)){
            newX += nrSteps;
        }

        Position newPosition = new Position(newX, newY);

        if (isNewPositionAllowed(newPosition)) {
            position = newPosition;
            return UpdateResponse.SUCCESS;
        }
        return UpdateResponse.FAILED_OUTSIDE_WORLD;
    }

    @Override
    public Robot addRobotToWorld(String[] nameAndMake, int maximumShots, int PORT) {
        Position position = Position.getRandomPosition(this);
        position = validateLaunchPosition(position);
        Direction direction = Direction.getRandomDirection();
        Robot robot = new Robot(nameAndMake, maximumShots, position, direction, PORT);
        String name = nameAndMake[0];
        String make = nameAndMake[1];
        System.out.println("\n\u001B[33m\u001B[1m"+name+" \u001B[0m("+make+")"+" \u001B[34mlaunched at \u001B[0m["+position.getX()+","+position.getY()+"]\n");
        addRobotToList(robot, PORT);
        return robot;
    }

    @Override
    public void addRobotToList(Robot robot, int PORT) {
        robots.put(PORT, robot);
    }

    @Override
    public void removeRobot(int PORT) {
        robots.remove(PORT);
    }

    @Override
    public Map<Integer, Robot> getRobots() {
        return robots;
    }

    @Override
    public Position validateLaunchPosition(Position position) {
        Map<Integer, Robot> robots = getRobots();
        List<Obstacle> obstacles = getObstacles();
        for (Map.Entry<Integer, Robot> entry: robots.entrySet()) {
            while (entry.getValue().getPosition().equals(position)) {
                position = Position.getRandomPosition(this);
            }
        }
        for (Obstacle obstacle: obstacles) {
            while (obstacle.blocksPosition(position)) {
                position = Position.getRandomPosition(this);
            }
        }
        return position;
    }

    @Override
    public void showWorldState() {
        StringBuilder dump = new StringBuilder();

        List<Obstacle> obstacles = getObstacles();

        dump.append("Obstacles\n---------\n");

        if (!obstacles.isEmpty()) {
            dump.append("* There are ").append(obstacles.size()).append(" obstacle:\n");
        } else {
            dump.append(" No obstacles");
        }

        for (Obstacle obstacle: obstacles) {
            String obstacleString = "  - At ("+obstacle.getBottomLeftX()
                    +","+obstacle.getBottomLeftY()+") to ("
                    +(obstacle.getBottomLeftX()+4)+","
                    +(obstacle.getBottomLeftY()+4)+")";
            dump.append(obstacleString).append("\n");
        }
        Map<Integer, Robot> robots = getRobots();
        dump.append("\n\nRobots\n------\n");
        if (robots.isEmpty()) {
            dump.append("* No Robots Launched\n");
        }
        for (Map.Entry<Integer, Robot> entry: robots.entrySet()) {
            Robot robot = entry.getValue();
            String name = robot.getName();
            String make = robot.getMake();

            Position position = robot.getPosition();
            int xCoord = position.getX();
            int yCoord = position.getY();
            String positionString = "["+xCoord+","+yCoord+"]";

            Direction direction = robot.getDirection();
            String status = robot.getStatus();

            dump.append("Robot: ").append(name).append("\n");
            dump.append("Make: ").append(make).append("\n");
            dump.append("Position: ").append(positionString).append("\n");
            dump.append("Direction: ").append(direction).append("\n");
            dump.append("State: ").append(status).append("\n\n");
        }

        System.out.println("\n===========\nWorld State");
        System.out.println("===========\n");
        System.out.println(dump);

    }

    @Override
    public void showRobots() {
        Map<Integer, Robot> robots = getRobots();
        if (robots.isEmpty()) {
            System.out.println("There are no robots in this world.");
        } else {
            int robotCount = 1;
            System.out.println("There are robots:");
            for (Map.Entry<Integer, Robot> entry: robots.entrySet()) {
                Position position = entry.getValue().getPosition();
                System.out.println("\n Robot " + (robotCount++) + ":");
                System.out.println(" " + "=".repeat((" name: [" + entry.getValue().getName() + "]").length()));
                System.out.println(" name: [" + TitleCaseConverter.toTitleCase(entry.getValue().getName()) + "]");
                System.out.println(" make: [" + TitleCaseConverter.toTitleCase(entry.getValue().getMake()) + "]");
                System.out.println(" "+"_".repeat((" name: ["+entry.getValue().getName()+"]").length()));
                System.out.println(" position : "+"["+position.getX()+","+position.getY()+"]");
                System.out.println(" direction: ["+entry.getValue().getDirection()+"]");
                System.out.println(" shields  :  "+entry.getValue().getShields());
                System.out.println(" shots    :  "+ Gun.getNumberOfShots());
                System.out.println(" status   : ["+entry.getValue().getStatus()+"]");
            }
        }
    }
}