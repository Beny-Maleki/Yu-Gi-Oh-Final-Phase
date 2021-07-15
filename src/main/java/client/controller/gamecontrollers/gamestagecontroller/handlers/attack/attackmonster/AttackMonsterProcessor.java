package client.controller.gamecontrollers.gamestagecontroller.handlers.attack.attackmonster;

import client.model.gameprop.BoardProp.MonsterHouse;
import client.model.gameprop.gamemodel.Game;

public abstract class AttackMonsterProcessor {
    AttackMonsterProcessor processor;

    protected AttackMonsterProcessor(AttackMonsterProcessor processor) {
        this.processor = processor;
    }

    protected String process(MonsterHouse target, Game game) {
        if (processor != null)
            return processor.process(target, game);
        else return null;
    }
}
