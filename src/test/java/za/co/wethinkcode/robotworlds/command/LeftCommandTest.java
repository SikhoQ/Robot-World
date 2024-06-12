//package za.co.wethinkcode.robotworlds.command;
//
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import za.co.wethinkcode.robotworlds.Direction;
//import za.co.wethinkcode.robotworlds.robot.Robot;
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
//    private Robot mockRobot;
//    private IWorld mockWorld;
//
//    @BeforeEach
//    void setUp() {
//        leftCommand = new LeftCommand();
//        mockRobot = mock(Robot.class);
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