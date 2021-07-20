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
import connector.commands.commnadclasses.GetCardsOnTradeCommand;
import connector.commands.commnadclasses.GetUsersCardCommand;
import connector.commands.commnadclasses.LogInCommand;
import javafx.scene.control.Label;

import java.io.IOException;

public class LoginPageController extends Controller {

    public void login(String username, String password, Label message) {
        message.setStyle("-fx-text-fill: red ; -fx-font-size: 15");

        try {
            if (username.equals("") || password.equals("")) throw new EmptyTextFieldException();
            LogInCommand response = setLoginRequestToServer(username, password);

            if (responseException != null) {
                handleLoginErrors(message);
            } else {
                getUserCardsFromDataBase(message, response);
                getTradeShopInfoFromDataBase();
            }
        } catch (EmptyTextFieldException | IOException e) {
            e.printStackTrace();
            message.setText(e.getMessage());
        }
        message.setStyle("-fx-text-fill: red ; -fx-font-size: 15");
        displayMessage(message);
    }

    private void getTradeShopInfoFromDataBase() throws IOException {
        try {
            while (ClientListener.getServerResponse().getCommandType() != CommandType.GET_CARD_FOR_TRADES) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        processGettingCardsOnTrade();
    }

    private void getUserCardsFromDataBase(Label message, LogInCommand response) throws IOException {
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
        ClientSender.getSender().sendMessage(new GetCardsOnTradeCommand(CommandType.GET_CARD_FOR_TRADES));
    }

    private void handleLoginErrors(Label message) {
        System.out.println(responseException.getMessage());
        message.setText(responseException.getMessage());
        responseException = null;
    }

    private LogInCommand setLoginRequestToServer(String username, String password) {
        ClientSender.getSender().sendMessage(new LogInCommand(CommandType.LOGIN, username, password));

        try {
            while (ClientListener.getServerResponse().getCommandType() != CommandType.LOGIN) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (LogInCommand) ClientListener.getServerResponse();
    }

    private void processGettingUserCardResponse(Label message) throws IOException {
        GetUsersCardCommand getCardResponse = (GetUsersCardCommand) ClientListener.getServerResponse();
        UserDataBase.getInstance().setUserMagicCards(getCardResponse.getUserMagicCard());
        UserDataBase.getInstance().setUserMonsterCards(getCardResponse.getUserMonsterCard());
        moveToPage(message, Menu.MAIN_MENU);
    }

    private void processGettingCardsOnTrade() {
        GetCardsOnTradeCommand getCardsOnTradeCommand = (GetCardsOnTradeCommand) ClientListener.getServerResponse();
        UserDataBase.getInstance().addElementsToCardOnTrades(getCardsOnTradeCommand.getCardForTrades());
    }


    private void processLoginResponse(Label message, LogInCommand response) {
        LoginUser.setUser(response.getUser());
        Client.getClient().setToken(response.getToken());
    }
}
