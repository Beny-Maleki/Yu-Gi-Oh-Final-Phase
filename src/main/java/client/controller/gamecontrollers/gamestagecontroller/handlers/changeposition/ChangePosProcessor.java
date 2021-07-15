package client.controller.gamecontrollers.gamestagecontroller.handlers.changeposition;

import client.model.enums.GameEnums.WantedPos;
import client.model.gameprop.BoardProp.MonsterHouse;
import client.model.gameprop.SelectedCardProp;

public abstract class ChangePosProcessor {
    ChangePosProcessor processor;

    protected ChangePosProcessor(ChangePosProcessor processor) {
        this.processor = processor;
    }

    protected String process(SelectedCardProp cardProp, WantedPos pos, MonsterHouse monsterHouse) {
        if (processor != null) {
            return processor.process(cardProp, pos, monsterHouse);
        } else {
            return null;
        }
    }
}
