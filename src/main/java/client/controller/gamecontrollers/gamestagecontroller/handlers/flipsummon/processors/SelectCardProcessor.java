package client.controller.gamecontrollers.gamestagecontroller.handlers.flipsummon.processors;

import client.controller.gamecontrollers.gamestagecontroller.handlers.flipsummon.FlipSummonProcessor;
import client.model.enums.GameEnums.CardLocation;
import client.model.enums.GameEnums.GamePhaseEnums.MainPhase;
import client.model.enums.GameEnums.cardvisibility.MonsterHouseVisibilityState;
import client.model.gameprop.SelectedCardProp;
import client.model.gameprop.gamemodel.Game;

public class SelectCardProcessor extends FlipSummonProcessor {
    public SelectCardProcessor(FlipSummonProcessor processor) {
        super(processor);
    }

    public String process(Game game) {
        SelectedCardProp cardProp = game.getCardProp();
        if (cardProp == null)
            return MainPhase.NO_CARD_SELECTED_YET.toString();

        if (game.getHiredMonster() != null)
            return MainPhase.HIRE_MONSTER_BEFORE.toString();

        if (!cardProp.getLocation().equals(CardLocation.MONSTER_ZONE))
            return MainPhase.WRONG_LOCATION_FOR_CHANGE.toString();

        if (!cardProp.getCardPlace().getState().equals(MonsterHouseVisibilityState.DH))
            return MainPhase.CANT_FLIP_SUMMON_THIS_CARD.toString();


        return super.process(game);
    }
}
