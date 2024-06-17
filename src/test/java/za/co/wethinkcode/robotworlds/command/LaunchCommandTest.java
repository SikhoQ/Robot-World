package za.co.wethinkcode.robotworlds.command;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.JsonUtility;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.world.TextWorld;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LaunchCommandTest {
    IWorld mockWorld;
    JsonNode mockNode;
    LaunchCommand launchCommand;
    Object[] arguments;

    @BeforeEach
    void setUp() {
        mockWorld = new TextWorld();
        String request = "{\"robot\": \"RobotName\", \"command\": \"launch\", \"arguments\": [\"Make\", 10, 20]}";
        mockNode = JsonUtility.jsonFieldAccess(request);
        arguments = new Object[]{"Make", 10, 20}; // Example arguments
        launchCommand = new LaunchCommand(arguments);
    }

    @Test
    public void testCreateRobot() {
        Map<String, Object> jsonFields = new HashMap<>();
        jsonFields.put("robot", "RobotName");
        jsonFields.put("command", "launch");
        jsonFields.put("arguments", arguments);

        Robot createdRobot = launchCommand.createRobot(mockNode, mockWorld, 1234);

        assertEquals("Make", createdRobot.getMake());
        assertEquals(0, createdRobot.getShields());
        assertEquals(20, createdRobot.getGun().getMaximumShots());
    }

    @Test
    public void testExecute() {
        Robot robot = new Robot(new String[]{"make", "name2"}, 3, new Position(1, 3), Direction.NORTH, 8081);

        ServerResponse response = launchCommand.execute(robot, mockWorld);

        // Verify that the server response contains the expected data and state
        assertEquals("OK", response.getResult());
        assertEquals(1, ((Position) response.getData().get("position")).getX());
        assertEquals(3, ((Position) response.getData().get("position")).getY());
        assertEquals(mockWorld.getVisibility(), response.getData().get("visibility"));
        assertEquals(mockWorld.getReload(), response.getData().get("reload"));
        assertEquals(mockWorld.getRepair(), response.getData().get("repair"));
        assertEquals(mockWorld.getShields(), response.getData().get("shields"));
        assertEquals(1, ((Position) response.getState().get("position")).getX());
        assertEquals(3, ((Position) response.getState().get("position")).getY());
        assertEquals(Direction.NORTH, response.getState().get("direction"));
        assertEquals(0, response.getState().get("shields"));
        assertEquals(3, response.getState().get("shots"));
        assertEquals("NORMAL", response.getState().get("status"));
    }
}