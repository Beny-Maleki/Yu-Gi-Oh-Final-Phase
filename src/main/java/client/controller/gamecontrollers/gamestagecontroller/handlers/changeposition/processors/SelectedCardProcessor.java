package client.controller.gamecontrollers.gamestagecontroller.handlers.changeposition.processors;

import client.controller.gamecontrollers.gamestagecontroller.handlers.changeposition.ChangePosProcessor;
import client.model.enums.GameEnums.CardLocation;
import client.model.enums.GameEnums.GamePhaseEnums.MainPhase;
import client.model.enums.GameEnums.WantedPos;
import client.model.gameprop.BoardProp.MonsterHouse;
import client.model.gameprop.SelectedCardProp;

public class SelectedCardProcessor extends ChangePosProcessor {

    public SelectedCardProcessor(ChangePosProcessor processor) {
        super(processor);
    }

    public String process(SelectedCardProp cardProp, WantedPos pos, MonsterHouse monsterHouse) {
        if (cardProp == null) return MainPhase.NO_CARD_SELECTED_YET.toString();
        if (!cardProp.getLocation().equals(CardLocation.MONSTER_ZONE))
            return MainPhase.WRONG_LOCATION_FOR_CHANGE.toString();

        return super.process(cardProp, pos, monsterHouse);
    }
}
