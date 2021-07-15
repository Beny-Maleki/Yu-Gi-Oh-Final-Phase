package client.model.events.eventChildren;

import client.model.events.Event;

public class OpponentMonsterWantsToAttack extends Event {
    private static OpponentMonsterWantsToAttack instance;

    {
        name = this.getClass().getSimpleName();
    }

    private OpponentMonsterWantsToAttack() {
    }

    public static OpponentMonsterWantsToAttack getInstance() {
        if (instance == null) {
            instance = new OpponentMonsterWantsToAttack();
        }
        return instance;
    }
}
