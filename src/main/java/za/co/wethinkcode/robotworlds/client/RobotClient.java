package za.co.wethinkcode.robotworlds.client;

import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.Sleep;
import za.co.wethinkcode.robotworlds.server.ServerResponse;
import za.co.wethinkcode.robotworlds.world.configuration.Config;

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
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private static int colorIndex = 0;

    private static final String[] COLORS = {
            "\u001B[31m", // Red
            "\u001B[32m", // Green
            "\u001B[33m", // Yellow
            "\u001B[34m", // Blue
            "\u001B[35m", // Purple
            "\u001B[36m", // Cyan
            "\u001B[37m", // White
            "\u001B[0m"   // Reset
    };

    /**
     * Main entry point for the RobotClient application.
     * Establishes a connection to the server, reads and processes user commands.
     *
     * @param args Command line arguments (not used in this application)
     */
    public static void main(String[] args) {
        String ADDRESS = "";
        int PORT = 0;

        if (args.length == 2) {
            try {
                ADDRESS = args[0];
                PORT = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                throw new RuntimeException("\nInvalid argument for \"ADDRESS\" and/or \"PORT\"\n\nQuitting...");
            }
        } else {
            throw new RuntimeException("\nInvalid argument for \"ADDRESS\" and/or \"PORT\"\n\nQuitting...");
        }

        printWithColor("|====================================|");
        printWithColor("|=========   ROBOT WORLDS   =========|");
        printWithColor("|====================================|\n");

        RobotClient client = new RobotClient();

        client.startConnection(ADDRESS, PORT);
        String robotName = client.launchRobot();
        client.run(robotName);
    }

    public void startConnection(String ipAddress, int port) {
        printWithColor("Connecting...");
        Sleep.sleep(1000);
        try {
            clientSocket = new Socket(ipAddress, port);
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
        printWithColor("Connected to server on port: " + port);
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
            printWithColor(serverResponseObject.getData().get("message").toString());
        }

        Map<String, Object> state = serverResponseObject.getState();
        String robotName = request.robot();

        @SuppressWarnings("unchecked")
        Map<String, Integer> position = (Map<String, Integer>) state.get("position");
        int xCoord = position.get("x");
        int yCoord = position.get("y");
        String direction = (String) state.get("direction");

        printWithColor(robotName+" launched at ["+xCoord+","+yCoord+"], facing "+direction);
        return robotName;
    }

    private void run(String robotName) {
        String userInput = UserInput.getInput("\n"+robotName+"> What must I do next?");
        while (!userInput.equalsIgnoreCase("exit")) {
            ClientRequest request = UserInput.handleUserInput(robotName, userInput);
            String clientRequest = Json.toJson(request);
            sendClientRequest(clientRequest);
            String serverResponse = getServerResponse();
            ServerResponse serverResponseObject = getServerResponseObject(serverResponse);
            printRequestResult(robotName, request.command(), serverResponseObject, request);
            userInput = UserInput.getInput("\n"+robotName+"> What must I do next?");
        }
    }

    private void sendClientRequest(String clientRequest) {
        out.println(clientRequest);
    }

    private void printRequestResult(String robotName, String command, ServerResponse serverResponse, ClientRequest request) {
        String result = serverResponse.getResult();
        Map<String, Object> data = serverResponse.getData();
        Map<String, Object> state = serverResponse.getState();
        Config config = Config.readConfiguration();

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
            if (command.equalsIgnoreCase("LOOK")) {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> objects = (List<Map<String, Object>>) data.get("objects");
                if (!objects.isEmpty()) {
                    printWithColor(robotName + "> Objects detected:");
                    for (Map<String, Object> object : objects) {
                        String objectDirection = (String) object.get("direction");
                        String type = (String) object.get("type");
                        int distance = (int) object.get("distance");

                        printWithColor(" - Direction: [" + objectDirection + "], Type: [" + type + "], Distance: [" + distance + "]");
                    }
                } else {
                    printWithColor(robotName + "> No objects detected:");
                }

            } else if (command.equalsIgnoreCase("FORWARD") ||
                    command.equalsIgnoreCase("BACK")) {
                printWithColor(robotName+"> "+data.get("message"));
                printWithColor("Now at ["+xCoord+","+yCoord+"], facing "+robotDirection);
            } else if (command.equalsIgnoreCase("TURN")) {
                printWithColor(robotName+"> "+data.get("message"));
                printWithColor("Now at ["+xCoord+","+yCoord+"], facing "+robotDirection);
            } else if (command.equalsIgnoreCase("STATE")) {
                int shields = (int) state.get("shields");
                int shots = (int) state.get("shots");
                String status = (String) state.get("status");

                assert position != null;
                printWithColor("Position : ["+position.get("x")+","+position.get("y")+"]");
                printWithColor("Direction: ["+robotDirection+"]");
                printWithColor("Shields  :  "+shields);
                printWithColor("Shots    :  "+shots);
                printWithColor("Status   : ["+status+"]");
            } else if (command.equalsIgnoreCase("ORIENTATION")) {
                printWithColor("Direction: ["+robotDirection+"]");
            } else if (command.equalsIgnoreCase("FIRE")) {
                String message = (String) data.get("message");
                if (message.equalsIgnoreCase("HIT")) {
                    String enemyName = (String) data.get("name");
                    int enemyDistance = (int) data.get("distance");
                    @SuppressWarnings("unchecked")
                    Map<String, Object> enemyState = (Map<String, Object>) data.get("state");
                    @SuppressWarnings("unchecked")
                    Map<String, Integer> enemyPosition = (Map<String, Integer>) enemyState.get("position");
                    Integer enemyPositionX = enemyPosition.get("x");
                    Integer enemyPositionY = enemyPosition.get("y");
                    String enemyDirection = (String) enemyState.get("direction");
                    int enemyShields = (int) enemyState.get("shields");
                    int enemyShots = (int) enemyState.get("shots");
                    String enemyStatus = (String) enemyState.get("status");

                    printWithColor(robotName+"> Hit!");
                    printWithColor(" ".repeat(robotName.length())+"Enemy state:\n"+"_".repeat(11));
                    printWithColor(" ".repeat(robotName.length())+"position: ["+enemyPositionX+","+enemyPositionY+"]");
                    printWithColor(" ".repeat(robotName.length())+"direction: ["+enemyDirection+"]");
                    printWithColor(" ".repeat(robotName.length())+"shields: "+enemyShields);
                    printWithColor(" ".repeat(robotName.length())+"shots: "+enemyShots);
                    printWithColor(" ".repeat(robotName.length())+"status: "+enemyStatus);
                } else {
                    printWithColor(robotName+"> Miss!");
                }
                int robotShots = (int) state.get("shots");
                if (robotShots != 0)
                    printWithColor("\n"+robotName+"> "+robotShots+" shot(s) left");
                else
                    printWithColor("\n"+robotName+"> No shots left. Reload!");
            } else if (command.equalsIgnoreCase("RELOAD")) {
                printWithColor(robotName+"> Fully reloaded! "+state.get("shots")+" shot(s) left");
            }
        } else {
            printWithColor(data.get("message").toString());
        }
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

    private static void printWithColor(String text) {
        String color = COLORS[colorIndex % COLORS.length];
        System.out.println(color + text + COLORS[7]); // Reset color
        colorIndex++;
    }
}
