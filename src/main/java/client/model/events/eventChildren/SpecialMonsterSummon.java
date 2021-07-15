package client.model.events.eventChildren;

import client.model.events.Event;

public class SpecialMonsterSummon extends Event {
    private static SpecialMonsterSummon instance;

    {
        name = this.getClass().getSimpleName();
    }

    private SpecialMonsterSummon() {}

    public static SpecialMonsterSummon getInstance() {
        if (instance == null) {
            instance = new SpecialMonsterSummon();
        }
        return instance;
    }
}
