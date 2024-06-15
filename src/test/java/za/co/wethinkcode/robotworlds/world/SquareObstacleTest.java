package za.co.wethinkcode.robotworlds.world;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.world.SquareObstacle;

import static org.junit.jupiter.api.Assertions.*;

class SquareObstacleTest {

    @Test
    void testBlocksPosition_InsideObstacle() {
        SquareObstacle obstacle = new SquareObstacle(0, 0);
        Position positionInside = new Position(2, 2);
        assertTrue(obstacle.blocksPosition(positionInside), "Position inside obstacle should be blocked");
    }

    @Test
    void testBlocksPosition_OutsideObstacle() {
        SquareObstacle obstacle = new SquareObstacle(0, 0);
        Position positionOutside = new Position(10, 10);
        assertFalse(obstacle.blocksPosition(positionOutside), "Position outside obstacle should not be blocked");
    }

    @Test
    void testBlocksPath_VerticalPathBlocked() {
        SquareObstacle obstacle = new SquareObstacle(5, 5);
        Position start = new Position(5, 0);
        Position destination = new Position(5, 10);
        assertTrue(obstacle.blocksPath(start, destination), "Vertical path should be blocked by the obstacle");
    }

    @Test
    void testBlocksPath_HorizontalPathBlocked() {
        SquareObstacle obstacle = new SquareObstacle(5, 5);
        Position start = new Position(0, 5);
        Position destination = new Position(10, 5);
        assertTrue(obstacle.blocksPath(start, destination), "Horizontal path should be blocked by the obstacle");
    }

    @Test
    void testBlocksPath_PathNotBlocked() {
        SquareObstacle obstacle = new SquareObstacle(5, 5);
        Position start = new Position(0, 0);
        Position destination = new Position(10, 10);
        assertFalse(obstacle.blocksPath(start, destination), "Path should not be blocked by the obstacle");
    }
}
