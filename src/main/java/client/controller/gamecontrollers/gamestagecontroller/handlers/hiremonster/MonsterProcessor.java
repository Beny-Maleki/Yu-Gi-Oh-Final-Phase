package client.controller.gamecontrollers.gamestagecontroller.handlers.hiremonster;

import client.model.enums.GameEnums.TypeOfHire;
import client.model.gameprop.gamemodel.Game;

public abstract class MonsterProcessor {
    MonsterProcessor processor;

    protected MonsterProcessor(MonsterProcessor processor) {
        this.processor = processor;
    }

    protected String process(Game game, TypeOfHire type) {
        if (processor != null) {
            return processor.process(game, type);
        }else{
            return null;
        }
    }
}
