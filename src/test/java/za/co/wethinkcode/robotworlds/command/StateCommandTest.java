//package za.co.wethinkcode.robotworlds.command;
//
//import org.junit.jupiter.api.Test;
//import za.co.wethinkcode.robotworlds.Direction;
//import za.co.wethinkcode.robotworlds.robot.SimpleBot;
//import za.co.wethinkcode.robotworlds.world.IWorld;
//import za.co.wethinkcode.robotworlds.server.ServerResponse;
//
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//public class StateCommandTest {
//
//    @Test
//    void testExecute() {
//        // Mocking dependencies
//        SimpleBot mockBot = mock(SimpleBot.class);
//        IWorld mockWorld = mock(IWorld.class);
//
//        // Set up the mock responses
////        when(mockBot.getPosition()).thenReturn("(0,0)");
//        when(mockBot.getDirection()).thenReturn(Direction.valueOf("NORTH"));
//        when(mockBot.getShields()).thenReturn(100);
//        when(mockBot.getGun().getNumberOfShots()).thenReturn(10);
//        when(mockBot.getStatus()).thenReturn("ACTIVE");
//
//        // Create StateCommand instance
//        StateCommand stateCommand = new StateCommand();
//
//        // Execute the command
//        ServerResponse response = stateCommand.execute(mockBot, mockWorld);
//
//        // Verify the result
//        assertEquals("OK", response.getResult());
//
//        // Verify the state data
//        Map<String, Object> stateData = response.getState();
//        assertEquals("(0,0)", stateData.get("position"));
//        assertEquals("NORTH", stateData.get("direction"));
//        assertEquals(100, stateData.get("shields"));
//        assertEquals(10, stateData.get("shots"));
//        assertEquals("ACTIVE", stateData.get("status"));
//
//        // Optionally, verify interactions with mocks
//        verify(mockBot).getPosition();
//        verify(mockBot).getDirection();
//        verify(mockBot).getShields();
//        verify(mockBot.getGun()).getNumberOfShots();
//        verify(mockBot).getStatus();
//    }
//}