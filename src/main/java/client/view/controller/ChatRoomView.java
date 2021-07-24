package client.view.controller;

import animatefx.animation.FadeInLeft;
import animatefx.animation.FadeInUp;
import animatefx.animation.FadeOut;
import client.controller.Controller;
import client.controller.menues.menuhandlers.menucontrollers.ChatRoomController;
import client.model.Message;
import client.model.MessageHistory;
import client.model.userProp.LoginUser;
import client.model.userProp.OnWorkThreads;
import client.model.userProp.User;
import client.network.ClientListener;
import connector.MessageWrapper;
import connector.commands.commnadclasses.ChatBoxCommand;
import connector.commands.commnadclasses.ChatCommandType;
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
import java.util.ArrayList;
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

        updateNumOfLoggedIns((ChatBoxCommand) ClientListener.getServerResponse());

        messageTextField.clear();

        scrollPane.setMinViewportHeight(425);

        makeMessages((ChatBoxCommand) ClientListener.getServerResponse());

        updatePinnedMessage((ChatBoxCommand) ClientListener.getServerResponse());

        ChatRoomController.getInstance().setCurrentFXMLController(this);

    }

    public void update(ChatBoxCommand response) {
        generalUpdate(response);
    }

    public void generalUpdate(ChatBoxCommand response) {
        updateNumOfLoggedIns(response);
        if (response.getChatCommandType() == ChatCommandType.UPDATE_NEW) {
            updateNewMessages(response);
        } else if (response.getChatCommandType() == ChatCommandType.EDIT_MESSAGE) {
            updateEditedMessage(response);
        }
        updatePinnedMessage(response);
    }

    private void updateEditedMessage(ChatBoxCommand response) {
        ArrayList<MessageWrapper> wrappers = MessageWrapper.getMessageWrappers();
        for (MessageWrapper wrapper : wrappers) {
            if (wrapper.getMessage().getID().equals(response.getMessageID())) {
                wrapper.getMessageTextLabel().setText(response.getSentMessage());
            }
        }
    }

    public void updateNumOfLoggedIns(ChatBoxCommand response) {
        int newNumberOfLoggedIns = response.getNumberOfLoggedIns();

        if (MessageHistory.getCurrentNumberOfLoggedIns() != newNumberOfLoggedIns) {
            numberOfLoggedInsLabel.setText(String.valueOf(newNumberOfLoggedIns));
            new FadeInUp(numberOfLoggedInsLabel).play();
        }

        MessageHistory.setCurrentNumberOfLoggedIns(newNumberOfLoggedIns);
    }

    private void updateNewMessages(ChatBoxCommand response) {
        if (response.getAllMessages() == null) return;
        Set<String> newIDs = response.getAllMessages().keySet();
        Set<String> prevIDs = MessageHistory.getCurrentMessages().keySet();

        LinkedHashMap<String, Message> newLinkedHM = response.getAllMessages();
        for (String id : newIDs) {
            if (!prevIDs.contains(id)) {
                visualizeMessage(newLinkedHM.get(id), response);
            }
        }

        MessageHistory.getCurrentMessages().clear();
        for (String newID : newIDs) {
            Message newMessage = ChatRoomController.getAllMessages().get(newID);
            MessageHistory.getCurrentMessages().put(newID, newMessage);
        }

        scrollPane.vvalueProperty().bind(content.heightProperty());
    }

    private void updatePinnedMessage(ChatBoxCommand response) {
        String newPinnedMessageID = response.getPinnedMessageID();

        if (newPinnedMessageID == null) return;

        Message pinnedMessageObject = response.getAllMessages().get(newPinnedMessageID);
        System.out.println(pinnedMessageObject.getMessageString());
        pinnedMessageLabel.setText(pinnedMessageObject.getMessageString());
        new FadeInLeft(pinnedMessageLabel).play();

        setEventsOfPinnedMessage(pinnedMessageObject, messageContainersByID.get(pinnedMessageObject.getID()));

        MessageHistory.setPinnedMessageID(newPinnedMessageID);
    }

    private void makeMessages(ChatBoxCommand serverResponse) {
        LinkedHashMap<String, Message> allMessages = ChatRoomController.getAllMessages();

        if (allMessages.size() == 0) return;

        Set<String> IDs = allMessages.keySet();
        for (String id : IDs) {
            Message message = allMessages.get(id);
            visualizeMessage(message, serverResponse);
        }
    }

    private void visualizeMessage(Message message, ChatBoxCommand chatBoxCommand) {
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
            messagePane.setPrefHeight(50);

            Label repliedMessage = new Label(
                    chatBoxCommand.getAllMessages().get(message.getIDInReplyTo()).getMessageString()
            );
            repliedMessage.setStyle("-fx-font-size: 13; -fx-font-weight: bold; -fx-text-fill: #fa346a");
            repliedMessage.setPadding(new Insets(0, 5, 0, 5));
            repliedMessage.setMaxWidth(messagePane.getMaxWidth());

            Label senderUsernameLabel = new Label(message.getSender().getUsername());
            senderUsernameLabel.setStyle("-fx-font-size: 13; -fx-font-weight: bold; -fx-text-fill: #a966f1");
            senderUsernameLabel.setPadding(new Insets(0, 5, 0, 5));
            senderUsernameLabel.setLayoutY(17);

            messageTextLabel.setLayoutY(35);
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

        messageContainer.getChildren().add(messagePane);

        content.getChildren().add(messageContainer);

        new MessageWrapper(message, messagePane, messageTextLabel);
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
            showUserDetails(message.getSender(), image);
        });
        avatar.setFitHeight(30);
        avatar.setFitWidth(30);
        return avatar;
    }

    private void showUserDetails(User user, Image avatarImage) {
        Pane pane = makeAPopUp(root, 100, 100, 300, 300);
        pane.setStyle("-fx-background-color: black; -fx-border-radius: 10; -fx-background-radius: 10");

        Button closeButtonOfProfile = new Button("X");
        closeButtonOfProfile.setCursor(Cursor.HAND);
        closeButtonOfProfile.setPrefWidth(20);
        closeButtonOfProfile.setPrefHeight(20);
        closeButtonOfProfile.setStyle("-fx-text-fill: white; -fx-background-color: red; -fx-background-radius: 10; -fx-border-radius: 10");
        closeButtonOfProfile.setOnAction(e -> {
            new FadeOut(pane).play();
            root.getChildren().remove(pane);
        });

        ImageView avatar = new ImageView(avatarImage);
        avatar.setFitWidth(100);
        avatar.setFitHeight(100);
        avatar.setLayoutY(50);
        avatar.setLayoutX(100);
        pane.getChildren().add(avatar);

        Label username = new Label("Username: " + user.getUsername());
        Label nickname = new Label("Nickname: " + user.getNickname());
        Label score = new Label("Score: " + user.getScore());

        username.setLayoutX(50);
        username.setLayoutY(200);
        username.setStyle("-fx-text-fill: white; -fx-font-size: 20; -fx-font-weight: bold");

        nickname.setLayoutX(50);
        nickname.setLayoutY(225);
        nickname.setStyle("-fx-text-fill: white; -fx-font-size: 20; -fx-font-weight: bold");

        score.setLayoutX(50);
        score.setLayoutY(250);
        score.setStyle("-fx-text-fill: white; -fx-font-size: 20; -fx-font-weight: bold");

        pane.getChildren().addAll(closeButtonOfProfile, username, nickname, score);
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
//            messageLabel.setText(messageTextField.getText());
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
            updateNewMessages((ChatBoxCommand) ClientListener.getServerResponse());
        }
    }

    public void cancelReply(ActionEvent actionEvent) {
        repliedMessageID = null;
        repliedMessageLabel.setText("");
        cancelReplyButton.setVisible(false);
    }

    public void close(ActionEvent actionEvent) {
        LoginUser.setOnlineThread(OnWorkThreads.NONE);
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
