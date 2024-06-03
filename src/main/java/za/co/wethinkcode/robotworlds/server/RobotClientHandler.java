package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RobotClientHandler implements Runnable {
    private final Socket clientSocket;
    // private World world;

    public RobotClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    // Uncomment this constructor if using World
    // public RobotClientHandler(Socket clientSocket, World world) {
    //   this.clientSocket = clientSocket;
    //   this.world = world;
    // }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

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
        if (command.equals("quit")) {
            return "Goodbye!";
        } else {
            return "Unknown command: " + command;
        }
    }
}