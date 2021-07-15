package client.model.enums;

public enum Error {

    //Register and Profile errors:
    INVALID_COMMAND("invalid command"),
    INVALID_NAVIGATION("client.controller navigation is not possible"),
    INVALID_USER_OR_PASS("Username and password didn't match!"),
    INVALID_ENTER_MENU("please login first"),
    INVALID_NICKNAME("user with this nickname already exists"),
    INVALID_USERNAME("user with username U_N already exists"),
    INVALID_LOGOUT("no user is logged in now"),
    INVALID_PASSWORD("current password is invalid"),
    REPEAT_PASS_WRONG("you enter wrong repeat for password"),
    INVALID_NEW_PASSWORD("please enter a new password"),
    INVALID_LOGIN("another user has login already"),
    // MainMenu errors:
    CHEAT_DENIED("cheat denied!"),
    // DeckMenu errors:
    REPETITIOUS_DECK_NAME("deck with name %s already exists"),
    NOT_FOUND_DECK_NAME("deck with name %s does not exist"),
    NOT_FOUND_CARD_NAME_IN_COLLECTION("card with name %s does not exist in your collection or totally doesn't exist"),
    FULL_MAIN_DECK("main deck is full"),
    FULL_SIDE_DECK("side deck is full"),
    NUMBER_LIMITATION_PASSED("there are already three cards with name %s in deck %s"),
    NOT_FOUND_CARD_NAME_IN_MAIN_DECK("card with name %s does not exist in main deck"),
    NOT_FOUND_CARD_NAME_IN_SIDE_DECK("card with name %s does not exist in side deck"),
    //

    //ShopMenu errors:
    INVALID_CARD_NAME_IN_SHOP("there is no card with name %s"),
    NOT_ENOUGH_MONEY("not enough money"),
    //

    //Show card error
    INVALID_CARD_NAME("there is no card with this name");


    private final String errorMessage;

    Error(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    public String toString() {
        return errorMessage;
    }
}
