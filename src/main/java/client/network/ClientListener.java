package client.network;

import client.controller.Controller;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import connector.commands.Command;
import connector.commands.CommandType;
import connector.commands.commnadclasses.GetUserTradeRequestsCommand;
import connector.commands.commnadclasses.GetUsersCardCommand;
import connector.commands.commnadclasses.LogInCommand;
import connector.commands.commnadclasses.RegisterCommand;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientListener extends Thread {
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

    public static Command getServerResponse() {
        return serverResponse;
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
                    serverResponse = gson.fromJson(command, RegisterCommand.class);
                    break;
                case LOGIN:
                    serverResponse = gson.fromJson(command, LogInCommand.class);
                    break;
                case GET_USER_CARD:
                    serverResponse = gson.fromJson(command, GetUsersCardCommand.class);
                    break;
                case GET_USER_TRADE_REQUEST:
                    serverResponse = gson.fromJson(command, GetUserTradeRequestsCommand.class);
                case DUEL:
                case PROFILE:
            }

            Controller.setResponseCommand(serverResponse);
            Controller.setResponseException(serverResponse.getException());

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            serverResponse.setCommandType(CommandType.WAITING);
        }
    }
}
