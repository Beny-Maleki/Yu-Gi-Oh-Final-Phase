package client.controller.gamecontrollers.gamestagecontroller.handlers.flipsummon;

import client.controller.gamecontrollers.gamestagecontroller.handlers.flipsummon.processors.FlipCardProcessor;
import client.controller.gamecontrollers.gamestagecontroller.handlers.flipsummon.processors.SelectCardProcessor;
import client.model.gameprop.gamemodel.Game;

public class FlipSummonChain {
    FlipSummonProcessor processor;

    public FlipSummonChain() {
        buildChain();
    }

    private void buildChain() {
        processor = new SelectCardProcessor(new FlipCardProcessor(null));
    }

    public String request(Game game) {
        return processor.process(game);
    }
}
