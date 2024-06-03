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

    /**
     * Launches a robot based on user input and communicates with the server.
     *
     * This method prompts the user to enter a command to launch a robot in the format
     * 'launch <make> <name>'. It processes the input to create a ClientRequest object,
     * serializes this request to JSON, and sends it to the server. After receiving
     * the server's response, it deserializes the response, extracts relevant data, and
     * prints the robot's position, direction, and status. Finally, it returns the name
     * of the robot.
     *
     * @return the name of the robot that was launched
     */
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

    /**
     * Handles the interaction loop with the user for issuing commands to a specific robot.
     *
     * This method continuously prompts the user to input commands for the specified robot
     * until the user enters "exit". For each command, it creates a ClientRequest object,
     * serializes it to JSON, and sends it to the server. It then receives the server's response,
     * deserializes it into a ServerResponse object, and prompts the user for the next command.
     *
     * @param robotName the name of the robot for which commands are being issued
     */
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

    /**
     * Prompts the user for input with the specified prompt message and returns the input.
     *
     * This method repeatedly prompts the user with the given message until a non-blank
     * input is received. It prints the prompt message, reads the input from the inputReader,
     * and ensures that the input is not blank before returning it. If an IOException occurs
     * while reading the input, it throws a RuntimeException.
     *
     * @param prompt the message to display to the user
     * @return the user's input as a non-blank string
     * @throws RuntimeException if an IOException occurs while reading the input
     */
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

    /**
     * Reads the server response from the input stream.
     *
     * This method reads a line from the input stream `in` and returns it as a string.
     * If an `IOException` occurs while reading, it prints an error message and throws
     * a `RuntimeException`.
     *
     * @return the server response as a string
     * @throws RuntimeException if an IOException occurs while reading the server response
     */
    private String getServerResponse() {
        String serverResponse = null;
        try {
            return in.readLine();
        } catch (IOException e) {
            System.out.println("serverResponse exception");
            throw new RuntimeException();
        }
    }

    /**
     * Converts a JSON string representing a server response into a ServerResponse object.
     *
     * This method uses the `Json` class to deserialize the provided JSON string into
     * a `ServerResponse` object.
     *
     * @param serverResponse the JSON string representing the server response
     * @return the deserialized `ServerResponse` object
     */
    private ServerResponse getServerResponseObject(String serverResponse) {
        Json json = new Json();
        return json.fromJson(serverResponse);
    }
}
