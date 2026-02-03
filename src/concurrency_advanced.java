import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class concurrency_advanced {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);

        // Create a pool of 10 workers.
        // Even if 100 people connect, only 10 get served at once, the others queue up.
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        System.out.println("Multithreaded Server listening on 8080...");

        while (true) {
            Socket socket = server.accept(); // Main thread ONLY accepts connections

            // Hand off the work to the pool. The main thread loop immediately continues.
            threadPool.submit(new ClientHandler(socket));
        }
    }
}