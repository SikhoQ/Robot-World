package za.co.wethinkcode.robotworlds.world;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TextWorldTest {

    private IWorld textWorld;

    @BeforeEach
    void setUp() {
        textWorld = new TextWorld();
    }


    @Test
    void testGetWorldEdges() {
        assertNotNull(textWorld.getWorldEdges(), "World edges should not be null");
    }


    @Test
    void testReset() {
        textWorld.reset();
//        assertEquals(new Position(0, 0), textWorld.getPosition(), "Position should be reset to the center");
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
