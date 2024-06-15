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

//    @Test
//    void testGetReload() {
//        assertEquals(5, textWorld.getReload(), "Reload value should match the default configuration");
//    }

//    @Test
//    void testGetRepair() {
//        assertEquals(5, textWorld.getRepair(), "Repair value should match the default configuration");
//    }

//    @Test
//    void testGetShields() {
//        assertEquals(5, textWorld.getShields(), "Shields value should match the default configuration");
//    }

    @Test
    void testGetVisibility() {
        assertEquals(50, textWorld.getVisibility(), "Visibility value should match the default configuration");
    }

    @Test
    void testGetWorldEdges() {
        assertNotNull(textWorld.getWorldEdges(), "World edges should not be null");
    }

//    @Test
//    void testShowObstacles() {
//        // Mock an obstacle list for the TextWorld instance
//        List<Obstacle> mockObstacles = List.of(mock(Obstacle.class));
//        when(textWorld.getObstacles()).thenReturn(mockObstacles);
//
//        // Test the showObstacles method
//        textWorld.showObstacles();
//        // Verify that System.out.println was called at least once (you can't verify output in JUnit directly)
//        verify(textWorld, atLeastOnce()).showObstacles();
//    }

    @Test
    void testGetObstacles() {
        List<Obstacle> obstacles = textWorld.getObstacles();
        assertNotNull(obstacles, "Obstacles list should not be null");
        assertFalse(obstacles.isEmpty(), "Obstacles list should not be empty");
    }

//    @Test
//    void testIsAtEdge() {
//        assertFalse(textWorld.isAtEdge(), "Initially, the position should not be at the edge");
//        // Test a specific edge condition, assuming default world size
//        textWorld.updatePosition(100);
//        assertTrue(textWorld.isAtEdge(), "After moving, the position should be at the edge");
//    }

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

//    @Test
//    void testUpdatePosition_Success() {
//        Position currentPosition = textWorld.getPosition();
//        assertEquals(Position.CENTRE, currentPosition, "Initial position should be the centre");
//
//        // Move North by 5 steps
//        assertEquals(TextWorld.UpdateResponse.SUCCESS, textWorld.updatePosition(5), "Update should be successful");
//        assertEquals(new Position(0, 5), textWorld.getPosition(), "Position should be updated correctly");
//    }

//    @Test
//    void testUpdatePosition_FailedOutsideWorld() {
//        // Move South by 201 steps (outside default world size)
//        assertEquals(TextWorld.UpdateResponse.FAILED_OUTSIDE_WORLD, textWorld.updatePosition(201),
//                "Update should fail as it's outside the world");
//        assertEquals(Position.CENTRE, textWorld.getPosition(), "Position should remain at the centre");
//    }

//    @Test
//    void testLaunchRobot_SimpleBot() {
//        SimpleBot robot = textWorld.launchRobot("SIMPLEBOT", "TestBot", 10, 123);
//        assertNotNull(robot, "Launched robot should not be null");
//        assertEquals("TestBot", robot.getName(), "Robot name should match");
//    }
//
//    @Test
//    void testLaunchRobot_SniperBot() {
//        SimpleBot robot = textWorld.launchRobot("SNIPERBOT", "SniperBot", 15, 456);
//        assertNotNull(robot, "Launched robot should not be null");
//        assertEquals("SniperBot", robot.getName(), "Robot name should match");
//    }
//
//    @Test
//    void testRemoveRobot() {
//        SimpleBot robot = textWorld.launchRobot("SIMPLEBOT", "TestBot", 10, 123);
//        assertNotNull(robot, "Launched robot should not be null");
//
//        textWorld.removeRobot(123);
//        assertTrue(textWorld.getRobots().isEmpty(), "Robots map should be empty after removal");
//    }

    //    @Test
//    void testValidateLaunchPosition() {
//        Position position = textWorld.validateLaunchPosition(Position.CENTRE);
//        assertFalse(position.equals(Position.CENTRE), "Validated position should not be the centre");
//    }
//
//    @Test
    void testShowWorldState() {
        // Just invoke the method and see if any exceptions are thrown
        assertDoesNotThrow(() -> textWorld.showWorldState(), "showWorldState should not throw exceptions");
    }

    @Test
    void testShowRobots_NoRobots() {
        // No robots launched initially
        assertDoesNotThrow(() -> textWorld.showRobots(), "showRobots should not throw exceptions");
    }

//    @Test
//    void testShowRobots_WithRobots() {
//        textWorld.launchRobot("SIMPLEBOT", "TestBot1", 10, 123);
//        textWorld.launchRobot("SNIPERBOT", "SniperBot", 15, 456);
//
//        // Just invoke the method and see if any exceptions are thrown
//        assertDoesNotThrow(() -> textWorld.showRobots(), "showRobots should not throw exceptions");
//    }
//}
}
