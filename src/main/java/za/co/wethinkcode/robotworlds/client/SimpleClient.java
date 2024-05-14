package za.co.wethinkcode.robotworlds.client;


import java.net.*;
import java.io.*;

public class SimpleClient {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", 5000);
                PrintStream out = new PrintStream(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
        )
        {
            out.println("quit");
            out.flush();
            String messageFromServer = in.readLine();
            System.out.println("Response: "+messageFromServer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}