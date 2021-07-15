package client.model.userProp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ScoreboardItem {
    private static ObservableList<ScoreboardItem> scoreboardItems = FXCollections.observableArrayList();
    private final int rank;
    private final String username;
    private final int score;

    public ScoreboardItem(String username, int rank, int score) {
        this.username = username;
        this.rank = rank;
        this.score = score;
        scoreboardItems.add(this);
    }

    public static ObservableList<ScoreboardItem> getScoreboardItems() {
        return scoreboardItems;
    }

    public int getRank() {
        return rank;
    }

    public int getScore() {
        return score;
    }

    public String getUsername() {
        return username;
    }
}
