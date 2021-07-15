package client.model.events.eventChildren;

import client.model.events.Event;

public class FieldMagicFaceUp extends Event {
    private static FieldMagicFaceUp instance;

    {
        name = this.getClass().getSimpleName();
    }

    public static FieldMagicFaceUp getInstance() {
        if (instance == null) {
            instance = new FieldMagicFaceUp();
        }
        return instance;
    }
}
