package client.model.events.eventChildren;

import client.model.events.Event;

public class OpponentDoesNormalOrFipSummon extends Event {
    private static OpponentDoesNormalOrFipSummon instance;

    {
        name = this.getClass().getSimpleName();
    }

    private OpponentDoesNormalOrFipSummon() {
    }

    public static OpponentDoesNormalOrFipSummon getInstance() {
        if (instance == null) {
            instance = new OpponentDoesNormalOrFipSummon();
        }
        return instance;
    }
}
