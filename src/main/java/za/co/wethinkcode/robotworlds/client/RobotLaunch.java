package za.co.wethinkcode.robotworlds.client;

import za.co.wethinkcode.robotworlds.JsonUtility;
import za.co.wethinkcode.robotworlds.server.ServerResponse;

import java.util.Map;

/**
 * This class is responsible for launching a robot in the RobotWorlds game.
 * It handles user input, sends requests to the server, and processes the server's response.
 */
public class RobotLaunch {

    /**
     * Launches a robot in the RobotWorlds game.
     *
     * @param client The RobotClient instance used to communicate with the server.
     * @param userInput The user's input for launching the robot.
     * @return The name of the launched robot.
     */
    public static String launchRobot(RobotClient client, String userInput) {
        ServerResponse serverResponseObject;
        ClientRequest request;
        String robotName = "";
        String[] userInputSplit = userInput.split(" ", 2);

        while (true) {
            if (userInputSplit.length == 2) {
                robotName = (userInputSplit[1].split(" ").length == 2) ? (userInputSplit[1].split(" ")[1]) : "";
            }
            request = UserInput.handleUserInput(robotName, userInput);
            String clientRequest = JsonUtility.toJson(request);
            client.out.println(clientRequest);
            String serverResponse = client.getServerResponse();
            serverResponseObject = client.getServerResponseObject(serverResponse);

            if (serverResponseObject.getResult().equals("OK")) {
                break;
            }

            System.out.println("\u001B[31m" + serverResponseObject.getData().get("message") + "\u001B[0m");
            client.run();
        }

        Map<String, Object> state = serverResponseObject.getState();
        robotName = request.robot();

        @SuppressWarnings("unchecked")
        Map<String, Integer> robotPosition = (Map<String, Integer>) state.get("position");
        int robotPositionX = robotPosition.get("x");
        int robotPositionY = robotPosition.get("y");
        String robotFacing = (String) state.get("direction");

        System.out.println("\u001B[32m" + robotName + " launched at [" + robotPositionX + "," + robotPositionY + "], facing " + robotFacing + "\u001B[0m");
        client.isRobotLaunched = true;

        return robotName;
    }
}
