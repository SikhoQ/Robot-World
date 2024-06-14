package za.co.wethinkcode.robotworlds.command;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.co.wethinkcode.robotworlds.world.IWorld;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class CommandTest {

    @Mock
    private IWorld mockWorld;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        // Initialize mocks and object mapper
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateLaunchCommand() throws Exception {
        // Arrange: Prepare a JSON string representing a launch command
        String jsonString = "{ \"robot\": \"robot1\", \"command\": \"launch\", \"arguments\": [\"SIMPLEBOT\", \"robot1\"] }";
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        // Mock the world to return an empty map for robots
        when(mockWorld.getRobots()).thenReturn(Map.of());

        // Act: Create a command from the JSON node
        Command command = Command.create(jsonNode, mockWorld);

        // Assert: Verify the command is an instance of LaunchCommand
        assertTrue(command instanceof LaunchCommand);
    }

    @Test
    public void testCreateLookCommand() throws Exception {
        // Arrange: Prepare a JSON string representing a look command
        String jsonString = "{ \"robot\": \"robot1\", \"command\": \"look\", \"arguments\": [] }";
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        // Act: Create a command from the JSON node
        Command command = Command.create(jsonNode, mockWorld);

        // Assert: Verify the command is an instance of LookCommand
        assertTrue(command instanceof LookCommand);
    }

    @Test
    public void testCreateStateCommand() throws Exception {
        // Arrange: Prepare a JSON string representing a state command
        String jsonString = "{ \"robot\": \"robot1\", \"command\": \"state\", \"arguments\": [] }";
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        // Act: Create a command from the JSON node
        Command command = Command.create(jsonNode, mockWorld);

        // Assert: Verify the command is an instance of StateCommand
        assertTrue(command instanceof StateCommand);
    }

    @Test
    public void testCreateForwardCommand() throws Exception {
        // Arrange: Prepare a JSON string representing a forward command
        String jsonString = "{ \"robot\": \"robot1\", \"command\": \"forward\", \"arguments\": [\"5\"] }";
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        // Act: Create a command from the JSON node
        Command command = Command.create(jsonNode, mockWorld);

        // Assert: Verify the command is an instance of ForwardCommand
        assertTrue(command instanceof ForwardCommand);
    }

    @Test
    public void testCreateInvalidCommand() throws Exception {
        // Arrange: Prepare a JSON string representing an unknown command
        String jsonString = "{ \"robot\": \"robot1\", \"command\": \"unknown\", \"arguments\": [] }";
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        // Act: Create a command from the JSON node
        Command command = Command.create(jsonNode, mockWorld);

        // Assert: Verify the command is an instance of InvalidCommand
        assertTrue(command instanceof InvalidCommand);
    }

    @Test
    public void testCreateCommandWithBadArguments() throws Exception {
        // Arrange: Prepare a JSON string representing a forward command with invalid arguments
        String jsonString = "{ \"robot\": \"robot1\", \"command\": \"forward\", \"arguments\": [\"-5\"] }";
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        // Act: Create a command from the JSON node
        Command command = Command.create(jsonNode, mockWorld);

        // Assert: Verify the command is an instance of InvalidCommand
        assertTrue(command instanceof InvalidCommand);
    }
}

