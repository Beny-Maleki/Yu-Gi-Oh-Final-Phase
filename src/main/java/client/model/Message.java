package client.model;

import client.model.userProp.User;
import server.MessageDatabase;

import java.time.LocalDateTime;
import java.util.UUID;

public class Message {
    private boolean isPinned;
    private boolean isInReplyToAnotherMessage;
    private final String ID;
    private String message;
    private String IDInReplyTo;
    private User sender;
    private LocalDateTime time;

    public Message(String message, User sender, boolean isInReplyToAnotherMessage, String IDInReplyTo) {
        ID = UUID.randomUUID().toString();
        this.sender = sender;
        this.message = message;
        this.time = LocalDateTime.now();
        this.isInReplyToAnotherMessage = isInReplyToAnotherMessage;
        if (isInReplyToAnotherMessage) this.IDInReplyTo = IDInReplyTo;

        MessageDatabase.getInstance().putToAllMessages(ID, this);
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
