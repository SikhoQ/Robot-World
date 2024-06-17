//package za.co.wethinkcode.robotworlds.command;
//
//import za.co.wethinkcode.robotworlds.robot.Robot;
//import za.co.wethinkcode.robotworlds.server.ServerResponse;
//import za.co.wethinkcode.robotworlds.world.IWorld;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//public class RepairCommandTest {
//
//    @Test
//    public void testExecute() {
//        RepairCommand repairCommand = new RepairCommand();
//        Robot mockRobot = mock(Robot.class);
//        IWorld mockWorld = mock(IWorld.class);
//
//        // Set up mock behavior for the robot
//        when(mockRobot.getName()).thenReturn("TestRobot");
//        when(mockRobot.getClass().getSimpleName()).thenReturn("TestRobotType");
//        when(mockRobot.getStatus()).thenReturn("NORMAL");
//        when(mockRobot.getRepairTime()).thenReturn(1000L); // Repair time in milliseconds
//
//        // Execute the repair command
//        ServerResponse response = repairCommand.execute(mockRobot, mockWorld);
//
//        // Verify that the shield is repaired and the correct response is generated
//        assertEquals("OK", response.getResult());
//        assertEquals("Done", response.getData().get("message"));
//        assertEquals("NORMAL", response.getState().get("status")); // Make sure status is set back to NORMAL after repairing
//        assertEquals(Robot.MAX_SHIELDS, response.getState().get("shields")); // Make sure shields are restored to default value after repairing
//    }
//}