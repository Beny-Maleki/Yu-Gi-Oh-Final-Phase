package client.network;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static Client client;
    private static Scanner netIn;
    private final ClientListener listener;
    private Socket socket;
    private String token;

    public Client() {
        try {
            socket = new Socket(NetworkConfiguration.getHost(), NetworkConfiguration.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }

        listener = new ClientListener(socket);
        client = this;
    }

    public static Client getClient() {
        return client;
    }

    public void startGetResponseFromServer() {
        listener.start();
    }

    public Socket getSocket() {
        return socket;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
