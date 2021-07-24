package client.controller.menues.menuhandlers.menucontrollers;

import client.UserDataBase;
import client.controller.Controller;
import client.model.Exceptions.EmptyTextFieldException;
import client.model.enums.Menu;
import client.model.userProp.LoginUser;
import client.network.Client;
import client.network.ClientListener;
import client.network.ClientSender;
import connector.commands.CommandType;
import connector.commands.commnadclasses.ChatBoxCommand;
import connector.commands.commnadclasses.GetUsersCardCommand;
import connector.commands.commnadclasses.LogInCommand;
import javafx.scene.control.Label;

import java.io.IOException;

public class LoginPageController extends Controller {

    public void login(String username, String password, Label message) {
        message.setStyle("-fx-text-fill: red ; -fx-font-size: 15");

        try {
            if (username.equals("") || password.equals("")) throw new EmptyTextFieldException();

            LogInCommand logInCommand = new LogInCommand(CommandType.LOGIN, username, password);
            ClientListener.setCurrentCommandID(logInCommand.getCommandID());
            ClientListener.setServerResponse(logInCommand);

            ClientSender.getSender().sendMessage(logInCommand);

            waitForServerResponse();

            if (ClientListener.getServerResponse() instanceof  ChatBoxCommand)
            System.out.println(((ChatBoxCommand) ClientListener.getServerResponse()).getMessageID());

            LogInCommand response = (LogInCommand) ClientListener.getServerResponse();

            if (responseException != null) {
                message.setText(responseException.getMessage());
                responseException = null;
            } else {
                processLoginResponse(message, response);

                GetUsersCardCommand getUsersCardCommand = new GetUsersCardCommand(CommandType.GET_USER_CARD, Client.getClient().getToken());
                ClientListener.setCurrentCommandID(getUsersCardCommand.getCommandID());
                ClientListener.setServerResponse(getUsersCardCommand);
                ClientSender.getSender().sendMessage(getUsersCardCommand);
                waitForServerResponse();
                processGettingUserCardResponse(message);
            }
        } catch (EmptyTextFieldException | IOException e) {
            e.printStackTrace();
            message.setText(e.getMessage());
        }
        message.setStyle("-fx-text-fill: red ; -fx-font-size: 15");
        displayMessage(message);
    }

    private void waitForServerResponse() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {
            if (!ClientListener.getCurrentCommandID().equals(ClientListener.getServerResponse().getCommandID())) break;
        }
        ClientListener.setCurrentCommandID(ClientListener.getServerResponse().getCommandID());
    }

    private void processGettingUserCardResponse(Label message) throws IOException {
        GetUsersCardCommand getCardResponse = (GetUsersCardCommand) ClientListener.getServerResponse();
        UserDataBase.getInstance().setUserMagicCards(getCardResponse.getUserMagicCard());
        UserDataBase.getInstance().setUserMonsterCards(getCardResponse.getUserMonsterCard());
        moveToPage(message, Menu.MAIN_MENU);
    }

    private void processLoginResponse(Label message, LogInCommand response) {
        LoginUser.setUser(response.getUser());
        Client.getClient().setToken(response.getToken());
    }
}
