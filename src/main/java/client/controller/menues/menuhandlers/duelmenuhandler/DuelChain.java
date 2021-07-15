package client.controller.menues.menuhandlers.duelmenuhandler;

import client.controller.menues.menuhandlers.duelmenuhandler.processors.RoundNumberProcessor;
import client.controller.menues.menuhandlers.duelmenuhandler.processors.SecPlayerProcessor;
import client.controller.menues.menuhandlers.duelmenuhandler.processors.deckValidatorProcessor;
import client.model.enums.MenusMassages.Duel;

public class DuelChain {
    DuelProcessor processor;

    public DuelChain() {
        buildChain();
    }

    private void buildChain() {
        processor = new SecPlayerProcessor(new deckValidatorProcessor(new RoundNumberProcessor(null)));
    }

    public Duel request(String[] data) {
       return processor.process(data);
    }
}
