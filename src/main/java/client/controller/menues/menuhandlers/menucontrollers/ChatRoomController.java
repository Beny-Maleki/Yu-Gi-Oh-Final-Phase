package client.controller.menues.menuhandlers.menucontrollers;

import client.controller.Controller;
import client.model.Message;
import client.model.userProp.LoginUser;
import client.model.userProp.User;
import client.network.ClientListener;
import client.network.ClientSender;
import client.view.controller.ChatRoomView;
import connector.commands.CommandType;
import connector.commands.commnadclasses.ChatBoxCommand;
import connector.commands.commnadclasses.ChatCommandType;

import java.util.LinkedHashMap;

public class ChatRoomController extends Controller{
    private static ChatRoomController instance;
    private static LinkedHashMap<String, Message> allMessages;
    private static String pinnedMessageID;
    private static User sender;
    private static int numberOfLoggedIns;

    static {
        allMessages = new LinkedHashMap<>();
    }

    private ChatRoomView currentFXMLController;

    private ChatRoomController(){
    }


    public ChatRoomView getCurrentFXMLController() {
        return currentFXMLController;
    }

    public void setCurrentFXMLController(ChatRoomView currentFXMLController) {
        this.currentFXMLController = currentFXMLController;
    }

    public static ChatRoomController getInstance() {
        if (instance == null) instance = new ChatRoomController();
        return instance;
    }

    public static LinkedHashMap<String, Message> getAllMessages() {
        return allMessages;
    }

    public static String getPinnedMessageID() {
        return pinnedMessageID;
    }

    public static int getNumberOfLoggedIns() {
        return numberOfLoggedIns;
    }

    private static void waitForServerResponse() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {
            if (!ClientListener.getCurrentCommandID().equals(ClientListener.getServerResponse().getCommandID())) break;
        }
        ClientListener.setCurrentCommandID(ClientListener.getServerResponse().getCommandID());
    }

    public void initializeData() {
        ChatBoxCommand chatBoxCommand = new ChatBoxCommand(
                CommandType.CHAT, null, ChatCommandType.INITIAL_DATA_REQUEST, false, null, LoginUser.getUser()
        );
        ClientListener.setCurrentCommandID(chatBoxCommand.getCommandID());
        ClientListener.setServerResponse(chatBoxCommand);
        sender = LoginUser.getUser();

        ClientSender.getSender().sendMessage(chatBoxCommand);

        ChatRoomController.waitForServerResponse();

        ChatBoxCommand chatBoxCommandResponse = (ChatBoxCommand) ClientListener.getServerResponse();

        allMessages = chatBoxCommandResponse.getAllMessages();
        pinnedMessageID = chatBoxCommandResponse.getPinnedMessage();
        numberOfLoggedIns = chatBoxCommand.getNumberOfLoggedIns();
    }

    public static void updateData() {
        ChatBoxCommand chatBoxCommand = new ChatBoxCommand(
                CommandType.CHAT, null, ChatCommandType.UPDATE, false, null, LoginUser.getUser()
        );
        ClientListener.setCurrentCommandID(chatBoxCommand.getCommandID());
        ClientListener.setServerResponse(chatBoxCommand);
        sender = LoginUser.getUser();

        ClientSender.getSender().sendMessage(chatBoxCommand);

        ChatRoomController.waitForServerResponse();

        ChatBoxCommand chatBoxCommandResponse = (ChatBoxCommand) ClientListener.getServerResponse();

        allMessages = chatBoxCommandResponse.getAllMessages();
        pinnedMessageID = chatBoxCommandResponse.getPinnedMessage();
        numberOfLoggedIns = chatBoxCommand.getNumberOfLoggedIns();
    }

    public String sendMessage(String messageText, boolean isInReplyTo, String IDInReplyTo) {
        if (matcher("\\s*", messageText).matches()) {
            return "INVALID_MESSAGE";
        }


        ChatBoxCommand chatBoxCommand = new ChatBoxCommand( // TODO: isInReplyToAnother, IDInReplyTo
                CommandType.CHAT, messageText, ChatCommandType.NEW_MESSAGE, false, null, LoginUser.getUser()
        );
        ClientListener.setServerResponse(chatBoxCommand);
        ClientListener.setCurrentCommandID(chatBoxCommand.getCommandID());
        sender = LoginUser.getUser();
        chatBoxCommand.setSender(sender);

        ClientSender.getSender().sendMessage(chatBoxCommand);

        ChatRoomController.waitForServerResponse();

        return "SUCCESSFUL";
    }

    public String deleteMessage(String ID) {
        sender = LoginUser.getUser();

        ChatBoxCommand chatBoxCommand = new ChatBoxCommand(
               CommandType.CHAT, ChatCommandType.OMIT_MESSAGE, ID
        );
        ClientListener.setCurrentCommandID(chatBoxCommand.getCommandID());
        ClientListener.setServerResponse(chatBoxCommand);
        chatBoxCommand.setSender(sender);

        ClientSender.getSender().sendMessage(chatBoxCommand);

        ChatRoomController.waitForServerResponse();

        return "SUCCESSFUL";
    }

    public String editMessage(String ID, String editedMessage) {
        sender = LoginUser.getUser();

        ChatBoxCommand chatBoxCommand = new ChatBoxCommand(
          CommandType.CHAT, ChatCommandType.EDIT_MESSAGE, ID, editedMessage
        );
        ClientListener.setCurrentCommandID(chatBoxCommand.getCommandID());
        ClientListener.setServerResponse(chatBoxCommand);
        chatBoxCommand.setSender(sender);

        ClientSender.getSender().sendMessage(chatBoxCommand);

        ChatRoomController.waitForServerResponse();

        return "SUCCESSFUL";
    }

    public String pinMessage(String ID) {
        sender = LoginUser.getUser();

        ChatBoxCommand chatBoxCommand = new ChatBoxCommand(
                CommandType.CHAT, ChatCommandType.PIN_MESSAGE, ID
        );
        ClientListener.setCurrentCommandID(chatBoxCommand.getCommandID());
        ClientListener.setServerResponse(chatBoxCommand);
        chatBoxCommand.setSender(sender);

        ClientSender.getSender().sendMessage(chatBoxCommand);

        ChatRoomController.waitForServerResponse();

        return "SUCCESSFUL";
    }
}
