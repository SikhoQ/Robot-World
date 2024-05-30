package za.co.wethinkcode.robotworlds;
import java.util.*;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public static Direction getRandomDirection() {
        Random random = new Random();
        int directionOrdinal = random.nextInt(4);
        return Direction.fromOrdinal(directionOrdinal);
    }

    private static Direction fromOrdinal(int ordinal) {
        if (ordinal < 0 || ordinal >= Direction.values().length) {
            throw new IllegalArgumentException("Invalid ordinal.");
        }
        return values()[ordinal];
    }
}
