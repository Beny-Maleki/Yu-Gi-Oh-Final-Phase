package client.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import connector.commands.Command;
import connector.commands.CommandType;
import connector.commands.commnadclasses.*;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientListener extends Thread {
    private static Command serverResponse;
    private static Scanner netIn;
    private static String currentCommandID;

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

    public static void setCurrentCommandID(String currentCommandID) {
        ClientListener.currentCommandID = currentCommandID;
    }

    public static String getCurrentCommandID() {
        return currentCommandID;
    }

    public static void setServerResponse(Command serverResponse) {
        ClientListener.serverResponse = serverResponse;
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
                    break;
                case CHAT:
                    serverResponse = gson.fromJson(command, ChatBoxCommand.class);
                    break;
                case GET_CARD_FOR_TRADES:
                    serverResponse = gson.fromJson(command, GetCardsOnTradeCommand.class);
                    break;
                case DUEL:
                case PROFILE:
            }

        }
    }
}
