<<<<<<< HEAD
package za.co.wethinkcode.robotworlds.server;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import org.junit.*;

public class RobotWorldServerTest {
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static final int PORT = 5000;
    private static RobotWorldServer server;
    private static Thread serverThread;

    @BeforeClass
    public static void setUpBeforeClass() {
        System.setOut(new PrintStream(outContent));
        server = new RobotWorldServer(PORT);
        serverThread = new Thread(server::start);
        serverThread.start();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        System.setOut(originalOut);
        server.shutdown();
        try {
            serverThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() {
        outContent.reset();
    }

    @After
    public void tearDown() {
        outContent.reset();
    }
=======
//package za.co.wethinkcode.robotworlds.server;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//import static org.mockito.Mockito.*;
//
//class RobotWorldServerTest {
//    private RobotWorldServer server;
//    @Mock
//    private ServerSocket mockServerSocket;
//
//    @BeforeEach
//    void setUp() {
//
////        mockServerSocket = mock(ServerSocket.class);
//        server = new RobotWorldServer(8080);
//    }
//
//    @Test
//    void testRun_ServerStarted() throws IOException {
//        // Mock server socket behavior
//        Socket mockSocket = mock(Socket.class);
//        when(mockServerSocket.accept()).thenReturn(mockSocket);
//
//        // Run the server in a separate thread to simulate real behavior
//        Thread serverThread = new Thread(() -> server.start());
//        serverThread.start();
//
//        // Allow the server thread to start and accept at least one connection
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // Verify that the server socket was opened and accepted at least once
//        verify(mockServerSocket, atLeastOnce()).accept();
//
//        // Stop the server to clean up the test
//        server.shutdown();
//        serverThread.interrupt();
//    }
//}
>>>>>>> sikho

    @Test
    public void testServerStart() {
        assertTrue(server.isAlive());
    }

<<<<<<< HEAD
    @Test
    public void testAcceptMultipleClients() throws IOException, InterruptedException {
        // Simulate multiple client connections
        try (Socket clientSocket1 = new Socket("localhost", PORT);
             Socket clientSocket2 = new Socket("localhost", PORT)) {
            // Verify that both clients are accepted
            assertTrue(clientSocket1.isConnected());
            assertTrue(clientSocket2.isConnected());
        }
    }

}
=======
//
//    @Test
//    void testShutdown_ServerStopped() throws IOException {
//        // Mock server socket behavior
//        when(mockServerSocket.accept()).thenReturn(null);
//
//        // Set up the server with the mock server socket
//        server.serverSocket = mockServerSocket;
//
//        // Mock client handlers
//        RobotClientHandler mockClientHandler1 = mock(RobotClientHandler.class);
//        RobotClientHandler mockClientHandler2 = mock(RobotClientHandler.class);
//        List<RobotClientHandler> mockClients = List.of(mockClientHandler1, mockClientHandler2);
//        server.clients.addAll(mockClients);
//
//        // Run the server in a separate thread to simulate real behavior
//        Thread serverThread = new Thread(() -> server.run());
//        serverThread.start();
//
//        // Shutdown the server
//        server.shutdown();
//
//        // Verify that the server socket was closed
//        verify(mockServerSocket).close();
//
//        // Verify that each client handler was disconnected
//        verify(mockClientHandler1).disconnectClient();
//        verify(mockClientHandler2).disconnectClient();
//    }
//
//    // Add more unit tests to cover other server behaviors, such as handling client connections and exceptions.
>>>>>>> sikho
