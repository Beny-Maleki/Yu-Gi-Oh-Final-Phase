package client.controller.gamecontrollers.gamestagecontroller.handlers.changeposition.processors;

import client.controller.gamecontrollers.gamestagecontroller.handlers.changeposition.ChangePosProcessor;
import client.model.enums.GameEnums.GamePhaseEnums.MainPhase;
import client.model.enums.GameEnums.WantedPos;
import client.model.enums.GameEnums.cardvisibility.MonsterHouseVisibilityState;
import client.model.gameprop.BoardProp.MonsterHouse;
import client.model.gameprop.SelectedCardProp;

public class ChangeRequestProcessor extends ChangePosProcessor {
    public ChangeRequestProcessor(ChangePosProcessor processor) {
        super(processor);
    }

    public String process(SelectedCardProp cardProp, WantedPos pos, MonsterHouse summonedMonsterHouses) {
        MonsterHouse monsterHouse = (MonsterHouse) cardProp.getCardPlace();
        if (monsterHouse == summonedMonsterHouses)
            return MainPhase.CANT_CHANGE_POS_OF_HIRED_CARD.toString();
        if (monsterHouse.isPosChange())
            return MainPhase.POS_CHANGE_BEFORE.toString();
        else {
            if (pos.equals(WantedPos.ATTACK)) {
                monsterHouse.changePos();
                monsterHouse.setState(MonsterHouseVisibilityState.OO);
            } else {
                monsterHouse.changePos();
                monsterHouse.setState(MonsterHouseVisibilityState.DO);
            }
            return MainPhase.MONSTER_CHANGE_POS.toString();
        }
    }
}
