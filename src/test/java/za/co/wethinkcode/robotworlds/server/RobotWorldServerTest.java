/*
package za.co.wethinkcode.robotworlds.server;


import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RobotWorldServerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final int PORT = 8080;
    private RobotWorldServer server;
    private Thread serverThread;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        server = new RobotWorldServer();
        serverThread = new Thread(server::start);
        serverThread.start();
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testServerStart() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            Socket clientSocket = new Socket("localhost", PORT);
            serverSocket.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testClientConnection() {
        try {

            Socket clientSocket = new Socket("localhost", PORT);
            assertEquals("Client connected: " + clientSocket + "\n", outContent.toString());
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testQuitCommand() {
        ;
    }
}
*/
