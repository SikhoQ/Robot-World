package za.co.wethinkcode.robotworlds.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.IWorld;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StateCommandTest {

    private StateCommand stateCommand;
    private Robot mockRobot;
    private IWorld mockWorld;

    @BeforeEach
    void setUp() {
        // Initialize the StateCommand instance and mock objects
        stateCommand = new StateCommand();
        mockRobot = mock(Robot.class);
        mockWorld = mock(IWorld.class);
    }

    @Test
    void testExecute() {
        // Arrange: Set up the mock robot's state
        Position position = new Position(5, 5);
        Direction direction = Direction.NORTH;
        int shields = 3;
        int shots = 10;
        String status = "ACTIVE";

        when(mockRobot.getPosition()).thenReturn(position);
        when(mockRobot.getDirection()).thenReturn(direction);
        when(mockRobot.getShields()).thenReturn(shields);
        when(mockRobot.getShots()).thenReturn(shots);
        when(mockRobot.getStatus()).thenReturn(status);

        // Act: Execute the state command
        ServerResponse response = stateCommand.execute(mockRobot, mockWorld);

        // Assert: Verify the response result is "OK"
        assertEquals("OK", response.getResult());

        // Assert: Verify the response state contains the correct robot state
        Map<String, Object> responseState = response.getState();
        assertEquals(position, responseState.get("position"));
        assertEquals(direction, responseState.get("direction"));
        assertEquals(shields, responseState.get("shields"));
        assertEquals(shots, responseState.get("shots"));
        assertEquals(status, responseState.get("status"));

        // Assert: Verify the response data is empty
        Map<String, Object> responseData = response.getData();
        assertEquals(0, responseData.size());
    }
}