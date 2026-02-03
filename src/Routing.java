import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class Routing {

    public static void main(String[] args) throws Exception {
        final ServerSocket server = new ServerSocket(8080);
        System.out.println("Listening for connection on port 8080 ....");
        while (true) {
            try (Socket socket = server.accept()) {
                InputStreamReader isr = new InputStreamReader(socket.getInputStream());
                BufferedReader reader = new BufferedReader(isr);
                String line = reader.readLine();

                String[] parts = line.split(" ");
                String path = parts[1];

                if (!line.isEmpty()) {
                    System.out.println(path);

                }

                String message ="";
                if (path.equals("/")){
                    message = "I like pancakes";

                }else if(path.equals("/sasindu")){
                    message = "Bimmala ew palayan yanna!";
                }else if(path.equals("/lahiru")){
                    message = "Lahiru lahiru lahiru PIPEEE lahiru";

                }else if(path.equals("/pramod")){
                    message = "Boru marci dannepa Pramod! Api dnnw aca pathultm gihin kyl";

                }

//            Giving out to the HTTP server

                Date today = new Date();
                String HTTPResponse = "HTTP/1.1 200 OK\r\n\r\n" + message;

                socket.getOutputStream().write(HTTPResponse.getBytes(StandardCharsets.UTF_8));
            }



        }

    }
}