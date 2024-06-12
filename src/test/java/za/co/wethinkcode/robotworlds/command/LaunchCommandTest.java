//package za.co.wethinkcode.robotworlds.command;
//
//import org.junit.Test;
//import za.co.wethinkcode.robotworlds.robot.Robot;
//import za.co.wethinkcode.robotworlds.world.IWorld;
//import za.co.wethinkcode.robotworlds.server.ServerResponse;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//public class LaunchCommandTest {
//
//    @Test
//    public void testExecute() {
//        // Create a mock Robot
//        Robot robot = mock(Robot.class);
//        when(robot.getPosition()).thenReturn(new int[]{0, 0});
//        when(robot.getDirection()).thenReturn("");
//        when(robot.getShields()).thenReturn(0);
//        when(robot.getShots()).thenReturn(0);
//        when(robot.getStatus()).thenReturn("");
//
//        // Create a mock IWorld
//        IWorld world = mock(IWorld.class);
//        when(world.getVisibility()).thenReturn(0);
//        when(world.getReload()).thenReturn(0);
//        when(world.getRepair()).thenReturn(0);
//        when(world.getShields()).thenReturn(0);
//
//        // Create a LaunchCommand instance
//        LaunchCommand launchCommand = new LaunchCommand("SIMPLEBOT", "Robot1");
//
//        // Execute the command
//        ServerResponse response = launchCommand.execute(robot, world);
//
//        // Check the result
//        assertEquals("OK", response.getResult());
//
//        // Check the data
//        Map<String, Object> data = response.getData();
//        assertEquals(2, ((int[]) data.get("position")).length);
//        assertEquals(0, data.get("visibility"));
//        assertEquals(0, data.get("reload"));
//        assertEquals(0, data.get("repair"));
//        assertEquals(0, data.get("shields"));
//
//        // Check the state
//        Map<String, Object> state = response.getState();
//        assertEquals(2, ((int[]) state.get("position")).length);
//        assertEquals("", state.get("direction"));
//        assertEquals(0, state.get("shields"));
//        assertEquals(0, state.get("shots"));
//        assertEquals("", state.get("status"));
//    }
//}