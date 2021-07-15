package client.model.enums.GameEnums.GamePhaseEnums;

public enum General {
    IS_ACTIVATED_BEFORE("magic is already active"),
    SPELL_ACTIVATED_SUCCESSFULLY("spell activated"),
    SPELL_CARD_ZONE_FULL("spell card zone is full"),
    MAGIC_SPELL_SPEED_2("cant active magic card with spell 2 from your hand !!!! set them first"),
    CANT_ACTIVE_OPPONENT_CARD("cant active opponent card"),
    SELECT_CARD_MESSAGE("card selected"),
    SPELL_ACTIVATE_NOT_IN_MAIN_PHASE("you can’t activate an effect on this stage"),
    NOT_SPELL_CARD("you can only active spell cards"),
    NO_CARD_SELECT("no card selected yet"),
    NEXT_PHASE_MESSAGE("phase: StAgE"),
    NO_CARD_SELECTED("no card selected yet"),
    CARD_DESELECT_SUCCESSFULLY("card deselected"),
    CARD_SELECTED_SUCCESSFULLY("card selected successfully");
    String messageToString;

    General(String messageToString) {
        this.messageToString = messageToString;
    }

    public String toString() {
        return messageToString;
    }
}
