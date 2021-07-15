package client.controller.gamecontrollers.gamestagecontroller.handlers.changeposition.processors;

import client.controller.gamecontrollers.gamestagecontroller.handlers.changeposition.ChangePosProcessor;
import client.model.enums.GameEnums.GamePhaseEnums.MainPhase;
import client.model.enums.GameEnums.WantedPos;
import client.model.enums.GameEnums.cardvisibility.MonsterHouseVisibilityState;
import client.model.gameprop.BoardProp.MonsterHouse;
import client.model.gameprop.SelectedCardProp;

public class CardStateProcessor extends ChangePosProcessor {

    public CardStateProcessor(ChangePosProcessor processor) {
        super(processor);
    }

    public String process(SelectedCardProp cardProp, WantedPos pos, MonsterHouse summonedMonsterHouse) {
        MonsterHouse monsterHouse;
        monsterHouse = (MonsterHouse) cardProp.getCardPlace();
        if ((monsterHouse.getState().equals(MonsterHouseVisibilityState.DO) && pos.equals(WantedPos.DEFENCE)) ||
                (monsterHouse.getState().equals(MonsterHouseVisibilityState.OO) && pos.equals(WantedPos.ATTACK))) {
            return MainPhase.ALREADY_IN_WANTED_POS.toString();
        }
        if ((monsterHouse.getState().equals(MonsterHouseVisibilityState.DH)))
            return MainPhase.FLIP_NEEDED.toString();
        return super.process(cardProp, pos, summonedMonsterHouse);
    }
}
