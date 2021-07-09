package controller.gamecontrollers.gamestagecontroller.handlers.activeeffect;

import controller.gamecontrollers.gamestagecontroller.handlers.activeeffect.processors.ActiveSpellProcessor;
import controller.gamecontrollers.gamestagecontroller.handlers.activeeffect.processors.SelectCardProcessor;
import model.gameprop.gamemodel.Game;

import java.io.FileNotFoundException;

public class ActiveEffectChain {
    ActiveEffectProcessor processor;

    public ActiveEffectChain() {
        buildChain();
    }

    private void buildChain() {
        processor = new SelectCardProcessor(new ActiveSpellProcessor(null));
    }

    public String request(Game game) throws FileNotFoundException {
        return processor.process(game);
    }

}
