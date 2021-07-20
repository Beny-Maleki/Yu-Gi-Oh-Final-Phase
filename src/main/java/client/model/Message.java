package client.model;

import client.model.userProp.User;
import server.MessageDatabase;

import java.time.LocalDateTime;
import java.util.UUID;

public class Message {
    private boolean isPinned;
    private boolean isInReplyToAnotherMessage;
    private final String ID;
    private String messageString;
    private String IDInReplyTo;
    private User sender;
    private LocalDateTime time;

    public Message(String messageString, User sender, boolean isInReplyToAnotherMessage, String IDInReplyTo) {
        ID = UUID.randomUUID().toString();
        this.sender = sender;
        this.messageString = messageString;
        this.time = LocalDateTime.now();
        this.isInReplyToAnotherMessage = isInReplyToAnotherMessage;
        if (isInReplyToAnotherMessage) this.IDInReplyTo = IDInReplyTo;

        MessageDatabase.getInstance().putToAllMessages(ID, this);
    }

    public void setMessageString(String messageString) {
        this.messageString = messageString;
    }

    public String getMessageString() {
        return messageString;
    }

    public User getSender() {
        return sender;
    }

    public String getID() {
        return ID;
    }

    public boolean isInReplyToAnotherMessage() {
        return isInReplyToAnotherMessage;
    }
}
