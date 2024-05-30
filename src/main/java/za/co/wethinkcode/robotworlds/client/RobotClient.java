package za.co.wethinkcode.robotworlds.client;

import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.Sleep;

import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 * Class to represent a client application that connects to a robot server.
 * It enables the user to send commands to the server and receive responses.
 *
 * The application reads commands from the user through the standard input and sends them to the server.
 * It then prints the server's responses to the standard output.
 */
public class RobotClient {
    private static final String ADDRESS = "localhost";
    private static final int PORT = 5000;
    private boolean robotLaunched = false;

    /**
     * Main entry point for the RobotClient application.
     * Establishes a connection to the server, reads and processes user commands.
     *
     * @param args Command line arguments (not used in this application)
     */
    public static void main(String[] args) {
        System.out.println("*********************************************");
        System.out.println("*             ROBOT WORLD                   *");
        System.out.println("*********************************************\n");

        RobotClient client = new RobotClient();
        client.run();
    }

    private void run() {
        try (
                Socket socket = new Socket(ADDRESS, PORT);
                PrintStream out = new PrintStream(socket.getOutputStream());
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
        ) {
            System.out.println("Connected to server on port: " + PORT + "\n");
            Sleep.sleep(1000);

            do {
                System.out.print("Enter command > ");

                String userInput = inputReader.readLine();

                // Check for exit command
                if ("exit".equalsIgnoreCase(userInput.trim())) {
                    System.out.println("Exiting Robot World Client.");
                    break;
                }

                // Process user input
                ClientRequest request = UserInput.handleUserInput(userInput);

                if (request.getCommand().equalsIgnoreCase("launch")) {
                    robotLaunched = true;
                }

                if (robotLaunched) {
                    // Convert ClientRequest obj to JSON format string
                    String jsonString = Json.toJson(request);

                    // Send over to server
                    out.println(jsonString);
                    System.out.println("Client request JSON string:\n" + jsonString);

                    String response = in.readLine();
                    System.out.println("Server response JSON string:\n" + response);
                    // Add more code to read and process server response
                } else {
                    System.out.println("You need to launch a robot first.");
                    System.out.println("Use 'launch [make] [name]'");
                }
            } while (socket.isConnected());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
