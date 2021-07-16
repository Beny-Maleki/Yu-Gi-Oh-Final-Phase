package server.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;



public class ServerGate {
    private static ServerSocket serverSocket;

    static {
        try {
            serverSocket = new ServerSocket(7755);
            System.out.println("server is on and listening...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initialize() {
        Socket socket = null;
        while (true) { // listen for new client requests...
            try {
                socket = serverSocket.accept();
                System.out.println("client: " + socket.getInetAddress().getHostAddress() + " requested to connect...");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // make a channel for each client...
            ClientHandler clientHandler = null;
            try {
                clientHandler = new ClientHandler(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Thread thread = new Thread(clientHandler);
            thread.start();
            System.out.println("client: " + socket.getInetAddress() + " is being served");
        }

    }
}
