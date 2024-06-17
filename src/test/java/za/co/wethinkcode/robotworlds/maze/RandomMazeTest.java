package za.co.wethinkcode.robotworlds.maze;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.world.Obstacle;
import za.co.wethinkcode.robotworlds.world.SquareObstacle;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.world.configuration.Config;
import za.co.wethinkcode.robotworlds.world.configuration.WorldSize;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class RandomMazeTest {

    private RandomMaze randomMaze;

    @BeforeEach
    void setUp() {
        randomMaze = new RandomMaze();
    }

    @Test
    void testCreateObstacles() throws Exception {
        // Set up configuration and reflection to access private method
        Config config = Config.readConfiguration();
        Method createObstacles = RandomMaze.class.getDeclaredMethod("createObstacles");
        createObstacles.setAccessible(true);

        // Invoke the private method
        createObstacles.invoke(randomMaze);

        // Check the state of the obstacles list
        List<Obstacle> obstacles = randomMaze.obstacles;
        assertNotNull(obstacles, "The obstacles list should not be null");

        // Verify the number of obstacles created is within the expected range
        int worldSize = config.getWorldSize().getHeight() * config.getWorldSize().getWidth();
        int minimumObstacles = (int) (worldSize * 0.001);
        int maximumObstacles = (int) (worldSize * 0.002);
        assertTrue(obstacles.size() >= minimumObstacles && obstacles.size() <= maximumObstacles,
                "The number of obstacles should be within the expected range");

        // Verify the obstacles are of the correct type and within bounds
        int worldX = config.getWorldSize().getWidth() / 2;
        int worldY = config.getWorldSize().getHeight() / 2;
        for (Obstacle obstacle : obstacles) {
            assertTrue(obstacle instanceof SquareObstacle, "Each obstacle should be an instance of SquareObstacle");
            int x = obstacle.getBottomLeftX();
            int y = obstacle.getBottomLeftY();
            assertTrue(x >= -worldX && x <= worldX, "Obstacle X coordinate should be within world bounds");
            assertTrue(y >= -worldY && y <= worldY, "Obstacle Y coordinate should be within world bounds");
        }
    }
}
