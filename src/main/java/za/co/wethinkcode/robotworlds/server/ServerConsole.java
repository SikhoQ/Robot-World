package za.co.wethinkcode.robotworlds.server;

import java.util.*;
import za.co.wethinkcode.robotworlds.world.TextWorld;


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
        while (true) {
            System.out.print("Server Console > ");
            String input = scanner.nextLine();
            processInput(input);
        }
    }

    private void processInput(String input) {
        if (input.equalsIgnoreCase("QUIT")) {
            server.shutdown();
        } else if (input.equalsIgnoreCase("ROBOTS")) {
            server.showRobots(world);
        } else if (input.equalsIgnoreCase("DUMP")) {
            server.showWorldState(world);
        } else {
            System.out.println("Unknown command: " + input);
        }
    }
}

