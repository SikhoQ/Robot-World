package za.co.wethinkcode.robotworlds.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.Direction;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrientationCommandTest {

    private OrientationCommand orientationCommand;
    private Robot mockRobot;
    private IWorld mockWorld;

    @BeforeEach
    void setUp() {
        // Initialize the OrientationCommand instance and mock objects
        orientationCommand = new OrientationCommand();
        mockRobot = mock(Robot.class);
        mockWorld = mock(IWorld.class);
    }

    @Test
    void testOrientationCommandNorth() {
        //  Set up the mock robot's direction to NORTH
        when(mockRobot.getDirection()).thenReturn(Direction.NORTH);

        // Execute the orientation command
        ServerResponse response = orientationCommand.execute(mockRobot, mockWorld);

        // Verify the response result is "OK"
        assertEquals("OK", response.getResult());

        // Verify the response state contains the correct direction
        Map<String, Object> state = response.getState();
        assertEquals(Direction.NORTH, state.get("direction"));
    }

    @Test
    void testOrientationCommandEast() {
        //  Set up the mock robot's direction to EAST
        when(mockRobot.getDirection()).thenReturn(Direction.EAST);

        // Execute the orientation command
        ServerResponse response = orientationCommand.execute(mockRobot, mockWorld);

        // Verify the response result is "OK"
        assertEquals("OK", response.getResult());

        //  Verify the response state contains the correct direction
        Map<String, Object> state = response.getState();
        assertEquals(Direction.EAST, state.get("direction"));
    }

    @Test
    void testOrientationCommandSouth() {
        //  Set up the mock robot's direction to SOUTH
        when(mockRobot.getDirection()).thenReturn(Direction.SOUTH);

        // Execute the orientation command
        ServerResponse response = orientationCommand.execute(mockRobot, mockWorld);

        // Verify the response result is "OK"
        assertEquals("OK", response.getResult());

        //  Verify the response state contains the correct direction
        Map<String, Object> state = response.getState();
        assertEquals(Direction.SOUTH, state.get("direction"));
    }

    @Test
    void testOrientationCommandWest() {
        //  Set up the mock robot's direction to WEST
        when(mockRobot.getDirection()).thenReturn(Direction.WEST);

        //  Execute the orientation command
        ServerResponse response = orientationCommand.execute(mockRobot, mockWorld);

        //  Verify the response result is "OK"
        assertEquals("OK", response.getResult());

        // Verify the response state contains the correct direction
        Map<String, Object> state = response.getState();
        assertEquals(Direction.WEST, state.get("direction"));
    }
}
