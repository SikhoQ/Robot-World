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
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            handleCommand(command);
        }
    }

    private void handleCommand(String command) {
        if (command.equalsIgnoreCase("QUIT")) {
            server.shutdown();
        } else if (command.equalsIgnoreCase("ROBOTS")) {
            world.showRobots();
        } else if (command.equalsIgnoreCase("DUMP")) {
            world.showWorldState();
        } else {
            System.out.println("Unknown command: " + command);
        }
    }
}
