package za.co.wethinkcode.robotworlds.robot;

/**
 * The Gun class represents a weapon with a fixed number of shots.
 * It provides methods to fire shots, reload the gun, and get information about the gun's status.
 */
public class Gun {
    private final int MAX_SHOTS;    // The maximum number of shots the gun can hold.
    private static int numberOfShots; // The current number of shots available.

    /**
     * Constructs a Gun object with the specified number of shots.
     *
     * @param numberOfShots the initial number of shots in the gun.
     */
    public Gun(int numberOfShots) {
        Gun.numberOfShots = numberOfShots;
        this.MAX_SHOTS = numberOfShots;
    }

    /**
     * Gets the distance a shot can travel based on the number of shots available.
     *
     * @return the distance a shot can travel.
     */
    public int getShotDistance() {
        return new int[]{0, 5, 4, 3, 2, 1}[MAX_SHOTS];
    }

    /**
     * Gets the current number of shots available in the gun.
     *
     * @return the current number of shots.
     */
    public static int getNumberOfShots() {
        return numberOfShots;
    }

    /**
     * Gets the maximum number of shots the gun can hold.
     *
     * @return the maximum number of shots.
     */
    public int getMaximumShots() {
        return MAX_SHOTS;
    }

    /**
     * Fires a shot from the gun, reducing the number of shots by one.
     * If the gun has no shots left, the number of shots remains unchanged.
     */
    public void fireShot() {
        if (numberOfShots > 0) {
            numberOfShots--;
        }
    }

    /**
     * Reloads the gun, setting the number of shots to the maximum.
     */
    public void reload() {
        numberOfShots = MAX_SHOTS;
    }

    /**
     * Sets the current number of shots available in the gun.
     *
     * @param numberOfShots the new number of shots.
     */
    public void setNumberOfShots(int numberOfShots) {
        Gun.numberOfShots = numberOfShots;
    }
}
