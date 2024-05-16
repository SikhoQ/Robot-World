package za.co.wethinkcode.robotworlds.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import za.co.wethinkcode.robotworlds.world.TextWorld;
import za.co.wethinkcode.robotworlds.Robot;


public class RobotClientHandler implements Runnable {
    private final Socket clientSocket;
    private TextWorld world;

    public RobotClientHandler(Socket clientSocket, TextWorld world) {
        this.clientSocket = clientSocket;
        this.world = world;
    }

    @Override
    public void run() {
        try {
            // Initialize input and output streams for communication with the client
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Handle communication with the client
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String response = processCommand(inputLine);
                out.println(response);
            }
        } catch (IOException e) {
            System.err.println("Error handling client input: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    public void disconnectClient() {
        try {
            this.clientSocket.close();
        } catch (IOException e) {
            // handle thrown exception in calling code
            throw new RuntimeException(e);
        }
    }

    private String processCommand(String command) {
        // Process client command and return response
        // Example: Handle movement command
//        if (command.startsWith("move")) {
//            // Extract movement direction from command
//            String direction = command.substring(5).trim(); // Assuming command format is "move <direction>"
//            // Update world state accordingly
//            return world.moveRobot(direction); // Assuming moveRobot method updates world state and returns response
        command = command.toUpperCase();
        if (command.startsWith("MOVE")) {
            // Implement logic to handle MOVE command
            return "Moving robot...";
        } else if (command.startsWith("FIRE")) {
            // Implement logic to handle FIRE command
            return "Firing...";
        } else if (command.startsWith("LAUNCH")) {
            String name = command.split(" ")[1].toLowerCase();
            Robot robot = new Robot(name);
            return world.launchRobot(robot, name);
        } else {
            // Handle unknown commands
            return "Unknown command: " + command;
        }
    }
}
