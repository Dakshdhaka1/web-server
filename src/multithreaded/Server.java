package multithreaded;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        int port = 8010;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();

                new Thread(() -> handleClient(clientSocket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket socket) {
        try (
                BufferedReader fromClient =
                        new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter toClient =
                        new PrintWriter(socket.getOutputStream(), true)
        ) {
            String clientMsg = fromClient.readLine();
            System.out.println("Client says: " + clientMsg);

            toClient.println("Hello from server " + socket.getInetAddress());

        } catch (IOException e) {
            System.out.println("Client disconnected");
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }
}
