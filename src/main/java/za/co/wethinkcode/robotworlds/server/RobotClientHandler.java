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
        } catch (IOException ignored) {
        } finally {
            try {
                clientSocket.close();
                System.out.println("Client socket closed.");
                System.exit(0);
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

    public void close() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error closing client socket: " + e.getMessage());
        }
    }


    private String processCommand(String command) {
        command = command.toUpperCase();
        if (command.startsWith("LOOK")) {
            return "Looking around...";
        } else if (command.startsWith("STATE")) {
            return "showing state...";
        } else if (command.startsWith("LAUNCH")) {
            String name = command.split(" ")[1].toLowerCase();
            return world.launchRobot(name);
        } else {
            return "Unknown command: " + command;
        }
    }
}
