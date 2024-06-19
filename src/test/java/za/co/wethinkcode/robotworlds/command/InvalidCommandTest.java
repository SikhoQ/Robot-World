package za.co.wethinkcode.robotworlds.command;


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
        assertEquals("Unsupported command", response.getData().get("message"));
    }

    @Test
    public void testBadArgumentsError() {
        InvalidCommand invalidCommand = new InvalidCommand("BAD ARGUMENTS");
        Robot mockRobot = mock(Robot.class);
        IWorld mockWorld = mock(IWorld.class);

        ServerResponse response = invalidCommand.execute(mockRobot, mockWorld);

        assertEquals("ERROR", response.getResult());
        assertEquals("Could not parse arguments", response.getData().get("message"));
    }

    @Test
    public void testNoSpaceError() {
        InvalidCommand invalidCommand = new InvalidCommand("NO SPACE");
        Robot mockRobot = mock(Robot.class);
        IWorld mockWorld = mock(IWorld.class);

        ServerResponse response = invalidCommand.execute(mockRobot, mockWorld);

        assertEquals("ERROR", response.getResult());
        assertEquals("No more space in this world", response.getData().get("message"));
    }

    @Test
    public void testNameTakenError() {
        InvalidCommand invalidCommand = new InvalidCommand("NAME TAKEN");
        Robot mockRobot = mock(Robot.class);
        IWorld mockWorld = mock(IWorld.class);

        ServerResponse response = invalidCommand.execute(mockRobot, mockWorld);

        assertEquals("ERROR", response.getResult());
        assertEquals("Too many of you in this world", response.getData().get("message"));
    }

    @Test
    public void testInvalidJsonFieldsError() {
        InvalidCommand invalidCommand = new InvalidCommand("INVALID JSON FIELDS");
        Robot mockRobot = mock(Robot.class);
        IWorld mockWorld = mock(IWorld.class);

        ServerResponse response = invalidCommand.execute(mockRobot, mockWorld);

        assertEquals("ERROR", response.getResult());
        assertEquals("Invalid JSON fields", response.getData().get("message"));
    }
}