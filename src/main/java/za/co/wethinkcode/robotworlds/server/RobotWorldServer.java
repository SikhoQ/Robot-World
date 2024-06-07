package za.co.wethinkcode.robotworlds.server;

import za.co.wethinkcode.robotworlds.world.TextWorld;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


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

    public void run() {}

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
        closeServer();
        System.exit(0);
    }

    /**
     * Retrieves the list of active {@link RobotClientHandler} instances.
     *
     * @return a list of active {@link RobotClientHandler} instances
     */
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

    /**
     * The main entry point of the server.
     * It initializes the server, sets up the world, and starts accepting client connections.
     *
     * @param args Command line arguments, not used in this context.
     * @throws IOException If an error occurs while accepting client connections.
     */
    public static void main(String[] args) throws IOException {
        TextWorld world = new TextWorld();

        RobotWorldServer server = new RobotWorldServer();
        ServerConsole console = new ServerConsole(server, world);

        new Thread(console).start();

        try {
            System.out.println("Server started. Waiting for clients...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected on port: "+clientSocket.getPort());

                RobotClientHandler clientHandler = new RobotClientHandler(clientSocket, world);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } finally {
            System.out.println("Quitting server...");
            System.exit(0);
        }
    }
}