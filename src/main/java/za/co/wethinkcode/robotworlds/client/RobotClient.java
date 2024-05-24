package za.co.wethinkcode.robotworlds.client;

import java.net.*;
import java.io.*;
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

    /**
     * Main entry point for the RobotClient application.
     * Establishes a connection to the server, reads and processes user commands.
     *
     * @param args Command line arguments (not used in this application)
     * @throws IOException If an I/O error occurs while establishing the connection or reading/writing data
     * @throws InterruptedException If the thread is interrupted while waiting for user input
     */
    public static void main(String[] args) {
        try (
                Socket socket = new Socket(ADDRESS, PORT);
                PrintStream out = new PrintStream(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
        )
        {
            Scanner scanner = null;
            try {
                scanner = new Scanner(System.in);
                System.out.println("Connected to server on port: "+PORT+"\n");
                Thread.sleep(1000);

                System.out.println("Available commands:");
                System.out.println("   launch [make] [name] - launch robot into world");
                System.out.println("   look                 - look around in robot's field of view");
                System.out.println("   state                - robot state\n");

                do {
                    System.out.print("Enter command > ");
                    String commandInput = scanner.nextLine();
                    // I'll change this to method call later to allow validation in said method
                    System.out.println("your command: "+commandInput);
                    out.println(commandInput);
                    String response = in.readLine();
                    System.out.println(response);
                } while (socket.isConnected());

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                if (scanner!= null) {
                    scanner.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
