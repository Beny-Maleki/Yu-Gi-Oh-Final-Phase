package client.controller.gamecontrollers.gamestagecontroller.handlers.hirespell;

import client.controller.gamecontrollers.gamestagecontroller.handlers.hirespell.processors.BoardProcessor;
import client.controller.gamecontrollers.gamestagecontroller.handlers.hirespell.processors.SetSpellProcessor;
import client.model.gameprop.gamemodel.Game;

public class SetSpellChain {
    SpellProcessor processor;

    public SetSpellChain() {
        buildChain();
    }

    private void buildChain() {
        processor =new BoardProcessor(new SetSpellProcessor(null));
    }

    public String request(Game game) {
        return processor.process(game);
    }
}
