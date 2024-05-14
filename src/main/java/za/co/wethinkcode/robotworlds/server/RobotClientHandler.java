package za.co.wethinkcode.robotworlds.server;

import java.io.*;
import java.net.Socket;

public class RobotClientHandler implements Runnable {
    private final Socket clientSocket;
    private DataInputStream in;
    private DataOutputStream out;
    //    private World world;


    //    public RobotClientHandler(Socket clientSocket, World world) {
    //        this.clientSocket = clientSocket;
    //        this.world = world;
    //    }

    public RobotClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
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

    private String processCommand(String command) {
        // Process client command and return response
        // Example: Handle movement command
//        if (command.startsWith("move")) {
//            // Extract movement direction from command
//            String direction = command.substring(5).trim(); // Assuming command format is "move <direction>"
//            // Update world state accordingly
//            return world.moveRobot(direction); // Assuming moveRobot method updates world state and returns response
        if (command.equals("quit")) {
            return "Goodbye!";
        } else {
            return "Unknown command: " + command;
        }
    }

    public void close() {
        try {
            // Close input and output streams
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            // Close client socket
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing client connection: " + e.getMessage());
        }

    }
}