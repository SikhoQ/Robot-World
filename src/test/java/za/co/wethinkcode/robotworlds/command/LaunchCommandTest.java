//package za.co.wethinkcode.robotworlds.command;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import za.co.wethinkcode.robotworlds.Position;
//import za.co.wethinkcode.robotworlds.robot.Gun;
//import za.co.wethinkcode.robotworlds.robot.SimpleBot;
//import za.co.wethinkcode.robotworlds.server.ServerResponse;
//import za.co.wethinkcode.robotworlds.world.IWorld;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
///**
// * Unit tests for the LaunchCommand class.
// */
//public class LaunchCommandTest {
//
//    private LaunchCommand launchCommand;
//    private Object[] arguments;
//    private JsonNode mockJsonNode;
//    private IWorld mockWorld;
//
//    @BeforeEach
//    void setUp() {
//        arguments = new Object[]{"Make", "Model", 10}; // Example arguments
//        launchCommand = new LaunchCommand(arguments);
//
//        // Mock dependencies
//        mockJsonNode = mock(JsonNode.class);
//        mockWorld = mock(IWorld.class);
//    }
//
//    /**
//     * Test the createRobot method of LaunchCommand.
//     */
//    @Test
//    void testCreateRobot() {
//        // Mock behavior for JsonNode and IWorld
//        when(Json.getJsonFields(mockJsonNode)).thenReturn(new HashMap<>() {{
//            put("robot", "RobotName");
//        }});
//        when(mockWorld.launchRobot("Make", "RobotName", 10, 1234)).thenReturn(new SimpleBot());
//
//        // Call createRobot method
//        SimpleBot bot = launchCommand.createRobot(mockJsonNode, mockWorld, 1234);
//
//        // Verify the expected behavior
//        assertNotNull(bot);
//        assertEquals("Make", launchCommand.getArguments()[0]);
//        assertEquals("Model", launchCommand.getArguments()[1]); // Example, adjust as per your actual arguments
//        assertEquals(10, launchCommand.getArguments()[2]); // Example, adjust as per your actual arguments
//    }
//
//    /**
//     * Test the execute method of LaunchCommand.
//     */
//    @Test
//    void testExecute() {
//        // Setup a mock SimpleBot
//        SimpleBot mockBot = mock(SimpleBot.class);
//        when(mockBot.getPosition()).thenReturn(new Position(0, 0)); // Example position
//
//        // Mock behavior for IWorld
//        when(mockWorld.getVisibility()).thenReturn(100); // Example visibility
//        when(mockWorld.getReload()).thenReturn(5); // Example reload time
//        when(mockWorld.getRepair()).thenReturn(true); // Example repair availability
//        when(mockWorld.getShields()).thenReturn(200); // Example shields
//
//        // Mock behavior for Gun in SimpleBot
//        Gun mockGun = mock(Gun.class);
//        when(mockBot.getGun()).thenReturn(mockGun);
//        when(mockGun.getNumberOfShots()).thenReturn(20); // Example shots
//
//        // Mock status
//        when(mockBot.getStatus()).thenReturn("Active"); // Example status
//
//        // Execute the command
//        ServerResponse serverResponse = launchCommand.execute(mockBot, mockWorld);
//
//        // Verify the result
//        assertNotNull(serverResponse);
//        assertEquals("OK", serverResponse.getResult());
//
//        // Verify data field
//        Map<String, Object> data = serverResponse.getData();
//        assertNotNull(data);
//        assertEquals(0, ((Position) data.get("position")).getX()); // Adjust based on your Position class
//        assertEquals(0, ((Position) data.get("position")).getY()); // Adjust based on your Position class
//        assertEquals(100, data.get("visibility")); // Example visibility
//        assertEquals(5, data.get("reload")); // Example reload time
//        assertEquals(true, data.get("repair")); // Example repair availability
//        assertEquals(200, data.get("shields")); // Example shields
//
//        // Verify state field
//        Map<String, Object> state = serverResponse.getState();
//        assertNotNull(state);
//        assertEquals(0, ((Position) state.get("position")).getX()); // Adjust based on your Position class
//        assertEquals(0, ((Position) state.get("position")).getY()); // Adjust based on your Position class
//        assertEquals(mockBot.getDirection(), state.get("direction")); // Assuming SimpleBot.getDirection() returns Direction enum
//        assertEquals(mockBot.getShields(), state.get("shields")); // Example shields
//        assertEquals(20, state.get("shots")); // Example shots
//        assertEquals("Active", state.get("status")); // Example status
//    }
//}