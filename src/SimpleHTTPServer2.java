import java.io.*;
import java.net.*;
import java.nio.file.*;

public class SimpleHTTPServer2 {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(8080);
        System.out.println("Server started! Visit http://localhost:8080/index.html");

        while (true) {
            try (Socket socket = server.accept()) {
                // 1. Read the first line of the request (e.g., "GET /index.html HTTP/1.1")
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String requestLine = reader.readLine();
                if (requestLine == null) continue;

                // Extract the filename (between "GET " and " HTTP/1.1")
                String fileName = requestLine.split(" ")[1];

                // Security: Default to index.html if path is "/"
                if (fileName.equals("/")) fileName = "/index.html";

                // 2. Map the request to our 'www' folder
                Path filePath = Paths.get("www", fileName);

                if (Files.exists(filePath)) {
                    // 3. Determine MIME Type
                    String contentType = Files.probeContentType(filePath);
                    byte[] fileContent = Files.readAllBytes(filePath);

                    // 4. Send the "200 OK" Response
                    String header = "HTTP/1.1 200 OK\r\n" +
                            "Content-Type: " + contentType + "\r\n" +
                            "Content-Length: " + fileContent.length + "\r\n" +
                            "\r\n";

                    socket.getOutputStream().write(header.getBytes());
                    socket.getOutputStream().write(fileContent);
                } else {
                    // 5. Send "404 Not Found" if file is missing
                    String error404 = "HTTP/1.1 404 Not Found\r\n\r\nFile Not Found!";
                    socket.getOutputStream().write(error404.getBytes());
                }
            }
        }
    }
}