package client.model.enums.GameEnums.GamePhaseEnums;

public enum BattlePhase {
    DEFEAT_ATTACK_OO_TARGET("Your monster card is destroyed and you received OO_DEFEAT battle\n" +
            "damage"),
    CANT_ATTACK_IN_DEFENCE("your card must be in attack position !"),
    DEFEAT_ATTACK_ON_DEFENCE("no card is destroyed and you received D_DEFEAT battle damage"),
    SUCCESSFUL_ATTACK_ON_DEFENCE("the defense position monster is destroyed"),
    SUCCESSFUL_ATTACK_OFFENSIVE("your opponent’s monster is destroyed and your opponent receives\n" +
            "OO_WIN battle damage"),
    NO_CARD_ELIMINATE("no card is destroyed"),
    BOTH_CARD_ELIMINATE("both you and your opponent monster cards are destroyed and no\n" +
            "one receives damage"),
    ALREADY_ATTACK("this card already attacked"),
    CANT_ATTACK_WRONG_lOC("can't attack with this card"),
    EMPTY_LOC_TO_ATTACK("there is no card in this position"),
    CANT_ATTACK_DIRECT("you can't attack directly"),
    CANT_ATTACK_ON_FIRST_TURN("can't attack on first turn"),
    ATTACK_NOT_IN_BATTLE_PHASE("you must attack in battle phase only"),
    NO_CARD_SELECTED_YET("no card selected yet");


    private String messageToString;

    BattlePhase(String messageToString) {
        this.messageToString = messageToString;
    }

    public String toString() {
        return messageToString;
    }

}
