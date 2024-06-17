package za.co.wethinkcode.robotworlds.server;

import za.co.wethinkcode.robotworlds.world.TextWorld;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


public class RobotWorldServer extends Thread{
    private final List<RobotClientHandler> clients;
    private final ServerSocket serverSocket;
    private final TextWorld world;


    public RobotWorldServer(int PORT) {
        clients = new ArrayList<>();
        world = new TextWorld();
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            throw new RuntimeException("Failed to connect server on port: "+PORT);
        }
    }

    public void run() {
        ServerConsole console = new ServerConsole(this, world);
        new Thread(console).start();
      
        try {
            System.out.println("Server started. Waiting for clients...");
            while (true) {
                RemoveClient checkDisconnectedClient = new RemoveClient(this);
                checkDisconnectedClient.start();
                Socket clientSocket = serverSocket.accept();
                System.out.println("\nNew client connected on local port: "+clientSocket.getPort());
                RobotClientHandler clientHandler = new RobotClientHandler(clientSocket, world);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.out.println("Quitting server...");
        }
    }

    public void shutdown() {
        for (RobotClientHandler client: clients) {
            try {
                client.disconnectClient();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            closeServer();
        } catch (IOException e) {
            throw new RuntimeException("Error while closing server:\n"+e);
        }
        System.exit(0);
    }

    public List<RobotClientHandler> getClients() {
        return clients;
    }
    public void removeClient(RobotClientHandler disconnectedClient) {
        clients.remove(disconnectedClient);
        world.removeRobot(disconnectedClient.getClientSocket().getPort());
    }

    public void closeServer() throws IOException {
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        int PORT = 0;
        if (args.length == 1) {
            try {
                PORT = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                throw new RuntimeException("\nInvalid argument for \"PORT\"\n\nQuitting...");
            }
        } else {
            throw new RuntimeException("\nInvalid argument for \"PORT\"\n\nQuitting...");
        }
        RobotWorldServer server = new RobotWorldServer(PORT);
        server.start();
    }
}