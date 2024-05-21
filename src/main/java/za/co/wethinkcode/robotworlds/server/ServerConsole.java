package za.co.wethinkcode.robotworlds.server;

<<<<<<< HEAD
import java.util.Scanner;
import za.co.wethinkcode.robotworlds.world.TextWorld;


=======
import za.co.wethinkcode.robotworlds.world.TextWorld;
import java.util.Scanner;

/**
 * The ServerConsole class handles server-side console input, allowing the server operator to
 * interact with the server through commands. This class implements Runnable to run in a separate thread.
 */
>>>>>>> origin/main-clone
public class ServerConsole implements Runnable {
    private final RobotWorldServer server;
    private final TextWorld world;

    public ServerConsole(RobotWorldServer server, TextWorld world) {
        this.server = server;
        this.world = world;
    }

<<<<<<< HEAD
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Server Console > ");

            System.out.println("Available commands:");
            System.out.println("   Dump     - Displays a representation of the world’s state showing robots, obstacles,");
            System.out.println("   Robot    - Lists all robots in the world including the robot’s name and state");
            System.out.println("   Quit     - Disconnects all robots and ends the world\n");
            String input = scanner.nextLine();
            processInput(input);
=======
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
>>>>>>> origin/main-clone
        }
    }

    private void processInput(String input) {
        if (input.equalsIgnoreCase("QUIT")) {
            server.shutdown();
        } else if (input.equalsIgnoreCase("ROBOTS")) {
<<<<<<< HEAD
            server.showRobots();
=======
            server.showRobots(world);
>>>>>>> origin/main-clone
        } else if (input.equalsIgnoreCase("DUMP")) {
            server.showWorldState(world);
        } else {
            System.out.println("Unknown command: " + input);
        }
    }
}
<<<<<<< HEAD

=======
>>>>>>> origin/main-clone
