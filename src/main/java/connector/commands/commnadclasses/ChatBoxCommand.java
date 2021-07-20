package connector.commands.commnadclasses;

import client.model.Message;
import client.model.userProp.User;
import connector.commands.Command;
import connector.commands.CommandType;

import java.util.LinkedHashMap;

public class ChatBoxCommand extends Command {
    private ChatCommandType chatCommandType;

    private boolean isInReplyToAnother;
    private String sentMessage;
    private String IDInReplyTo;
    private String messageID;

    private User sender;
    private String pinnedMessageID;


    private int numberOfLoggedIns;
    private LinkedHashMap<String, Message> allMessages;

    public ChatBoxCommand(CommandType commandType, String sentMessage, ChatCommandType chatCommandType,
                          boolean isInReplyToAnother, String IDInReplyTo, User sender) {
        super(commandType);

        this.sentMessage = sentMessage;
        this.chatCommandType = chatCommandType;
        this.isInReplyToAnother = isInReplyToAnother;

        this.IDInReplyTo = IDInReplyTo;
        this.sender = sender;
    }

    public ChatBoxCommand(CommandType commandType) {
        super(commandType);
    }

    public ChatBoxCommand(CommandType commandType, ChatCommandType chatCommandType, String messageID) {
        super(commandType);
        this.chatCommandType = chatCommandType;
        this.messageID = messageID;
    }

    public ChatBoxCommand(CommandType commandType, ChatCommandType chatCommandType, String messageID, String editedMessage) {
        super(commandType);
        this.chatCommandType = chatCommandType;
        this.messageID = messageID;
        this.sentMessage = editedMessage;
    }

    public String getSentMessage() {
        return sentMessage;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getSender() {
        return sender;
    }

    public void setAllMessages(LinkedHashMap<String, Message> allMessages) {
        this.allMessages = allMessages;
    }

    public boolean isInReplyToAnother() {
        return isInReplyToAnother;
    }

    public String getIDInReplyTo() {
        return IDInReplyTo;
    }

    public String getMessageID() {
        return messageID;
    }

    public int getNumberOfLoggedIns() {
        return numberOfLoggedIns;
    }

    public void setNumberOfLoggedIns(int numberOfLoggedIns) {
        this.numberOfLoggedIns = numberOfLoggedIns;
    }

    public ChatCommandType getChatCommandType() {
        return chatCommandType;
    }

    public void setChatCommandType(ChatCommandType chatCommandType) {
        this.chatCommandType = chatCommandType;
    }

    public void setPinnedMessageID(String pinnedMessageID) {
        this.pinnedMessageID = pinnedMessageID;
    }

    public LinkedHashMap<String, Message> getAllMessages() {
        return allMessages;
    }

    public String getPinnedMessageID() {
        return pinnedMessageID;
    }
}
