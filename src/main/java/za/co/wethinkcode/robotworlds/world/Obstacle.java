package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.Position;

/**
 * The Obstacle interface defines the behavior of obstacles in the world.
 * It provides methods for retrieving position information and checking if an obstacle blocks a position or path.
 */
public interface Obstacle {

    /**
     * Retrieves the x-coordinate of the bottom-left corner of the obstacle.
     *
     * @return the x-coordinate of the bottom-left corner.
     */
    int getBottomLeftX();

    /**
     * Retrieves the y-coordinate of the bottom-left corner of the obstacle.
     *
     * @return the y-coordinate of the bottom-left corner.
     */
    int getBottomLeftY();

    /**
     * Retrieves the size of the obstacle.
     *
     * @return the size of the obstacle.
     */
    int getSize();

    /**
     * Checks if the obstacle blocks the given position.
     *
     * @param position the position to check.
     * @return true if the obstacle blocks the position, false otherwise.
     */
    boolean blocksPosition(Position position);

    /**
     * Checks if the obstacle blocks the path between two positions.
     *
     * @param a the starting position of the path.
     * @param b the ending position of the path.
     * @return true if the obstacle blocks the path, false otherwise.
     */
    boolean blocksPath(Position a, Position b);
}
