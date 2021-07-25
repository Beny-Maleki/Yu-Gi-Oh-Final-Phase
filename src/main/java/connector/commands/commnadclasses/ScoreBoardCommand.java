package connector.commands.commnadclasses;

import client.model.userProp.ScoreboardItem;
import connector.commands.Command;
import connector.commands.CommandType;

import java.util.ArrayList;

public class ScoreBoardCommand extends Command {
    private ArrayList<ScoreboardItem> scoreboardItems;

    public ScoreBoardCommand(CommandType commandType) {
        super(commandType);
        scoreboardItems = new ArrayList<>();
    }

    public ArrayList<ScoreboardItem> getScoreboardItems() {
        return scoreboardItems;
    }

    public void addItemToScoreBoardItems(ScoreboardItem scoreboardItem) {
        scoreboardItems.add(scoreboardItem);
    }
}
