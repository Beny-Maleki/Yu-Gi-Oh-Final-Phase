package server;

import client.model.Message;

import java.util.LinkedHashMap;

public class MessageDatabase {
    private static MessageDatabase instance;
    private LinkedHashMap<String, Message> allMessages;
    private String pinnedMessageID;

    private MessageDatabase() {
        allMessages = new LinkedHashMap<>();
    }

    public static MessageDatabase getInstance() {
        if (instance == null) instance = new MessageDatabase();
        return instance;
    }

    public LinkedHashMap<String, Message> getAllMessages() {
        return allMessages;
    }

    public Message getFromAllMessages(String key) {
        return allMessages.get(key);
    }

    public void putToAllMessages(String key, Message value) {
        allMessages.put(key, value);
    }

    public void removeFromAllMessages(String key) {
        allMessages.remove(key);
    }

    public void setPinnedMessageID(String ID) {
        pinnedMessageID = ID;
    }

    public String getPinnedMessageID() {
        return pinnedMessageID;
    }
}
