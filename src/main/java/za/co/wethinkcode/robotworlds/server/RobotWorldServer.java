package za.co.wethinkcode.robotworlds.server;

import za.co.wethinkcode.robotworlds.world.TextWorld;
import za.co.wethinkcode.robotworlds.world.configuration.ServerConfiguration;

import java.io.IOException;
import java.io.ObjectInputFilter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class RobotWorldServer extends Thread {
    private final List<RobotClientHandler> clients;
    private final ServerSocket serverSocket;
    private final TextWorld world;
    private boolean running;

    public RobotWorldServer(int PORT) {
        clients = new ArrayList<>();
        world = new TextWorld();
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            throw new RuntimeException("Failed to connect server on port: " + PORT);
        }
    }

    @Override
    public void run() {
        ServerConsole serverConsole = new ServerConsole(this, world);
        new Thread(serverConsole).start();

        try {
            System.out.println("\u001B[34mServer started. Waiting for clients...\u001B[0m");
            running = true;
            while (running) {
                RemoveClient checkDisconnectedClient = new RemoveClient(this);
                checkDisconnectedClient.start();
                Socket clientSocket = serverSocket.accept();
                System.out.print("\u001B[33mNew client connected on local port: \u001B[0m");
                System.out.println(clientSocket.getPort());
                RobotClientHandler clientHandler = new RobotClientHandler(clientSocket, world);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Error in server loop: " + e);
            System.out.println("Quitting server...");
        }
    }

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

    public boolean isServerStopped() {
        return !running;
    }

    public static void main(String[] args) throws RuntimeException {
        ServerConfiguration.configureServer();
        ServerConfiguration.setupLogging(ServerConfiguration.LOG_FILE_PATH);

        int PORT;
        if (args.length == 1) {
            try {
                PORT = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid argument for \"PORT\"\n\nQuitting...");
                throw new RuntimeException();
            }
        } else {
            System.out.println("\nInvalid argument for \"PORT\"\n\nQuitting...");
            throw new RuntimeException();
        }
        RobotWorldServer server = new RobotWorldServer(PORT);
        server.start();
    }
}
