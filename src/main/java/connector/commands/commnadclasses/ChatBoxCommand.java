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
    private boolean haveSentMessage;
    private boolean haveOmittedMessage;
    private boolean haveEditedMessage;
    private boolean havePinnedMessage;
    private boolean isInReplyToAnother;

    private int numberOfLoggedIns;

    private String sentMessage;
    private String IDInReplyTo;
    private String messageID;

    private User sender;

    private LinkedHashMap<String, Message> allMessages;
    private LinkedHashMap<String, Message> pinnedMessage;
    
    public ChatBoxCommand(CommandType commandType) {
        super(commandType);
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

    public boolean haveSentMessage() {
        return haveSentMessage;
    }

    public boolean haveOmittedMessage() {
        return haveOmittedMessage;
    }

    public boolean haveEditedMessage() {
        return haveEditedMessage;
    }

    public boolean havePinnedMessage() {
        return havePinnedMessage;
    }

    public int getNumberOfLoggedIns() {
        return numberOfLoggedIns;
    }

    public void setNumberOfLoggedIns(int numberOfLoggedIns) {
        this.numberOfLoggedIns = numberOfLoggedIns;
    }

    public String createJson() {
        return null;
    }

    public static String toJson(ChatBoxCommand chatBoxCommand) {
        Gson gson = new Gson();
        Type type = new TypeToken<LinkedHashMap<String, Message>> () {}.getType();
        String JSONed = gson.toJson(chatBoxCommand, type);
        return null;
    }
}
