package client.model.enums.GameEnums.GamePhaseEnums;

public enum MainPhase {
    CANT_FIND_ADVANCED_ART_FOR_HIRE_RITUAL_MONSTER("you must have advanced ritual art for hiring ritual monster"),
    SUMMONED_SUCCESSFULLY("summon successfully"),
    CANT_SUMMON_MAGIC("can't summon spell or trap card"),
    CARD_FLIP_SUCCESSFULLY("flip summoned successfully"),
    CANT_FLIP_SUMMON_THIS_CARD("can't flip summon this card"),
    CANT_CHANGE_POS_OF_HIRED_CARD("can't change the position of summoned card"),
    FLIP_NEEDED("you must flip this card"),
    SET_SUCCESSFULLY("set successfully"),
    CANT_HIRE_CARD("cant hire this card"),
    HIRE_MONSTER_BEFORE("you already summoned/set on this turn"),
    BOARD_IS_FULL("player board is full"),
    TRIBUTE_NOT_POSSIBLE("dont have enough card for tribute"),
    ONE_TRIBUTE_NEED("you need to tribute 1 monster" +
            "\n (type the address in this format : (T:<cardAddress>)"),
    TW0_TRIBUTE_NEED("you need to tribute 2 monster" +
            "\n (type the address in this format : (T1:<cardAddress> T2:<cardAddress>)"),
    NO_CARD_SELECTED_YET("no card selected yet"),
    ALREADY_IN_WANTED_POS("already in wanted position"),
    POS_CHANGE_BEFORE("position of this monster  change before "),
    MONSTER_CHANGE_POS("monster card position changed successfully"),
    CANT_SET_MONSTER_INSTEAD_OF_SPELL("you must select a Spell card"),
    WRONG_LOCATION_FOR_CHANGE("you can't change this card position");
    String messageToString;

    MainPhase(String messageToString) {
        this.messageToString = messageToString;
    }

    public String toString() {
        return messageToString;
    }

}
