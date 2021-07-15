package client.model.enums;

public enum Menu {
    WELCOME_MENU("WelcomePage"),
    MAIN_MENU("MainPage"),
    REGISTER_MENU("RegisterPage"),
    LOGIN_MENU("LoginPage"),
    PROFILE_CHANGE_PASSWORD("ProfileChangePassword"),
    PROFILE_CHANGE_NICKNAME("ProfileChangeNickname"),
    SHOP_MENU("ShopMenu"),
    DECKS_VIEW("DecksView"),
    SCOREBOARD_MENU("ScoreBoardPage"),
    PROFILE_MENU("ProfilePage"),
    DECK_MODIFIER("DeckModifier"),
    DUEL_PAGE("DuelPage"),
    ROCK_PAPER_SCISSOR_PAGE("RockPaperScissor"),
    CARD_CREATOR_PAGE("CardCreatorPage"),
    CHOOSE_MONSTER_ACTIONS("ChooseMonsterActions"),
    CHOOSE_MAGIC_ACTIONS("ChooseMagicActions"),
    CHOOSE_FIELD_CARDS_DETAILS("ChooseFieldCardsDetails"),
    IMPORT("ImportPage"),
    EXPORT("ExportPage"),
    IMPORT_EXPORT("ImportExportPage"),
    GAME_PAGE("GamePage");

    String address;

    Menu(String address) {
        this.address = address;
    }

    public String getAddress() {
        return "/graphicprop/fxml/" + address + ".fxml";
    }
}
