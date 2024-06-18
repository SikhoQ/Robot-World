package za.co.wethinkcode.robotworlds;

import java.util.Random;

/**
 * The Direction enum represents cardinal directions: NORTH, SOUTH, WEST, and EAST.
 */
public enum Direction {
    NORTH, SOUTH, WEST, EAST;

    /**
     * Generates a random direction.
     * @return A randomly selected Direction.
     */
    public static Direction getRandomDirection() {
        Random random = new Random();
        int directionOrdinal = random.nextInt(4);
        return Direction.fromOrdinal(directionOrdinal);
    }

    /**
     * Converts an ordinal value to a Direction enum value.
     * @param ordinal The ordinal value of the direction.
     * @return The Direction enum corresponding to the ordinal value.
     * @throws IllegalArgumentException if the ordinal is out of range.
     */
    public static Direction fromOrdinal(int ordinal) {
        if (ordinal < 0 || ordinal >= Direction.values().length) {
            throw new IllegalArgumentException("Invalid ordinal.");
        }
        return values()[ordinal];
    }
}
