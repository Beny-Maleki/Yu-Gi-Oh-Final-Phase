package client.view.controller;

import animatefx.animation.FadeInLeft;
import animatefx.animation.FadeInUp;
import animatefx.animation.FadeOut;
import client.controller.Controller;
import client.controller.menues.menuhandlers.menucontrollers.ChatRoomController;
import client.model.Message;
import client.model.MessageHistory;
import client.model.userProp.LoginUser;
import client.model.userProp.User;
import connector.commands.CommandType;
import connector.commands.commnadclasses.ChatBoxCommand;
import connector.commands.commnadclasses.ChatCommandType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

public class ChatRoomView extends Controller {

    public ScrollPane scrollPane;
    public TextField messageTextField;
    public Label numberOfLoggedInsLabel;
    public AnchorPane root;
    public ImageView sendButton;
    public Label pinnedMessageLabel;
    public Label pinnedMessageTitle;
    public Label repliedMessageLabel;
    public Button cancelReplyButton;
    public VBox content;
    public Pane pinnedMessageContainer;

    private static HashMap<String, HBox> messageContainersByID;

    static {
        messageContainersByID = new HashMap<>();
    }

    String selectedMessageID;
    String repliedMessageID;
    User selectedUserAvatar;

    public void initialize() {
        ChatRoomController.getInstance().initializeData();

        updateNumOfLoggedIns();

        messageTextField.clear();

        scrollPane.setMinViewportHeight(425);

        makeMessages();

        updatePinnedMessage();

        ChatRoomController.getInstance().setCurrentFXMLController(this);

        new Thread(() -> {
           //while (true) {
               if (root.isVisible()) {
                   ChatBoxCommand response = (ChatBoxCommand) ChatRoomController.getResponseCommand();
                   if (response.getChatCommandType() == ChatCommandType.UPDATE) {
                       System.out.println("here");
                       Platform.runLater(() -> {
                           ChatRoomView currentView = ChatRoomController.getInstance().getCurrentFXMLController();
                           currentView.generalUpdate();

                           ChatRoomController.setResponseCommand(new ChatBoxCommand(CommandType.WAITING));
                       });
                   }
               }
          // }
        }).start();
    }

    public void generalUpdate() {
        ChatRoomController.getInstance().updateData();

        updateNumOfLoggedIns();
        updateMessages();
        updatePinnedMessage();
        ChatRoomController.setResponseCommand(new ChatBoxCommand(CommandType.WAITING));
    }

    public void updateNumOfLoggedIns() {
        ChatBoxCommand chatBoxCommand = (ChatBoxCommand) ChatRoomController.getResponseCommand();
        int newNumberOfLoggedIns = chatBoxCommand.getNumberOfLoggedIns();

        if (MessageHistory.getCurrentNumberOfLoggedIns() != newNumberOfLoggedIns) {
            numberOfLoggedInsLabel.setText(String.valueOf(newNumberOfLoggedIns));
            new FadeInUp(numberOfLoggedInsLabel).play();
        }

        MessageHistory.setCurrentNumberOfLoggedIns(newNumberOfLoggedIns);
    }

    private void updateMessages() {
        ChatBoxCommand chatBoxCommand = (ChatBoxCommand) ChatRoomController.getResponseCommand();
        Set<String> newIDs = chatBoxCommand.getAllMessages().keySet();
        Set<String> prevIDs = MessageHistory.getCurrentMessages().keySet();

        LinkedHashMap<String, Message> newLinkedHM = chatBoxCommand.getAllMessages();
        for (String id : newIDs) {
            if (!prevIDs.contains(id)) {
                visualizeMessage(newLinkedHM.get(id));
            }
        }

        MessageHistory.getCurrentMessages().clear();
        for (String newID : newIDs) {
            Message newMessage = ChatRoomController.getAllMessages().get(newID);
            MessageHistory.getCurrentMessages().put(newID, newMessage);
        }

        scrollPane.vvalueProperty().bind(content.heightProperty());
    }

    private void updatePinnedMessage() {
        ChatBoxCommand chatBoxCommand = (ChatBoxCommand) ChatRoomController.getResponseCommand();
        String newPinnedMessageID = chatBoxCommand.getPinnedMessageID();

        if (newPinnedMessageID == null) return;

        if (!newPinnedMessageID.equals(MessageHistory.getPinnedMessageID())) {
            Message pinnedMessageObject = chatBoxCommand.getAllMessages().get(newPinnedMessageID);
            pinnedMessageLabel.setText(pinnedMessageObject.getMessageString());
            new FadeInLeft(pinnedMessageLabel).play();

            setEventsOfPinnedMessage(pinnedMessageObject, messageContainersByID.get(pinnedMessageObject.getID()));
        }
        MessageHistory.setPinnedMessageID(newPinnedMessageID);
    }

