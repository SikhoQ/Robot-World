package za.co.wethinkcode.robotworlds.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to handle communication between a server and a single client.
 * It processes commands received from the client and sends responses back to the client.
 * This class implements Runnable to allow handling client communication in a separate thread.
 */
public class RobotClientHandler implements Runnable {
    private final Socket clientSocket;

    public RobotClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * Handles communication with the client.
     */
    @Override
    public void run() {

    }

    /**
     * Disconnects the client from the server.
     *
     * @throws IOException if an I/O error occurs while closing the client socket
     */
    public void disconnectClient() throws IOException {
        this.clientSocket.close();
    }

    /**
     * Closes the client socket.
     *
     * @throws IOException if an I/O error occurs while closing the client socket
     */
    public void close() throws IOException {
        clientSocket.close();
    }


}
