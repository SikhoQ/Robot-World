//package za.co.wethinkcode.robotworlds.command;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import za.co.wethinkcode.robotworlds.Direction;
//import za.co.wethinkcode.robotworlds.Position;
//import za.co.wethinkcode.robotworlds.command.LookCommand;
//import za.co.wethinkcode.robotworlds.robot.SimpleBot;
//import za.co.wethinkcode.robotworlds.robot.SniperBot;
//import za.co.wethinkcode.robotworlds.server.ServerResponse;
//import za.co.wethinkcode.robotworlds.world.*;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//public class LookCommandTest {
//
//    private LookCommand lookCommand;
//    private SimpleBot simpleBot;
//    private IWorld mockWorld;
//
//    @BeforeEach
//    void setUp() {
//        lookCommand = new LookCommand();
//
//        // Mock world
//        mockWorld = mock(IWorld.class);
//
//        // Create a SimpleBot with position and direction
//        simpleBot = new SimpleBot("TestBot", new Position(5, 5), Direction.NORTH, 100);
//
//        // Set up mock behavior for the world
//        when(mockWorld.getVisibility()).thenReturn(5);
//        when(mockWorld.getRobots()).thenReturn(new HashMap<>()); // Initialize with an empty map
//        when(mockWorld.getObstacles()).thenReturn(new ArrayList<>()); // Initialize with an empty list
//        //when(mockWorld.getWorldEdges()).thenReturn(new Edge(0, 100, 0, 100)); // Assuming a world size
//
//        // Add obstacles and other robots as needed for specific tests
//    }
//
//    @Test
//    void testExecuteLookNorth() {
//        // Setup
//        List<Obstacle> obstacles = new ArrayList<>();
//        obstacles.add(new SquareObstacle(5, 6));
//        Map<Integer, SimpleBot> robots = new HashMap<>();
//        robots.put(1, new SimpleBot("EnemyBot", new Position(5, 7), Direction.NORTH, 100));
//        when(mockWorld.getObstacles()).thenReturn(obstacles);
//        when(mockWorld.getRobots()).thenReturn(robots);
//
//        // Execute command
//        ServerResponse response = lookCommand.execute(simpleBot, mockWorld);
//
//        // Assertions
//        List<Map<String, Object>> objects = (List<Map<String, Object>>) response.getData().get("objects");
//        assertEquals(2, objects.size());
//
//        Map<String, Object> obstacle = objects.get(0);
//        assertEquals("OBSTACLE", obstacle.get("type"));
//        assertEquals(1, obstacle.get("distance"));
//
//        Map<String, Object> robot = objects.get(1);
//        assertEquals("ROBOT", robot.get("type"));
//        assertEquals(2, robot.get("distance"));
//    }
//
//    @Test
//    void testExecuteLookSouth() {
//        // Setup
//        List<Obstacle> obstacles = new ArrayList<>();
//        obstacles.add(new SquareObstacle(5, 4));
//        Map<Integer, SimpleBot> robots = new HashMap<>();
//        robots.put(1, new SimpleBot("EnemyBot", new Position(5, 3), Direction.SOUTH, 100));
//        when(mockWorld.getObstacles()).thenReturn(obstacles);
//        when(mockWorld.getRobots()).thenReturn(robots);
//
//        // Execute command
//        ServerResponse response = lookCommand.execute(simpleBot, mockWorld);
//
//        // Assertions
//        List<Map<String, Object>> objects = (List<Map<String, Object>>) response.getData().get("objects");
//        assertEquals(2, objects.size());
//
//        Map<String, Object> obstacle = objects.get(0);
//        assertEquals("OBSTACLE", obstacle.get("type"));
//        assertEquals(1, obstacle.get("distance"));
//
//        Map<String, Object> robot = objects.get(1);
//        assertEquals("ROBOT", robot.get("type"));
//        assertEquals(2, robot.get("distance"));
//    }
//
//    @Test
//    void testExecuteLookEast() {
//        // Setup
//        List<Obstacle> obstacles = new ArrayList<>();
//        obstacles.add(new SquareObstacle(6, 5));
//        Map<Integer, SimpleBot> robots = new HashMap<>();
//        robots.put(1, new SimpleBot("EnemyBot", new Position(7, 5), Direction.EAST, 100));
//        when(mockWorld.getObstacles()).thenReturn(obstacles);
//        when(mockWorld.getRobots()).thenReturn(robots);
//
//        // Execute command
//        ServerResponse response = lookCommand.execute(simpleBot, mockWorld);
//
//        // Assertions
//        List<Map<String, Object>> objects = (List<Map<String, Object>>) response.getData().get("objects");
//        assertEquals(2, objects.size());
//
//        Map<String, Object> obstacle = objects.get(0);
//        assertEquals("OBSTACLE", obstacle.get("type"));
//        assertEquals(1, obstacle.get("distance"));
//
//        Map<String, Object> robot = objects.get(1);
//        assertEquals("ROBOT", robot.get("type"));
//        assertEquals(2, robot.get("distance"));
//    }
//
//    @Test
//    void testExecuteLookWest() {
//        // Setup
//        List<Obstacle> obstacles = new ArrayList<>();
//        obstacles.add(new SquareObstacle(4, 5));
//        Map<Integer, SimpleBot> robots = new HashMap<>();
//        robots.put(1, new SimpleBot("EnemyBot", new Position(3, 5), Direction.WEST, 100));
//        when(mockWorld.getObstacles()).thenReturn(obstacles);
//        when(mockWorld.getRobots()).thenReturn(robots);
//
//        // Execute command
//        ServerResponse response = lookCommand.execute(simpleBot, mockWorld);
//
//        // Assertions
//        List<Map<String, Object>> objects = (List<Map<String, Object>>) response.getData().get("objects");
//        assertEquals(2, objects.size());
//
//        Map<String, Object> obstacle = objects.get(0);
//        assertEquals("OBSTACLE", obstacle.get("type"));
//        assertEquals(1, obstacle.get("distance"));
//
//        Map<String, Object> robot = objects.get(1);
//        assertEquals("ROBOT", robot.get("type"));
//        assertEquals(2, robot.get("distance"));
//    }
//
//    @Test
//    void testLookAtWorldEdge() {
//        // Setup
//        Map<Integer, SimpleBot> robots = new HashMap<>();
//        robots.put(1, new SimpleBot("EdgeBot", new Position(100, 5), Direction.EAST, 100));
//        when(mockWorld.getRobots()).thenReturn(robots);
//
//        // Execute command
//        ServerResponse response = lookCommand.execute(simpleBot, mockWorld);
//
//        // Assertions
//        List<Map<String, Object>> objects = (List<Map<String, Object>>) response.getData().get("objects");
//        assertEquals(1, objects.size());
//
//        Map<String, Object> edge = objects.get(0);
//        assertEquals("EDGE", edge.get("type"));
//        assertEquals(0, edge.get("distance"));
//    }
//
//    // Additional tests can be added to cover more scenarios if needed
//
//}

