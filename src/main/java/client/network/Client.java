package client.network;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static Client client;
    private static Scanner netIn;
    private final ClientListener listener;
    private Socket socket;

    public Client() {
        try {
            socket = new Socket(NetworkConfiguration.getHost(), NetworkConfiguration.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }

        listener = new ClientListener(socket);
    }


    public void startGetResponseFromServer() {
        listener.start();
    }

    public static Client getClient() {
        return client;
    }

    public Socket getSocket() {
        return socket;
    }
}
