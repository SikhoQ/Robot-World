package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.Position;

/**
 * The SquareObstacle class represents a square obstacle in the world.
 * It implements the Obstacle interface and provides methods for retrieving position information and checking if the obstacle blocks a position or path.
 */
public class SquareObstacle implements Obstacle {
    private final int bottomLeftX;
    private final int bottomLeftY;

    /**
     * Constructs a SquareObstacle object with the specified bottom-left corner coordinates.
     *
     * @param x the x-coordinate of the bottom-left corner.
     * @param y the y-coordinate of the bottom-left corner.
     */
    public SquareObstacle(int x, int y) {
        this.bottomLeftX = x;
        this.bottomLeftY = y;
    }

    /**
     * Retrieves the x-coordinate of the bottom-left corner of the square obstacle.
     *
     * @return the x-coordinate of the bottom-left corner.
     */
    public int getBottomLeftX() {
        return this.bottomLeftX;
    }

    /**
     * Retrieves the y-coordinate of the bottom-left corner of the square obstacle.
     *
     * @return the y-coordinate of the bottom-left corner.
     */
    public int getBottomLeftY() {
        return this.bottomLeftY;
    }

    /**
     * Retrieves the size of the square obstacle.
     *
     * @return the size of the square obstacle.
     */
    public int getSize() {
        return 5;
    }

    /**
     * Checks if the square obstacle blocks the given position.
     *
     * @param position the position to check.
     * @return true if the square obstacle blocks the position, false otherwise.
     */
    public boolean blocksPosition(Position position) {
        return (position.getX() >= this.bottomLeftX && position.getX() <= this.bottomLeftX + 4)
                && (position.getY() >= this.bottomLeftY && position.getY() <= this.bottomLeftY + 4);
    }

    /**
     * Checks if the square obstacle blocks the path between two positions.
     *
     * @param start the starting position of the path.
     * @param dest  the ending position of the path.
     * @return true if the square obstacle blocks the path, false otherwise.
     */
    public boolean blocksPath(Position start, Position dest) {
        if (start.getX() == dest.getX()) {
            return (this.bottomLeftY >= Math.min(start.getY(), dest.getY())
                    && this.bottomLeftY <= Math.max(start.getY(), dest.getY()))
                    && (start.getX() >= bottomLeftX && start.getX() <= bottomLeftX + 4);
        } else {
            return (this.bottomLeftX >= Math.min(start.getX(), dest.getX())
                    && this.bottomLeftX <= Math.max(start.getX(), dest.getX()))
                    && (start.getY() >= bottomLeftY && start.getY() <= bottomLeftY + 4);
        }
    }
}
