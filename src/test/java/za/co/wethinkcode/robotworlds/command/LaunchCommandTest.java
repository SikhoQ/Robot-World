package za.co.wethinkcode.robotworlds.command;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.robot.SimpleBot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.world.TextWorld;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LaunchCommandTest {
    @Mock
    Map<String, Object> data;
    Map<String, Object> state;
    IWorld world;
    SimpleBot simpleBot;
    String result;
    Position pos;

    @BeforeEach
    void setUp() {
        world = mock(IWorld.class);
        simpleBot = mock(SimpleBot.class);
        pos = mock(Position.class);

    }

    @Test
    public void testExecuteMethod(){
//        IWorld world = new TextWorld();
//        SimpleBot simpleBot = new SimpleBot("John", new Position(10, 20), Direction.NORTH,3389);
        when(simpleBot.getPosition()).thenReturn(pos);

    }

    /**
     * Test the execution of the LaunchCommand.
     * This test verifies that the command execution returns a proper ServerResponse with the expected data and state.
     */
//    @Test
//    void testExecute() {
//        when(mockBot.getPosition()).thenReturn(new int[]{0, 0});
//        when(mockBot.getDirection()).thenReturn(Direction.valueOf("NORTH"));
//        when(mockBot.getShields()).thenReturn(5);
//        when(mockBot.getGun().getNumberOfShots()).thenReturn(3);
//        when(mockBot.getStatus()).thenReturn("ACTIVE");
//
//        when(mockWorld.getVisibility()).thenReturn(100);
//        when(mockWorld.getReload()).thenReturn(2);
//        when(mockWorld.getRepair()).thenReturn(1);
//        when(mockWorld.getShields()).thenReturn(5);
//
//        LaunchCommand command = new LaunchCommand(new Object[]{"SIMPLEBOT", 5, 3});
//        ServerResponse response = command.execute(mockBot, mockWorld);
//
//        assertEquals("OK", response.getResult());
//        assertEquals(5, response.getData().get("shields"));
//        assertEquals(100, response.getData().get("visibility"));
//        assertEquals(2, response.getData().get("reload"));
//        assertEquals(1, response.getData().get("repair"));
//        assertArrayEquals(new int[]{0, 0}, (int[]) response.getData().get("position"));
//
//        assertEquals("NORTH", response.getState().get("direction"));
//        assertEquals(5, response.getState().get("shields"));
//        assertEquals(3, response.getState().get("shots"));
//        assertEquals("ACTIVE", response.getState().get("status"));
//        assertArrayEquals(new int[]{0, 0}, (int[]) response.getState().get("position"));
//    }


    /**
     * Test the creation of a robot using the LaunchCommand.
     * This test verifies that a robot is correctly created with the provided arguments.
     */
    @Test
    void testCreateRobot() throws Exception {
        String json = "{ \"robot\": \"TestBot\", \"command\": \"LAUNCH\", \"arguments\": [\"SIMPLEBOT\", 5, 3] }";
        JsonNode rootNode = new ObjectMapper().readTree(json);

        when(world.launchRobot("SIMPLEBOT", "TestBot", 3, 8080)).thenReturn(simpleBot);

        LaunchCommand command = new LaunchCommand(new Object[]{"SIMPLEBOT", 5, 3});
        SimpleBot robot = command.createRobot(rootNode, world, 8080);

        assertNotNull(robot);
        verify(world, times(1)).launchRobot("SIMPLEBOT", "TestBot", 3, 8080);
    }
}