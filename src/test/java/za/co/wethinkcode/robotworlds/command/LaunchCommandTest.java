//package za.co.wethinkcode.robotworlds.command;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import org.junit.jupiter.api.Disabled;
//import za.co.wethinkcode.robotworlds.Direction;
//import za.co.wethinkcode.robotworlds.JsonUtility;
//import za.co.wethinkcode.robotworlds.Position;
//import za.co.wethinkcode.robotworlds.robot.Robot;
//import za.co.wethinkcode.robotworlds.server.ServerResponse;
//import za.co.wethinkcode.robotworlds.world.IWorld;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//public class LaunchCommandTest {
//
//    @Disabled
//    public void testCreateRobot() {
//        Object[] arguments = {"Make", 10, 20}; // Example arguments
//        LaunchCommand launchCommand = new LaunchCommand(arguments);
//        IWorld mockWorld = mock(IWorld.class);
//        JsonNode mockNode = mock(JsonNode.class);
//        Map<String, Object> jsonFields = new HashMap<>();
//        jsonFields.put("robot", "RobotName");
//
//        when(JsonUtility.getJsonFields(mockNode)).thenReturn(Optional.of(jsonFields));
//
//        Robot createdRobot = launchCommand.createRobot(mockNode, mockWorld, 1234);
//
//        // Verify that the robot is created with the correct parameters
//        assertEquals("Make", createdRobot.getMake());
//        assertEquals(10, createdRobot.getShields());
//        assertEquals(20, createdRobot.getGun().getMaximumShots());
//    }
//
//    @Test
//    public void testExecute() {
//        Object[] arguments = {"Make", 10, 20}; // Example arguments
//        LaunchCommand launchCommand = new LaunchCommand(arguments);
//        IWorld mockWorld = mock(IWorld.class);
//        Robot mockRobot = mock(Robot.class);
//        when(mockRobot.getPosition()).thenReturn(new Position(0, 0));
//        when(mockRobot.getDirection()).thenReturn(Direction.NORTH);
//        when(mockRobot.getShields()).thenReturn(10);
//        when(mockRobot.getGun().getNumberOfShots()).thenReturn(20);
//        when(mockRobot.getStatus()).thenReturn("Active");
//
//        ServerResponse response = launchCommand.execute(mockRobot, mockWorld);
//
//        // Verify that the server response contains the expected data and state
//        assertEquals("OK", response.getResult());
//        assertEquals(0, ((Position) response.getData().get("position")).getX());
//        assertEquals(0, ((Position) response.getData().get("position")).getY());
//        assertEquals(mockWorld.getVisibility(), response.getData().get("visibility"));
//        assertEquals(mockWorld.getReload(), response.getData().get("reload"));
//        assertEquals(mockWorld.getRepair(), response.getData().get("repair"));
//        assertEquals(mockWorld.getShields(), response.getData().get("shields"));
//        assertEquals(0, ((Position) response.getState().get("position")).getX());
//        assertEquals(0, ((Position) response.getState().get("position")).getY());
//        assertEquals(Direction.NORTH, response.getState().get("direction"));
//        assertEquals(10, response.getState().get("shields"));
//        assertEquals(20, response.getState().get("shots"));
//        assertEquals("Active", response.getState().get("status"));
//    }
//}