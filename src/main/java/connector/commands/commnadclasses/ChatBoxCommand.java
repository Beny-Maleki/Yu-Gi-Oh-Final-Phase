package connector.commands.commnadclasses;

import client.model.Message;
import client.model.userProp.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import connector.commands.Command;
import connector.commands.CommandType;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;

public class ChatBoxCommand extends Command {
    private ChatCommandType chatCommandType;
    private boolean isInReplyToAnother;

    private int numberOfLoggedIns;

    private String sentMessage;
    private String IDInReplyTo;
    private String messageID;

    private User sender;

    private LinkedHashMap<String, Message> allMessages;
    private LinkedHashMap<String, Message> pinnedMessage;
    
    public ChatBoxCommand(CommandType commandType, String sentMessage, ChatCommandType chatCommandType,
                          boolean isInReplyToAnother, String IDInReplyTo, User sender) {
        super(commandType);

        this.sentMessage = sentMessage;
        this.chatCommandType = chatCommandType;
        this.isInReplyToAnother = isInReplyToAnother;

        this.IDInReplyTo = IDInReplyTo;
        this.sender = sender;
    }


    public String getSentMessage() {
        return sentMessage;
    }

    public User getSender() {
        return sender;
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

    public static String  toJson(ChatBoxCommand chatBoxCommand) {
        Gson gson = new Gson();
        Type type = new TypeToken<LinkedHashMap<String, Message>> () {}.getType();
        String JSONed = gson.toJson(chatBoxCommand, type);
        return JSONed;
    }

    public static LinkedHashMap<String, Message> fromJSON (String JSONed) {
        Gson gson = new Gson();
        Type type = new TypeToken<LinkedHashMap<String, Message>> () {}.getType();
        LinkedHashMap<String, Message> messages = gson.fromJson(JSONed, type);
        return messages;
    }
}
