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
        assertEquals(Direction.WEST, response.getState().get("direction"));
        assertEquals(10, response.getState().get("shields"));
        assertEquals(3, response.getState().get("shots"));
        assertEquals("NORMAL", response.getState().get("status"));
    }
}