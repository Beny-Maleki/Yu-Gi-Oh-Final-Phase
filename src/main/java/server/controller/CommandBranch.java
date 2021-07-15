package server.controller;

public enum CommandBranch {
    USER("user", UserManagement.getInstance()),
    SHOP("shop", ShopManagement.getInstance()),
    SCOREBOARD("scoreboard", ScoreboardManagement.getInstance()),
    DUEL("duel", DuelManagement.getInstance());


    private final String val;
    private final Controller instance;

    public String getVal() {
        return this.val;
    }

    public Controller getInstance() {
        return this.instance;
    }

    CommandBranch(String val, Controller instance) {
        this.val = val;
        this.instance = instance;
    }
}
