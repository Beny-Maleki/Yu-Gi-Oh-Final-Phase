package client.controller.gamecontrollers.gamestagecontroller.handlers.hiremonster;

import client.controller.gamecontrollers.gamestagecontroller.handlers.hiremonster.processors.BoardProcessor;
import client.controller.gamecontrollers.gamestagecontroller.handlers.hiremonster.processors.HireMonsterProcessor;
import client.model.enums.GameEnums.TypeOfHire;
import client.model.gameprop.gamemodel.Game;

public class HireMonsterChain {
    MonsterProcessor processor;

    public HireMonsterChain() {
        buildChain();
    }

    private void buildChain() {
        processor =new BoardProcessor(new HireMonsterProcessor(null));
    }

    public String request(Game game, TypeOfHire type) {
        return processor.process(game, type);
    }
}
