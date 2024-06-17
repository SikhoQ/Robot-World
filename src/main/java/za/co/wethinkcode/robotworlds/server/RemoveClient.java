package za.co.wethinkcode.robotworlds.server;


import java.net.Socket;

/**
 * The RemoveClient class represents a thread that removes disconnected clients from the server.
 * It extends the Thread class and provides the implementation for the run method.
 */
public class RemoveClient extends Thread {
    RobotWorldServer server;

    public RemoveClient(RobotWorldServer server) {
        this.server = server;
    }
    public void run() {
        while (true) {
            RobotClientHandler[] clients = new RobotClientHandler[] {};
            if (!server.getClients().isEmpty())
                clients = server.getClients().toArray(new RobotClientHandler[0]);
            for (RobotClientHandler client: clients) {
                Socket clientSocket = client.getClientSocket();
                if ((!clientSocket.isConnected() || clientSocket.isClosed()) && server.getClients().contains(client)) {
                    server.removeClient(client);
                    System.out.println("\nClient disconnected on local port: "+clientSocket.getPort());
                }
            }
        }
    }
}