    private void makeMessages() {
        LinkedHashMap<String, Message> allMessages = ChatRoomController.getAllMessages();

        if (allMessages.size() == 0) return;

        Set<String> IDs = allMessages.keySet();
        for (String id : IDs) {
            Message message = allMessages.get(id);
            visualizeMessage(message);
        }
    }

    private void visualizeMessage(Message message) {
        HBox messageContainer = new HBox();
        messageContainer.setPrefWidth(500);
        messageContainer.setSpacing(10);
        messageContainersByID.put(message.getID(), messageContainer);

        Pane messagePane = new Pane();
        messagePane.setPadding(new Insets(5, 15, 0, 15));
        messagePane.setLayoutX(0);

        Label messageTextLabel = new Label(message.getMessageString());
        messageTextLabel.setPadding(new Insets(0, 5, 0, 5));

        messagePane.getChildren().add(messageTextLabel);
        messagePane.setCursor(Cursor.HAND);
        messagePane.setOnMouseClicked(e -> {
            selectedMessageID = message.getID();
            showOptions(e, message, messageContainer, messageTextLabel);
        });

        if (message.isInReplyToAnotherMessage()) {
            // assigning size and layouts according to isInReplyToAnother == true !
            //TODO: complete the styles of this block!
            messagePane.setPrefHeight(50);
            messageTextLabel.setLayoutY(20);
        } else {
            messagePane.setPrefHeight(30);
            Label senderUsernameLabel = new Label(message.getSender().getUsername());
            senderUsernameLabel.setStyle("-fx-font-size: 13; -fx-font-weight: bold; -fx-text-fill: #a966f1");
            senderUsernameLabel.setPadding(new Insets(0, 5, 0, 5));
            messagePane.getChildren().add(senderUsernameLabel);

            messageTextLabel.setLayoutY(13);
        }

        if (LoginUser.getUser().getUsername().equals(message.getSender().getUsername())) {
            //positioning and styling messages according to sender!
            messagePane.setStyle("-fx-background-radius: 10; -fx-border-radius: 10; -fx-background-color: #84BDEF");
            messageContainer.setAlignment(Pos.CENTER_RIGHT);
            messageContainer.setPadding(new Insets(0, 25, 0, 0));
            messageTextLabel.setStyle("-fx-text-fill: black");
        } else {
            ImageView avatar = makeAvatar(message);
            messageContainer.getChildren().add(avatar);

            messagePane.setStyle("-fx-background-radius: 10; -fx-border-radius: 10; -fx-background-color: #1B2A38");
            messageContainer.setAlignment(Pos.CENTER_LEFT);
            messageContainer.setPadding(new Insets(0, 0, 0, 10));
            messageTextLabel.setStyle("-fx-text-fill: white");
        }

        messageContainer.getChildren().addAll(messagePane);

        content.getChildren().add(messageContainer);
    }

    private ImageView makeAvatar(Message message) {
        ImageView avatar;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(message.getSender().getAvatarAddress());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(inputStream);
        avatar = new ImageView(image);
        avatar.setCursor(Cursor.HAND);
        avatar.setOnMouseClicked(e -> {
            selectedUserAvatar = message.getSender();
            showUserDetails();
        });
        avatar.setFitHeight(30);
        avatar.setFitWidth(30);
        return avatar;
    }

    private void showUserDetails() {

    }

