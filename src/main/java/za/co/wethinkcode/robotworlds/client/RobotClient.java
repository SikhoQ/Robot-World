package za.co.wethinkcode.robotworlds.client;

import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.server.ServerResponse;

import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 * Class to represent a client application that connects to a robot server.
 * It enables the user to send commands to the server and receive responses.
 *x
 * The application reads commands from the user through the standard input and sends them to the server.
 * It then prints the server's responses to the standard output.
 */
public class RobotClient {
    private static final String ADDRESS = "localhost";
    private static final int PORT = 5000;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader inputReader;

    public void startConnection(String ipAddress, int port) {
        try {
            clientSocket = new Socket(ADDRESS, PORT);
        } catch (IOException e) {
            throw new RuntimeException("clientSocket exception: "+e);
        }
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException("out exception: "+e);
        }
        try {
            in = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException("in exception: "+e);
        }
        inputReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Connected to server on port: " + PORT + "\n");
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
    /**
     * Main entry point for the RobotClient application.
     * Establishes a connection to the server, reads and processes user commands.
     *
     * @param args Command line arguments (not used in this application)
     */
    public static void main(String[] args) {
        System.out.println("*********************************************");
        System.out.println("*             ROBOT WORLD                   *");
        System.out.println("*********************************************\n");

        RobotClient client = new RobotClient();

        client.startConnection(ADDRESS, PORT);
        String robotName = client.launchRobot();
        client.run(robotName);
    }

    public String launchRobot() {
        // prompt user for launch and get input
        String userInput = getInput("Launch a robot.\nUse 'launch <make> <name>'");
        // process input and get relevant ClientRequest instance
        ClientRequest request = UserInput.handleUserInput(userInput);
        // use this instance to serialize user input as client request to send to server
        Json json = new Json();
        String clientRequest = json.toJson(request);
        // send serialized request to server
        out.println(clientRequest);
        // get server response
        String serverResponse = getServerResponse();
        // get server response object
        ServerResponse serverResponseObject = getServerResponseObject(serverResponse);

        Map<String, Object> data = serverResponseObject.getData();
        String robotName = request.getRobot();
        Object position = data.get("position");
        System.out.println(robotName+" launched at "+position);
        return robotName;
    }

    private void run(String robotName) {
        String userInput = getInput(robotName+"> What must I do next?");
        while (!userInput.equalsIgnoreCase("exit")) {
            ClientRequest request = UserInput.handleUserInput(userInput);
            Json json = new Json();
            String clientRequest = json.toJson(request);
            out.println(clientRequest);
            String serverResponse = getServerResponse();
            ServerResponse serverResponseObject = getServerResponseObject(serverResponse);
            printRequestResult(robotName, request.getCommand(), serverResponseObject);
            userInput = getInput(robotName+"> What must I do next?");
        }
    }

    private void printRequestResult(String robotName, String command, ServerResponse serverResponse) {
        Map<String, Object> data = serverResponse.getData();
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> objects = (List<Map<String, Object>>) data.get("objects");

        // Check if the command is "LOOK"
        if (command.equalsIgnoreCase("LOOK")) {
            // Get the data map from the server response
            if (!objects.isEmpty()) {
                System.out.println("Objects detected by " + robotName + ":");
                for (Map<String, Object> object : objects) {
                    String direction = (String) object.get("direction");
                    String type = (String) object.get("type");
                    int distance = (int) object.get("distance");

                    System.out.println(" - Direction: " + direction + ", Type: " + type + ", Distance: " + distance);
                }
            } else {
                System.out.println("No objects detected by " + robotName);
            }
        }
    }


    private String getInput(String prompt) {
        String input;
        do {
            System.out.println(prompt);
            try {
                input = inputReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while (input.isBlank());
        return input;
    }

    private String getServerResponse() {
        try {
            return in.readLine();
        } catch (IOException e) {
            System.out.println("serverResponse exception");
            throw new RuntimeException();
        }
    }

    private ServerResponse getServerResponseObject(String serverResponse) {
        Json json = new Json();
        return json.fromJson(serverResponse);
    }
}
