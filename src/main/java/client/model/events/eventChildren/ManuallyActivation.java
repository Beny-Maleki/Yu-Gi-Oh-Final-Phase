package client.model.events.eventChildren;

import client.model.events.Event;

public class ManuallyActivation extends Event {
    private static ManuallyActivation instance;

    {
        name = this.getClass().getSimpleName();
    }

    private ManuallyActivation() {}

    public static ManuallyActivation getInstance() {
        if (instance == null) {
            instance = new ManuallyActivation();
        }
        return instance;
    }
}
