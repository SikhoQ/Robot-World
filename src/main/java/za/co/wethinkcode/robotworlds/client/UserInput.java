package za.co.wethinkcode.robotworlds.client;

public class UserInput {

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
