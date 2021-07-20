package client.controller.menues.menuhandlers.menucontrollers;

import client.UserDataBase;
import client.controller.Controller;
import client.model.enums.Error;
import client.model.enums.MenusMassages.Main;
import client.model.userProp.LoginUser;
import client.network.Client;
import client.network.ClientListener;
import client.network.ClientSender;
import client.view.menudisplay.MainMenuDisplay;
import connector.commands.CommandType;
import connector.commands.commnadclasses.GetUserTradeRequestsCommand;

import java.util.regex.Matcher;

public class MainMenuController extends Controller {
    public static void showCurrentMenu() {
        MainMenuDisplay.display(Main.CURRENT_MENU);
    }

    public static void invalidCommand() {
        MainMenuDisplay.display(Error.INVALID_COMMAND);
    }

    public static void enterMenu(Matcher matcher) {
    }

    public void logout() {
        LoginUser.setUser(null);
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

    public boolean getPlayerTradeRequest() {
        GetUserTradeRequestsCommand command =
                new GetUserTradeRequestsCommand(CommandType.GET_USER_TRADE_REQUEST, Client.getClient().getToken());
        ClientListener.setServerResponse(command);
        ClientListener.setCurrentCommandID(command.getCommandID());

        ClientSender.getSender().sendMessage(command);

        waitForServerResponse();

        GetUserTradeRequestsCommand response = (GetUserTradeRequestsCommand) ClientListener.getServerResponse();
        if (response.getRequests().size() == 0) return false;
        else UserDataBase.getInstance().setUserTradeRequests(response.getRequests());
        return true;
    }
}
