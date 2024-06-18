package za.co.wethinkcode.robotworlds.maze;

import za.co.wethinkcode.robotworlds.world.Obstacle;
import za.co.wethinkcode.robotworlds.world.SquareObstacle;
import za.co.wethinkcode.robotworlds.Position;
import java.util.*;

/**
 * The SimpleMaze class represents a maze with a fixed set of obstacles.
 * It implements the Maze interface and provides methods to retrieve obstacles and check path blockages.
 */
public class SimpleMaze implements Maze {

    /** List to store obstacles in the maze. */
    private List<Obstacle> obstacles;

    /**
     * Constructs a new SimpleMaze object with an empty list of obstacles.
     */
    public SimpleMaze() {
        this.obstacles = new ArrayList<>();
    }

    /**
     * Retrieves a list of obstacles in the maze.
     *
     * @return a list of obstacles present in the maze.
     */
    @Override
    public List<Obstacle> getObstacles() {
        createObstacles();
        return obstacles;
    }

    /**
     * Checks if a path between two positions in the maze is blocked by obstacles.
     *
     * <p>This method determines whether there are any obstacles blocking the path between
     * the specified positions {@code start} and {@code destination} in the maze.</p>
     *
     * @param start the starting position of the path.
     * @param destination the ending position of the path.
     * @return {@code true} if the path between positions {@code start} and {@code destination} is blocked by obstacles,
     *         {@code false} otherwise.
     */
    @Override
    public boolean blocksPath(Position start, Position destination) {
        Obstacle[] obstaclesArray = obstacles.toArray(new Obstacle[0]);
        for (Obstacle obs : obstaclesArray) {
            // Check vertical path
            if (start.getX() == destination.getX()) {
                if ((obs.getBottomLeftY() >= Math.min(start.getY(), destination.getY())
                        && obs.getBottomLeftY() <= Math.max(start.getY(), destination.getY()))
                        && (start.getX() >= obs.getBottomLeftX() && start.getX() <= obs.getBottomLeftX() + 4)) {
                    return true;
                }
            }
            // Check horizontal path
            else {
                if ((obs.getBottomLeftX() >= Math.min(start.getX(), destination.getX())
                        && obs.getBottomLeftX() <= Math.max(start.getX(), destination.getX()))
                        && (start.getY() >= obs.getBottomLeftY() && start.getY() <= obs.getBottomLeftY() + 4)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Creates and adds a fixed set of obstacles to the maze.
     */
    private void createObstacles() {
        SquareObstacle obstacle = new SquareObstacle(1, 1);
        obstacles.add(obstacle);
    }
}
