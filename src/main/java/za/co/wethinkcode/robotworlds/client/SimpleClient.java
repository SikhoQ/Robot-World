package za.co.wethinkcode.robotworlds.client;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class SimpleClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of robots: ");
        int robotNum = sc.nextInt();

        for (int i = 0; i < robotNum; i++) {
            new Thread(new RobotClient(i)).start();
        }
    }
}

class RobotClient implements Runnable {
    private final int robotId;

    public RobotClient(int robotId) {
        this.robotId = robotId;
    }

    @Override
    public void run() {
        try (
                Socket socket = new Socket("localhost", 5000);
                OutputStream out = socket.getOutputStream();
                InputStream in = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in))
        ) {
            // Send robot identification
            String message = "Robot " + robotId + " connected\n";
            out.write(message.getBytes());

            // Get list of robots (ignoring visibility for now)
            out.write("look\n".getBytes());
            String data = reader.readLine();
            System.out.println("Robots in the world: " + data);

            // Get robot state information (use defaults for unimplemented features)
            out.write("state\n".getBytes());
            data = reader.readLine();
            System.out.println("Robot " + robotId + " state: " + data + " (using defaults for some features)");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
