package za.co.wethinkcode.robotworlds.server;

import za.co.wethinkcode.robotworlds.maze.*;
import za.co.wethinkcode.robotworlds.world.TextWorld;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class RobotWorldServer extends Thread{
    private static final int PORT = 5000;
    private static final List<RobotClientHandler> clients = new ArrayList<>();
    private static final ServerSocket serverSocket;

    static {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        /*ADD MEANINGFUL CODE FOR SERVER START-UP*/
    }

    public void shutdown() {
        // Disconnect all robots
        for (RobotClientHandler client: clients) {
            client.disconnectClient();
        }
        // Shut down the server
        closeServer();
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

    public static List<RobotClientHandler> getClients() {
        return clients;
    }

    private void closeServer() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            // handle in calling code
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Maze maze = new SimpleMaze();
        TextWorld world = new TextWorld(maze);

        RobotWorldServer server = new RobotWorldServer();
        ServerConsole console = new ServerConsole(server);
        new Thread(console).start();

        try {
            System.out.println("Server started. Waiting for clients...");
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                RobotClientHandler clientHandler = new RobotClientHandler(clientSocket, world);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.out.println("Server socket closed. Cannot accept new connections.");
        }
    }
}

