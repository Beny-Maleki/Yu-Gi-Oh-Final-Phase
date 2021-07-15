package client.model.enums.MenusMassages;

public enum Main {
    CURRENT_MENU("client.Main client.controller"),
    SUCCESSFULLY_ENTER_MENU("you entered client.Main client.controller successfully"),
    SUCCESSFULLY_EXIT_MENU("client.Main client.controller exited successfully"),
    SUCCESSFULLY_LOGOUT("User logout successfully"),
    MONEY_CHEAT_ACCEPTED("Your money increased by %s and now is %s");

    private final String mainMessage;

    Main(String mainMessage) {
        this.mainMessage = mainMessage;
    }

    public String getMainMessage() {
        return mainMessage;
    }
}
