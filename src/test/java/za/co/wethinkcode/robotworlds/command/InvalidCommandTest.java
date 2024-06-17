package za.co.wethinkcode.robotworlds.command;

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
        assertEquals("Unsupported command", response.getData().get("message"));
    }

    @Test
    void testBadArgumentsError() {
        InvalidCommand command = new InvalidCommand("BAD ARGUMENTS");
        IWorld mockWorld = mock(IWorld.class);
        SimpleBot mockBot = mock(SimpleBot.class);

        ServerResponse response = command.execute(mockBot, mockWorld);

        assertEquals("ERROR", response.getResult());
        assertTrue(response.getData().containsKey("message"));
        assertEquals("Could not parse arguments", response.getData().get("message"));
    }

    @Test
    void testNoSpaceError() {
        InvalidCommand command = new InvalidCommand("NO SPACE");
        IWorld mockWorld = mock(IWorld.class);
        SimpleBot mockBot = mock(SimpleBot.class);

        ServerResponse response = command.execute(mockBot, mockWorld);

        assertEquals("ERROR", response.getResult());
        assertTrue(response.getData().containsKey("message"));
        assertEquals("No more space in this world", response.getData().get("message"));
    }

    @Test
    void testNameTakenError() {
        InvalidCommand command = new InvalidCommand("NAME TAKEN");
        IWorld mockWorld = mock(IWorld.class);
        SimpleBot mockBot = mock(SimpleBot.class);

        ServerResponse response = command.execute(mockBot, mockWorld);

        assertEquals("ERROR", response.getResult());
        assertTrue(response.getData().containsKey("message"));
        assertEquals("Too many of you in this world", response.getData().get("message"));
    }

    @Test
    void testUnknownError() {
        InvalidCommand command = new InvalidCommand("UNKNOWN ERROR");
        IWorld mockWorld = mock(IWorld.class);
        SimpleBot mockBot = mock(SimpleBot.class);

        ServerResponse response = command.execute(mockBot, mockWorld);

        assertEquals("ERROR", response.getResult());
        assertFalse(response.getData().containsKey("message"));
    }
}