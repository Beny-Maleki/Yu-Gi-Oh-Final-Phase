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

    public static void addUserToLoggedIn(ClientInfo clientInfo, String token, User user) {
        clientInfo.user = user;
        loggedInClients.put(token, clientInfo);
    }

    public static User getUserByToken(String token) {
        loggedInClients.forEach(((s, clientInfo) -> {
            System.out.println(clientInfo.user.getUsername());
            System.out.println("and the token is " + s);
        }));
        return loggedInClients.get(token).user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setToken(String token) {
    }
}
