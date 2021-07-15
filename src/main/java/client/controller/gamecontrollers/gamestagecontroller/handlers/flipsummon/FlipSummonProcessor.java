package client.controller.gamecontrollers.gamestagecontroller.handlers.flipsummon;

import client.model.gameprop.gamemodel.Game;

public abstract class FlipSummonProcessor {
    FlipSummonProcessor processor;

    public FlipSummonProcessor(FlipSummonProcessor processor) {
        this.processor = processor;
    }

    public String process(Game game) {
        if (processor != null)
            return processor.process(game);
        else return null;
    }
}
