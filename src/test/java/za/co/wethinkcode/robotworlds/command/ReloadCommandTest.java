//package za.co.wethinkcode.robotworlds.command;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import za.co.wethinkcode.robotworlds.robot.Gun;
//import za.co.wethinkcode.robotworlds.robot.SimpleBot;
//import za.co.wethinkcode.robotworlds.server.ServerResponse;
//import za.co.wethinkcode.robotworlds.world.IWorld;
//
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//public class ReloadCommandTest {
//
//    private ReloadCommand reloadCommand;
//    private SimpleBot simpleBot;
//    private IWorld mockWorld;
//
//    @BeforeEach
//    void setUp() {
//        reloadCommand = new ReloadCommand();
//        // Create a mock IWorld
//        mockWorld = mock(IWorld.class);
//
//        // Create a SimpleBot with a mocked Gun
//        Gun mockGun = mock(Gun.class);
//        simpleBot = new SimpleBot("TestBot", null, null, mockGun, 100); // Assuming 100ms reload time
//    }
//
//    @Test
//    void testExecuteReload() {
//        int initialShots = simpleBot.getGun().getNumberOfShots();
//        String initialStatus = simpleBot.getStatus();
//
//        ServerResponse serverResponse = reloadCommand.execute(simpleBot, mockWorld);
//
//        assertEquals("OK", serverResponse.getResult());
//        assertEquals("Done", serverResponse.getData().get("message"));
//        assertEquals(simpleBot.getPosition(), serverResponse.getState().get("position"));
//        assertEquals(simpleBot.getDirection(), serverResponse.getState().get("direction"));
//        assertEquals(simpleBot.getShields(), serverResponse.getState().get("shields"));
//        assertEquals(initialShots + 1, serverResponse.getState().get("shots")); // Assuming reload adds one shot
//        assertEquals("RELOAD", serverResponse.getState().get("status"));
//
//        // Verify bot's state has been updated
//        assertEquals("RELOAD", simpleBot.getStatus());
//        assertEquals(initialShots + 1, simpleBot.getGun().getNumberOfShots());
//    }
//
//    @Test
//    void testExecuteWithInterruptedSleep() {
//        // Mock interrupted sleep scenario
//        Thread.currentThread().interrupt();
//
//        int initialShots = simpleBot.getGun().getNumberOfShots();
//        String initialStatus = simpleBot.getStatus();
//
//        ServerResponse serverResponse = reloadCommand.execute(simpleBot, mockWorld);
//
//        assertEquals("OK", serverResponse.getResult());
//        assertEquals("Done", serverResponse.getData().get("message"));
//        assertEquals(simpleBot.getPosition(), serverResponse.getState().get("position"));
//        assertEquals(simpleBot.getDirection(), serverResponse.getState().get("direction"));
//        assertEquals(simpleBot.getShields(), serverResponse.getState().get("shields"));
//        assertEquals(initialShots, serverResponse.getState().get("shots")); // Number of shots unchanged
//        assertEquals("RELOAD", serverResponse.getState().get("status"));
//
//        // Verify bot's state has been updated
//        assertEquals("RELOAD", simpleBot.getStatus());
//        assertEquals(initialShots, simpleBot.getGun().getNumberOfShots());
//    }
//
//    @Test
//    void testExecuteWithNullBot() {
//        ServerResponse serverResponse = reloadCommand.execute(null, mockWorld);
//
//        assertEquals("OK", serverResponse.getResult());
//        assertEquals("Done", serverResponse.getData().get("message"));
//        assertEquals(null, serverResponse.getState().get("position")); // Example: assuming null position
//        assertEquals(null, serverResponse.getState().get("direction")); // Example: assuming null direction
//        assertEquals(null, serverResponse.getState().get("shields")); // Example: assuming null shields
//        assertEquals(null, serverResponse.getState().get("shots")); // Example: assuming null shots
//        assertEquals("RELOAD", serverResponse.getState().get("status"));
//    }
//
//    @Test
//    void testExecuteWithNullWorld() {
//        ServerResponse serverResponse = reloadCommand.execute(simpleBot, null);
//
//        assertEquals("OK", serverResponse.getResult());
//        assertEquals("Done", serverResponse.getData().get("message"));
//        assertEquals(simpleBot.getPosition(), serverResponse.getState().get("position"));
//        assertEquals(simpleBot.getDirection(), serverResponse.getState().get("direction"));
//        assertEquals(simpleBot.getShields(), serverResponse.getState().get("shields"));
//        assertEquals(simpleBot.getGun().getNumberOfShots(), serverResponse.getState().get("shots")); // Number of shots unchanged
//        assertEquals("RELOAD", serverResponse.getState().get("status"));
//    }
//
//
//}