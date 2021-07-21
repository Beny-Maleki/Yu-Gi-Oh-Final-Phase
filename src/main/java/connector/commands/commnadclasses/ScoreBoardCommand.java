package connector.commands.commnadclasses;

import client.model.userProp.ScoreboardItem;
import connector.commands.Command;
import connector.commands.CommandType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ScoreBoardCommand extends Command {
    private ObservableList<ScoreboardItem> scoreboardItems;

    public ScoreBoardCommand(CommandType commandType) {
        super(commandType);
        scoreboardItems = FXCollections.observableArrayList();
    }

    public ObservableList<ScoreboardItem> getScoreboardItems() {
        return scoreboardItems;
    }

    public void addItemToScoreBoardItems(ScoreboardItem scoreboardItem) {
        scoreboardItems.add(scoreboardItem);
    }
}
