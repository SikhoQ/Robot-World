package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.Position;

/**
 * The Edge class represents an edge defined by two positions: top-left and bottom-right.
 * It provides methods to retrieve the minimum and maximum coordinates of the edge.
 */
public class Edge {
    private final Position topLeft;
    private final Position bottomRight;

    /**
     * Constructs an Edge with the specified top-left and bottom-right positions.
     *
     * @param topLeft     the top-left position of the edge.
     * @param bottomRight the bottom-right position of the edge.
     */
    public Edge(Position topLeft, Position bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    /**
     * Gets the minimum x-coordinate of the edge.
     *
     * @return the minimum x-coordinate.
     */
    public int getMinimumX() {
        return topLeft.getX();
    }

    /**
     * Gets the maximum x-coordinate of the edge.
     *
     * @return the maximum x-coordinate.
     */
    public int getMaximumX() {
        return bottomRight.getX();
    }

    /**
     * Gets the minimum y-coordinate of the edge.
     *
     * @return the minimum y-coordinate.
     */
    public int getMinimumY() {
        return bottomRight.getY();
    }

    /**
     * Gets the maximum y-coordinate of the edge.
     *
     * @return the maximum y-coordinate.
     */
    public int getMaximumY() {
        return topLeft.getY();
    }
}
