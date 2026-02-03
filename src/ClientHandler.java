import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;


class ClientHandler implements Runnable {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (socket) { // Try-with-resources here closes the socket when this method ends
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = reader.readLine();

            if (line != null && !line.isEmpty()) {
                System.out.println("Handling request on thread: " + Thread.currentThread().getName());

                // Simulate a slow task (like a database query) to prove multithreading works
                // Thread.sleep(5000);

                String response = "HTTP/1.1 200 OK\r\n\r\nHello from " + Thread.currentThread().getName();
                socket.getOutputStream().write(response.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}