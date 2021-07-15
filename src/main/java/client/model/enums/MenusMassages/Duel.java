package client.model.enums.MenusMassages;

public enum Duel {
    CURRENT_MENU("Duel client.controller"),
    SUCCESSFULLY_ENTER_MENU("you entered Duel client.controller successfully"),
    SUCCESSFULLY_EXIT_MENU("Duel client.controller exited successfully"),
    INVALID_SECOND_PLAYER("there is no player with this username"),
    USER_ONE_NO_ACTIVE_DECK("U1 has no active deck"),
    USER_TWO_NO_ACTIVE_DECK("U2 has no active deck"),
    USER_ONE_INVALID_ACTIVE_DECK("U1’s deck is invalid"),
    USER_TWO_INVALID_ACTIVE_DECK("U2’s deck is invalid"),
    INVALID_NUMBER_OF_ROUNDS("number of rounds is not supported"),
    CANT_PLAY_WITH_YOURSELF("You should choose another player!");
    private final String message;

    Duel(String message) {
        this.message = message;
    }

    public String toString() {
        return message;
    }
}
