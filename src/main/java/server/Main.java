package server;

import client.model.userProp.User;
import server.controller.ServerGate;

public class Main {
    public static void main(String[] args) {
        ServerDataBase.getInstance().restoreDate();
        ServerGate.initialize();
        User.getAllUsers().forEach(user ->
                System.out.println(user.getUsername()
                ));
    }
}
