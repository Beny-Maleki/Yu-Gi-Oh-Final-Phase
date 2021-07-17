package server;

import server.controller.ServerGate;

public class Main {
    public static void main(String[] args) {
        ServerDataBase.getInstance().restoreDate();
        ServerGate.initialize();
    }
}
