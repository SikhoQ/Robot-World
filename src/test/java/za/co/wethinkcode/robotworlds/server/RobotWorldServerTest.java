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
