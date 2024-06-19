package za.co.wethinkcode.robotworlds.client;

import za.co.wethinkcode.robotworlds.JsonUtility;
import za.co.wethinkcode.robotworlds.server.ServerResponse;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.net.Socket;
import java.util.List;
import java.util.Map;

/**
 * The RobotClient class represents a client that connects to a server and interacts with a robot.
 */
public class RobotClient {
    private Socket clientSocket;
    PrintWriter out;
    private BufferedReader in;
    boolean isRobotLaunched = false;
    private String robotName;

    /**
     * The main method is the entry point of the program.
     * It parses command-line arguments, connects to the server, and starts the client.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 5000;

        if (args.length == 2) {
            try {
                serverAddress = args[0];
                serverPort = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("\nInvalid argument for \"ADDRESS\" and/or \"PORT\"\n\nUsing 'localhost' '5000'");
            }
        } else {
            System.err.println("\nInvalid argument for \"ADDRESS\" and/or \"PORT\"\n\nUsing 'localhost' '5000'");
        }

        System.out.println("|====================================|");
        System.out.println("|=========   ROBOT WORLDS   =========|");
        System.out.println("|====================================|\n");

        RobotClient client = new RobotClient();

        client.startConnection(serverAddress, serverPort);
        client.run();
    }

    /**
     * Starts a connection to the server.
     *
     * @param serverAddress The address of the server.
     * @param serverPort The port of the server.
     */
    public void startConnection(String serverAddress, int serverPort) {
        System.out.println("Connecting...");
        try {
            clientSocket = new Socket(serverAddress, serverPort);
        } catch (IOException e) {
            System.err.println("Error connecting to server. Check port");
            System.exit(1);
        }
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException("out exception: " + e);
        }
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException("in exception: " + e);
        }
        System.out.println("Connected to server on port: " + serverPort);
    }

    /**
     * Runs the client, handling user input and sending requests to the server.
     */
    public void run() {
        while (true) {
            while (!isRobotLaunched) {
                String userInput = UserInput.getInput("\nLaunch a robot:\nUse 'launch <make> <name>'");
                robotName = RobotLaunch.launchRobot(this, userInput);
            }
            String userInput = UserInput.getInput("\n" + robotName + "> What must I do next?");

            if (userInput.equalsIgnoreCase("EXIT")) {break;}

            ClientRequest request = UserInput.handleUserInput(robotName, userInput);
            String clientRequest = JsonUtility.toJson(request);
            sendClientRequest(clientRequest);
            String serverResponse = getServerResponse();
            ServerResponse serverResponseObject = getServerResponseObject(serverResponse);
            printRequestResult(robotName, request.command(), serverResponseObject);
        }
    }

    /**
     * Sends a client request to the server.
     *
     * @param clientRequest The client request in JSON format.
     */
    private void sendClientRequest(String clientRequest) {
        out.println(clientRequest);
    }

    /**
     * Prints the result of a client request.
     *
     * @param robotName The name of the robot.
     * @param command The command that was sent to the server.
     * @param serverResponse The server response.
     */
    private void printRequestResult(String robotName, String command, ServerResponse serverResponse) {
        String result = serverResponse.getResult();
        Map<String, Object> data = serverResponse.getData();
        Map<String, Object> state = serverResponse.getState();

        if (result.equalsIgnoreCase("OK")) {
            switch (command.toLowerCase()) {
                case "look":
                    printLookResult(robotName, data);
                    break;
                case "forward":
                case "back":
                    printMoveResult(robotName, data);
                    break;
                case "turn":
                    printTurnResult(robotName, data);
                    break;
                case "fire":
                    printFireResult(robotName, robotName, data, state);
                    break;
                case "reload":
                    printReloadResult(robotName, state);
                    break;
                case "repair":
                    printRepairResult(robotName, state);
                    break;
                case "shield":
                case "state":
                    printRobotState(robotName, state);
                    break;
                default:
                    System.out.println(data.get("message"));
            }
        } else {
            System.out.println(data.get("message"));
        }
    }

    /**
     * Prints the result of a repair command.
     *
     * @param robotName The name of the robot.
     * @param state The state of the robot after the repair command.
     */
    private void printRepairResult(String robotName, Map<String, Object> state) {
        System.out.println(robotName + "> Shield repaired.\nShield strength: " + state.get("shields"));
    }

    /**
     * Prints the state of the robot.
     *
     * @param robotName The name of the robot.
     * @param state The state of the robot.
     */
    private void printRobotState(String robotName, Map<String, Object> state) {
        @SuppressWarnings("unchecked")
        Map<String, Integer> robotPosition = (Map<String, Integer>) state.get("position");

        if (robotPosition != null) {
            String robotFacing = (String) state.get("direction");

            System.out.println(robotName + " is at [\u001B[33m" + robotPosition.get("x") + "\u001B[0m,\u001B[34m" + robotPosition.get("y") + "\u001B[0m], facing \u001B[32m" + robotFacing + "\u001B[0m");
        }
    }

    /**
     * Prints the result of a look command.
     *
     * @param robotName The name of the robot.
     * @param data The data returned by the server.
     */
    private void printLookResult(String robotName, Map<String, Object> data) {
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> objects = (List<Map<String, Object>>) data.get("objects");

        if (!objects.isEmpty()) {
            System.out.println(robotName + "> Objects detected:");
            for (Map<String, Object> object : objects) {
                String objectDirection = (String) object.get("direction");
                String objectType = (String) object.get("type");
                int objectDistance = (int) object.get("distance");

                System.out.println(" - Direction: [\u001B[33m" + objectDirection + "\u001B[0m], Type: [\u001B[34m" + objectType + "\u001B[0m], Distance: [\u001B[32m" + objectDistance + "\u001B[0m]");
            }
        } else {
            System.out.println(robotName + "> No objects detected:");
        }
    }

    /**
     * Prints the result of a move command.
     *
     * @param robotName The name of the robot.
     * @param data The data returned by the server.
     */
    private void printMoveResult(String robotName, Map<String, Object> data) {
        System.out.println(robotName + "> " + data.get("message"));
    }

    /**
     * Prints the result of a turn command.
     *
     * @param robotName The name of the robot.
     * @param data The data returned by the server.
     */
    private void printTurnResult(String robotName, Map<String, Object> data) {
        System.out.println(robotName + "> " + data.get("message"));
    }

    /**
     * Prints the result of a fire command.
     *
     * @param robotName The name of the robot.
     * @param enemyName The name of the enemy robot.
     * @param data The data returned by the server.
     * @param state The state of the robot after the fire command.
     */
    private void printFireResult(String robotName, String enemyName, Map<String, Object> data, Map<String, Object> state) {
        String message = (String) data.get("message");

        if (message.equalsIgnoreCase("HIT")) {
            @SuppressWarnings("unchecked")
            Map<String, Object> enemyState = (Map<String, Object>) data.get("state");
            printRobotState(enemyName, enemyState);
        } else if (message.equalsIgnoreCase("MISS")) {
            System.out.println(robotName + "> Missed!");
        }

        int robotShots = (int) state.get("shots");
        if (robotShots != 0) {
            System.out.println("\n" + robotName + "> " + robotShots + " shot(s) left.");
        }
    }

    /**
     * Prints the result of a reload command.
     *
     * @param robotName The name of the robot.
     * @param state The state of the robot after the reload command.
     */
    private void printReloadResult(String robotName, Map<String, Object> state) {
        System.out.println(robotName + "> " + state.get("shots"));
    }

    /**
     * Retrieves the server response as a string.
     *
     * @return The server response.
     */
    public String getServerResponse() {
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException("IOException reading server response", e);
        }
    }

    /**
     * Converts the server response JSON string to a ServerResponse object.
     *
     * @param serverResponse The server response JSON string.
     * @return The ServerResponse object.
     */
    public ServerResponse getServerResponseObject(String serverResponse) {
        return JsonUtility.fromJson(serverResponse);
    }
}
