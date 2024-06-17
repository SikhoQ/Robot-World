package za.co.wethinkcode.robotworlds.command;

<<<<<<< HEAD
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.robot.SimpleBot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class InvalidCommandTest {

    @Test
    void testUnknownCommandError() {
        InvalidCommand command = new InvalidCommand("UNKNOWN COMMAND");
        IWorld mockWorld = mock(IWorld.class);
        SimpleBot mockBot = mock(SimpleBot.class);

        ServerResponse response = command.execute(mockBot, mockWorld);

        assertEquals("ERROR", response.getResult());
        assertTrue(response.getData().containsKey("message"));
=======

import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class InvalidCommandTest {

    @Test
    public void testUnknownCommandError() {
        InvalidCommand invalidCommand = new InvalidCommand("UNKNOWN COMMAND");
        Robot mockRobot = mock(Robot.class);
        IWorld mockWorld = mock(IWorld.class);

        ServerResponse response = invalidCommand.execute(mockRobot, mockWorld);

        assertEquals("ERROR", response.getResult());
>>>>>>> sikho
        assertEquals("Unsupported command", response.getData().get("message"));
    }

    @Test
<<<<<<< HEAD
    void testBadArgumentsError() {
        InvalidCommand command = new InvalidCommand("BAD ARGUMENTS");
        IWorld mockWorld = mock(IWorld.class);
        SimpleBot mockBot = mock(SimpleBot.class);

        ServerResponse response = command.execute(mockBot, mockWorld);

        assertEquals("ERROR", response.getResult());
        assertTrue(response.getData().containsKey("message"));
=======
    public void testBadArgumentsError() {
        InvalidCommand invalidCommand = new InvalidCommand("BAD ARGUMENTS");
        Robot mockRobot = mock(Robot.class);
        IWorld mockWorld = mock(IWorld.class);

        ServerResponse response = invalidCommand.execute(mockRobot, mockWorld);

        assertEquals("ERROR", response.getResult());
>>>>>>> sikho
        assertEquals("Could not parse arguments", response.getData().get("message"));
    }

    @Test
<<<<<<< HEAD
    void testNoSpaceError() {
        InvalidCommand command = new InvalidCommand("NO SPACE");
        IWorld mockWorld = mock(IWorld.class);
        SimpleBot mockBot = mock(SimpleBot.class);

        ServerResponse response = command.execute(mockBot, mockWorld);

        assertEquals("ERROR", response.getResult());
        assertTrue(response.getData().containsKey("message"));
=======
    public void testNoSpaceError() {
        InvalidCommand invalidCommand = new InvalidCommand("NO SPACE");
        Robot mockRobot = mock(Robot.class);
        IWorld mockWorld = mock(IWorld.class);

        ServerResponse response = invalidCommand.execute(mockRobot, mockWorld);

        assertEquals("ERROR", response.getResult());
>>>>>>> sikho
        assertEquals("No more space in this world", response.getData().get("message"));
    }

    @Test
<<<<<<< HEAD
    void testNameTakenError() {
        InvalidCommand command = new InvalidCommand("NAME TAKEN");
        IWorld mockWorld = mock(IWorld.class);
        SimpleBot mockBot = mock(SimpleBot.class);

        ServerResponse response = command.execute(mockBot, mockWorld);

        assertEquals("ERROR", response.getResult());
        assertTrue(response.getData().containsKey("message"));
=======
    public void testNameTakenError() {
        InvalidCommand invalidCommand = new InvalidCommand("NAME TAKEN");
        Robot mockRobot = mock(Robot.class);
        IWorld mockWorld = mock(IWorld.class);

        ServerResponse response = invalidCommand.execute(mockRobot, mockWorld);

        assertEquals("ERROR", response.getResult());
>>>>>>> sikho
        assertEquals("Too many of you in this world", response.getData().get("message"));
    }

    @Test
<<<<<<< HEAD
    void testUnknownError() {
        InvalidCommand command = new InvalidCommand("UNKNOWN ERROR");
        IWorld mockWorld = mock(IWorld.class);
        SimpleBot mockBot = mock(SimpleBot.class);

        ServerResponse response = command.execute(mockBot, mockWorld);

        assertEquals("ERROR", response.getResult());
        assertFalse(response.getData().containsKey("message"));
=======
    public void testInvalidJsonFieldsError() {
        InvalidCommand invalidCommand = new InvalidCommand("INVALID JSON FIELDS");
        Robot mockRobot = mock(Robot.class);
        IWorld mockWorld = mock(IWorld.class);

        ServerResponse response = invalidCommand.execute(mockRobot, mockWorld);

        assertEquals("ERROR", response.getResult());
        assertEquals("Invalid JSON fields", response.getData().get("message"));
>>>>>>> sikho
    }
}