package za.co.wethinkcode.robotworlds.server;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
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

    @Test
    public void testServerStart() {
        assertTrue(server.isAlive());
    }

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

//    @Test
//    public void testClientConnection() {
//        // Assuming your server prints out client connections
//        assertTrue(outContent.toString().contains(" Client connected: "));
//    }
//
//    @Ignore
//    @Test
//    public void testQuitCommand() {
//        // Implement your test for the quit command here
//    }
}
