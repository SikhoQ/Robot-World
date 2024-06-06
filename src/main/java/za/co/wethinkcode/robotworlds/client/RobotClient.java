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
        // look command
        if (command.equalsIgnoreCase("LOOK")) {
            Map<String, Object> data = serverResponse.getData();
            Set<Map.Entry<String, Object>> entry = data.entrySet();

            @SuppressWarnings("unchecked") // entryItem is certainly of type Map<String, List<Map<String, Object>>>
            Map<String, List<Map<String, Object>>> entryItem =
                    (Map<String, List<Map<String, Object>>>) entry.toArray()[0];

            List<Map<String, Object>> objects = entryItem.get("objects");

            // each Map of String to Object
            // Object will be value of 'object' fields
            // from data Map returned, get value of the single entry
            if (!objects.isEmpty()) {
                System.out.println(robotName+"> Objects detected");
            } else {
                System.out.println(robotName+"> No objects detected");
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
