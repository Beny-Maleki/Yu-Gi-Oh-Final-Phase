package client.controller.menues.menuhandlers.duelmenuhandler;


import client.model.enums.MenusMassages.Duel;

public abstract class DuelProcessor {
    DuelProcessor processor;

    protected DuelProcessor(DuelProcessor processor) {
        this.processor = processor;
    }

    protected Duel process(String[] data) {
        if (processor != null) {
           return processor.process(data);
        }else{
            return null;
        }
    }

}
