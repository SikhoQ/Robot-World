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

        System.out.println("|====================================|");
        System.out.println("|=========   ROBOT WORLDS   =========|");
        System.out.println("|====================================|\n");

        RobotClient client = new RobotClient();

        client.startConnection(ADDRESS, PORT);
        String robotName = client.launchRobot();
        client.run(robotName);
    }

    /**
     * Establishes a connection to the server using the specified IP address and port.
     * <p>Attempts to create a socket connection and initialize the input and output streams.
     * Throws a RuntimeException if an IOException occurs.
     * @param ipAddress the IP address of the server
     * @param port the port number of the server
     * @throws RuntimeException if an I/O error occurs during connection setup
     */
    public void startConnection(String ipAddress, int port) {
        System.out.println("Connecting...");
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
        System.out.println("Connected to server on port: " + port);
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
        Map<String, Integer> position = (Map<String, Integer>) state.get("position");
        int xCoord = position.get("x");
        int yCoord = position.get("y");
        String direction = (String) state.get("direction");

        System.out.println(robotName+" launched at ["+xCoord+","+yCoord+"], facing "+direction);
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
                System.out.println("Position : ["+position.get("x")+","+position.get("y")+"]");
                System.out.println("Direction: ["+robotDirection+"]");
                System.out.println("Shields  :  "+shields);
                System.out.println("Shots    :  "+shots);
                System.out.println("Status   : ["+status+"]");
            } else if (command.equalsIgnoreCase("ORIENTATION")) {
                System.out.println("Direction: ["+robotDirection+"]");
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

                    System.out.println(robotName+"> HIt!");
                    System.out.println(" ".repeat(robotName.length())+"Enemy state:\n"+"_".repeat(11));
                    System.out.println(" ".repeat(robotName.length())+"position: ["+enemyPositionX+","+enemyPositionY+"]");
                    System.out.println(" ".repeat(robotName.length())+"direction: ["+enemyDirection+"]");
                    System.out.println(" ".repeat(robotName.length())+"shields: "+enemyShields);
                    System.out.println(" ".repeat(robotName.length())+"shots: "+enemyShots);
                    System.out.println(" ".repeat(robotName.length())+"status: "+enemyStatus);
                } else {
                    System.out.println(robotName+"> Miss!");
                }
                int robotShots = (int) state.get("shots");
                if (robotShots != 0)
                    System.out.println("\n"+robotName+"> "+robotShots+" shot(s) left");
                else
                    System.out.println("\n"+robotName+"> No shots left. Reload!");
            } else if (command.equalsIgnoreCase("RELOAD")) {
                System.out.println(robotName+"> Fully reloaded! "+state.get("shots")+" shot(s) left");
            }
        } else {
            System.out.println(data.get("message"));
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
}
