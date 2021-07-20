package client.model;

import java.util.LinkedHashMap;

public class MessageHistory {
    private static LinkedHashMap<String, Message> currentMessages;
    private static int currentNumberOfLoggedIns;
    private static String pinnedMessageID;

    static {
        currentMessages = new LinkedHashMap<>();
    }

    public static LinkedHashMap<String, Message> getCurrentMessages() {
        return currentMessages;
    }

    public static void setCurrentMessages(LinkedHashMap<String, Message> currentMessages) {
        MessageHistory.currentMessages = currentMessages;
    }

    public static String getPinnedMessageID() {
        return pinnedMessageID;
    }

    public static void setPinnedMessageID(String pinnedMessageID) {
        MessageHistory.pinnedMessageID = pinnedMessageID;
    }

    public static int getCurrentNumberOfLoggedIns() {
        return currentNumberOfLoggedIns;
    }

    public static void setCurrentNumberOfLoggedIns(int currentNumberOfLoggedIns) {
        MessageHistory.currentNumberOfLoggedIns = currentNumberOfLoggedIns;
    }
}
