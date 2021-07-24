package connector;

import client.model.Message;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class MessageWrapper {
    private static ArrayList<MessageWrapper> messageWrappers;

    static {
        messageWrappers = new ArrayList<>();
    }

    private Message message;
    private Pane messagePane;
    private Label messageTextLabel;

    public MessageWrapper(Message message, Pane messagePane, Label messageTextLabel) {
        this.message = message;
        this.messagePane = messagePane;
        this.messageTextLabel = messageTextLabel;

        messageWrappers.add(this);
    }

    public static ArrayList<MessageWrapper> getMessageWrappers() {
        return messageWrappers;
    }

    public Message getMessage() {
        return message;
    }

    public Label getMessageTextLabel() {
        return messageTextLabel;
    }
}
