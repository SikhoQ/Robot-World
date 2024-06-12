package za.co.wethinkcode.robotworlds.maze;

import za.co.wethinkcode.robotworlds.world.Obstacle;
import za.co.wethinkcode.robotworlds.world.SquareObstacle;
import za.co.wethinkcode.robotworlds.Position;

import java.util.*;

/**
 * Class to represent a simple maze implementation containing obstacles.
 */
public class SimpleMaze implements Maze {

    List<Obstacle> obstacles;

    public SimpleMaze() {
        this.obstacles = new ArrayList<>();
    }

    /**
     * Returns the list of obstacles in the maze.
     *
     * @return a list of Obstacle objects representing the obstacles in the maze.
     */
    @Override
    public List<Obstacle> getObstacles() {
        createObstacles();
        return obstacles;
    }

    /**
     * Checks if the path from the start position to the destination position is blocked by any obstacle.
     *
     * @param start the starting position of the path
     * @param dest the destination position of the path
     * @return true if the path is blocked by an obstacle, false otherwise
     */
    @Override
    public boolean blocksPath(Position start, Position dest) {

        Obstacle[] obstaclesArray = obstacles.toArray(new Obstacle[obstacles.size() - 1]);
        for (Obstacle obs: obstaclesArray) {
            // vertical path
            if (start.getX() == dest.getX()) {
                if ((obs.getBottomLeftY() >= Math.min(start.getY(), dest.getY())
                        && obs.getBottomLeftY() <= Math.max(start.getY(), dest.getY()))
                        && (start.getX() >= obs.getBottomLeftX() && start.getX() <= obs.getBottomLeftX() + 4)) {
                    return true;
                }
            }
            // horizontal
            else {
                if ((obs.getBottomLeftX() >= Math.min(start.getX(), dest.getX())
                        && obs.getBottomLeftX() <= Math.max(start.getX(), dest.getX()))
                        && (start.getY() >= obs.getBottomLeftY() && start.getY() <= obs.getBottomLeftY() + 4)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void createObstacles() {
//        SquareObstacle obstacle = new SquareObstacle(1, 1);
//        obstacles.add(obstacle);
    }
}

