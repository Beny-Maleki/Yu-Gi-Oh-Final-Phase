package client.controller.menues.menuhandlers.menucontrollers;

import client.controller.Controller;
import client.model.userProp.ScoreboardItem;
import client.model.userProp.User;

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

    public void setScoreboardItems() {
        ArrayList<User> sortedUsers = User.getAllUsers();
        sortedUsers.sort(Comparator.comparing(User::getScore).thenComparing(User::getNickname));
        int rank = 1, counter = 1;
        User tempUser = null;
        for (User user : sortedUsers) {
            if (counter == 11) break;
            if (tempUser != null) {
                if (user.getScore() > tempUser.getScore()) {
                    rank = counter;
                }
                new ScoreboardItem(user.getUsername(), rank, user.getScore());
            } else {
                new ScoreboardItem(user.getUsername(), 1, user.getScore());
            }
            tempUser = user;
            counter++;
        }
    }
}
