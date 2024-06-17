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

    // ANSI color codes
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";

    public static void main(String[] args) {
        String serverAddress = "";
        int serverPort = 0;

        if (args.length == 2) {
            try {
                serverAddress = args[0];
                serverPort = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                throw new RuntimeException(ANSI_RED + "\nInvalid argument for \"ADDRESS\" and/or \"PORT\"\n\nQuitting..." + ANSI_RESET);
            }
        } else {
            throw new RuntimeException(ANSI_RED + "\nInvalid argument for \"ADDRESS\" and/or \"PORT\"\n\nQuitting..." + ANSI_RESET);
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
            throw new RuntimeException(ANSI_RED + "clientSocket exception: " + e + ANSI_RESET);
        }
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(ANSI_RED + "out exception: " + e + ANSI_RESET);
        }
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(ANSI_RED + "in exception: " + e + ANSI_RESET);
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
            System.out.println(ANSI_RED + serverResponseObject.getData().get("message") + ANSI_RESET);
        }

        Map<String, Object> state = serverResponseObject.getState();
        String robotName = request.robot();

        @SuppressWarnings("unchecked")
        Map<String, Integer> robotPosition = (Map<String, Integer>) state.get("position");
        int robotPositionX = robotPosition.get("x");
        int robotPositionY = robotPosition.get("y");
        String robotFacing = (String) state.get("direction");

        System.out.println(ANSI_GREEN + robotName + " launched at [" + robotPositionX + "," + robotPositionY + "], facing " + robotFacing + ANSI_RESET);
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
            System.out.println(ANSI_RED + data.get("message") + ANSI_RESET);
        }
    }

    private void printRepairResult(String robotName, Map<String, Object> state) {
        System.out.println(ANSI_GREEN + robotName + "> Shield repaired.\nShield strength: " + state.get("shields") + ANSI_RESET);
    }

    private void printRobotState(String robotName, Map<String, Object> state) {
        @SuppressWarnings("unchecked")
        Map<String, Integer> robotPosition = (Map<String, Integer>) state.get("position");

        if (robotPosition != null) {
            String robotFacing = (String) state.get("direction");

            System.out.println(ANSI_GREEN + robotName + " is at [" + robotPosition.get("x") + "," + robotPosition.get("y") + "], facing " + robotFacing + ANSI_RESET);
        }
    }

    private void printLookResult(String robotName, Map<String, Object> data) {
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> objects = (List<Map<String, Object>>) data.get("objects");

        if (!objects.isEmpty()) {
            System.out.println(ANSI_GREEN + robotName + "> Objects detected:" + ANSI_RESET);
            for (Map<String, Object> object : objects) {
                String objectDirection = (String) object.get("direction");
                String objectType = (String) object.get("type");
                int objectDistance = (int) object.get("distance");

                System.out.println(ANSI_GREEN + " - Direction: [" + objectDirection + "], Type: [" + objectType + "], Distance: [" + objectDistance + "]" + ANSI_RESET);
            }
        } else {
            System.out.println(ANSI_GREEN + robotName + "> No objects detected:" + ANSI_RESET);
        }
    }

    private void printMoveResult(String robotName, Map<String, Object> data) {
        System.out.println(ANSI_GREEN + robotName + "> " + data.get("message") + ANSI_RESET);
    }

    private void printTurnResult(String robotName, Map<String, Object> data) {
        System.out.println(ANSI_GREEN + robotName + "> " + data.get("message") + ANSI_RESET);
    }

    private void printFireResult(String robotName, String enemyName, Map<String, Object> data, Map<String, Object> state) {
        String message = (String) data.get("message");

        if (message.equalsIgnoreCase("HIT")) {
            @SuppressWarnings("unchecked")
            Map<String, Object> enemyState = (Map<String, Object>) data.get("state");
            printRobotState(enemyName, enemyState);
        } else if (message.equalsIgnoreCase("MISS")) {
            System.out.println(ANSI_RED + robotName + "> Missed!" + ANSI_RESET);
        }

        int robotShots = (int) state.get("shots");
        if (robotShots != 0) {
            System.out.println(ANSI_GREEN + "\n" + robotName + "> " + robotShots + " shot(s) left" + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "\n" + robotName + "> No shots left. Reload gun" + ANSI_RESET);
        }
    }

    private void printReloadResult(String robotName, Map<String, Object> state) {
        System.out.println(ANSI_GREEN + robotName + "> Gun reloaded. " + state.get("shots") + " shot(s) left" + ANSI_RESET);
    }

    private String getServerResponse() {
        try {
            return in.readLine();
        } catch (IOException e) {
            System.out.println(ANSI_RED + "serverResponse exception" + ANSI_RESET);
            throw new RuntimeException(e);
        }
    }

    private ServerResponse getServerResponseObject(String serverResponse) {
        return Json.fromJson(serverResponse);
    }
}
