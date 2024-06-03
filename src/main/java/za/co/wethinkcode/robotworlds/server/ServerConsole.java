package za.co.wethinkcode.robotworlds.server;

import za.co.wethinkcode.robotworlds.world.TextWorld;
import java.util.Scanner;

/**
 * The ServerConsole class handles server-side console input, allowing the server operator to
 * interact with the server through commands. This class implements Runnable to run in a separate thread.
 */
public class ServerConsole implements Runnable {
    private final RobotWorldServer server;
    private final TextWorld world;
    private boolean isRunning;

    public ServerConsole(RobotWorldServer server, TextWorld world) {
        this.server = server;
        this.world = world;
        this.isRunning = true;
    }

    /**
     * The run method is the entry point of the ServerConsole thread. It continuously
     * prompts the user for input and processes it using the processInput method.
     * The loop continues until the user enters "QUIT".
     */
    @Override
    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            String input;
            do {
                System.out.print("Server Console > ");
                input = scanner.nextLine();
                processInput(input);
            } while (!input.equalsIgnoreCase("QUIT"));
        } catch (Exception e) {
            System.err.println("An error occurred while reading input: " + e.getMessage());
        }
    }

    private void processInput(String input) {
        if (input.equalsIgnoreCase("QUIT")) {
            // should move shutdown method to world class
            this.isRunning = false;
            server.shutdown();
        } else if (input.equalsIgnoreCase("ROBOTS")) {
            world.showRobots();
        } else if (input.equalsIgnoreCase("DUMP")) {
            world.showWorldState();
        } else {
            System.out.println("Unknown command: " + input);
        }
    }

    public boolean getRunningState() {
        return this.isRunning;
    }
}
