package za.co.wethinkcode.robotworlds.client;

import java.sql.SQLOutput;
import java.util.Scanner;

public class UserInput {
    public UserInput() {}

    /**
     * This method handles user input and processes it to create a ClientRequest object.
     *
     * @param robotName The name of the robot for which the request is being made.
     * @param userInput The user's input command.
     * @return A ClientRequest object containing the robot's name, command, and arguments.
     */
    public static ClientRequest handleUserInput(String robotName, String userInput) {
        String[] userInputArray = userInput.trim().split(" ", 2);
        String commandInput = userInputArray[0].trim().toUpperCase();

        String command = commandInput.toLowerCase();
        Object[] arguments = getArguments(commandInput, userInputArray);

        if (command.equalsIgnoreCase("RELOAD")) {
            System.out.println(robotName + "> Reloading gun...");
        } else if (command.equalsIgnoreCase("REPAIR")) {
            System.out.println(robotName + "> Repairing shield...");
        }

        return new ClientRequest(robotName, command, arguments);
    }

    /**
     * This method processes the user input to extract the arguments for the ClientRequest object.
     *
     * @param commandInput The command input from the user.
     * @param userInputArray The user input split into an array.
     * @return An array of objects representing the arguments for the ClientRequest object.
     */
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

    /**
     * This method retrieves the maximum shield strength from user input.
     * It uses a recursive approach to handle invalid input and ensures that the input is a valid integer.
     *
     * @return The maximum shield strength as an integer.
     */
    private static int getShieldStrength() {
        try {
            return Integer.parseInt(UserInput.getInput("Maximum shield strength: "));
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return getShieldStrength();
        }
    }

    /**
     * This method retrieves the maximum number of shots from user input.
     * It uses a while loop and recursion to handle invalid input and ensures that the input is a valid integer within the range [0, 5].
     *
     * @return The maximum number of shots as an integer.
     */
    private static int getMaximumShots() {
        while (true) {
            try {
                final int maxShots = Integer.parseInt(UserInput.getInput("Maximum number of shots [0 - 5]: "));
                if (maxShots >= 0 && maxShots <= 5)
                    return maxShots;
                System.out.println("Invalid input.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                return getMaximumShots();
            }
        }
    }

    /**
     * This method retrieves user input from the console.
     * It uses a Scanner object to read input from the user and a do-while loop to ensure that the input is not blank.
     *
     * @param prompt The prompt to display to the user.
     * @return The user's input as a string.
     */
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
