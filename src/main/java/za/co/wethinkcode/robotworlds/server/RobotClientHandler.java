package za.co.wethinkcode.robotworlds.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.client.ClientRequest;
import za.co.wethinkcode.robotworlds.command.Command;
import za.co.wethinkcode.robotworlds.world.TextWorld;

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
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String clientRequest;
            while ((clientRequest = in.readLine()) != null) {
                String response = processRequest(clientRequest);
                out.println(response);
            }
        } catch (IOException ignored) {
        } finally {
            try {
                clientSocket.close();
                System.out.println("Client socket closed.");
                System.exit(0);
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
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

    private String processRequest(String clientRequest) {
        // return server response json string from this method
        JsonNode rootNode = Json.jsonFieldAccess(clientRequest);
        try {
            Command commandObject = Command.create(rootNode);
            try {
                return Json.toJson(commandObject);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IllegalArgumentException e) {
            // return 'unsupported command' error upon exception
            // a.k.a response for badly formed request
            Map<String, String> data = new HashMap<>();
            data.put("message", "Unsupported command");
            Map<String, Object> dataCopy = new HashMap<>(data);
            try {
                return Json.toJson(new ServerResponse("ERROR", dataCopy));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
