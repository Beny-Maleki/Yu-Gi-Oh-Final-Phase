package client.controller.gamecontrollers.gamestagecontroller.handlers.activeeffect;

import client.model.gameprop.gamemodel.Game;

import java.io.FileNotFoundException;

public abstract class ActiveEffectProcessor {
    ActiveEffectProcessor processor;

    public ActiveEffectProcessor(ActiveEffectProcessor processor) {
        this.processor = processor;
    }

    public String process(Game game) throws FileNotFoundException {
        if (processor != null)
            return processor.process(game);

        return null;
    }
}
