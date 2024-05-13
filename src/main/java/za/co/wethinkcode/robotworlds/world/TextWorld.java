package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.maze.Maze;

import java.util.*;


public class TextWorld implements IWorld {
    private Position position = CENTRE;
    private final List<Obstacle> obstacles;
    private Direction heading =Direction.UP;
    private final Position TOP_LEFT = new Position(-100,200);
    private final Position BOTTOM_RIGHT = new Position(100,-200);

    public TextWorld(Maze maze) {
        obstacles = maze.getObstacles();
        heading = Direction.UP;
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
        heading = Direction.UP;
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
            heading = Direction.RIGHT;
        }
        else {
            heading = Direction.LEFT;
        }
    }

    @Override
    public UpdateResponse updatePosition(int nrSteps) {
        int newX = position.getX();
        int newY = position.getY();

        if (Direction.UP.equals(heading)) {
            newY += nrSteps;
        }

        else if (Direction.DOWN.equals(heading)) {
            newY -= nrSteps;
        }

        else if (Direction.LEFT.equals(heading)) {
            newX -= nrSteps;
        }

        else if (Direction.RIGHT.equals(heading)){
            newX += nrSteps;
        }

        Position newPosition = new Position(newX, newY);

        if (isNewPositionAllowed(newPosition)) {
            position = newPosition;
            return UpdateResponse.SUCCESS;
        }
        return UpdateResponse.FAILED_OUTSIDE_WORLD;
    }
}

