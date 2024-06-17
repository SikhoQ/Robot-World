package za.co.wethinkcode.robotworlds.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import za.co.wethinkcode.robotworlds.world.TextWorld;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServerConsoleTest {

    private RobotWorldServer mockServer;
    private TextWorld mockWorld;

    @BeforeEach
    public void setUp() {
        mockServer = mock(RobotWorldServer.class);
        mockWorld = mock(TextWorld.class);
    }

    @Test
    public void testQuitCommand() {
        String input = "QUIT\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ServerConsole console = new ServerConsole(mockServer, mockWorld);
        Thread consoleThread = new Thread(console);
        consoleThread.start();

        try {
            consoleThread.join(1000);  // wait for the thread to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        verify(mockServer, times(1)).shutdown();
    }

    @Test
    public void testRobotsCommand() {
        String input = "ROBOTS\nQUIT\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ServerConsole console = new ServerConsole(mockServer, mockWorld);
        Thread consoleThread = new Thread(console);
        consoleThread.start();

        try {
            consoleThread.join(1000);  // wait for the thread to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        verify(mockWorld, times(1)).showRobots();
    }

    @Test
    public void testDumpCommand() {
        String input = "DUMP\nQUIT\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ServerConsole console = new ServerConsole(mockServer, mockWorld);
        Thread consoleThread = new Thread(console);
        consoleThread.start();

        try {
            consoleThread.join(1000);  // wait for the thread to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        verify(mockWorld, times(1)).showWorldState();
    }

    @Test
    public void testUnknownCommand() {
        String input = "UNKNOWN\nQUIT\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        ServerConsole console = new ServerConsole(mockServer, mockWorld);
        Thread consoleThread = new Thread(console);
        consoleThread.start();

        try {
            consoleThread.join(1000);  // wait for the thread to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String consoleOutput = out.toString();
        assertTrue(consoleOutput.contains("Unknown command: UNKNOWN"));
    }
}

