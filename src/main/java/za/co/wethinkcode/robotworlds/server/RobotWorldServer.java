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
    /**
     * The run method is the entry point for the server thread.
     * It initializes a ServerConsole instance, starts it in a separate thread,
     * and then enters an infinite loop to accept new client connections.
     * For each new client, it creates a new RobotClientHandler instance,
     * adds it to the list of clients, and starts a new thread to handle the client.
     * If an IOException occurs during the server's operation, it prints a message and exits.
     *
     * @throws IOException If an error occurs while accepting client connections.
    */
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
    /**
     * Shuts down the server and all its clients.
     * This method disconnects all clients and then closes the server socket.
     * Finally, it terminates the server process.
     */
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
    /**
     * Retrieves the list of active {@link RobotClientHandler} instances.
     *
     * @return a list of active {@link RobotClientHandler} instances
     */
    public List<RobotClientHandler> getClients() {
        return clients;
    }
    public void removeClient(RobotClientHandler disconnectedClient) {
        clients.remove(disconnectedClient);
        world.removeRobot(disconnectedClient.getClientSocket().getPort());
    }
    /**
     * Closes the server socket and stops accepting new client connections.
     * This method is called when the server needs to shut down gracefully.
     * It ensures that all active client connections are closed before terminating the server process.
     *
     * @throws IOException If an error occurs while closing the server socket.
     */
    public void closeServer() throws IOException {
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * The main entry point of the server.
     * It initializes the server, sets up the world, and starts accepting client connections.
     *
     * @param args Command line arguments, not used in this context.
     * @throws IOException If an error occurs while accepting client connections.
     */
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