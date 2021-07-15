package client.model.gameprop.turnBasedObserver;

import java.util.ArrayList;

public abstract class TurnObserver {
    protected static ArrayList<TurnObserver> turnObservers;

    static {
        turnObservers = new ArrayList<>();
    }

    protected int remainedTurns;

    public TurnObserver() {
        turnObservers.add(this);
    }

    public static ArrayList<TurnObserver> getTurnObservers() {
        return turnObservers;
    }

    public void update() {
        remainedTurns--;
    }

    public static void clearTurnObserver() {
        turnObservers.clear();
    }
}
