//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import za.co.wethinkcode.robotworlds.server.RobotWorldServer;
//import za.co.wethinkcode.robotworlds.server.ServerConsole;
//import za.co.wethinkcode.robotworlds.world.TextWorld;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.io.PrintStream;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.*;
//
//public class ServerConsoleTest {
//    private RobotWorldServer mockServer;
//    private TextWorld mockWorld;
//
//    @BeforeEach
//    public void setUp() {
//        mockServer = mock(RobotWorldServer.class);
//        mockWorld = mock(TextWorld.class);
//    }
//
//
//    @Test
//    public void testProcessInputDump() {
//        ServerConsole serverConsole = new ServerConsole(mockServer, mockWorld);
//        serverConsole.processInput("DUMP");
//        verify(mockServer, times(1)).showWorldState(mockWorld);
//    }
//
//    @Test
//    public void testProcessInputUnknownCommand() {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outputStream));
//        ServerConsole serverConsole = new ServerConsole(mockServer, mockWorld);
//        serverConsole.processInput("INVALID");
//        assertTrue(outputStream.toString().contains("Unknown command"));
//        System.setOut(System.out);
//    }
//
//    // Testing run method is tricky due to infinite loop and scanner dependency
//    // One approach could be to refactor the code to allow injection of a Scanner
//    // Then you can mock the Scanner in tests and provide predefined inputs.
//    // Alternatively, you can test the run method manually.
//}
