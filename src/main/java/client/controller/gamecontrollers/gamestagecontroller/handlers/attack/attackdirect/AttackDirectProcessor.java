package client.controller.gamecontrollers.gamestagecontroller.handlers.attack.attackdirect;

import client.model.gameprop.gamemodel.Game;

public abstract class AttackDirectProcessor {
    AttackDirectProcessor processor;

    public AttackDirectProcessor(AttackDirectProcessor processor) {
        this.processor = processor;
    }

    public String process(Game game) {
        if (processor != null) {
            return processor.process(game);
        } else return null;
    }
}
