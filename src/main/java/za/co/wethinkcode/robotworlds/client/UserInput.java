package za.co.wethinkcode.robotworlds.client;

public class UserInput {

    public UserInput() {}

    /**
     * This method should process user input and return the corresponding
     * ClientRequest instance
     * ClientRequest is a class used for formatting the request protocol
     */
    public static ClientRequest handleUserInput(String userInput) {
        // NEED TO CHANGE THIS IMPLEMENTATION TO USE A VALIDATING
        // METHOD TO CHECK IF THE INPUT IS A VALID COMMAND BEFORE
        // SPLITTING STRING FOR COMMAND, OR FIND A WAY TO RE-JOIN
        // IF NOT VALID COMMAND INPUT
        String[] userInputArray = userInput.trim().split(" ", 2);
        String commandInput = userInputArray[0].trim().toUpperCase();

        String command = commandInput.toLowerCase();
        String name = "";
        Object[] arguments = new Object[] {};
        if (commandInput.equals("LAUNCH") && userInputArray.length > 1) {
            String[] argsArray = userInputArray[1].trim().split(" ");
            arguments = new String[] {argsArray[0]};
            if (argsArray.length > 1) {
                name = argsArray[1].trim();
            }
        } else if ((commandInput.equals("FORWARD") || commandInput.equals("BACK")) &&
                userInputArray.length > 1) {
            arguments = new Integer[] {Integer.parseInt(userInputArray[1])};
        } else if (commandInput.equals("TURN") && userInputArray.length > 1) {
            arguments = new String[] {userInputArray[1]};
        }

        return new ClientRequest(name, command, arguments);
    }
}
