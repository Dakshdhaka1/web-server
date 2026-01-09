package multithreaded;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    public Runnable getRunnable() {
        return () -> {
            int port = 8010;
            try (
                    Socket socket = new Socket(InetAddress.getByName("localhost"), port);
                    PrintWriter toServer =
                            new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader fromServer =
                            new BufferedReader(new InputStreamReader(socket.getInputStream()))
            ) {
                toServer.println("Hello from Client " + socket.getLocalSocketAddress());

                String response = fromServer.readLine();
                System.out.println("Response from Server: " + response);

            } catch (IOException e) {
                System.out.println("Connection closed");
            }
        };
    }

    public static void main(String[] args) {
        Client client = new Client();

        for (int i = 0; i < 100; i++) {
            new Thread(client.getRunnable()).start();
        }
    }
}
