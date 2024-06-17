package za.co.wethinkcode.robotworlds.server;

import za.co.wethinkcode.robotworlds.world.TextWorld;
import java.util.Scanner;

/**
 * The ServerConsole class represents a console that allows the user to interact with the server.
 * It extends the Thread class and provides the implementation for the run method.
 */
public class ServerConsole implements Runnable {
    private final RobotWorldServer server;
    private final TextWorld world;

    public ServerConsole(RobotWorldServer server, TextWorld world) {
        this.server = server;
        this.world = world;
    }

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
            server.shutdown();
        } else if (input.equalsIgnoreCase("ROBOTS")) {
            world.showRobots();
        } else if (input.equalsIgnoreCase("DUMP")) {
            world.showWorldState();
        } else {
            System.out.println("Unknown command: " + input);
        }
    }
}
