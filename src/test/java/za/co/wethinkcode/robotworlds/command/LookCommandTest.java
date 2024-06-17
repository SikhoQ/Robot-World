//package za.co.wethinkcode.robotworlds.command;
//
//import za.co.wethinkcode.robotworlds.Direction;
//import za.co.wethinkcode.robotworlds.Position;
//import za.co.wethinkcode.robotworlds.robot.Robot;
//import za.co.wethinkcode.robotworlds.server.ServerResponse;
//import za.co.wethinkcode.robotworlds.world.IWorld;
//
//import java.util.List;
//import java.util.Map;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//public class LookCommandTest {
//    @Test
//    public void testExecute() {
//        LookCommand lookCommand = new LookCommand();
//        IWorld mockWorld = mock(IWorld.class);
//        Robot mockRobot = mock(Robot.class);
//        when(mockRobot.getPosition()).thenReturn(new Position(0, 0));
//        when(mockRobot.getDirection()).thenReturn(Direction.NORTH);
//        when(mockRobot.getShields()).thenReturn(10);
//        when(mockRobot.getGun().getNumberOfShots()).thenReturn(20);
//        when(mockRobot.getStatus()).thenReturn("Active");
//        when(mockWorld.getVisibility()).thenReturn(3); // Set visibility to 3 for testing
//
//        ServerResponse response = lookCommand.execute(mockRobot, mockWorld);
//
//        // Verify that the server response contains the expected data and state
//        assertEquals("OK", response.getResult());
//        assertEquals(0, ((Position) response.getState().get("position")).getX());
//        assertEquals(0, ((Position) response.getState().get("position")).getY());
//        assertEquals(Direction.NORTH, response.getState().get("direction"));
//        assertEquals(10, response.getState().get("shields"));
//        assertEquals(20, response.getState().get("shots"));
//        assertEquals("Active", response.getState().get("status"));
//
//        // Verify that the data contains the expected objects
//        Map<String, Object> data = response.getData();
//        assertEquals(6, ((List<?>) data.get("objects")).size()); // Assuming there are 6 objects detected
//    }
//}
