package client.model.events.eventChildren;

import client.model.events.Event;

public class GotAttacked extends Event {
    private static GotAttacked instance;

    public static GotAttacked getInstance() {
        if (instance == null) {
            instance = new GotAttacked();
        }
        return instance;
    }
}
