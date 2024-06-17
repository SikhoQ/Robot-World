package za.co.wethinkcode.robotworlds.command;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.robot.Gun;
import za.co.wethinkcode.robotworlds.robot.SimpleBot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LaunchCommandTest {
    @Mock
    Map<String, Object> data;
    Map<String, Object> state;
    IWorld world;
    SimpleBot bot;
    String result;
    Position pos;
    Direction direction;
    LaunchCommand launchCommand;
    ServerResponse response;
    Gun gun;

    @BeforeEach
    void setUp() {
        world = mock(IWorld.class);
        bot = mock(SimpleBot.class);
        pos = mock(Position.class);
        launchCommand = mock(LaunchCommand.class);
        response = mock(ServerResponse.class);
        gun = mock(Gun.class);
    }

    @Test
    void testCreateRobot() throws Exception {
        String json = "{ \"robot\": \"TestBot\", \"command\": \"LAUNCH\", \"arguments\": [\"SIMPLEBOT\", 5, 3] }";
        JsonNode rootNode = new ObjectMapper().readTree(json);

        when(world.launchRobot("SIMPLEBOT", "TestBot", 3, 8080)).thenReturn(bot);

        LaunchCommand command = new LaunchCommand(new Object[]{"SIMPLEBOT", 5, 3});
        SimpleBot robot = command.createRobot(rootNode, world, 8080);

        assertNotNull(robot);
        verify(world, times(1)).launchRobot("SIMPLEBOT", "TestBot", 3, 8080);
    }

    @Test
    public void testExecuteMethod() {

        when(bot.getPosition()).thenReturn(pos);
        when(world.getVisibility()).thenReturn(50);
        when(world.getReload()).thenReturn(5);
        when(world.getRepair()).thenReturn(5);
        when(world.getShields()).thenReturn(5);
        when(bot.getDirection()).thenReturn(direction);
        when(response.getResult()).thenReturn("OK");
        when(gun.getNumberOfShots()).thenReturn(5);

        LaunchCommand command = new LaunchCommand(new Object[]{"SIMPLEBOT", 5, 3});

        assertEquals("OK", response.getResult());
    }


}