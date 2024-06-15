package za.co.wethinkcode.robotworlds.world.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class ServerConfiguration {

    public static final String LOG_FILE_PATH = "/home/wtc/my_work/dbn_14_robot_worlds/server_log.txt";
    private static final String CONFIG_FILE_PATH = "serverConfig.json";

    public static void configureServer() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode config = loadConfiguration(objectMapper);

        Scanner scanner = new Scanner(System.in);

        if (config.has("port") && config.get("port").asText().isEmpty()) {
            System.out.print("Enter server PORT: ");
            int port = Integer.parseInt(scanner.nextLine().trim());
            config.put("port", port);
        }

        if (config.has("serverName") && config.get("serverName").asText().isEmpty()) {
            System.out.print("Enter server name: ");
            String serverName = scanner.nextLine().trim();
            config.put("serverName", serverName);
        }

        ObjectNode worldSize = (ObjectNode) config.get("worldSize");
        if (worldSize == null) {
            worldSize = objectMapper.createObjectNode();
            config.set("worldSize", worldSize);
        }

        if (worldSize.has("width") && worldSize.get("width").asText().isEmpty()) {
            System.out.print("Enter world width: ");
            int width = Integer.parseInt(scanner.nextLine().trim());
            worldSize.put("width", width);
        }

        if (worldSize.has("height") && worldSize.get("height").asText().isEmpty()) {
            System.out.print("Enter world height: ");
            int height = Integer.parseInt(scanner.nextLine().trim());
            worldSize.put("height", height);
        }

        if (config.has("visibility") && config.get("visibility").asText().isEmpty()) {
            System.out.print("Enter visibility: ");
            int visibility = Integer.parseInt(scanner.nextLine().trim());
            config.put("visibility", visibility);
        }

        if (config.has("reload") && config.get("reload").asText().isEmpty()) {
            System.out.print("Enter reload time (ms): ");
            int reload = Integer.parseInt(scanner.nextLine().trim());
            config.put("reload", reload);
        }

        if (config.has("repair") && config.get("repair").asText().isEmpty()) {
            System.out.print("Enter repair time (ms): ");
            int repair = Integer.parseInt(scanner.nextLine().trim());
            config.put("repair", repair);
        }

        if (config.has("shields") && config.get("shields").asText().isEmpty()) {
            System.out.print("Enter shields: ");
            int shields = Integer.parseInt(scanner.nextLine().trim());
            config.put("shields", shields);
        }

        saveConfiguration(objectMapper, config);
    }

    private static ObjectNode loadConfiguration(ObjectMapper objectMapper) {
        File configFile = new File(CONFIG_FILE_PATH);
        ObjectNode config = objectMapper.createObjectNode();
        if (configFile.exists()) {
            try {
                config = (ObjectNode) objectMapper.readTree(configFile);
            } catch (IOException e) {
                System.out.println("Failed to load configuration file. Starting with empty configuration.");
            }
        }
        return config;
    }

    private static void saveConfiguration(ObjectMapper objectMapper, ObjectNode config) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(CONFIG_FILE_PATH), config);
        } catch (IOException e) {
            System.out.println("Failed to save configuration file.");
        }
    }

    public static void setupLogging(String logFilePath) {
        try {
            // Create a FileOutputStream for the log file
            FileOutputStream fileOutputStream = new FileOutputStream(logFilePath);

            // Create TeeOutputStream to write to both console and log file
            TeeOutputStream teeOut = new TeeOutputStream(System.out, fileOutputStream);
            TeeOutputStream teeErr = new TeeOutputStream(System.err, fileOutputStream);

            // Create PrintStreams for the TeeOutputStream
            PrintStream psOut = new PrintStream(teeOut);
            PrintStream psErr = new PrintStream(teeErr);

            // Redirect System.out and System.err
            System.setOut(psOut);
            System.setErr(psErr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}