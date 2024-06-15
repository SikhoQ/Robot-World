package za.co.wethinkcode.robotworlds.maze;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.world.Obstacle;
import za.co.wethinkcode.robotworlds.world.SquareObstacle;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleMazeTest {

    private SimpleMaze simpleMaze;

    @BeforeEach
    void setUp() {
        simpleMaze = new SimpleMaze();
    }

    @Test
    void testGetObstacles() {
        List<Obstacle> obstacles = simpleMaze.getObstacles();
        assertNotNull(obstacles, "The obstacles list should not be null");
        assertEquals(1, obstacles.size(), "The obstacles list should contain one obstacle");
        assertTrue(obstacles.get(0) instanceof SquareObstacle, "The obstacle should be an instance of SquareObstacle");
        assertEquals(1, obstacles.get(0).getBottomLeftX(), "The obstacle's bottom left X coordinate should be 1");
        assertEquals(1, obstacles.get(0).getBottomLeftY(), "The obstacle's bottom left Y coordinate should be 1");
    }

    @Test
    void testBlocksPath_verticalBlocked() {
        Position start = new Position(1, 0);
        Position destination = new Position(1, 5);
        simpleMaze.getObstacles(); // Create the obstacles
        assertTrue(simpleMaze.blocksPath(start, destination), "Path should be blocked by the obstacle");
    }

    @Test
    void testBlocksPath_horizontalBlocked() {
        Position start = new Position(0, 1);
        Position destination = new Position(5, 1);
        simpleMaze.getObstacles(); // Create the obstacles
        assertTrue(simpleMaze.blocksPath(start, destination), "Path should be blocked by the obstacle");
    }
}
