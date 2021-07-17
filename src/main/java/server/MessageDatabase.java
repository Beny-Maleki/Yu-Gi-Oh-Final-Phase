package server;

import client.model.Message;

import java.util.LinkedHashMap;

public class MessageDatabase {
    private static MessageDatabase instance;
    private LinkedHashMap<String, Message> allMessages;
    private LinkedHashMap<String, Message> pinnedMessages;

    private MessageDatabase() {
        allMessages = new LinkedHashMap<>();
        pinnedMessages = new LinkedHashMap<>();
    }

    public static MessageDatabase getInstance() {
        if (instance == null) instance = new MessageDatabase();
        return instance;
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

    public void putToPinnedMessages(String key, Message value) {
        pinnedMessages.put(key, value);
    }

    public Message getFromPinnedMessages(String key) {
        return pinnedMessages.get(key);
    }

    public void removeFromPinnedMessages(String key) {
        pinnedMessages.remove(key);
    }


}
