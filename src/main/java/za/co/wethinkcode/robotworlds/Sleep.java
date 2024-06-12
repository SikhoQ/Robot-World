package za.co.wethinkcode.robotworlds;

/**
 * The Sleep class provides utility methods for pausing execution for a specified duration.
 */
public class Sleep {
    /**
     * Constructs a Sleep object.
     * This constructor is provided for completeness but is not used, as all methods are static.
     */
    public Sleep() {}

    /**
     * Pauses the execution of the current thread for the specified number of milliseconds.
     *
     * @param milliseconds The duration to pause execution in milliseconds.
     * @throws RuntimeException if an InterruptedException occurs while sleeping.
     */
    public static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
