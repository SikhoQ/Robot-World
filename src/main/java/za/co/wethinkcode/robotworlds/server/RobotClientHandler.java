package za.co.wethinkcode.robotworlds.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.fasterxml.jackson.databind.JsonNode;
import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.command.Command;
import za.co.wethinkcode.robotworlds.command.LaunchCommand;
import za.co.wethinkcode.robotworlds.world.IWorld;

/**
 * Class to handle communication between a server and a single client.
 * It processes commands received from the client and sends responses back to the client.
 * This class implements Runnable to allow handling client communication in a separate thread.
 */
public class RobotClientHandler implements Runnable {
    private final Socket clientSocket;
    private final IWorld world;

    public RobotClientHandler(Socket clientSocket, IWorld world) {
        this.clientSocket = clientSocket;
        this.world = world;
    }

    public Socket getClientSocket() {
        return clientSocket;
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
            Robot robot = null;
            Json json = new Json();
            while ((clientRequest = in.readLine()) != null) {
                Command command = getCommand(clientRequest, world);
                if (command.getCommand().equalsIgnoreCase("LAUNCH")) {
                    LaunchCommand launchCommand = (LaunchCommand) command;
                    robot = launchCommand.createRobot(world, clientSocket.getPort());
                }
                ServerResponse serverResponseObject = command.execute(robot, world);
                String serverResponse = json.toJson(serverResponseObject);
                out.println(serverResponse);
            }
        } catch (IOException ignored) {
        } finally {
            try {
                disconnectClient();
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


    public Command getCommand(String clientRequest, IWorld world) {
        Json json = new Json();
        JsonNode rootNode = json.jsonFieldAccess(clientRequest);
        return Command.create(rootNode, world);
    }

    /*
    * use this function to handle error responses since each command's execute
    * returns a ServerResponse object
    * */
    private String processRequest(String clientRequest) {
        // return 'unsupported command' error upon exception
        // a.k.a response for badly formed request
//        Map<String, String> data = new HashMap<>();
//        data.put("message", "Unsupported command");
//        Map<String, Object> dataCopy = new HashMap<>(data);
//        return json.toJson(new ServerResponse("ERROR", dataCopy));
        return  "";
    }
}
