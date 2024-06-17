package za.co.wethinkcode.robotworlds.server;

import java.net.Socket;

public class RemoveClient extends Thread {
    RobotWorldServer server;

    public RemoveClient(RobotWorldServer server) {
        this.server = server;
    }
    public void run() {
        while (!server.isServerStopped()) {
            RobotClientHandler[] clients = new RobotClientHandler[] {};
            try {
                if (!server.getClients().isEmpty())
                    clients = server.getClients().toArray(new RobotClientHandler[0]);
                for (RobotClientHandler client: clients) {
                    Socket clientSocket = client.getClientSocket();
                    if ((!clientSocket.isConnected() || clientSocket.isClosed()) && server.getClients().contains(client)) {
                        server.removeClient(client);
                        System.out.println("\nClient disconnected on local port: "+clientSocket.getPort());
                    }
                }
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
        }
    }
}
