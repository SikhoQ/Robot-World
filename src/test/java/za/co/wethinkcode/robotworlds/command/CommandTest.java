package za.co.wethinkcode.robotworlds.command;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.robot.SimpleBot;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommandTest {

    private IWorld world;
    private SimpleBot robot;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        world = mock(IWorld.class);
        robot = mock(SimpleBot.class);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateLaunchCommand() throws Exception {
        String jsonString = "{\"robot\":\"testRobot\",\"command\":\"launch\",\"arguments\":[\"SimpleBot\", 5, 10]}";
        JsonNode rootNode = objectMapper.readTree(jsonString);
        Command command = Command.create(rootNode, world);
        assertTrue(command instanceof LaunchCommand);
    }

    @Test
    public void testCreateLookCommand() throws Exception {
        String jsonString = "{\"robot\":\"testRobot\",\"command\":\"look\"}";
        JsonNode rootNode = objectMapper.readTree(jsonString);
        Command command = Command.create(rootNode, world);
        assertTrue(command instanceof LookCommand);
    }

    @Test
    public void testCreateInvalidCommand() throws Exception {
        String jsonString = "{\"robot\":\"testRobot\",\"command\":\"invalidCommand\"}";
        JsonNode rootNode = objectMapper.readTree(jsonString);
        Command command = Command.create(rootNode, world);
        assertTrue(command instanceof InvalidCommand);
    }

    @Test
    public void testCreateForwardCommand() throws Exception {
        String jsonString = "{\"robot\":\"testRobot\",\"command\":\"forward\",\"arguments\":[5]}";
        JsonNode rootNode = objectMapper.readTree(jsonString);
        Command command = Command.create(rootNode, world);
        assertTrue(command instanceof ForwardCommand);
    }

    @Test
    public void testCreateTurnCommand() throws Exception {
        String jsonString = "{\"robot\":\"testRobot\",\"command\":\"turn\",\"arguments\":[\"right\"]}";
        JsonNode rootNode = objectMapper.readTree(jsonString);
        Command command = Command.create(rootNode, world);
        assertTrue(command instanceof RightCommand);
    }

    @Test
    public void testCreateInvalidArguments() throws Exception {
        String jsonString = "{\"robot\":\"testRobot\",\"command\":\"forward\",\"arguments\":[-1]}";
        JsonNode rootNode = objectMapper.readTree(jsonString);
        Command command = Command.create(rootNode, world);
        assertTrue(command instanceof InvalidCommand);
    }
}