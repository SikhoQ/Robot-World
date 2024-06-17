//package za.co.wethinkcode.robotworlds.command;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import za.co.wethinkcode.robotworlds.command.*;
//import za.co.wethinkcode.robotworlds.robot.Robot;
//import za.co.wethinkcode.robotworlds.world.IWorld;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.mock;
//
//public class CommandTest {
//
//    private IWorld mockWorld;
//
//    @BeforeEach
//    public void setUp() {
//        // Create a mock for IWorld
//        mockWorld = mock(IWorld.class);
//    }
//
//    @Test
//    public void testCreateLaunchCommand() {
//        JsonNode rootNode = createMockJsonNode("LAUNCH", "robotName", new Object[]{"make", 10, 5});
//        Command command = Command.create(rootNode, mockWorld);
//        assertEquals(LaunchCommand.class, command.getClass());
//    }
//
//    @Test
//    public void testCreateLookCommand() {
//        JsonNode rootNode = createMockJsonNode("LOOK", "robotName", null);
//        Command command = Command.create(rootNode, mockWorld);
//        assertEquals(LookCommand.class, command.getClass());
//    }
//
//    @Test
//    public void testCreateInvalidCommand() {
//        JsonNode rootNode = createMockJsonNode("INVALID_COMMAND", "robotName", null);
//        Command command = Command.create(rootNode, mockWorld);
//        assertEquals(InvalidCommand.class, command.getClass());
//    }
//
//    @Test
//    public void testValidateCommandWithInvalidArguments() {
//        String result = Command.validateCommand("", "LAUNCH", new Object[]{"", 10, 5}, mockWorld);
//        assertEquals("BAD ARGUMENTS", result);
//    }
//
//    @Test
//    public void testValidateCommandWithUnknownCommand() {
//        String result = Command.validateCommand("", "UNKNOWN", null, mockWorld);
//        assertEquals("UNKNOWN COMMAND", result);
//    }
//
//    private JsonNode createMockJsonNode(String command, String robot, Object[] arguments) {
//        Map<String, Object> jsonFields = new HashMap<>();
//        jsonFields.put("command", command);
//        jsonFields.put("robot", robot);
//        jsonFields.put("arguments", arguments);
//        return JsonUtility.toJsonNode(jsonFields);
//    }
//}
