package za.co.wethinkcode.robotworlds.client;

import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.Sleep;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.configuration.Config;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class RobotClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public static void main(String[] args) {
        String serverAddress = "";
        int serverPort = 0;

        if (args.length == 2) {
            try {
                serverAddress = args[0];
                serverPort = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                throw new RuntimeException("\nInvalid argument for \"ADDRESS\" and/or \"PORT\"\n\nQuitting...");
            }
        } else {
            throw new RuntimeException("\nInvalid argument for \"ADDRESS\" and/or \"PORT\"\n\nQuitting...");
        }

        System.out.println("|====================================|");
        System.out.println("|=========   ROBOT WORLDS   =========|");
        System.out.println("|====================================|\n");

        RobotClient client = new RobotClient();

        client.startConnection(serverAddress, serverPort);
        String robotName = client.launchRobot();
        client.run(robotName);
    }

    public void startConnection(String serverAddress, int serverPort) {
        System.out.println("Connecting...");
        Sleep.sleep(1000);
        try {
            clientSocket = new Socket(serverAddress, serverPort);
        } catch (IOException e) {
            throw new RuntimeException("clientSocket exception: " + e);
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
        Sleep.sleep(1500);
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
            String prompt = "\nLaunch a robot:\nUse 'launch <make> <name>'" +
                    "\nAvailable robot makes:\n* SNIPERBOT\n* SIMPLEBOT";
            String userInput = UserInput.getInput(prompt);
            if (userInput.equalsIgnoreCase("EXIT")) {
                try {
                    stopConnection();
                } catch (IOException ignored) {}
                System.exit(0);
            }
            String robotName = "";
            String[] userInputSplit = userInput.split(" ", 2);
            if (userInputSplit.length == 2) {
                robotName = (userInputSplit[1].split(" ").length == 2) ? (userInputSplit[1].split(" ")[1]) : "";
            }
            request = UserInput.handleUserInput(robotName, userInput);
            String clientRequest = Json.toJson(request);
            out.println(clientRequest);
            String serverResponse = getServerResponse();
            serverResponseObject = getServerResponseObject(serverResponse);
            if (serverResponseObject.getResult().equals("OK")) {
                break;
            }
            System.out.println(serverResponseObject.getData().get("message"));
        }

        Map<String, Object> state = serverResponseObject.getState();
        String robotName = request.robot();

        @SuppressWarnings("unchecked")
        Map<String, Integer> robotPosition = (Map<String, Integer>) state.get("position");
        int robotPositionX = robotPosition.get("x");
        int robotPositionY = robotPosition.get("y");
        String robotFacing = (String) state.get("direction");

        System.out.println(robotName + " launched at [" + robotPositionX + "," + robotPositionY + "], facing " + robotFacing);
        return robotName;
    }

    private void run(String robotName) {
        String userInput = UserInput.getInput("\n" + robotName + "> What must I do next?");
        while (!userInput.equalsIgnoreCase("exit")) {
            ClientRequest request = UserInput.handleUserInput(robotName, userInput);
            String clientRequest = Json.toJson(request);
            sendClientRequest(clientRequest);
            String serverResponse = getServerResponse();
            ServerResponse serverResponseObject = getServerResponseObject(serverResponse);
            printRequestResult(robotName, request.command(), serverResponseObject, request);
            userInput = UserInput.getInput("\n" + robotName + "> What must I do next?");
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
            switch (command.toLowerCase()) {
                case "launch":
                    launchRobot();
                    break;
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

    private String getServerResponse() {
        try {
            return in.readLine();
        } catch (IOException e) {
            System.out.println("serverResponse exception");
            throw new RuntimeException(e);
        }
    }

    private ServerResponse getServerResponseObject(String serverResponse) {
        return Json.fromJson(serverResponse);
    }
}