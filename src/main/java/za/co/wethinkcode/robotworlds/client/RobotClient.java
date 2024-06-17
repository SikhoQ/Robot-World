package za.co.wethinkcode.robotworlds.client;

import za.co.wethinkcode.robotworlds.JsonUtility;
import za.co.wethinkcode.robotworlds.Sleep;
import za.co.wethinkcode.robotworlds.server.ServerResponse;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class RobotClient {
    private Socket clientSocket;
    PrintWriter out;
    private BufferedReader in;
    boolean isRobotLaunched = false;
    private String robotName;
    private boolean serverRunning = true;

    public static void main(String[] args) {
        String serverAddress = null;
        int serverPort = 0;

        if (args.length == 2) {
            try {
                serverAddress = args[0];
                serverPort = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("\nInvalid argument for \"ADDRESS\" and/or \"PORT\"\n\nQuitting...");
                System.exit(1);
            }
        } else {
            System.err.println("\nInvalid argument for \"ADDRESS\" and/or \"PORT\"\n\nQuitting...");
            System.exit(1);
        }

        System.out.println("|====================================|");
        System.out.println("|=========   ROBOT WORLDS   =========|");
        System.out.println("|====================================|\n");

        RobotClient client = new RobotClient();

        client.startConnection(serverAddress, serverPort);
        client.run();
    }

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

    public void stopConnection() {
        try {
            if (clientSocket != null) {
                clientSocket.close();
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Error closing client socket: " + e.getMessage());
        }
    }

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

    private void sendClientRequest(String clientRequest) {
        out.println(clientRequest);
    }

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

    private void printRepairResult(String robotName, Map<String, Object> state) {
        System.out.println(robotName + "> Shield repaired.\nShield strength: "+state.get("shields"));
    }

    private void printRobotState(String robotName, Map<String, Object> state) {
        @SuppressWarnings("unchecked")
        Map<String, Integer> robotPosition = (Map<String, Integer>) state.get("position");

        if (robotPosition != null) {
            String robotFacing = (String) state.get("direction");

            System.out.println(robotName + " is at [" + robotPosition.get("x") + "," + robotPosition.get("y") + "], facing " + robotFacing);
        }
    }

    private void printLookResult(String robotName, Map<String, Object> data) {
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> objects = (List<Map<String, Object>>) data.get("objects");

        if (!objects.isEmpty()) {
            System.out.println(robotName + "> Objects detected:");
            for (Map<String, Object> object : objects) {
                String objectDirection = (String) object.get("direction");
                String objectType = (String) object.get("type");
                int objectDistance = (int) object.get("distance");

                System.out.println(" - Direction: [" + objectDirection + "], Type: [" + objectType + "], Distance: [" + objectDistance + "]");
            }
        } else {
            System.out.println(robotName + "> No objects detected:");
        }
    }

    private void printMoveResult(String robotName, Map<String, Object> data) {
        System.out.println(robotName + "> " + data.get("message"));
    }

    private void printTurnResult(String robotName, Map<String, Object> data) {
        System.out.println(robotName + "> " + data.get("message"));
    }

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
            System.out.println("\n" + robotName + "> " + robotShots + " shot(s) left");
        } else {
            System.out.println("\n" + robotName + "> No shots left. Reload gun");
        }
    }

    private void printReloadResult(String robotName, Map<String, Object> state) {
        System.out.println(robotName + "> Gun reloaded. " + state.get("shots") + " shot(s) left");
    }

    public String getServerResponse() {
        try {
            String response = in.readLine();
            if (response == null) {
                System.out.println("Server connection closed by the server.");
                serverRunning = false; // Set flag to false when server shuts down
            }
            return response;
        } catch (IOException e) {
            System.out.println("Error reading server response: " + e.getMessage());
            serverRunning = false; // Set flag to false if an exception occurs
            return null;
        }
    }


    public ServerResponse getServerResponseObject(String serverResponse) {
        return JsonUtility.fromJson(serverResponse);
    }
}