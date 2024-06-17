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

/**
 * Test class for RightCommand.
 */
public class RightCommandTest {
    private Command command;
    private Robot robot;
    private IWorld world;

    /**
     * Set up before each test.
     */
    @BeforeEach
    void setUp() {
        command = new RightCommand();
        world = mock(IWorld.class);
        robot = new Robot(new String[] {"make", "name"}, 3, new Position(1, 1), Direction.NORTH, 8080);
    }

    /**
     * Test for getting the command name.
     */
    @Test
    void testRightName() {
        assertEquals("right", command.getCommand());
    }

    /**
     * Test for executing the right command.
     */
    @Test
    void executeRight() {
        ServerResponse response = command.execute(robot, world);
        assertEquals("OK", response.getResult());
        assertEquals("Done", response.getData().get("message"));
        assertEquals(new Position(1, 1), response.getState().get("position"));
        assertEquals(Direction.EAST, response.getState().get("direction"));
        assertEquals(0, response.getState().get("shields"));
        assertEquals(3, response.getState().get("shots"));
        assertEquals("NORMAL", response.getState().get("status"));
    }
}
