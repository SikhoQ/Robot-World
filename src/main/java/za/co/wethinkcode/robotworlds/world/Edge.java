package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.Position;

public class Edge {
    private final Position topLeft;
    private final Position bottomRight;

    public Edge(Position topLeft, Position bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public int getMinimumX() {
        return topLeft.getX();
    }

    public int getMaximumX() {
        return bottomRight.getX();
    }

    public int getMinimumY() {
        return bottomRight.getY();
    }

    public int getMaxmimumY() {
        return topLeft.getY();
    }
}
