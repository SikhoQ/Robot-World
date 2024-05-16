package za.co.wethinkcode.robotworlds.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class RobotWorldServer extends Thread{
    private static final int PORT = 5000;
    private static final List<RobotClientHandler> clients = new ArrayList<>();

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                RobotClientHandler clientHandler = new RobotClientHandler(clientSocket);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Error in the server: " + e.getMessage());
        }

    }

    public void shutdown() {
        // Close all client connections
        for (RobotClientHandler client : clients) {
            client.close(); // Implement close() method in RobotClientHandler class
        }

        System.out.println("Server shutdown successfully");

        // Shutdown the program
        System.exit(0);
    }

    public void showWorldState() {
        // Access the world state and collect information for the dump
        StringBuilder dump = new StringBuilder();

        // Append information about robots
//        for (RobotClientHandler client : clients) {
//            dump.append("Robot: ").append(client.getName()).append("\n");
//            dump.append("Position: ").append(client.getPosition()).append("\n");
//            dump.append("Direction: ").append(client.getCurrentDirection().append("\n"));
//            dump.append("State: ").append(client.getStatus()).append("\n");
//        }

        // Append information about obstacles or other world elements
        // Iterate over obstacles and append their positions or any relevant information

        // Print or output the dump to the console
        System.out.println("World Dump:");
        System.out.println(dump.toString());

    }

    public void showRobots() {
        /*TODO*/
    }

    public static void main(String[] args) {
        /* when server is started, include
           starting a new thread of the
           ServerConsole instance
        */
        RobotWorldServer server = new RobotWorldServer();
        ServerConsole console = new ServerConsole(server);
        new Thread(console).start();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                RobotClientHandler clientHandler = new RobotClientHandler(clientSocket);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Error in the server: " + e.getMessage());
        }
    }
}

