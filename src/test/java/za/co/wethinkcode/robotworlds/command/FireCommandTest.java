package za.co.wethinkcode.robotworlds.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.world.TextWorld;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FireCommandTest {
    private Command command;
    private Robot robot1;
    private IWorld world;

    @BeforeEach
    void setUp() {
        command = new FireCommand();
        world = new TextWorld();
        robot1 = new Robot(new String[]{"make", "name1"}, 3, new Position(1, 1), Direction.NORTH, 8080);
        world.addRobotToList(robot1, 8080);
    }

    @Test
    void testFireName() {
        assertEquals("fire", command.getCommand());
    }

    @Test
    void executeFireWithShotsHit() {
        Robot robot2 = new Robot(new String[]{"make", "name2"}, 3, new Position(1, 3), Direction.NORTH, 8081);
        world.addRobotToList(robot2, 8081);

        ServerResponse response = command.execute(robot1, world);

        assertEquals("OK", response.getResult());
        assertEquals("Hit", response.getData().get("message"));
        assertEquals(9, robot2.getShields());

        world.removeRobot(8081);
    }

    @Test
    void executeFireWithShotsMissNoRobot() {
        ServerResponse response = command.execute(robot1, world);

        assertEquals("OK", response.getResult());
        assertEquals("Miss", response.getData().get("message"));
    }

    @Test
    void executeFireWithShotsMissRobotFar() {
        Robot robot2 = new Robot(new String[]{"make", "name2"}, 3, new Position(1, 5), Direction.NORTH, 8081);
        world.addRobotToList(robot2, 8081);

        ServerResponse response = command.execute(robot1, world);

        assertEquals("OK", response.getResult());
        assertEquals("Miss", response.getData().get("message"));

        world.removeRobot(8081);
    }

    @Test
    void executeFireWithoutShots() {
        robot1.getGun().setNumberOfShots(0);

        ServerResponse response = command.execute(robot1, world);

        assertEquals("OK", response.getResult());
        assertEquals("Empty", response.getData().get("message"));
    }
}
