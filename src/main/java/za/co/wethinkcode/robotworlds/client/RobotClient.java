package za.co.wethinkcode.robotworlds.client;

import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.Robot;
import za.co.wethinkcode.robotworlds.Sleep;
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
        Map<String, Object> state = serverResponseObject.getState();
        String robotName = request.getRobot();
        Object position = data.get("position");
        Object direction = state.get("direction");
        Object status = state.get("status");
        System.out.println(position+" "+"["+direction+"]"+" "+robotName+"> "+status);
        return robotName;
    }

    private void run(String robotName) {
        String userInput = getInput(robotName+"> What must I do next?");
        while (!userInput.equalsIgnoreCase("exit")) {
            // get ClientRequest obj using user input
            ClientRequest request = UserInput.handleUserInput(userInput);
            Json json = new Json();
            // convert to json String
            String clientRequest = json.toJson(request);
            // send to server
            out.println(clientRequest);
            // get server response as json String
            String serverResponse = getServerResponse();
            // convert json String to ServerResponse object
            ServerResponse serverResponseObject = getServerResponseObject(serverResponse);
            // at this point, the command has already been carried out on the server side,
            // use this server response object to print relevant fields to user
            userInput = getInput(robotName+"> What must I do next?");
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
        String serverResponse = null;
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
