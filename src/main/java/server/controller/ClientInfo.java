package server.controller;

import client.model.userProp.User;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientInfo {
    private static final ArrayList<ClientInfo> CLIENT_INFOS;
    private static final HashMap<String, ClientInfo> LOGIN_CLIENT_HASH_MAP;
    private static final ArrayList<User> LOGGED_IN_USERS;

    static {
        CLIENT_INFOS = new ArrayList<>();
        LOGIN_CLIENT_HASH_MAP = new HashMap<>();
        LOGGED_IN_USERS = new ArrayList<>();
    }

    private final Socket clientSocket;
    private User user;
    private String token;

    ClientInfo(Socket clientSocket) {
        this.clientSocket = clientSocket;
        CLIENT_INFOS.add(this);
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public static void addUserToLoggedIn(ClientInfo clientInfo, String token, User user) {
        clientInfo.user = user;
        LOGIN_CLIENT_HASH_MAP.put(token, clientInfo);
    }

    public static ArrayList<User> getLoggedInUsers() {
        return LOGGED_IN_USERS;
    }

    public static void removeUserFromLoggedIn(String token) {
        LOGIN_CLIENT_HASH_MAP.remove(token);
    }

    public static HashMap<String, ClientInfo> getLoginClientHashMap() {
        return LOGIN_CLIENT_HASH_MAP;
    }

    public static User getUserByToken(String token) {
        return LOGIN_CLIENT_HASH_MAP.get(token).user;
    }

    public static void closeUserSocket(Socket socket) {
        for (ClientInfo clientInfo : CLIENT_INFOS) {
            if (clientInfo.getClientSocket() == socket) {
                try {
                    socket.close();
                    System.out.println("socket closed ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
