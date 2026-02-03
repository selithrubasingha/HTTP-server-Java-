import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Concurrency {

    public static void main(String[] args) throws IOException {
        final ServerSocket server = new ServerSocket(8080);
        System.out.println("Listening for connection on port 8080 ....");
        while (true){
            Socket socket = server.accept();

            ClientHandler worker = new ClientHandler(socket);

            Thread thread = new Thread(worker);
            thread.start();
            /*
            Super simple threading ... now 10,15,100 people can
            use it at the same time and there will that number of
            threads ...(not used in production , just for prototyping)
             */

        }
    }
}