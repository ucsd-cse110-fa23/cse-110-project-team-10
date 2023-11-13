package cse110_project;

import com.sun.javafx.font.directwrite.RECT;
import com.sun.net.httpserver.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class Server {

    // initialize server port and hostname
    private static final int SERVER_PORT = 8100;
    private static final String SERVER_HOSTNAME = "localhost";

    private HttpServer server = null;

    public Server() {

    }

    public void startServer() {
        System.out.println("Starting the server...");
        // create a thread pool to handle requests
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

        // create a map to store data

        // create a server
        server = null;
        while (server == null) {
            try {
                server = HttpServer.create(
                        new InetSocketAddress(SERVER_HOSTNAME, SERVER_PORT),
                        0);
            } catch (IOException e) {
                System.out.println("Failed to make the server, retrying in a few seconds");
                try {
                    TimeUnit.SECONDS.wait(3);
                } catch (InterruptedException _e) {
                    System.out.println("Interrupted");
                }
            }
        }

        {
            WhisperRequestHandler handler = new WhisperRequestHandler();
            HttpContext context = server.createContext("/whisper");
            context.setHandler(handler);
        }
        {
            RecipeRequestHandler handler = new RecipeRequestHandler();
            HttpContext context = server.createContext("/genrecipe");
            context.setHandler(handler);
        }
        {
            RecipeStateHandler handler = new RecipeStateHandler();
            HttpContext context = server.createContext("/recipestate");
            context.setHandler(handler);
        }

        server.setExecutor(threadPoolExecutor);
        server.start();

        System.out.println("Server started on port " + SERVER_PORT);
    }

    public void stopServer() {
        server.stop(SERVER_PORT);
    }
}
