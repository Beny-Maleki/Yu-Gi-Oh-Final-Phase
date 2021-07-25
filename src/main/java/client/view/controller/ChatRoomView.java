package client.view.controller;

import animatefx.animation.FadeInLeft;
import animatefx.animation.FadeInUp;
import animatefx.animation.FadeOut;
import animatefx.animation.Flash;
import client.controller.Controller;
import client.controller.menues.menuhandlers.menucontrollers.ChatRoomController;
import client.model.Message;
import client.model.MessageHistory;
import client.model.userProp.LoginUser;
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
        content.getChildren().clear();
        MessageWrapper.getMessageWrappers().clear();
        MessageHistory.getCurrentMessages().clear();
        MessageHistory.setCurrentNumberOfLoggedIns(0);
        MessageHistory.setPinnedMessageID(null);

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
        } else if (response.getChatCommandType() == ChatCommandType.UPDATE_EDIT) {
            updateEditedMessage(response);
        } else if (response.getChatCommandType() == ChatCommandType.UPDATE_OMIT) {
            updateOmittedMessage(response);
        } else if (response.getChatCommandType() == ChatCommandType.UPDATE_PIN) {
            updatePinnedMessage(response);
        }
    }

    private void updateOmittedMessage(ChatBoxCommand response) {
        ArrayList<MessageWrapper> wrappers = MessageWrapper.getMessageWrappers();
        for (MessageWrapper wrapper : wrappers) {
            if (wrapper.getMessage().getID().equals(response.getMessageID())) {
                MessageHistory.getCurrentMessages().remove(response.getMessageID());
                content.getChildren().remove(wrapper.getMessageContainer());
                break;
            }
        }
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

        MessageHistory.setCurrentMessages((LinkedHashMap<String, Message>) response.getAllMessages().clone());

        scrollPane.vvalueProperty().bind(content.heightProperty());
    }

    private void updatePinnedMessage(ChatBoxCommand response) {
        String newPinnedMessageID = response.getPinnedMessageID();

        if (newPinnedMessageID == null) return;

        Message pinnedMessageObject = MessageHistory.getCurrentMessages().get(newPinnedMessageID);
        pinnedMessageLabel.setText(pinnedMessageObject.getMessageString());
        new FadeInLeft(pinnedMessageLabel).play();

        Pane messagePane = null;
        for (MessageWrapper messageWrapper : MessageWrapper.getMessageWrappers()) {
            if (messageWrapper.getMessage().getID().equals(newPinnedMessageID)) {
                messagePane = messageWrapper.getMessagePane();
            }
        }
        pinnedOnMouseClick(newPinnedMessageID, pinnedMessageObject, messagePane);

        MessageHistory.setPinnedMessageID(newPinnedMessageID);
    }

    private void pinnedOnMouseClick(String newPinnedMessageID, Message pinnedMessageObject, Pane messagePane) {
        HBox messageContainer = null;
        for (MessageWrapper messageWrapper : MessageWrapper.getMessageWrappers()) {
            if (messageWrapper.getMessage().getID().equals(newPinnedMessageID)) {
                messageContainer = messageWrapper.getMessageContainer();
                break;
            }
        }

        setEventsOfPinnedMessage(pinnedMessageObject, messageContainer, messagePane);
    }

    private void makeMessages(ChatBoxCommand serverResponse) {
        LinkedHashMap<String, Message> allMessages = serverResponse.getAllMessages();

        if (allMessages.size() == 0) return;

        Set<String> IDs = allMessages.keySet();
        for (String id : IDs) {
            Message message = allMessages.get(id);
            visualizeMessage(message, serverResponse);
        }

        MessageHistory.setCurrentMessages(serverResponse.getAllMessages());
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


        if (message.isInReplyToAnotherMessage()) {
            // replied section:
            Pane repliedMessagePane = new Pane();
            repliedMessagePane.setPadding(new Insets(0, 5, 0, 5));
            repliedMessagePane.setStyle("-fx-background-color: #580f0f; -fx-background-radius: 10; -fx-border-radius: 10");
            repliedMessagePane.toFront();

            Label repliedMessageSender = new Label(chatBoxCommand.getAllMessages().get(message.getIDInReplyTo()).getSender().getUsername());
            repliedMessageSender.setStyle("-fx-font-size: 13; -fx-font-weight: bold; -fx-text-fill: #ff7b7b");
            repliedMessageSender.setPadding(new Insets(0, 5, 0, 5));

            Label repliedMessage = new Label(chatBoxCommand.getAllMessages().get(message.getIDInReplyTo()).getMessageString());
            repliedMessage.setStyle("-fx-font-size: 13; -fx-font-weight: bold; -fx-text-fill: #e02e5d");
            repliedMessage.setPadding(new Insets(0, 5, 0, 5));
            repliedMessage.setLayoutY(repliedMessageSender.getHeight() + 15);

            repliedMessagePane.setOnMouseClicked(e -> {
                e.consume();
                for (MessageWrapper wrapper : MessageWrapper.getMessageWrappers()) {
                    if (wrapper.getMessage().getID().equals(repliedMessageID)) {
                        makeVisible(wrapper.getMessageContainer());
                        flashContainer(wrapper.getMessagePane());
                        e.consume();
                        break;
                    }
                }
            });
            repliedMessagePane.setLayoutY(10);
            repliedMessagePane.setLayoutX(5);
            repliedMessagePane.getChildren().addAll(repliedMessageSender, repliedMessage);
            ////

            //main section:
            Label senderUsernameLabel = new Label(message.getSender().getUsername());
            senderUsernameLabel.setStyle("-fx-font-size: 13; -fx-font-weight: bold; -fx-text-fill: #a966f1");
            senderUsernameLabel.setPadding(new Insets(0, 5, 0, 5));
            senderUsernameLabel.setLayoutY(45);

            messageTextLabel.setLayoutY(62.5);
            ////

            messagePane.getChildren().addAll(repliedMessagePane, senderUsernameLabel);
        } else {
            messagePane.setPrefHeight(30);
            Label senderUsernameLabel = new Label(message.getSender().getUsername());
            senderUsernameLabel.setStyle("-fx-font-size: 13; -fx-font-weight: bold; -fx-text-fill: #a966f1");
            senderUsernameLabel.setPadding(new Insets(0, 5, 0, 5));
            messagePane.getChildren().add(senderUsernameLabel);

            messageTextLabel.setLayoutY(13);
        }

        messagePane.getChildren().add(messageTextLabel);
        messagePane.setCursor(Cursor.HAND);
        messagePane.setOnMouseClicked(e -> {
            selectedMessageID = message.getID();
            showOptions(e, message, messageContainer, messageTextLabel, messagePane);
        });

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

        new MessageWrapper(message, messagePane, messageTextLabel, messageContainer);
    }

    private void flashContainer(Pane messagePane) {
        Flash flash = new Flash(messagePane);
        flash.setCycleCount(4);
        flash.play();
    }

    private void makeVisible(HBox messageContainer) {
        double h = scrollPane.getContent().getBoundsInLocal().getHeight();
        double y = (messageContainer.getBoundsInParent().getMaxY() + messageContainer.getBoundsInParent().getMinY()) / 2.0;
        double v = scrollPane.getViewportBounds().getHeight();

        scrollPane.layout();
        scrollPane.applyCss();
        messageContainer.getParent().layout();
        messageContainer.getParent().applyCss();
        messageContainer.layout();
        messageContainer.applyCss();

        scrollPane.setVvalue(scrollPane.getVmax() * ((y - 0.5 * v) / (h - v)));
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

    private void showOptions(MouseEvent mouseEvent, Message message, HBox messageContainer, Label messageLabel, Pane messagePane) {
        double layoutX = mouseEvent.getSceneX() - 250 ;
        double layoutY = mouseEvent.getSceneY() - 50 ;
        if (layoutX > 450) {
            layoutX = 450;
        }
        double sizeX = 50;
        double sizeY = 40;

        VBox options = makeOptions(content, layoutX, layoutY, sizeX, sizeY);


        if (message.getSender().getUsername().equals(LoginUser.getUser().getUsername())) {
            Label editLabel = new Label("Edit");
            HBox edit = new HBox(editLabel);
            edit.setCursor(Cursor.HAND);
            edit.setOnMouseClicked(e -> {
                editMessage(options, message, messageLabel);
                clearOptions(options);
            });

            Label pinLabel = new Label("Pin");
            HBox pin = new HBox(pinLabel);
            pin.setCursor(Cursor.HAND);
            pin.setOnMouseClicked(e -> {
                pinMessage(message, messageContainer);
                clearOptions(options);
            });

            Label deleteLabel = new Label("Delete");
            HBox delete = new HBox(deleteLabel);
            delete.setCursor(Cursor.HAND);
            delete.setOnMouseClicked(e -> {
                deleteMessage(message, messageContainer);
                clearOptions(options);
            });

            options.getChildren().addAll(edit, delete, pin);
        }

        Label replyLabel = new Label("Reply");
        HBox reply = new HBox(replyLabel);
        reply.setCursor(Cursor.HAND);
        reply.setOnMouseClicked(e -> {
            repliedMessageID = message.getID();
            repliedMessageLabel.setText("In reply to: " + message.getMessageString());
            cancelReplyButton.setVisible(true);
            clearOptions(options);
        });

        Label closeLabel = new Label("Close");
        HBox close = new HBox(closeLabel);
        close.setCursor(Cursor.HAND);
        close.setOnMouseClicked(e -> {
            clearOptions(options);
        });

        options.getChildren().addAll(reply, close);
        options.setStyle("-fx-background-color: white");
        root.getChildren().add(options);

    }

    private void clearOptions(VBox options) {
        root.getChildren().remove(options);
        for (Node n : content.getChildren()) {
            n.setDisable(false);
        }
    }

    private void deleteMessage(Message message, HBox messageContainer) {
        ChatRoomController.getInstance().deleteMessage(message.getID());
    }

    private void pinMessage(Message message, HBox messageContainer) {
        ChatRoomController.getInstance().pinMessage(message.getID());
    }

    private void setEventsOfPinnedMessage(Message message, HBox messageContainer, Pane messagePane) {
        pinnedMessageLabel.setText(message.getMessageString());
        pinnedMessageContainer.setOnMouseClicked(e -> {
            makeVisible(messageContainer);
            flashContainer(messagePane);
        });
        pinnedMessageLabel.setOnMouseClicked(e -> {
            makeVisible(messageContainer);
            flashContainer(messagePane);
        });
    }

    private void editMessage(VBox options, Message message, Label messageLabel) {
        messageTextField.setText(message.getMessageString());
        sendButton.setOnMouseClicked(e -> {
            ChatRoomController.getInstance().editMessage(message.getID(), messageTextField.getText());
            sendButton.setOnMouseClicked(this::sendMessage);

            messageTextField.clear();
        });
    }

    public void sendMessage(MouseEvent mouseEvent) {
        String messageText = messageTextField.getText();
        String clientControllerResponse = ChatRoomController.getInstance().sendMessage(messageText, repliedMessageID != null, repliedMessageID);


        messageTextField.clear();
        repliedMessageID = null;
        repliedMessageLabel.setText("");
        cancelReplyButton.setVisible(false);

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
        FadeOut fadeOut = new FadeOut(root);
        fadeOut.getTimeline().setOnFinished(e -> {
            root.setVisible(false);
        });

        fadeOut.play();
    }
}
