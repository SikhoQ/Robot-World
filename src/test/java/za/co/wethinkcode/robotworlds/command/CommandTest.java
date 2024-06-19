package za.co.wethinkcode.robotworlds.command;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.world.IWorld;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommandTest {
    private IWorld world;

    @BeforeEach
    void setUp() {
        world = mock(IWorld.class);
        Map<Integer, Robot> robots = new HashMap<>();
        when(world.getRobots()).thenReturn(robots);
    }

    @Test
    void testCreateLaunchCommand() throws IOException {
        String jsonString = "{\"robot\":\"name1\",\"command\":\"LAUNCH\",\"arguments\":[\"make\",3,3]}";
        JsonNode jsonNode = new ObjectMapper().readTree(jsonString);

        Command command = Command.create(jsonNode, world);

        assertInstanceOf(LaunchCommand.class, command);
    }

    @Test
    void testCreateForwardCommand() throws IOException {
        String jsonString = "{\"robot\":\"name1\",\"command\":\"FORWARD\",\"arguments\":[\"5\"]}";
        JsonNode jsonNode = new ObjectMapper().readTree(jsonString);

        Command command = Command.create(jsonNode, world);

        assertInstanceOf(ForwardCommand.class, command);
    }

    @Test
    void testCreateInvalidCommandUnknownCommand() throws IOException {
        String jsonString = "{\"robot\":\"name1\",\"command\":\"UNKNOWN\",\"arguments\":[]}";
        JsonNode jsonNode = new ObjectMapper().readTree(jsonString);

        Command command = Command.create(jsonNode, world);

        assertInstanceOf(InvalidCommand.class, command);
        assertEquals("invalid", command.getCommand());
    }

    @Test
    void testCreateInvalidCommandBadArguments() throws IOException {
        String jsonString = "{\"robot\":\"name1\",\"command\":\"FORWARD\",\"arguments\":[\"-5\"]}";
        JsonNode jsonNode = new ObjectMapper().readTree(jsonString);

        Command command = Command.create(jsonNode, world);

        assertInstanceOf(InvalidCommand.class, command);
        assertEquals("invalid", command.getCommand());
    }

    @Test
    void testCreateInvalidCommandInvalidJson() throws IOException {
        String jsonString = "{\"robot\":\"name1\",\"command\":\"FORWARD\"}";
        JsonNode jsonNode = new ObjectMapper().readTree(jsonString);

        Command command = Command.create(jsonNode, world);

        assertInstanceOf(InvalidCommand.class, command);
        assertEquals("invalid", command.getCommand());
    }

    @Test
    void testValidateCommand() {
        String validResult = Command.validateCommand("name1", "FORWARD", new Object[]{"5"}, world);
        assertEquals("VALID COMMAND", validResult);

        String invalidCommandResult = Command.validateCommand("name1", "JUMP", new Object[]{}, world);
        assertEquals("UNKNOWN COMMAND", invalidCommandResult);

        String invalidArgumentsResult = Command.validateCommand("name1", "FORWARD", new Object[]{"-5"}, world);
        assertEquals("BAD ARGUMENTS", invalidArgumentsResult);

        String validTurnResult = Command.validateCommand("name1", "TURN", new Object[]{"LEFT"}, world);
        assertEquals("VALID COMMAND", validTurnResult);

        String invalidTurnArgumentsResult = Command.validateCommand("name1", "TURN", new Object[]{"UP"}, world);
        assertEquals("BAD ARGUMENTS", invalidTurnArgumentsResult);
    }

    @Test
    void testInvalidRobotProperties() {
        assertTrue(Command.invalidRobotProperties(-1, 3));
        assertTrue(Command.invalidRobotProperties(3, -1));
        assertFalse(Command.invalidRobotProperties(3, 3));
    }

    @Test
    void testInvalidRobotName() {
        Robot robot = new Robot(new String[]{"make", "name1"}, 3, new Position(1, 1), Direction.NORTH, 8080);
        Map<Integer, Robot> robots = new HashMap<>();
        robots.put(8080, robot);
        when(world.getRobots()).thenReturn(robots);

        assertFalse(Command.invalidRobotName("name1", world));
        assertFalse(Command.invalidRobotName("name2", world));
        assertTrue(Command.invalidRobotName("", world));
        assertTrue(Command.invalidRobotName("   ", world));
    }
}