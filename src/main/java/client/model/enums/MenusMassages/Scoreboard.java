package client.model.enums.MenusMassages;

public enum Scoreboard {
    CURRENT_MENU("Scoreboard client.controller"),
    SUCCESSFULLY_ENTER_MENU("you entered Scoreboard client.controller successfully"),
    SUCCESSFULLY_EXIT_MENU("Scoreboard client.controller exited successfully");

    private final String message;

    Scoreboard(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
