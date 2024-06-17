//package za.co.wethinkcode.robotworlds.command;
//
//import za.co.wethinkcode.robotworlds.robot.Gun;
//import za.co.wethinkcode.robotworlds.robot.Robot;
//import za.co.wethinkcode.robotworlds.server.ServerResponse;
//import za.co.wethinkcode.robotworlds.world.IWorld;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//public class ReloadCommandTest {
//
//    @Test
//    public void testExecute() {
//        ReloadCommand reloadCommand = new ReloadCommand();
//        Robot mockRobot = mock(Robot.class);
//        IWorld mockWorld = mock(IWorld.class);
//
//        // Set up mock behavior for the robot
//        when(mockRobot.getName()).thenReturn("TestRobot");
//        when(mockRobot.getClass().getSimpleName()).thenReturn("TestRobotType");
//        when(mockRobot.getStatus()).thenReturn("NORMAL");
//        when(mockRobot.getReloadTime()).thenReturn(1000L); // Reload time in milliseconds
//
//        // Set up mock behavior for the gun
//        Gun mockGun = mock(Gun.class);
//        when(mockRobot.getGun()).thenReturn(mockGun);
//        when(mockGun.getNumberOfShots()).thenReturn(0);
//
//        // Execute the reload command
//        ServerResponse response = reloadCommand.execute(mockRobot, mockWorld);
//
//        // Verify that the gun is reloaded and the correct response is generated
//        assertEquals("OK", response.getResult());
//        assertEquals("Done", response.getData().get("message"));
//        assertEquals("NORMAL", response.getState().get("status")); // Make sure status is set back to NORMAL after reloading
//        assertEquals(1, ((int) response.getState().get("shots"))); // Make sure number of shots is incremented to 1 after reloading
//    }
//}