package za.co.wethinkcode.robotworlds.client;
/**
 * The UserInput class processes user input for the robot worlds client.
 * It parses the input command and its arguments, and creates a ClientRequest object.
 */
public class UserInput {
    /**
     * Handles user input by parsing the command and its arguments.
     *
     * @param userInput The raw input string entered by the user.
     * @return A ClientRequest object containing the parsed command, name, and arguments.
     */

    public UserInput() {}

    public static ClientRequest handleUserInput(String userInput) {
        String[] userInputArray = userInput.trim().split(" ", 2);
        String commandInput = userInputArray[0].trim().toUpperCase();

        String command = commandInput.toLowerCase();
        String name = "";
        String[] arguments = new String[] {};

        if (commandInput.equals("LAUNCH") && userInputArray.length > 1) {
            String[] argsArray = userInputArray[1].trim().split(" ");
            arguments = new String[] {argsArray[0]};
            if (argsArray.length > 1) {
                name = argsArray[1].trim().toLowerCase();
            }
        }
        return new ClientRequest(name, command, arguments);
    }
}
