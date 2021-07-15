package client.controller.gamecontrollers.gamestagecontroller.handlers.activeeffect;

import client.controller.gamecontrollers.gamestagecontroller.handlers.activeeffect.processors.ActiveSpellProcessor;
import client.model.gameprop.gamemodel.Game;

import java.io.FileNotFoundException;

public class ActiveEffectChain {
    ActiveEffectProcessor processor;

    public ActiveEffectChain() {
        buildChain();
    }

    private void buildChain() {
        processor =new ActiveSpellProcessor(null);
    }

    public String request(Game game) throws FileNotFoundException {
        return processor.process(game);
    }

}
