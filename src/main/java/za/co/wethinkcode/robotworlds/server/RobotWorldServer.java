package za.co.wethinkcode.robotworlds.server;

import za.co.wethinkcode.robotworlds.world.TextWorld;
<<<<<<< HEAD
=======
import za.co.wethinkcode.robotworlds.server.configuration.ServerConfiguration;

>>>>>>> sikho
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class RobotWorldServer extends Thread {
    private final List<RobotClientHandler> clients;
    private ServerSocket serverSocket;
    private final TextWorld world;
<<<<<<< HEAD

    public RobotWorldServer(int PORT) {
        clients = new ArrayList<>();
        world = new TextWorld();
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            throw new RuntimeException("Failed to connect server on port: " + PORT);
        }
    }

    /**
     * The run method is the entry point for the server thread.
     * It initializes a ServerConsole instance, starts it in a separate thread,
     * and then enters an infinite loop to accept new client connections.
     * For each new client, it creates a new RobotClientHandler instance,
     * adds it to the list of clients, and starts a new thread to handle the client.
     * If an IOException occurs during the server's operation, it prints a message and exits.
     */
    public void run() {
        ServerConsole console = new ServerConsole(this, world);
        new Thread(console).start();
=======
    private boolean running;
    private final int port;

    public RobotWorldServer(int PORT) {
        this.clients = new ArrayList<>();
        this.world = new TextWorld();
        this.port = PORT;
    }

    @Override
    public void run() {
        ServerConsole serverConsole = new ServerConsole(this, world);
        new Thread(serverConsole).start();
>>>>>>> sikho

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("\u001B[34mServer started. Waiting for clients...\u001B[0m");
            running = true;
            while (running) {
                Socket clientSocket = serverSocket.accept();
<<<<<<< HEAD
                System.out.println("\nNew client connected on local port: " + clientSocket.getPort());
                RobotClientHandler clientHandler = new RobotClientHandler(clientSocket, world);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
=======
                if (clientSocket != null) {
                    System.out.print("\u001B[33mNew client connected on local port: \u001B[0m");
                    System.out.println(clientSocket.getPort());
                    RobotClientHandler clientHandler = new RobotClientHandler(clientSocket, world);
                    clients.add(clientHandler);
                    new Thread(clientHandler).start();
                }
>>>>>>> sikho
            }
        } catch (IOException e) {
            System.err.println("Error in server loop: " + e);
            System.out.println("Quitting server...");
        }
    }

    /**
     * Shuts down the server and all its clients.
     * This method disconnects all clients and then closes the server socket.
     * Finally, it terminates the server process.
     */
    public void shutdown() {
<<<<<<< HEAD
=======
        running = false;
>>>>>>> sikho
        for (RobotClientHandler client : clients) {
            try {
                client.disconnectClient();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            closeServer();
        } catch (IOException e) {
            throw new RuntimeException("Error while closing server:\n" + e);
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

<<<<<<< HEAD
    /**
     * Removes a disconnected client from the list and updates the world.
     *
     * @param disconnectedClient the client to be removed
     */
=======
>>>>>>> sikho
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

<<<<<<< HEAD
    /**
     * The main entry point of the server.
     * It initializes the server, sets up the world, and starts accepting client connections.
     *
     * @param args Command line arguments, not used in this context.
     * @throws IOException If an error occurs while accepting client connections.
     */
    public static void main(String[] args) throws IOException {
        int PORT = 0;
=======
    public boolean isServerStopped() {
        return !running;
    }

    public static void main(String[] args) throws RuntimeException {
        ServerConfiguration.configureServer();
        ServerConfiguration.setupLogging(ServerConfiguration.LOG_FILE_PATH);

        int PORT;
>>>>>>> sikho
        if (args.length == 1) {
            try {
                PORT = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid argument for \"PORT\"\nUsing PORT 8080\n");
                PORT = 8080;
            }
        } else {
            System.out.println("\nInvalid argument for \"PORT\"\nUsing PORT 8080\n");
            PORT = 8080;
        }

        RobotWorldServer server = new RobotWorldServer(PORT);
        server.start();
    }
}
