<<<<<<< HEAD
//package za.co.wethinkcode.robotworlds.command;
//
//
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import za.co.wethinkcode.robotworlds.Direction;
//import za.co.wethinkcode.robotworlds.robot.SimpleBot;
//import za.co.wethinkcode.robotworlds.server.ServerResponse;
//import za.co.wethinkcode.robotworlds.world.IWorld;
//
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//class LeftCommandTest {
//
//    private LeftCommand leftCommand;
//    private SimpleBot mockRobot;
//    private IWorld mockWorld;
//
//    /*Initialize the LeftCommand instance and create mock objects for Robot and IWorld.
//     *This setup runs before each test to ensure a clean test environment.*/
//    @BeforeEach
//    void setUp() {
//        leftCommand = new LeftCommand();
//        mockRobot = mock(SimpleBot.class);
//        mockWorld = mock(IWorld.class);
//    }
//
//    @Test
//    void testExecute() {
//        // Mock current direction
//        Direction currentDirection = Direction.NORTH;
//        // Set up mock robot's direction
//        when(mockRobot.getDirection()).thenReturn(currentDirection);
//
//        // Execute the left command
//        mockRobot.setGun(3);
//        ServerResponse response = leftCommand.execute(mockRobot, mockWorld);
//
//        // Verify that the robot's direction is updated correctly
//        verify(mockRobot).setDirection(Direction.WEST);
//
//        // Verify response data
//        Map<String, Object> responseData = response.getData();
//        assertEquals("DONE", responseData.get("message"));
//    }
//}
=======
package za.co.wethinkcode.robotworlds.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LeftCommandTest {
    private Command command;
    private Robot robot;
    private IWorld world;

    @BeforeEach
    void setUp() {
        command = new LeftCommand();
        world = mock(IWorld.class);
        robot = new Robot(new String[] {"make", "name"}, 3, new Position(1, 1), Direction.NORTH, 8080);
    }

    @Test
    void testLeftName() {
        assertEquals("left", command.getCommand());
    }

    @Test
    void executeLeft() {
        ServerResponse response = command.execute(robot, world);
        assertEquals("OK", response.getResult());
        assertEquals("Done", response.getData().get("message"));
        assertEquals(new Position(1, 1), response.getState().get("position"));
        assertEquals(Direction.WEST, response.getState().get("direction"));
        assertEquals(0, response.getState().get("shields"));
        assertEquals(3, response.getState().get("shots"));
        assertEquals("NORMAL", response.getState().get("status"));
    }
}
>>>>>>> sikho
