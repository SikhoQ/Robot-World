package za.co.wethinkcode.robotworlds.server;

import java.util.Scanner;


public class ServerConsole implements Runnable {
    private RobotWorldServer server;

    public ServerConsole(RobotWorldServer server) {
        this.server = server;
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
            server.showRobots();
        } else if (input.equalsIgnoreCase("DUMP")) {
            server.showWorldState();
        } else if (input.equalsIgnoreCase("TEST")) {
            System.out.println("SUCCESS");
        }else {
            System.out.println("Unknown command: " + input);
        }
    }
}

