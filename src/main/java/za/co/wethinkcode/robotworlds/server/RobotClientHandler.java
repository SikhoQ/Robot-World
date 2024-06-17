package za.co.wethinkcode.robotworlds.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
<<<<<<< HEAD
import com.fasterxml.jackson.databind.JsonNode;
import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.robot.SimpleBot;
=======

import com.fasterxml.jackson.databind.JsonNode;
import za.co.wethinkcode.robotworlds.JsonUtility;
import za.co.wethinkcode.robotworlds.robot.Robot;
>>>>>>> sikho
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
    private Robot robot;

    public RobotClientHandler(Socket clientSocket, IWorld world) {
        this.clientSocket = clientSocket;
        this.world = world;
        this.robot = null;
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
     * Disconnects the client from the server.
     *
     * @throws IOException if an I/O error occurs while closing the client socket
     */
    public void disconnectClient() throws IOException {
        this.clientSocket.close();
    }


    public Command getCommand(JsonNode rootNode, IWorld world) {
        return Command.create(rootNode, world);
    }

    /**
    * use this function to handle error responses since each command's execute
    * returns a ServerResponse object
    */
    public String processRequest(String clientRequest) {
        JsonNode rootNode = JsonUtility.jsonFieldAccess(clientRequest);
        Command command = getCommand(rootNode, world);

        System.out.println("\u001B[32mCommand \u001B[0m["+command.getCommand()+"] \u001B[32mreceived from client on local port: \u001B[0m"+clientSocket.getPort());
        if (command.getCommand().equalsIgnoreCase("LAUNCH")) {
            LaunchCommand launchCommand = (LaunchCommand) command;
            robot = launchCommand.createRobot(rootNode, world, clientSocket.getPort());
        }
        ServerResponse serverResponseObject = command.execute(robot, world);
        return JsonUtility.toJson(serverResponseObject);
    }
}
