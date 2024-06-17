package za.co.wethinkcode.robotworlds.world;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Sleep;

import static org.junit.jupiter.api.Assertions.*;

public class SleepTest {

    @Test
    public void sleepDoesNotThrowExceptionWithCorrectValue() {
        assertDoesNotThrow(() -> Sleep.sleep(1000));
    }

    @Test
    public void sleepThrowsExceptionTest() {
        Thread.currentThread().interrupt();
        RuntimeException exceptionThrown = assertThrows(RuntimeException.class, () -> Sleep.sleep(1000));
        Thread.interrupted();
    }
}

