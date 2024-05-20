package za.co.wethinkcode.robotworlds.client;

import java.net.*;
import java.io.*;
import java.util.*;

public class RobotClient {
    private static final String ADDRESS = "localhost";
    private static final int PORT = 5000;

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(ADDRESS, PORT);
                PrintStream out = new PrintStream(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
        )
        {
            Scanner scanner = new Scanner(System.in);
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
        }
    }
}
