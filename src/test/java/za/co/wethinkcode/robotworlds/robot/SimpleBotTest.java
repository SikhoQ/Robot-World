package za.co.wethinkcode.robotworlds.robot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.world.IWorld;


public class SimpleBotTest {
    @Mock
    IWorld world;
    SimpleBot simpleBot;
    Position position;

    @BeforeEach
    void setUp() {
        world = mock(IWorld.class);
        simpleBot = mock(SimpleBot.class);
    }

    @Test
    public void updatingPosition(){
        when(simpleBot.updatePosition(10, world)).thenReturn("Done");
        assertEquals("Done", simpleBot.updatePosition(10, world));
    }

    @Test
    public void isPositionBlocked(){
        when(simpleBot.blocksPosition(position)).thenReturn(true);
        assertTrue(simpleBot.blocksPosition(position));
    }
}
