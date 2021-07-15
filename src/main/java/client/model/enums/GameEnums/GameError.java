package client.model.enums.GameEnums;

public enum GameError {
    INVALID_COMMAND("invalid command"),
    INVALID_SHOW_CARD_REQUEST("this card is not visible"),
    INVALID_DESELECT_CARD_REQUEST("no card is selected yet"),
    INVALID_SELECTION("invalid selection"),
    EMPTY_SELECTION("no card in this place"),
    INVALID_SIDE_MENU_REQUEST("invalid side client.controller request"),
    INVALID_PHASE_COMMAND("you can't do this action in this phase"),
    OPPONENT_SUMMON_REQUEST("you can't summon opponent card "),
    NO_EMPTY_MONSTER_HOUSE("monster card zone is full"),
    MONSTER_HIRED_BEFORE("you already summoned / set in this round"),
    CANT_SUMMON("you can't summon this card"),
    CARD_SELECTED_BEFORE("you have selected card before");
    String errorToString;

    GameError(String errorToString) {
        this.errorToString = errorToString;
    }

    public String toString() {
        return errorToString;
    }
}
