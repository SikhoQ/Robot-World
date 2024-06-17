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

/**
 * Class to represent a client application that connects to a robot server.
 * It enables the user to send commands to the server and receive responses.
 *x
 * The application reads commands from the user through the standard input and sends them to the server.
 * It then prints the server's responses to the standard output.
 */
public class RobotClient {
    private Socket clientSocket;
    PrintWriter out;
    private BufferedReader in;
    boolean isRobotLaunched = false;
    private String robotName;
    private boolean serverRunning = true;

    /**
     * Main entry point for the RobotClient application.
     * Establishes a connection to the server, reads and processes user commands.
     *
     * @param args Command line arguments (not used in this application)
     */
    public static void main(String[] args) {
<<<<<<< HEAD
        String ADDRESS = "";
        int PORT = 0;
=======
        String serverAddress = null;
        int serverPort = 0;
>>>>>>> sikho

        if (args.length == 2) {
            try {
                ADDRESS = args[0];
                PORT = Integer.parseInt(args[1]);
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

<<<<<<< HEAD
        client.startConnection(ADDRESS, PORT);
        String robotName = client.launchRobot();
        client.run(robotName);
=======
        client.startConnection(serverAddress, serverPort);
        client.run();
>>>>>>> sikho
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
        try {
            clientSocket = new Socket(ipAddress, port);
        } catch (IOException e) {
<<<<<<< HEAD
            throw new RuntimeException("clientSocket exception: "+e);
=======
            System.err.println("Error connecting to server. Check port");
            System.exit(1);
>>>>>>> sikho
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
<<<<<<< HEAD
        System.out.println("Connected to server on port: " + port);
        Sleep.sleep(1500);
    }

    /**
     * Closes the connection to the server.
     * <p>Closes the input stream, output stream, and socket.
     * Propagates any IOException that occurs.
     * @throws IOException if an I/O error occurs when closing the connection
     */
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    /**
     * Launches a robot based on user input.
     * <p>Prompts the user to input a robot make and name, sends a request to the server, and waits for a successful response.
     * If the server response is "OK", it prints the robot's launch position and direction.
     * <p>If the user inputs "EXIT", the connection is closed, and the program exits.
     * @return the name of the launched robot
     */
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
=======
        System.out.println("Connected to server on port: " + serverPort);
    }

    public void stopConnection() {
        try {
            if (clientSocket != null) {
                clientSocket.close();
>>>>>>> sikho
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Error closing client socket: " + e.getMessage());
        }
<<<<<<< HEAD

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

    /**
     * Runs the main interaction loop for the specified robot.
     * <p>Continuously prompts the user for commands to control the robot until the user types "exit".
     * Sends the commands to the server and processes the server's response.
     * @param robotName the name of the robot to control
     */
    private void run(String robotName) {
        String userInput = UserInput.getInput("\n"+robotName+"> What must I do next?");
        while (!userInput.equalsIgnoreCase("exit")) {
=======
    }

    public void run() {
        while (true) {
            while (!isRobotLaunched) {
                String userInput = UserInput.getInput("\nLaunch a robot:\nUse 'launch <make> <name>'");
                robotName = RobotLaunch.launchRobot(this, userInput);
            }
            String userInput = UserInput.getInput("\n" + robotName + "> What must I do next?");

            if (userInput.equalsIgnoreCase("EXIT")) {break;}

>>>>>>> sikho
            ClientRequest request = UserInput.handleUserInput(robotName, userInput);
            String clientRequest = JsonUtility.toJson(request);
            sendClientRequest(clientRequest);
            String serverResponse = getServerResponse();
            ServerResponse serverResponseObject = getServerResponseObject(serverResponse);
<<<<<<< HEAD
            printRequestResult(robotName, request.command(), serverResponseObject, request);
            userInput = UserInput.getInput("\n"+robotName+"> What must I do next?");
=======
            printRequestResult(robotName, request.command(), serverResponseObject);
>>>>>>> sikho
        }

    }

    /**
     * Sends a client request to the server.
     * @param clientRequest the request to send to the server
     */
    private void sendClientRequest(String clientRequest) {
        out.println(clientRequest);
    }

    private void printRequestResult(String robotName, String command, ServerResponse serverResponse) {
        String result = serverResponse.getResult();
        Map<String, Object> data = serverResponse.getData();
        Map<String, Object> state = serverResponse.getState();
        Config config = Config.readConfiguration();

        if (result.equalsIgnoreCase("OK")) {
<<<<<<< HEAD
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
=======
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
>>>>>>> sikho
            }
        } else {
            System.out.println(data.get("message"));
        }
    }

<<<<<<< HEAD
    /**
     * Reads the server's response.
     * @return the server's response as a string
     * @throws RuntimeException if an I/O error occurs while reading the response
     */
    private String getServerResponse() {
=======
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
>>>>>>> sikho
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

<<<<<<< HEAD
    /**
     * Converts the server's response from JSON to a ServerResponse object.
     * @param serverResponse the server's response as a JSON string
     * @return the ServerResponse object
     */
    private ServerResponse getServerResponseObject(String serverResponse) {
        return Json.fromJson(serverResponse);
=======

    public ServerResponse getServerResponseObject(String serverResponse) {
        return JsonUtility.fromJson(serverResponse);
>>>>>>> sikho
    }
}
