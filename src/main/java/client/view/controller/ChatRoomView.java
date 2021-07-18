package client.view.controller;

import client.controller.Controller;
import client.controller.menues.menuhandlers.menucontrollers.ChatRoomController;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ChatRoomView extends Controller {

    public ScrollPane scrollPane;
    public TextField messageTextField;
    public Label numberOfLoggedInsLabel;

    private boolean isInReplyTo;

    public void sendMessage(MouseEvent mouseEvent) {
        String messageText = messageTextField.getText();
        String clientControllerResponse = ChatRoomController.getInstance().sendMessage(messageText, isInReplyTo, "");
    }

    public void close(ActionEvent actionEvent) {
    }
}
