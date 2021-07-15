package server.controller;

public class ScoreboardManagement extends Controller {
    private static ScoreboardManagement instance;

    public static ScoreboardManagement getInstance() {
        if (instance == null) instance = new ScoreboardManagement();
        return instance;
    }

    @Override
    public String run(String command) {
        return null;
    }
}
