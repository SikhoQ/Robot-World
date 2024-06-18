package za.co.wethinkcode.robotworlds;

import za.co.wethinkcode.robotworlds.world.IWorld;

import java.util.Random;

/**
 * Represents a position in the world.
 */
public class Position {
    private final int x;
    private final int y;

    /**
     * Constructs a Position object with the given x and y coordinates.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x-coordinate of the position.
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the position.
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Checks if the position is within the specified rectangular area.
     * @param topLeft The top-left corner of the area.
     * @param bottomRight The bottom-right corner of the area.
     * @return True if the position is within the area, false otherwise.
     */
    public boolean isIn(Position topLeft, Position bottomRight) {
        boolean withinTop = this.y <= topLeft.getY();
        boolean withinBottom = this.y >= bottomRight.getY();
        boolean withinLeft = this.x >= topLeft.getX();
        boolean withinRight = this.x <= bottomRight.getX();
        return withinTop && withinBottom && withinLeft && withinRight;
    }

    /**
     * Generates a random position within the boundaries of the world.
     * @param world The world in which the position should be generated.
     * @return A random Position object within the boundaries of the world.
     */
    public static Position getRandomPosition(IWorld world) {
        Random random = new Random();
        int xMin = world.getWorldEdges().getMinimumX();
        int xMax = world.getWorldEdges().getMaximumX();
        int yMin = world.getWorldEdges().getMinimumY();
        int yMax = world.getWorldEdges().getMaximumY();

        int xCoord = random.nextInt((xMax - xMin) + 1) + xMin;
        int yCoord = random.nextInt((yMax - yMin) + 1) + yMin;

        return new Position(xCoord, yCoord);
    }
}