    private void showOptions(MouseEvent mouseEvent, Message message, HBox messageContainer, Label messageLabel) {
        double layoutX = 0;
        double layoutY = 0;
        double sizeX = 50;
        double sizeY = 40;

        VBox options = makeOptions(content, layoutX, layoutY, sizeX, sizeY);

        Label replyLabel = new Label("Reply");
        replyLabel.setOnMouseClicked(e -> {
            repliedMessageID = message.getID();
            repliedMessageLabel.setText("In reply to: " + message.getMessageString());
            cancelReplyButton.setVisible(true);
            root.getChildren().remove(options);
        });

        HBox reply = new HBox(replyLabel);
        reply.setOnMouseClicked(e -> {
            repliedMessageID = message.getID();
            repliedMessageLabel.setText("In reply to: " + message.getMessageString());
            cancelReplyButton.setVisible(true);
            root.getChildren().remove(options);
        });

        if (message.getSender().getUsername().equals(LoginUser.getUser().getUsername())) {
            Label editLabel = new Label("Edit");
            editLabel.setOnMouseClicked(e -> {
                editMessage(options, message, messageLabel);
                root.getChildren().remove(options);
            });

            HBox edit = new HBox(editLabel);
            edit.setOnMouseClicked(e -> {
                editMessage(options, message, messageLabel);
                root.getChildren().remove(options);
            });

            Label pinLabel = new Label("Pin");
            pinLabel.setOnMouseClicked(e -> {
                pinMessage(options, message, messageContainer);
                root.getChildren().remove(options);
            });

            HBox pin = new HBox(pinLabel);
            pin.setOnMouseClicked(e -> {
                pinMessage(options, message, messageContainer);
                root.getChildren().remove(options);
            });

            Label deleteLabel = new Label("Delete");
            deleteLabel.setOnMouseClicked(e -> {
                deleteMessage(options, message, messageContainer);
                root.getChildren().remove(options);
            });

            HBox delete = new HBox(deleteLabel);
            delete.setOnMouseClicked(e -> {
                deleteMessage(options, message, messageContainer);
                root.getChildren().remove(options);
            });

            options.getChildren().addAll(edit, delete, pin);
        }

        options.getChildren().add(reply);
        options.setStyle("-fx-background-color: white");
        options.toFront();
        root.getChildren().add(options);

    }

    private void deleteMessage(VBox options, Message message, HBox messageContainer) {
        ChatRoomController.getInstance().deleteMessage(message.getID());
        content.getChildren().remove(messageContainer);
    }

    private void pinMessage(VBox options, Message message, HBox messageContainer) {
        ChatRoomController.getInstance().pinMessage(message.getID());
        setEventsOfPinnedMessage(message, messageContainer);
        MessageHistory.setPinnedMessageID(message.getID());
    }

    private void setEventsOfPinnedMessage(Message message, HBox messageContainer) {
        pinnedMessageLabel.setText(message.getMessageString());
        pinnedMessageContainer.setOnMouseClicked(e -> {
            scrollPane.vvalueProperty().bind(messageContainer.layoutYProperty());
        });
        pinnedMessageLabel.setOnMouseClicked(e -> {
            scrollPane.vvalueProperty().bind(messageContainer.layoutYProperty());
        });
    }

    private void editMessage(VBox options, Message message, Label messageLabel) {
        messageTextField.setText(message.getMessageString());
        sendButton.setOnMouseClicked(e -> {
            ChatRoomController.getInstance().editMessage(message.getID(), messageTextField.getText());
            content.getChildren().remove(options);
            sendButton.setOnMouseClicked(this::sendMessage);
            messageLabel.setText(messageTextField.getText());
            messageTextField.clear();
        });
    }

    private Pane makeAPopUp(Pane parent, double layoutX, double layoutY, double sizeX, double sizeY) {
        Pane pane = new Pane();
        pane.setLayoutX(layoutX);
        pane.setLayoutY(layoutY);
        pane.setPrefWidth(sizeX);
        pane.setPrefHeight(sizeY);
        parent.getChildren().add(pane);

        return pane;
    }

    private VBox makeOptions(Pane parent, double layoutX, double layoutY, double sizeX, double sizeY) {
        VBox vBox = new VBox();
        vBox.setStyle("fx-background-color:  #37536C; -fx-border-radius: 5; -fx-background-radius: 5");
        vBox.setLayoutX(layoutX);
        vBox.setLayoutY(layoutY);
        vBox.setPrefWidth(sizeX);
        vBox.setPrefHeight(sizeY);

        parent.getChildren().add(vBox);
        for (Node child : parent.getChildren()) {
            if (child != vBox) child.setDisable(true);
        }

        return vBox;
    }

    public void sendMessage(MouseEvent mouseEvent) {
        String messageText = messageTextField.getText();
        String clientControllerResponse = ChatRoomController.getInstance().sendMessage(messageText, repliedMessageID != null, "");

        messageTextField.clear();

        if (clientControllerResponse.equals("SUCCESSFUL")) {
            updateMessages();
        }
    }

    public void cancelReply(ActionEvent actionEvent) {
        repliedMessageID = null;
        repliedMessageLabel.setText("");
        cancelReplyButton.setVisible(false);
    }

    public void close(ActionEvent actionEvent) {
        FadeOut fadeOut = new FadeOut(root);
        fadeOut.getTimeline().setOnFinished(e -> {
            root.setVisible(false);
        });

        fadeOut.play();
        for (Node node : root.getParent().getChildrenUnmodifiable()) {
            node.setDisable(false);
        }
    }
}
