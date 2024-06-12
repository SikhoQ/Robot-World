package za.co.wethinkcode.robotworlds;

import java.util.Random;

/**
 * The Position class represents a position on a two-dimensional grid.
 */
public class Position {
    private final int x;
    private final int y;

    /**
     * Constructs a Position object with the specified x and y coordinates.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x-coordinate of the position.
     *
     * @return The x-coordinate of the position.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the position.
     *
     * @return The y-coordinate of the position.
     */
    public int getY() {
        return y;
    }

    /**
     * Checks if this position is within a given rectangle defined by its top-left and bottom-right corners.
     *
     * @param topLeft     The top-left corner of the rectangle.
     * @param bottomRight The bottom-right corner of the rectangle.
     * @return True if this position is within the rectangle, false otherwise.
     */
    public boolean isIn(Position topLeft, Position bottomRight) {
        boolean withinTop = this.y <= topLeft.getY();
        boolean withinBottom = this.y >= bottomRight.getY();
        boolean withinLeft = this.x >= topLeft.getX();
        boolean withinRight = this.x <= bottomRight.getX();
        return withinTop && withinBottom && withinLeft && withinRight;
    }

    /**
     * Generates a random Position object within a predefined range.
     *
     * @return A randomly generated Position object.
     */
    public static Position getRandomPosition() {
        Random random = new Random();
        int xMin = -100;
        int xMax = 100;
        int yMin = -200;
        int yMax = 200;

        int xCoord = random.nextInt((xMax - xMin) + 1) + xMin;
        int yCoord = random.nextInt((yMax - yMin) + 1) + yMin;

        return new Position(xCoord, yCoord);
    }
}
