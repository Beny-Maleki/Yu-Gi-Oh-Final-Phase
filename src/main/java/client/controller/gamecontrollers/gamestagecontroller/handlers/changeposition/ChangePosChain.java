package client.controller.gamecontrollers.gamestagecontroller.handlers.changeposition;

import client.controller.gamecontrollers.gamestagecontroller.handlers.changeposition.processors.CardStateProcessor;
import client.controller.gamecontrollers.gamestagecontroller.handlers.changeposition.processors.ChangeRequestProcessor;
import client.model.enums.GameEnums.WantedPos;
import client.model.gameprop.BoardProp.MonsterHouse;
import client.model.gameprop.SelectedCardProp;

public class ChangePosChain {
    ChangePosProcessor processor;

    public ChangePosChain() {
        buildChain();
    }

    private void buildChain() {
        processor = new CardStateProcessor(new ChangeRequestProcessor(null));
    }

    public String request(SelectedCardProp cardProp, WantedPos pos, MonsterHouse monsterHouse) {
        return processor.process(cardProp, pos, monsterHouse);
    }
}
