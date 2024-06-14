//package za.co.wethinkcode.robotworlds.command;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import za.co.wethinkcode.robotworlds.robot.Robot;
//import za.co.wethinkcode.robotworlds.server.ServerResponse;
//import za.co.wethinkcode.robotworlds.world.IWorld;
//
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//public class LaunchCommandTest {
//    /* The test ensures that the LaunchCommand correctly launches a robot with the specified parameters
//    and returns the server response with the appropriate data and state.*/
//
//    private LaunchCommand launchCommand;
//    private IWorld mockWorld;
//
//    @BeforeEach
//    void setUp() {
//        // Initialize the LaunchCommand instance and mock objects
//        launchCommand = new LaunchCommand("Make", "Name");
//        mockWorld = mock(IWorld.class);
//    }
//
//    @Test
//    void testExecute() {
//        // Arrange: Set up mock world and robot parameters
//        Robot mockRobot = mock(Robot.class);
//        int port = 8080;
//        when(mockWorld.launchRobot("Make", "Name", port)).thenReturn(mockRobot);
//
//        // Act: Execute the launch command
//        ServerResponse response = launchCommand.execute(mockRobot, mockWorld);
//
//        // Assert: Verify the response result is "OK"
//        assertEquals("OK", response.getResult());
//
//        // Assert: Verify the response data contains the correct parameters
//        Map<String, Object> responseData = response.getData();
//        assertEquals(mockRobot.getPosition(), responseData.get("position"));
//        assertEquals(mockWorld.getVisibility(), responseData.get("visibility"));
//        assertEquals(mockWorld.getReload(), responseData.get("reload"));
//        assertEquals(mockWorld.getRepair(), responseData.get("repair"));
//        assertEquals(mockWorld.getShields(), responseData.get("shields"));
//
//        // Assert: Verify the response state contains the correct robot state
//        Map<String, Object> responseState = response.getState();
//        assertEquals(mockRobot.getPosition(), responseState.get("position"));
//        assertEquals(mockRobot.getDirection(), responseState.get("direction"));
//        assertEquals(mockRobot.getShields(), responseState.get("shields"));
//        assertEquals(mockRobot.getShots(), responseState.get("shots"));
//        assertEquals(mockRobot.getStatus(), responseState.get("status"));
//    }
//}