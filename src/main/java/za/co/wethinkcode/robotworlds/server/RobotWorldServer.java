package za.co.wethinkcode.robotworlds.server;

import za.co.wethinkcode.robotworlds.world.TextWorld;
import za.co.wethinkcode.robotworlds.server.configuration.ServerConfiguration;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class RobotWorldServer extends Thread {
    private final List<RobotClientHandler> clients;
    private ServerSocket serverSocket;
    private final TextWorld world;
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
