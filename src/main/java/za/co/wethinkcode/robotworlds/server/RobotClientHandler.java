package za.co.wethinkcode.robotworlds.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.fasterxml.jackson.databind.JsonNode;
import za.co.wethinkcode.robotworlds.JsonUtility;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.command.Command;
import za.co.wethinkcode.robotworlds.command.LaunchCommand;
import za.co.wethinkcode.robotworlds.world.IWorld;

/**
 * The RobotClientHandler class is responsible for handling client connections
 * and processing their requests in the robot world server.
 */
public class RobotClientHandler implements Runnable {
    private final Socket clientSocket;
    private final IWorld world;
    private Robot robot;

    /**
     * Constructs a RobotClientHandler with the specified client socket and world.
     *
     * @param clientSocket the socket connection to the client.
     * @param world the world in which the robot operates.
     */
    public RobotClientHandler(Socket clientSocket, IWorld world) {
        this.clientSocket = clientSocket;
        this.world = world;
        this.robot = null;
    }

    /**
     * Gets the client socket.
     *
     * @return the client socket.
     */
    public Socket getClientSocket() {
        return clientSocket;
    }

    /**
     * Runs the client handler, processing client requests and sending responses.
     */
    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String clientRequest;

            while ((clientRequest = in.readLine()) != null) {
                String serverResponse = processRequest(clientRequest);
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
     * Disconnects the client by closing the socket connection.
     *
     * @throws IOException if an I/O error occurs when closing the socket.
     */
    public void disconnectClient() throws IOException {
        this.clientSocket.close();
    }

    /**
     * Creates a command from the given JSON node and world.
     *
     * @param rootNode the JSON node representing the command.
     * @param world the world in which the command will be executed.
     * @return the created command.
     */
    public Command getCommand(JsonNode rootNode, IWorld world) {
        return Command.create(rootNode, world);
    }

    /**
     * Processes the client request, executing the corresponding command and returning
     * the server response as a JSON string.
     *
     * @param clientRequest the client request as a JSON string.
     * @return the server response as a JSON string.
     */
    public String processRequest(String clientRequest) {
        JsonNode rootNode = JsonUtility.jsonFieldAccess(clientRequest);
        Command command = getCommand(rootNode, world);

        System.out.println("\u001B[32mCommand \u001B[0m[" + command.getCommand() + "] \u001B[32mreceived from client on local port: \u001B[0m" + clientSocket.getPort());
        if (command.getCommand().equalsIgnoreCase("LAUNCH")) {
            LaunchCommand launchCommand = (LaunchCommand) command;
            robot = launchCommand.createRobot(rootNode, world, clientSocket.getPort());
        }
        ServerResponse serverResponseObject = command.execute(robot, world);
        return JsonUtility.toJson(serverResponseObject);
    }
}
