package za.co.wethinkcode.robotworlds.server;

import za.co.wethinkcode.robotworlds.world.TextWorld;
import java.util.Scanner;

<<<<<<< HEAD
/**
 * The ServerConsole class represents a console that allows the user to interact with the server.
 * It implements the Runnable interface and provides the implementation for the run method.
 */
=======
>>>>>>> parent of 6b26bef (Merge branch 'main' of gitlab.wethinkco.de:sqangule023/dbn_14_robot_worlds)
public class ServerConsole implements Runnable {
    private final RobotWorldServer server;
    private final TextWorld world;

    /**
     * Constructs a ServerConsole with the specified server and world.
     *
     * @param server the RobotWorldServer instance to interact with.
     * @param world the TextWorld instance representing the world of the robots.
     */
    public ServerConsole(RobotWorldServer server, TextWorld world) {
        this.server = server;
        this.world = world;
    }

    /**
     * The run method of the ServerConsole. It listens for user input from the console
     * and processes commands accordingly.
     */
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            handleCommand(command);
        }
    }

    /**
     * Handles the given command entered by the user in the console.
     *
     * @param command the command entered by the user.
     */
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
