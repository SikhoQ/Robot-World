package za.co.wethinkcode.robotworlds.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RightCommandTest {

    private RightCommand rightCommand;
    private Robot mockRobot;
    private IWorld mockWorld;

    @BeforeEach
    void setUp() {
        // Initialize the RightCommand instance and mock objects
        rightCommand = new RightCommand();
        mockRobot = mock(Robot.class);
        mockWorld = mock(IWorld.class);
    }

    @Test
    void testExecuteFromNorth() {
        // Arrange: Set up the mock robot's direction to NORTH
        when(mockRobot.getDirection()).thenReturn(Direction.NORTH);
        when(mockRobot.getPosition()).thenReturn(new Position(0, 0));
        when(mockRobot.getShields()).thenReturn(5);
        when(mockRobot.getShots()).thenReturn(3);
        when(mockRobot.getStatus()).thenReturn("ACTIVE");

        // Execute the right command
        ServerResponse response = rightCommand.execute(mockRobot, mockWorld);

        // Verify that the robot's direction is updated correctly to EAST
        verify(mockRobot).setDirection(Direction.EAST);

        // Verify response result and data
        assertEquals("OK", response.getResult());
        Map<String, Object> responseData = response.getData();
        assertEquals("DONE", responseData.get("message"));

        // Verify response state
        Map<String, Object> responseState = response.getState();
        assertEquals(Direction.NORTH, responseState.get("direction"));
        assertEquals(new Position(0, 0), responseState.get("position"));
        assertEquals(5, responseState.get("shields"));
        assertEquals(3, responseState.get("shots"));
        assertEquals("ACTIVE", responseState.get("status"));
    }

    @Test
    void testExecuteFromSouth() {
        //  Set up the mock robot's direction to SOUTH
        when(mockRobot.getDirection()).thenReturn(Direction.SOUTH);
        when(mockRobot.getPosition()).thenReturn(new Position(0, 0));
        when(mockRobot.getShields()).thenReturn(5);
        when(mockRobot.getShots()).thenReturn(3);
        when(mockRobot.getStatus()).thenReturn("ACTIVE");

        //  Execute the right command
        ServerResponse response = rightCommand.execute(mockRobot, mockWorld);

        // Verify that the robot's direction is updated correctly to WEST
        verify(mockRobot).setDirection(Direction.WEST);

        // Verify response result and data
        assertEquals("OK", response.getResult());
        Map<String, Object> responseData = response.getData();
        assertEquals("DONE", responseData.get("message"));

        // Verify response state
        Map<String, Object> responseState = response.getState();
        assertEquals(Direction.SOUTH, responseState.get("direction"));
        assertEquals(new Position(0, 0), responseState.get("position"));
        assertEquals(5, responseState.get("shields"));
        assertEquals(3, responseState.get("shots"));
        assertEquals("ACTIVE", responseState.get("status"));
    }

    @Test
    void testExecuteFromWest() {
        //  Set up the mock robot's direction to WEST
        when(mockRobot.getDirection()).thenReturn(Direction.WEST);
        when(mockRobot.getPosition()).thenReturn(new Position(0, 0));
        when(mockRobot.getShields()).thenReturn(5);
        when(mockRobot.getShots()).thenReturn(3);
        when(mockRobot.getStatus()).thenReturn("ACTIVE");

        //  Execute the right command
        ServerResponse response = rightCommand.execute(mockRobot, mockWorld);

        //  Verify that the robot's direction is updated correctly to NORTH
        verify(mockRobot).setDirection(Direction.NORTH);

        //  Verify response result and data
        assertEquals("OK", response.getResult());
        Map<String, Object> responseData = response.getData();
        assertEquals("DONE", responseData.get("message"));

        // Verify response state
        Map<String, Object> responseState = response.getState();
        assertEquals(Direction.WEST, responseState.get("direction"));
        assertEquals(new Position(0, 0), responseState.get("position"));
        assertEquals(5, responseState.get("shields"));
        assertEquals(3, responseState.get("shots"));
        assertEquals("ACTIVE", responseState.get("status"));
    }

    @Test
    void testExecuteFromEast() {
        //  Set up the mock robot's direction to EAST
        when(mockRobot.getDirection()).thenReturn(Direction.EAST);
        when(mockRobot.getPosition()).thenReturn(new Position(0, 0));
        when(mockRobot.getShields()).thenReturn(5);
        when(mockRobot.getShots()).thenReturn(3);
        when(mockRobot.getStatus()).thenReturn("ACTIVE");

        // Execute the right command
        ServerResponse response = rightCommand.execute(mockRobot, mockWorld);

        // Verify that the robot's direction is updated correctly to SOUTH
        verify(mockRobot).setDirection(Direction.SOUTH);

        //  Verify response result and data
        assertEquals("OK", response.getResult());
        Map<String, Object> responseData = response.getData();
        assertEquals("DONE", responseData.get("message"));

        // Verify response state
        Map<String, Object> responseState = response.getState();
        assertEquals(Direction.EAST, responseState.get("direction"));
        assertEquals(new Position(0, 0), responseState.get("position"));
        assertEquals(5, responseState.get("shields"));
        assertEquals(3, responseState.get("shots"));
        assertEquals("ACTIVE", responseState.get("status"));
    }
}