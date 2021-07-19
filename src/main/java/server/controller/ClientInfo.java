package server.controller;

import client.model.userProp.User;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientInfo {
    private static ArrayList<ClientInfo> clientInfos;
    private static HashMap<String, ClientInfo> loggedInClients;

    static {
        clientInfos = new ArrayList<>();
        loggedInClients = new HashMap<>();
    }

    private Socket clientSocket;
    private boolean isLoggedIn;
    private User user;

    ClientInfo(Socket clientSocket) {
        this.clientSocket = clientSocket;
        clientInfos.add(this);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setToken(String token) {
    }

    public static void addUserToLoggedIn(ClientInfo clientInfo, String token, User user) {
        clientInfo.user = user;
        loggedInClients.put(token, clientInfo);
    }

    public static HashMap<String, ClientInfo> getLoggedInClients() {
        return loggedInClients;
    }

    public static User getUserByToken(String token) {
        return loggedInClients.get(token).user;
    }

}
