package za.co.wethinkcode.robotworlds;

import java.io.IOException;

public class ClearScreen {
    public static void clearScreen() {
        final String operatingSystem = System.getProperty("os.name").toLowerCase();
        try {
            if (operatingSystem.contains("windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            System.err.println("An error occurred while trying to clear the screen" + e);
        }
    }

}
