//package za.co.wethinkcode.robotworlds.command;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import za.co.wethinkcode.robotworlds.Position;
//import za.co.wethinkcode.robotworlds.robot.Robot;
//import za.co.wethinkcode.robotworlds.robot.SimpleBot;
//import za.co.wethinkcode.robotworlds.world.Edge;
//import za.co.wethinkcode.robotworlds.world.IWorld;
//import za.co.wethinkcode.robotworlds.world.SquareObstacle;
//import za.co.wethinkcode.robotworlds.server.ServerResponse;
//import za.co.wethinkcode.robotworlds.world.Obstacle;
//import za.co.wethinkcode.robotworlds.Direction;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.*;
//
//public class LookCommandTest {
//
//    private LookCommand lookCommand;
//    private Robot mockRobot;
//    private IWorld mockWorld;
//
//    @BeforeEach
//    void setUp() {
//        // Initialize the LookCommand instance and mock objects
//        lookCommand = new LookCommand();
//        mockRobot = mock(Robot.class);
//        mockWorld = mock(IWorld.class);
//    }
//
//    @Test
//    void testLookWithNoObstaclesOrRobots() {
//        // Set up the mock robot's position and mock world visibility
//        when(mockRobot.getPosition()).thenReturn(new Position(0, 0));
//        when(mockWorld.getVisibility()).thenReturn(10);
//        when(mockWorld.getObstacles()).thenReturn(Collections.emptyList());
//        when(mockWorld.getRobots()).thenReturn(Collections.emptyMap());
//        when(mockWorld.getWorldEdges()).thenReturn(new Edge(new Position(50, 50), new Position(-50, -50)));
//
//        //  Execute the look command
//        ServerResponse response = lookCommand.execute(mockRobot, mockWorld);
//
//        //  Verify the response result is "OK"
//        assertEquals("OK", response.getResult());
//
//        //  Verify the response data contains no objects
//        Map<String, Object> responseData = response.getData();
//        List<?> objects = (List<?>) responseData.get("objects");
//        assertTrue(objects.isEmpty());
//
//        //  Verify the response state contains the correct fields
//        Map<String, Object> state = response.getState();
//        assertEquals(new Position(0, 0), state.get("position"));
//    }
//
//    @Test
//    void testLookWithObstacles() {
//        // Set up the mock robot's position, mock world visibility, and add an obstacle
//        when(mockRobot.getPosition()).thenReturn(new Position(0, 0));
//        when(mockWorld.getVisibility()).thenReturn(10);
//        Obstacle obstacle = new SquareObstacle(3, 0);  // Ensure SquareObstacle is a valid subclass of Obstacle
//        when(mockWorld.getObstacles()).thenReturn(List.of(obstacle));
//        when(mockWorld.getRobots()).thenReturn(Collections.emptyMap());
//        when(mockWorld.getWorldEdges()).thenReturn(new Edge(new Position(50, 50), new Position(-50, -50)));
//
//        // Act: Execute the look command
//        ServerResponse response = lookCommand.execute(mockRobot, mockWorld);
//
//        //  Verify the response result is "OK"
//        assertEquals("OK", response.getResult());
//
//        // Verify the response data contains the obstacle
//        Map<String, Object> responseData = response.getData();
//        List<Map<String, Object>> objects = (List<Map<String, Object>>) responseData.get("objects");
//        assertEquals(1, objects.size());
//        assertEquals("OBSTACLE", objects.get(0).get("type"));
//        assertEquals("EAST", objects.get(0).get("direction"));
//        assertEquals(7, objects.get(0).get("distance"));
//
//        // Assert: Verify the response state contains the correct fields
//        Map<String, Object> state = response.getState();
//        assertEquals(new Position(0, 0), state.get("position"));
//    }
//
//    @Test
//    void testLookWithRobots() {
//        // Arrange: Set up the mock robot's position, mock world visibility, and add another robot
//        when(mockRobot.getPosition()).thenReturn(new Position(0, 0));
//        when(mockWorld.getVisibility()).thenReturn(10);
//        Robot otherRobot = new SimpleBot("OtherBot", new Position(0, 3), Direction.NORTH);
//        when(mockWorld.getObstacles()).thenReturn(Collections.emptyList());
//        when(mockWorld.getRobots()).thenReturn(Map.of(1, otherRobot));
//        when(mockWorld.getWorldEdges()).thenReturn(new Edge(new Position(50, 50), new Position(-50, -50)));
//
//        // Execute the look command
//        ServerResponse response = lookCommand.execute(mockRobot, mockWorld);
//
//        //  Verify the response result is "OK"
//        assertEquals("OK", response.getResult());
//
//        //  Verify the response data contains the other robot
//        Map<String, Object> responseData = response.getData();
//        List<Map<String, Object>> objects = (List<Map<String, Object>>) responseData.get("objects");
//        assertEquals(1, objects.size());
//        assertEquals("ROBOT", objects.get(0).get("type"));
//        assertEquals("NORTH", objects.get(0).get("direction"));
//        assertEquals(3, objects.get(0).get("distance"));
//
//        //  Verify the response state contains the correct fields
//        Map<String, Object> state = response.getState();
//        assertEquals(new Position(0, 0), state.get("position"));
//    }
//
//    @Test
//    void testLookWithEdge() {
//        // Set up the mock robot's position, mock world visibility, and world edges
//        when(mockRobot.getPosition()).thenReturn(new Position(0, 0));
//        when(mockWorld.getVisibility()).thenReturn(10);
//        when(mockWorld.getObstacles()).thenReturn(Collections.emptyList());
//        when(mockWorld.getRobots()).thenReturn(Collections.emptyMap());
//        when(mockWorld.getWorldEdges()).thenReturn(new Edge(new Position(3, 0), new Position(-3, 0)));
//
//        // Act: Execute the look command
//        ServerResponse response = lookCommand.execute(mockRobot, mockWorld);
//
//        //  Verify the response result is "OK"
//        assertEquals("OK", response.getResult());
//
//        // Verify the response data contains the world edge
//        Map<String, Object> responseData = response.getData();
//        List<Map<String, Object>> objects = (List<Map<String, Object>>) responseData.get("objects");
//        assertEquals(2, objects.size());
//        assertEquals("EDGE", objects.get(0).get("type"));
//        assertEquals("NORTH", objects.get(0).get("direction"));
//        assertEquals(10, objects.get(0).get("distance"));
//
//        // Verify the response state contains the correct fields
//        Map<String, Object> state = response.getState();
//        assertEquals(new Position(0, 0), state.get("position"));
//    }
//}