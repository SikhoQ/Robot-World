package za.co.wethinkcode.robotworlds.client;

import java.util.Scanner;

public class UserInput {
    public UserInput() {}

    /**
     * This method should process user input and return the corresponding
     * ClientRequest instance
     * ClientRequest is a class used for formatting the request protocol
     */
    public static ClientRequest handleUserInput(String robotName, String userInput) {
        String[] userInputArray = userInput.trim().split(" ", 2);
        String commandInput = userInputArray[0].trim().toUpperCase();

        String command = commandInput.toLowerCase();
        Object[] arguments = getArguments(commandInput, userInputArray);

        if (command.equalsIgnoreCase("RELOAD"))
            System.out.println(robotName+"> Reloading...");

        return new ClientRequest(robotName, command, arguments);
    }

    private static Object[] getArguments(String commandInput, String[] userInputArray) {
        Object[] arguments = new Object[] {""};
        if (commandInput.equals("LAUNCH") && userInputArray.length > 1) {
            String[] argsArray = userInputArray[1].trim().split(" ");
            int maximumShots = getMaximumShots();
            int shieldStrength = getShieldStrength();
            arguments = new Object[] {argsArray[0], shieldStrength, maximumShots};
        } else if ((commandInput.equals("FORWARD") || commandInput.equals("BACK")) &&
                userInputArray.length > 1) {
            arguments = new Integer[] {Integer.parseInt(userInputArray[1])};
        } else if (commandInput.equals("TURN") && userInputArray.length > 1) {
            arguments = new String[] {userInputArray[1]};
        }
        return arguments;
    }

    private static int getShieldStrength() {
        try {
            return Integer.parseInt(UserInput.getInput("Maximum shield strength: "));
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return getShieldStrength();
        }
    }

    private static int getMaximumShots() {
        try {
            return Integer.parseInt(UserInput.getInput("Maximum number of shots: "));
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return getMaximumShots();
        }
    }

    public static String getInput(String prompt) {
        String input;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println(prompt);
            input = scanner.nextLine();;
        } while (input.isBlank());
        return input;
    }
}
