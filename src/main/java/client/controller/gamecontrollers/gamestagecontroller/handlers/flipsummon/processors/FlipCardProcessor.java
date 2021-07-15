package client.controller.gamecontrollers.gamestagecontroller.handlers.flipsummon.processors;

import client.controller.gamecontrollers.gamestagecontroller.handlers.flipsummon.FlipSummonProcessor;
import client.model.enums.GameEnums.GamePhaseEnums.MainPhase;
import client.model.enums.GameEnums.cardvisibility.MonsterHouseVisibilityState;
import client.model.gameprop.BoardProp.MonsterHouse;
import client.model.gameprop.SelectedCardProp;
import client.model.gameprop.gamemodel.Game;

public class FlipCardProcessor extends FlipSummonProcessor {

    public FlipCardProcessor(FlipSummonProcessor processor) {
        super(processor);
    }

    public String process(Game game) {
        SelectedCardProp cardProp = game.getCardProp();
        MonsterHouse flipCardHouse = (MonsterHouse) cardProp.getCardPlace();
        flipCardHouse.setState(MonsterHouseVisibilityState.OO);
        game.setHiredMonster(flipCardHouse);
        return MainPhase.CARD_FLIP_SUCCESSFULLY.toString();
    }
}
