package za.co.wethinkcode.robotworlds.robot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.robot.Gun;

import static org.junit.jupiter.api.Assertions.*;

class GunTest {

    private Gun gun;

    @BeforeEach
    void setUp() {
        // Initialize Gun with 5 initial shots for testing
        int initialShots = 5;
        gun = new Gun(initialShots);
    }

//    @Test
//    void testGetShotDistance() {
//        int shotDistance = gun.getShotDistance();
//        assertEquals(4, shotDistance, "Shot distance should be 4 for 5 initial shots");
//    }

    @Test
    void testGetNumberOfShots() {
        int numberOfShots = gun.getNumberOfShots();
        assertEquals(5, numberOfShots, "Number of shots should be 5 initially");
    }

    @Test
    void testGetMAX_SHOTS() {
        int maxShots = gun.getMAX_SHOTS();
        assertEquals(5, maxShots, "MAX_SHOTS should be 5");
    }

    @Test
    void testFireShot() {
        gun.fireShot();
        int remainingShots = gun.getNumberOfShots();
        assertEquals(4, remainingShots, "Firing one shot should reduce numberOfShots to 4");
    }

    @Test
    void testReload() {
        gun.fireShot(); // Reduce number of shots first
        gun.reload();
        int reloadedShots = gun.getNumberOfShots();
        assertEquals(5, reloadedShots, "Reloading should set numberOfShots back to MAX_SHOTS (5)");
    }

    // Add more tests for edge cases and additional behaviors as needed
}
