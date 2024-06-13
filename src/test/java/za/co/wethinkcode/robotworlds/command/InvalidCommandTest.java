package za.co.wethinkcode.robotworlds.command;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class InvalidCommandTest {

    @Test
    public void testUnknownCommandExecute() {
        // Create a mock Robot
        Robot robot = mock(Robot.class);

        // Create a mock IWorld
        IWorld world = mock(IWorld.class);

        // Create an InvalidCommand instance with error "UNKNOWN COMMAND"
        InvalidCommand invalidCommand = new InvalidCommand("UNKNOWN COMMAND");

        // Execute the command
        ServerResponse response = invalidCommand.execute(robot, world);

        // Check the result
        assertEquals("ERROR", response.getResult());

        // Check the data
        Map<String, Object> data = response.getData();
        assertEquals("Unsupported command", data.get("message"));

        // Check the state
        Map<String, Object> state = response.getState();
        assertTrue(state.isEmpty());
    }

    @Test
    public void testParseErrorExecute() {
        // Create a mock Robot
        Robot robot = mock(Robot.class);

        // Create a mock IWorld
        IWorld world = mock(IWorld.class);

        // Create an InvalidCommand instance with error "PARSE ERROR"
        InvalidCommand invalidCommand = new InvalidCommand("PARSE ERROR");

        // Execute the command
        ServerResponse response = invalidCommand.execute(robot, world);

        // Check the result
        assertEquals("ERROR", response.getResult());

        // Check the data
        Map<String, Object> data = response.getData();
        assertEquals("Could not parse arguments", data.get("message"));

        // Check the state
        Map<String, Object> state = response.getState();
        assertTrue(state.isEmpty());
    }
}
