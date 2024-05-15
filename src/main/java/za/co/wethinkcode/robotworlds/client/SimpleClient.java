package za.co.wethinkcode.robotworlds.client;

import java.net.*;
import java.io.*;

public class SimpleClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;

    public SimpleClient(int robotId) {
    }

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
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
                Socket socket = new Socket("localhost", 5000 );
                OutputStream out = socket.getOutputStream();
                InputStream in = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in))
        ) {
            // Send robot identification
            String message = "Robot " + robotId + " connected";
            out.write(message.getBytes());

            // Get list of robots (ignoring visibility for now)
            out.write("look".getBytes());
            String data = reader.readLine();
            System.out.println("Robots in the world: " + data);

            // Get robot state information (use defaults for unimplemented features)
            out.write("state".getBytes());
            data = reader.readLine();
            System.out.println("Robot " + robotId + " state: " + data + " (using defaults for some features)");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


/*
package za.co.wethinkcode.robotworlds.client;


import java.net.*;
import java.io.*;

public class SimpleClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;
    private final int robotId;

    public SimpleClient(int robotId) {
        this.robotId = robotId;
    }
    public void run() {
        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                OutputStream out = socket.getOutputStream();
                InputStream in = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in))
        ) {
            String message = "Robot " + robotId + " connected";
            out.write(message.getBytes());

            String response = reader.readLine();
            System.out.println("Response: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    public static void main(String[] args) {
        // Launch multiple robots (adjust number as needed)
        for (int i = 0; true; i++) {
            new Thread((Runnable) new SimpleClient(i)).start();
        }
    }
}
*/
