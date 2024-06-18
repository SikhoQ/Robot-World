package za.co.wethinkcode.robotworlds.maze;

import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.world.Obstacle;

import java.util.List;

/**
 * The Maze interface represents a maze through which a robot can navigate.
 * It defines methods for accessing maze obstacles and determining if a path is blocked.
 */
public interface Maze {

    /**
     * Retrieves a list of obstacles in the maze.
     *
     * @return a list of obstacles present in the maze.
     */
    List<Obstacle> getObstacles();

    /**
     * Checks if a path between two positions in the maze is blocked by obstacles.
     *
     * <p>This method determines whether there are any obstacles blocking the path between
     * the specified positions {@code a} and {@code b} in the maze.</p>
     *
     * @param a the starting position of the path.
     * @param b the ending position of the path.
     * @return {@code true} if the path between positions {@code a} and {@code b} is blocked by obstacles,
     *         {@code false} otherwise.
     */
    boolean blocksPath(Position a, Position b);
}
