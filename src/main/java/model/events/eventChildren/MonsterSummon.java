package model.events.eventChildren;

import model.events.Event;

public class MonsterSummon extends Event {
    private static MonsterSummon instance;

    {
        name = this.getClass().getSimpleName();
    }

    private MonsterSummon() {}

    public static MonsterSummon getInstance() {
        if (instance == null) {
            instance = new MonsterSummon();
        }
        return instance;
    }
}
