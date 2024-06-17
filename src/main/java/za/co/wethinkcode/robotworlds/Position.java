package za.co.wethinkcode.robotworlds;

import za.co.wethinkcode.robotworlds.world.IWorld;
<<<<<<< HEAD
=======

>>>>>>> sikho
import java.util.Random;

public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;
    }

    public boolean isIn(Position topLeft, Position bottomRight) {
        boolean withinTop = this.y <= topLeft.getY();
        boolean withinBottom = this.y >= bottomRight.getY();
        boolean withinLeft = this.x >= topLeft.getX();
        boolean withinRight = this.x <= bottomRight.getX();
        return withinTop && withinBottom && withinLeft && withinRight;
    }
    
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
