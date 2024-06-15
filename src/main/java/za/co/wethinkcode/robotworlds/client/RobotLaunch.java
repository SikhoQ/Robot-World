package za.co.wethinkcode.robotworlds.client;

import za.co.wethinkcode.robotworlds.JsonUtility;
import za.co.wethinkcode.robotworlds.server.ServerResponse;

import java.io.IOException;
import java.util.Map;

public class RobotLaunch {
    public static String launchRobot(RobotClient client, String userInput) {
        ServerResponse serverResponseObject;
        ClientRequest request;
        String robotName= "";
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
            System.out.println(serverResponseObject.getData().get("message"));
            client.run();
        }

        Map<String, Object> state = serverResponseObject.getState();
        robotName = request.robot();

        @SuppressWarnings("unchecked")
        Map<String, Integer> robotPosition = (Map<String, Integer>) state.get("position");
        int robotPositionX = robotPosition.get("x");
        int robotPositionY = robotPosition.get("y");
        String robotFacing = (String) state.get("direction");

        System.out.println(robotName + " launched at [" + robotPositionX + "," + robotPositionY + "], facing " + robotFacing);
        client.isRobotLaunched = true;

        return robotName;
    }
}
