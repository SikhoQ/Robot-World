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
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateLaunchCommand() throws Exception {
        // Arrange
        String jsonString = "{ \"robot\": \"robot1\", \"command\": \"launch\", \"arguments\": [\"SIMPLEBOT\", \"robot1\"] }";
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        when(mockWorld.getRobots()).thenReturn(Map.of());

        // Act
        Command command = Command.create(jsonNode, mockWorld);

        // Assert
        assertTrue(command instanceof LaunchCommand);
    }

    @Test
    public void testCreateLookCommand() throws Exception {
        // Arrange
        String jsonString = "{ \"robot\": \"robot1\", \"command\": \"look\", \"arguments\": [] }";
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        // Act
        Command command = Command.create(jsonNode, mockWorld);

        // Assert
        assertTrue(command instanceof LookCommand);
    }

    @Test
    public void testCreateStateCommand() throws Exception {
        // Arrange
        String jsonString = "{ \"robot\": \"robot1\", \"command\": \"state\", \"arguments\": [] }";
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        // Act
        Command command = Command.create(jsonNode, mockWorld);

        // Assert
        assertTrue(command instanceof StateCommand);
    }

    @Test
    public void testCreateForwardCommand() throws Exception {
        // Arrange
        String jsonString = "{ \"robot\": \"robot1\", \"command\": \"forward\", \"arguments\": [\"5\"] }";
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        // Act
        Command command = Command.create(jsonNode, mockWorld);

        // Assert
        assertTrue(command instanceof ForwardCommand);
    }

    @Test
    public void testCreateInvalidCommand() throws Exception {
        // Arrange
        String jsonString = "{ \"robot\": \"robot1\", \"command\": \"unknown\", \"arguments\": [] }";
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        // Act
        Command command = Command.create(jsonNode, mockWorld);

        // Assert
        assertTrue(command instanceof InvalidCommand);
    }

    @Test
    public void testCreateCommandWithBadArguments() throws Exception {
        // Arrange
        String jsonString = "{ \"robot\": \"robot1\", \"command\": \"forward\", \"arguments\": [\"-5\"] }";
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        // Act
        Command command = Command.create(jsonNode, mockWorld);

        // Assert
        assertTrue(command instanceof InvalidCommand);
    }
}
