package za.co.wethinkcode.robotworlds.server;

import za.co.wethinkcode.robotworlds.world.TextWorld;
import za.co.wethinkcode.robotworlds.server.configuration.ServerConfiguration;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * The RobotWorldServer class manages the server that handles connections from robot clients
 * and processes their interactions in the robot world.
 */
public class RobotWorldServer extends Thread {
    private final List<RobotClientHandler> clients;
    private ServerSocket serverSocket;
    private final TextWorld world;
    private boolean running;
    private final int port;

    /**
     * Constructs a RobotWorldServer with the specified port.
     *
     * @param PORT the port number on which the server listens for client connections.
     */
    public RobotWorldServer(int PORT) {
        this.clients = new ArrayList<>();
        this.world = new TextWorld();
        this.port = PORT;
    }

    /**
     * Runs the server, accepting client connections and starting a new thread for each client.
     */
    @Override
    public void run() {
        ServerConsole serverConsole = new ServerConsole(this, world);
        new Thread(serverConsole).start();

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("\u001B[34mServer started. Waiting for clients...\u001B[0m");
            running = true;
            while (running) {
                Socket clientSocket = serverSocket.accept();
                if (clientSocket != null) {
                    System.out.print("\u001B[33mNew client connected on local port: \u001B[0m");
                    System.out.println(clientSocket.getPort());
                    RobotClientHandler clientHandler = new RobotClientHandler(clientSocket, world);
                    clients.add(clientHandler);
                    new Thread(clientHandler).start();
                }
            }
        } catch (IOException e) {
            System.err.println("\u001B[31mError in server loop: " + e + "\u001B[0m");
            System.out.println("\u001B[31mQuitting server...\u001B[0m");
        }
    }

    /**
     * Shuts down the server and disconnects all connected clients.
     */
    public void shutdown() {
        running = false;
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
     * Gets the list of connected clients.
     *
     * @return the list of RobotClientHandler instances.
     */
    public List<RobotClientHandler> getClients() {
        return clients;
    }

    /**
     * Closes the server socket.
     *
     * @throws IOException if an I/O error occurs when closing the socket.
     */
    public void closeServer() throws IOException {
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The main method that starts the RobotWorldServer.
     *
     * @param args command line arguments, where the first argument is the port number.
     * @throws RuntimeException if an error occurs while starting the server.
     */
    public static void main(String[] args) throws RuntimeException {
        ServerConfiguration.configureServer();
        ServerConfiguration.setupLogging(ServerConfiguration.LOG_FILE_PATH);

        int PORT;
        if (args.length == 1) {
            try {
                PORT = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("\u001B[31m\nInvalid argument for \"PORT\"\nUsing PORT 5000\u001B[0m\n");
                PORT = 5000;
            }
        } else {
            System.out.println("\u001B[31m\nInvalid argument for \"PORT\"\nUsing PORT 5000\u001B[0m\n");
            PORT = 5000;
        }

        RobotWorldServer server = new RobotWorldServer(PORT);
        server.start();
    }
}
