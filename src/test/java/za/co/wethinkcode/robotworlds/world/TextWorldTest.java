package za.co.wethinkcode.robotworlds.world;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.robot.SimpleBot;
import za.co.wethinkcode.robotworlds.world.Obstacle;
import za.co.wethinkcode.robotworlds.world.TextWorld;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TextWorldTest {

    private TextWorld textWorld;

    @BeforeEach
    void setUp() {
        textWorld = new TextWorld();
    }


    @Test
    void testGetVisibility() {
        assertEquals(50, textWorld.getVisibility(), "Visibility value should match the default configuration");
    }

    @Test
    void testGetWorldEdges() {
        assertNotNull(textWorld.getWorldEdges(), "World edges should not be null");
    }



    @Test
    void testGetObstacles() {
        List<Obstacle> obstacles = textWorld.getObstacles();
        assertNotNull(obstacles, "Obstacles list should not be null");
        assertFalse(obstacles.isEmpty(), "Obstacles list should not be empty");
    }



    @Test
    void testReset() {
        textWorld.reset();
        assertEquals(new Position(0, 0), textWorld.getPosition(), "Position should be reset to the center");
        assertEquals(Direction.NORTH, textWorld.getCurrentDirection(), "Direction should be reset to NORTH");
    }

    @Test
    void testUpdateDirection() {
        textWorld.updateDirection(true); // turnRight
        assertEquals(Direction.EAST, textWorld.getCurrentDirection(), "Direction should be updated to EAST");
        textWorld.updateDirection(false); // turnLeft
        assertEquals(Direction.WEST, textWorld.getCurrentDirection(), "Direction should be updated to WEST");
    }


    @Test
    void testShowRobots_NoRobots() {
        assertDoesNotThrow(() -> textWorld.showRobots(), "showRobots should not throw exceptions");
    }


}
