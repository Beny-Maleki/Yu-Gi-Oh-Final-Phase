package client.network;

import Connector.commands.RegisterCommand;
import client.controller.Controller;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import Connector.commands.Command;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientListener extends Thread {
    private Socket socket;
    private Command serverResponse;
    private Scanner netIn;

    public ClientListener(Socket socket) {
        try {
            netIn = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String command = netIn.nextLine();
        Gson gson = new GsonBuilder().create();
        this.serverResponse = gson.fromJson(command, Command.class);
        while (true) {
            switch (serverResponse.getCommandType()) {
                case REGISTER:
                    this.serverResponse = gson.fromJson(command , RegisterCommand.class);
                case LOGIN:
                case DUEL:
                case PROFILE:
            }
            Controller.setResponseException(serverResponse.getException());
        }
    }

    public static Command getServerResponse() {
        return serverResponse;
    }
}
