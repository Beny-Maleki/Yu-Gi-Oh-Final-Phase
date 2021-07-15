package client.controller.menues.menuhandlers.duelmenuhandler.processors;

import client.controller.menues.menuhandlers.duelmenuhandler.DuelProcessor;
import client.model.enums.MenusMassages.Duel;

public class RoundNumberProcessor extends DuelProcessor {

    public RoundNumberProcessor(DuelProcessor processor) {
        super(processor);
    }

    public Duel process(String[] data) {
        if (!data[2].equals("3") && !data[2].equals("1")) {
            return Duel.INVALID_NUMBER_OF_ROUNDS;
        }
        return super.process(data);
    }
}
