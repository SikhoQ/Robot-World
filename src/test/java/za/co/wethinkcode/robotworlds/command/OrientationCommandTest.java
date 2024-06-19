package za.co.wethinkcode.robotworlds.command;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.robot.SimpleBot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.Direction;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class OrientationCommandTest {

    /**
     * Test the execute method of OrientationCommand.
     */
    @Test
    void testExecute() {
        // Create a Position and Direction for the SimpleBot
        Position position = new Position(0, 0); // Example position (adjust as per your Position class)
        Direction direction = Direction.NORTH; // Example direction

        // Create a SimpleBot instance with required parameters
        SimpleBot bot = new SimpleBot("BotName", position, direction, 100); // Adjust parameters as per your constructor

        // Mock IWorld interface if needed, or create a test implementation

        // Create an instance of OrientationCommand
        OrientationCommand orientationCommand = new OrientationCommand();

        // Execute the command
        ServerResponse serverResponse = orientationCommand.execute(bot, null); // Pass null for IWorld for simplicity

        // Verify the result
        assertNotNull(serverResponse);
        assertEquals("OK", serverResponse.getResult());

        // Verify data field
        Map<String, Object> data = serverResponse.getData();
        assertNotNull(data);
        assertNull(data.get("message")); // Ensure there is no message field in data for orientation command

        // Verify state field
        Map<String, Object> state = serverResponse.getState();
        assertNotNull(state);
        assertEquals(bot.getDirection(), state.get("direction")); // Verify direction field in state

    }

    @Test
    void testExecuteWithNullWorld() {
        SimpleBot bot = new SimpleBot("TestBot", null, Direction.NORTH, 100);
        OrientationCommand orientationCommand = new OrientationCommand();
        ServerResponse serverResponse = orientationCommand.execute(bot, null);
        assertNotNull(serverResponse);
        assertEquals("OK", serverResponse.getResult());
        assertEquals(Direction.NORTH, serverResponse.getState().get("direction"));
        assertNull(serverResponse.getData().get("visibility"));
}       @Test
    void testExecuteNorth() {
        SimpleBot bot = new SimpleBot("TestBot", null, Direction.NORTH, 100);
        OrientationCommand orientationCommand = new OrientationCommand();
        ServerResponse serverResponse = orientationCommand.execute(bot, null);
        assertNotNull(serverResponse);
        assertEquals("OK", serverResponse.getResult());
        assertEquals(Direction.NORTH, serverResponse.getState().get("direction"));
    }

    @Test
    void testExecuteEast() {
        SimpleBot bot = new SimpleBot("TestBot", null, Direction.EAST, 100);
        OrientationCommand orientationCommand = new OrientationCommand();
        ServerResponse serverResponse = orientationCommand.execute(bot, null);
        assertNotNull(serverResponse);
        assertEquals("OK", serverResponse.getResult());
        assertEquals(Direction.EAST, serverResponse.getState().get("direction"));
    }

}
