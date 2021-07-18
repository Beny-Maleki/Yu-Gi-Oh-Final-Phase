package client.controller.menues.menuhandlers.menucontrollers;

import client.controller.Controller;
import client.model.userProp.LoginUser;
import connector.commands.CommandType;
import connector.commands.commnadclasses.ChatBoxCommand;
import connector.commands.commnadclasses.ChatCommandType;

public class ChatRoomController extends Controller{
    private static ChatRoomController instance;

    private ChatRoomController(){}

    public static ChatRoomController getInstance() {
        if (instance == null) instance = new ChatRoomController();
        return instance;
    }

    public String sendMessage(String messageText, boolean isInReplyTo, String IDInReplyTo) {
        if (matcher("\\s*", messageText).matches()) {
            return "INVALID_MESSAGE";
        }

        ChatBoxCommand chatBoxCommand = new ChatBoxCommand( // TODO: isInReplyToAnother, IDInReplyTo
                CommandType.CHAT, messageText, ChatCommandType.NEW_MESSAGE, false, "0", LoginUser.getUser()
        );

        return null;
    }
}
