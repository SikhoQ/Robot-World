package za.co.wethinkcode.robotworlds.client;

import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.Position;
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

    /**
     * Main entry point for the RobotClient application.
     * Establishes a connection to the server, reads and processes user commands.
     *
     * @param args Command line arguments (not used in this application)
     */
    public static void main(String[] args) {
        System.out.println("|====================================|");
        System.out.println("|=========   ROBOT WORLDS   =========|");
        System.out.println("|====================================|\n");

        RobotClient client = new RobotClient();

        client.startConnection(ADDRESS, PORT);
        String robotName = client.launchRobot();
        client.run(robotName);
    }

    public void startConnection(String ipAddress, int port) {
        System.out.println("Connecting...");
        Sleep.sleep(1500);
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
        System.out.println("Connected to server on port: " + PORT);
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public String launchRobot() {
        ServerResponse serverResponseObject;
        ClientRequest request;
        while (true) {
            // prompt user for launch and get input
            String prompt = "\nLaunch a robot:\nUse 'launch <Bot type> <Bot name>'\n" +
                    "Available robot types:\n* SNIPERBOT\n* SIMPLEBOT";
            String userInput = getInput(prompt);
            if (userInput.equalsIgnoreCase("EXIT")) {
                try {
                    stopConnection();
                } catch (IOException ignored) {}
                System.exit(0);
            }
            // process input and get relevant ClientRequest instance
            request = UserInput.handleUserInput(userInput);
            // use this instance to serialize user input as client request to send to server
            Json json = new Json();
            String clientRequest = json.toJson(request);
            // send serialized request to server
            out.println(clientRequest);
            // get server response
            String serverResponse = getServerResponse();
            // get server response object
            serverResponseObject = getServerResponseObject(serverResponse);
            if (serverResponseObject.getResult().equals("OK")) {
                break;
            }
            System.out.println(serverResponseObject.getData().get("message"));
        }

        Map<String, Object> state = serverResponseObject.getState();
        String robotName = (String) request.arguments()[1];

        @SuppressWarnings("unchecked")
        Map<String, Integer> position = (Map<String, Integer>) state.get("position");
        int xCoord = position.get("x");
        int yCoord = position.get("y");
        String direction = (String) state.get("direction");

        System.out.println(robotName+" launched at ["+xCoord+","+yCoord+"], facing "+direction);
        return robotName;
    }

    private void run(String robotName) {
        String userInput = getInput("\n"+robotName+"> What must I do next?");
        while (!userInput.equalsIgnoreCase("exit")) {
            ClientRequest request = UserInput.handleUserInput(userInput);
            Json json = new Json();
            String clientRequest = json.toJson(request);
            sendClientRequest(clientRequest);
            String serverResponse = getServerResponse();
            ServerResponse serverResponseObject = getServerResponseObject(serverResponse);
            printRequestResult(robotName, request.command(), serverResponseObject, request);
            userInput = getInput("\n"+robotName+"> What must I do next?");
        }

    }

    private void sendClientRequest(String clientRequest) {
        out.println(clientRequest);
    }

    private void printRequestResult(String robotName, String command, ServerResponse serverResponse, ClientRequest request) {
        String result = serverResponse.getResult();
        Map<String, Object> data = serverResponse.getData();
        Map<String, Object> state = serverResponse.getState();

        if (result.equalsIgnoreCase("OK")) {
            @SuppressWarnings("unchecked")
            Map<String, Integer> position = (Map<String, Integer>) state.get("position");
            String robotDirection = (String) state.get("direction");

            int xCoord = 0;
            int yCoord = 0;
            if (position != null) {
                xCoord = position.get("x");
                yCoord = position.get("y");
            }
            // "LOOK" command
            if (command.equalsIgnoreCase("LOOK")) {
                // "data" for "LOOK" command is a list of maps
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> objects = (List<Map<String, Object>>) data.get("objects");
                // Get the data map from the server response
                if (!objects.isEmpty()) {
                    System.out.println(robotName + "> Objects detected:");
                    for (Map<String, Object> object : objects) {
                        String objectDirection = (String) object.get("direction");
                        String type = (String) object.get("type");
                        int distance = (int) object.get("distance");

                        System.out.println(" - Direction: [" + objectDirection + "], Type: [" + type + "], Distance: [" + distance + "]");
                    }
                } else {
                    System.out.println(robotName + "> No objects detected:");
                }

            } else if (command.equalsIgnoreCase("FORWARD") ||
                    command.equalsIgnoreCase("BACK")) {
                System.out.println(robotName+"> "+data.get("message"));
                System.out.println("Now at ["+xCoord+","+yCoord+"], facing "+robotDirection);
            } else if (command.equalsIgnoreCase("TURN")) {
                System.out.println(robotName+"> "+data.get("message"));
                System.out.println("Now at ["+xCoord+","+yCoord+"], facing "+robotDirection);
            } else if (command.equalsIgnoreCase("STATE")) {
                int shields = (int) state.get("shields");
                int shots = (int) state.get("shots");
                String status = (String) state.get("status");

                assert position != null;
                System.out.println("Position : ("+position.get("x")+","+position.get("y")+")");
                System.out.println("Direction: ["+robotDirection+"]");
                System.out.println("Shields  : ["+shields+"]");
                System.out.println("Shots    : "+"["+shots+"]");
                System.out.println("Status   : "+status);
            } else if (command.equalsIgnoreCase("ORIENTATION")) {
                System.out.println("Direction: ["+robotDirection+"]");
            }
        } else {
            System.out.println(data.get("message"));
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
            throw new RuntimeException(e);
        }
    }

    private ServerResponse getServerResponseObject(String serverResponse) {
        Json json = new Json();
        return json.fromJson(serverResponse);
    }
}
