package client.network;

import client.controller.Controller;
import client.controller.menues.menuhandlers.menucontrollers.ChatRoomController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import connector.commands.Command;
import connector.commands.CommandType;
import connector.commands.commnadclasses.*;
import javafx.application.Platform;

import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientListener extends Thread {
    private static Command serverResponse;
    private static Scanner netIn;
    private static String currentCommandID;
    private static Gson gson;

    static {
        gson = new GsonBuilder().create();
        serverResponse = new Command(CommandType.WAITING);
    }

    public ClientListener(Socket socket) {
        try {
            netIn = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCurrentCommandID() {
        return currentCommandID;
    }

    public static void setCurrentCommandID(String currentCommandID) {
        ClientListener.currentCommandID = currentCommandID;
    }

    public static Command getServerResponse() {
        return serverResponse;
    }

    public static void setServerResponse(Command serverResponse) {
        ClientListener.serverResponse = serverResponse;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String command = netIn.nextLine();
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
                        ChatCommandType chatCommandType = ((ChatBoxCommand)serverResponse).getChatCommandType();

                        if ( chatCommandType == ChatCommandType.UPDATE_EDIT ||
                             chatCommandType == ChatCommandType.UPDATE_PIN ||
                             chatCommandType == ChatCommandType.UPDATE_NEW ||
                             chatCommandType == ChatCommandType.UPDATE_OMIT ||
                             chatCommandType == ChatCommandType.UPDATE_NUM_LOGGED_INS) {
                            Platform.runLater(() -> ChatRoomController.getInstance().getCurrentFXMLController().update((ChatBoxCommand) serverResponse));
                        }
                        break;
                    case GET_CARD_FOR_TRADES:
                        serverResponse = gson.fromJson(command, GetCardsOnTradeCommand.class);
                        break;
                    case SCORE_BOARD:
                        serverResponse = gson.fromJson(command, ScoreBoardCommand.class);
                        break;
                    case DUEL:
                    case PROFILE:
                }

                Controller.setResponseException(serverResponse.getException());
                Controller.setResponseCommand(serverResponse);
            }
        } catch (NoSuchElementException ignored) {
        }
    }
}
