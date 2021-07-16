package client.network;

import Connector.commands.CommandType;
import Connector.commands.LogInCommand;
import Connector.commands.RegisterCommand;
import client.controller.Controller;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import Connector.commands.Command;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientListener extends Thread {
    private static Socket socket;
    private static Command serverResponse;
    private static Scanner netIn;

    {
        serverResponse = new Command(CommandType.WAITING);
    }

    public ClientListener(Socket socket) {
        try {
            netIn = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            String command = netIn.nextLine();
            Gson gson = new GsonBuilder().create();
            serverResponse = gson.fromJson(command, Command.class);
            System.out.println(serverResponse);

            switch (serverResponse.getCommandType()) {
                case REGISTER:
                    serverResponse = gson.fromJson(command , RegisterCommand.class);
                    break;
                case LOGIN:
                    serverResponse = gson.fromJson(command, LogInCommand.class);
                    break;
                case DUEL:
                case PROFILE:
            }
            Controller.setResponseCommand(serverResponse);
            Controller.setResponseException(serverResponse.getException());

            try {
                Thread.sleep(110);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

           serverResponse.setCommandType(CommandType.WAITING);
        }
    }

    public static Command getServerResponse() {
        return serverResponse;
    }
}
