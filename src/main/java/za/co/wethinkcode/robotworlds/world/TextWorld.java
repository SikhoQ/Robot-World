package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.Sleep;
import za.co.wethinkcode.robotworlds.maze.RandomMaze;
import za.co.wethinkcode.robotworlds.world.configuration.Config;

import java.util.*;


public class TextWorld implements IWorld {
    private Position position = CENTRE;
    private final List<Obstacle> obstacles;
    private Direction heading;
    private final Position TOP_LEFT;
    private final Position BOTTOM_RIGHT;
    private final Edge worldEdges;
    private final List<Robot> robots;
    private int worldHeight;
    private int worldWidth;
    private int visibility;
    private int reload;
    private int repair;
    private int shields;

    public TextWorld() {
        obstacles = new RandomMaze().getObstacles();
        heading = Direction.NORTH;
        robots = new ArrayList<>();
        setupWorld();
        int worldX = worldWidth / 2;
        int worldY = worldHeight / 2;
        TOP_LEFT = new Position(-worldX,worldY);
        BOTTOM_RIGHT = new Position(worldX,-worldY);
        worldEdges = new Edge(TOP_LEFT, BOTTOM_RIGHT);
    }

    private void setupWorld() {
        Config config = Config.readConfiguration();
        if (config != null) {
            worldWidth = config.getWorldSize().getWidth();
            worldHeight = config.getWorldSize().getHeight();
            visibility = config.getVisibility();
            reload = config.getReload();
            repair = config.getRepair();
            shields = config.getShields();
        } else {
            worldWidth = 200;
            worldHeight = 400;
            visibility = 50;
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

    public Edge getWorldEdges() {
        return worldEdges;
    }

    @Override
    public void showObstacles() {
        if (!obstacles.isEmpty()) {
            // need to build string to store in status, print for now
            System.out.println("There are some obstacles:");
            // convert list to array to iterate without errors
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
        boolean flag = true;

        for (Obstacle obstacle: obstacles) {
            if (obstacle.blocksPosition(position)) {
                flag = false;
            }
        }
        if (!position.isIn(TOP_LEFT, BOTTOM_RIGHT)) {
            flag = false;
        }
        return flag;
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
    public Robot launchRobot(String make, String name) {
        // change this to use make to create relevant robot
        Sleep.sleep(1300);
        Position position = Position.getRandomPosition();
        position = validatePosition(position);
        Direction direction = Direction.getRandomDirection();
        Robot robot = new Robot(name, position, direction);

        System.out.println(name+" ("+make+")"+" joined.");

        robots.add(robot);

        return robot;
    }

    @Override
    public List<Robot> getRobots() {
        return robots;
    }

    @Override
    public Position validatePosition(Position position) {
        List<Robot> robots = getRobots();
        for (Robot robot: robots) {
            while (robot.getPosition().equals(position)) {
                position = Position.getRandomPosition();
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
            dump.append(" There are obstacles:\n");
        } else {
            dump.append(" No obstacles");
        }

        for (Obstacle obstacle: obstacles) {
            String obstacleString = "  - At ["+obstacle.getBottomLeftX()
                    +","+obstacle.getBottomLeftY()+"] to ["
                    +obstacle.getBottomLeftX()+4+","
                    +obstacle.getBottomLeftY()+4+"]";
            dump.append(obstacleString).append("\n\n");
        }
        List<Robot> robots = getRobots();
        dump.append("Robots\n------\n");
        if (robots.isEmpty()) {
            dump.append(" No Robots Launched\n");
        }
        for (Robot robot: robots) {
            String name = robot.getName();

            Position position = robot.getPosition();
            int xCoord = position.getX();
            int yCoord = position.getY();
            String positionString = "["+xCoord+","+yCoord+"]";

            Direction direction = robot.getCurrentDirection();
            String status = robot.getStatus();

            dump.append("Robot: ").append(name).append("\n");
            dump.append("Position: ").append(positionString).append("\n");
            dump.append("Direction: ").append(direction).append("\n");
            dump.append("State: ").append(status).append("\n\n");
        }

        System.out.println("World Dump:");
        System.out.println("===========\n");
        System.out.println(dump);

    }

    @Override
    public void showRobots() {
        List<Robot> robots = getRobots();
        if (robots.isEmpty()) {
            System.out.println("There are no robots in this world.");
        } else {
            int robotCount = 1;
            System.out.println("There are robots:");
            for (Robot robot: robots) {
                System.out.println(" Robot "+(robotCount++)+":");
                System.out.println(" ========");
                System.out.println(robot.getName()+"\n");
            }
        }
    }
}
