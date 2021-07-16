package server.controller;

import client.model.userProp.User;

import java.net.Socket;
import java.util.ArrayList;

public class ClientInfo {
    private static ArrayList<ClientInfo> clientInfos;
    private static ArrayList<ClientInfo> loggedInClients;

    static {
        clientInfos = new ArrayList<>();
        loggedInClients = new ArrayList<>();
    }

    private Socket clientSocket;
    private String token;
    private boolean isLoggedIn;
    private User user;

    ClientInfo(Socket clientSocket) {
        this.clientSocket = clientSocket;
        clientInfos.add(this);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static void addUserToLoggedIn(ClientInfo clientInfo) {
        loggedInClients.add(clientInfo);
    }
}
