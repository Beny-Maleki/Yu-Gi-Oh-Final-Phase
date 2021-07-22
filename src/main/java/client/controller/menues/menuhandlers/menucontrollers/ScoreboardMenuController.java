package client.controller.menues.menuhandlers.menucontrollers;

import client.controller.Controller;
import client.model.userProp.ScoreboardItem;
import client.model.userProp.User;
import client.network.ClientListener;
import client.network.ClientSender;
import connector.commands.CommandType;
import connector.commands.commnadclasses.LogInCommand;
import connector.commands.commnadclasses.ScoreBoardCommand;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Comparator;

public class ScoreboardMenuController extends Controller {
    private static ScoreboardMenuController instance;

    private ScoreboardMenuController() {}

    public static ScoreboardMenuController getInstance() {
        if (instance == null) {
            instance = new ScoreboardMenuController();
        }
        return instance;
    }

    public ObservableList<ScoreboardItem> setScoreboardItems() {
        ScoreBoardCommand scoreBoardCommand = new ScoreBoardCommand(CommandType.SCORE_BOARD);
        ClientListener.setCurrentCommandID(scoreBoardCommand.getCommandID());
        ClientListener.setServerResponse(scoreBoardCommand);

        ClientSender.getSender().sendMessage(scoreBoardCommand);

        waitForServerResponse();

        ScoreBoardCommand response = (ScoreBoardCommand) ClientListener.getServerResponse();
        return response.getScoreboardItems();
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
}
