package client.controller.menues.menuhandlers.menucontrollers;

import client.UserCardCollection;
import client.controller.Controller;
import client.model.Exceptions.EmptyTextFieldException;
import client.model.enums.Menu;
import client.model.userProp.LoginUser;
import client.network.Client;
import client.network.ClientListener;
import client.network.ClientSender;
import connector.commands.CommandType;
import connector.commands.commnadclasses.GetUsersCardCommand;
import connector.commands.commnadclasses.LogInCommand;
import javafx.scene.control.Label;

import java.io.IOException;

public class LoginPageController extends Controller {

    public void login(String username, String password, Label message) {
        message.setStyle("-fx-text-fill: red ; -fx-font-size: 15");

        try {
            if (username.equals("") || password.equals("")) throw new EmptyTextFieldException();

            ClientSender.getSender().sendMessage(new LogInCommand(CommandType.LOGIN, username, password));

            try {
                while (ClientListener.getServerResponse().getCommandType() == CommandType.WAITING) {
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            LogInCommand response = (LogInCommand) ClientListener.getServerResponse();
            if (responseException != null) {
                message.setText(responseException.getMessage());
                responseException = null;
            } else {
                processLoginResponse(message, response);
                ClientSender.getSender().sendMessage(new GetUsersCardCommand(CommandType.GET_USER_CARD, Client.getClient().getToken()));
                try {
                    while (ClientListener.getServerResponse().getCommandType() != CommandType.GET_USER_CARD) {
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                processGettingUserCardResponse(message);
            }
        } catch (EmptyTextFieldException | IOException e) {
            message.setText(e.getMessage());
        }
        message.setStyle("-fx-text-fill: red ; -fx-font-size: 15");
        displayMessage(message);
    }

    private void processGettingUserCardResponse(Label message) throws IOException {
        GetUsersCardCommand getCardResponse = (GetUsersCardCommand) ClientListener.getServerResponse();
        UserCardCollection.setUserMagicCards(getCardResponse.getUserMagicCard());
        UserCardCollection.setUserMonsterCards(getCardResponse.getUserMonsterCard());
        moveToPage(message, Menu.MAIN_MENU);
    }

    private void processLoginResponse(Label message, LogInCommand response) {
        LoginUser.setUser(response.getUser());
        Client.getClient().setToken(response.getToken());
    }
}
