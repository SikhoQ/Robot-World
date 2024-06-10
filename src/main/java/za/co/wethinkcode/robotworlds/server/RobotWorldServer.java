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


    public RobotWorldServer() {
        int PORT = 5000;
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
        // RemoveClient is supposed to run an infinite loop checking for disconnected clients
        // and remove them from the list (therefore from the world)
        // -----currently not working------


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
        closeServer();
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
        RobotWorldServer server = new RobotWorldServer();
        server.start();
    }
}