package za.co.wethinkcode.robotworlds.world;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.world.Edge;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {

    @Test
    void testGetMinimumX() {
        Position topLeft = new Position(-10, 10);
        Position bottomRight = new Position(10, -10);
        Edge edge = new Edge(topLeft, bottomRight);
        assertEquals(-10, edge.getMinimumX(), "Minimum X coordinate should be -10");
    }

    @Test
    void testGetMaximumX() {
        Position topLeft = new Position(-10, 10);
        Position bottomRight = new Position(10, -10);
        Edge edge = new Edge(topLeft, bottomRight);
        assertEquals(10, edge.getMaximumX(), "Maximum X coordinate should be 10");
    }

    @Test
    void testGetMinimumY() {
        Position topLeft = new Position(-10, 10);
        Position bottomRight = new Position(10, -10);
        Edge edge = new Edge(topLeft, bottomRight);
        assertEquals(-10, edge.getMinimumY(), "Minimum Y coordinate should be -10");
    }

    @Test
    void testGetMaximumY() {
        Position topLeft = new Position(-10, 10);
        Position bottomRight = new Position(10, -10);
        Edge edge = new Edge(topLeft, bottomRight);
        assertEquals(10, edge.getMaximumY(), "Maximum Y coordinate should be 10");
    }
}
