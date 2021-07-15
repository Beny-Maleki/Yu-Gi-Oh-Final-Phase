package client.controller.gamecontrollers.gamestagecontroller.handlers.hirespell;

import client.model.gameprop.gamemodel.Game;

public abstract class SpellProcessor {
    SpellProcessor processor;

    protected SpellProcessor(SpellProcessor processor) {
        this.processor = processor;
    }

    protected String process(Game game) {
        if (processor != null) {
            return processor.process(game);
        }else{
            return null;
        }
    }
}
