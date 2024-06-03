package za.co.wethinkcode.robotworlds;

public class Sleep {
    public Sleep() {}

    /**
     * Pauses the execution of the current thread for the specified number of milliseconds.
     *
     * This method uses `Thread.sleep` to suspend the current thread for the given duration.
     * If the thread is interrupted while sleeping, it throws a `RuntimeException`.
     *
     * @param milliseconds the number of milliseconds for which to pause the execution
     * @throws RuntimeException if the thread is interrupted while sleeping
     */
    public static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
