package server.controller;

import client.model.userProp.User;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientInfo {
    private static final ArrayList<ClientInfo> CLIENT_INFOS;
    private static final HashMap<String, ClientInfo> LOGIN_CLIENT_HASH_MAP;

    static {
        CLIENT_INFOS = new ArrayList<>();
        LOGIN_CLIENT_HASH_MAP = new HashMap<>();
    }

    private Socket clientSocket;
    private User user;

    ClientInfo(Socket clientSocket) {
        this.clientSocket = clientSocket;
        CLIENT_INFOS.add(this);
    }

    public void setUser(User user) {
        this.user = user;
    }


    public static void addUserToLoggedIn(ClientInfo clientInfo, String token, User user) {
        clientInfo.user = user;
        LOGIN_CLIENT_HASH_MAP.put(token, clientInfo);
    }

    public static HashMap<String, ClientInfo> getLoginClientHashMap() {
        return LOGIN_CLIENT_HASH_MAP;
    }

    public static User getUserByToken(String token) {
        return LOGIN_CLIENT_HASH_MAP.get(token).user;
    }

}
