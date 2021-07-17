package client.controller.menues.menuhandlers.menucontrollers;

import connector.commands.CommandType;
import connector.commands.commnadclasses.RegisterCommand;
import animatefx.animation.FadeOut;
import client.controller.Controller;
import client.model.Exceptions.EmptyTextFieldException;
import client.model.enums.Menu;
import client.network.ClientListener;
import client.network.ClientSender;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class RegisterPageController extends Controller {

    private String imageAddress = "src/main/resources/graphicprop/images/avatar1.png";

    public void createUser(String username, String nickname, String password, Label message) {
        try {
            if (username.equals("") || password.equals("") || nickname.equals("")) throw new EmptyTextFieldException();

            RegisterCommand registerCommand = new RegisterCommand(CommandType.REGISTER, username, nickname, password, imageAddress);
            ClientSender.getSender().sendMessage(registerCommand);
            handleProgressBar();
            try {
               while (ClientListener.getServerResponse().getCommandType() == CommandType.WAITING) {
                   Thread.sleep(100);
               }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (responseException != null) {
                message.setText(responseException.getMessage());
                responseException = null;
            } else {
                moveToPage(message, Menu.LOGIN_MENU);
            }
        } catch (EmptyTextFieldException | IOException e) {
            message.setText(e.getMessage());
        }
        message.setStyle("-fx-text-fill: red ; -fx-font-size: 15");
        displayMessage(message);
    }

    public Pane makeAvatarSelector(ImageView imageView, Pane pane) throws FileNotFoundException {
        Pane avatarSelector = new Pane();
        setSelectorSize(avatarSelector);
        ImageView avatarOne = new ImageView();
        ImageView avatarTwo = new ImageView();
        ImageView avatarThree = new ImageView();
        ImageView avatarFour = new ImageView();
        Button button = new Button("Back");
        button.setOnMouseClicked(event -> {
            FadeOut fadeOut = new FadeOut(avatarSelector);
            fadeOut.getTimeline().setOnFinished(event1 -> pane.getChildren().remove(avatarSelector));
            fadeOut.play();
            pane.setStyle("-fx-opacity: 1");
            pane.getChildren().forEach(node -> node.setDisable(false));
        });
        button.setLayoutY(395);
        pane.setStyle("-fx-opacity: 0.5");
        pane.getChildren().forEach(node -> node.setDisable(true));
        avatarSelector.getChildren().add(button);
        setAvatarsSize(avatarOne, avatarTwo, avatarThree, avatarFour);
        setImageOnMouseCLickEvent(imageView, avatarOne, avatarTwo, avatarThree, avatarFour);
        avatarSelector.getChildren().addAll(avatarFour, avatarThree, avatarTwo, avatarOne);
        return avatarSelector;
    }

    private void setImageOnMouseCLickEvent(ImageView imageView, ImageView avatarOne, ImageView avatarTwo, ImageView avatarThree, ImageView avatarFour) {
        ArrayList<ImageView> imageViews = new ArrayList<>(Arrays.asList(avatarOne, avatarTwo, avatarThree, avatarFour));
        setOnMouseClickEvents(imageViews, imageView, 0);
        setOnMouseClickEvents(imageViews, imageView, 1);
        setOnMouseClickEvents(imageViews, imageView, 2);
        setOnMouseClickEvents(imageViews, imageView, 3);
    }

    private void setAvatarsSize(ImageView avatarOne, ImageView avatarTwo, ImageView avatarThree, ImageView avatarFour) throws FileNotFoundException {
        setLayout(avatarOne, 1);
        setLayout(avatarTwo, 2);
        setLayout(avatarThree, 3);
        setLayout(avatarFour, 4);
    }

    private void setSelectorSize(Pane avatarSelector) {
        avatarSelector.setPrefWidth(541);
        avatarSelector.setPrefHeight(424);
        avatarSelector.setLayoutY(60);
        avatarSelector.setLayoutX(235);
    }

    private void setLayout(ImageView imageview, int area) throws FileNotFoundException {
        setSize(imageview);
        String address = "src/main/resources/graphicprop/images/";
        switch (area) {
            case 1: {
                imageview.setLayoutX(60);
                imageview.setLayoutY(28);
                address = address + "avatar1";
                break;
            }
            case 2: {
                imageview.setLayoutY(28);
                imageview.setLayoutX(326);
                address = address + "avatar2";
                break;
            }
            case 3: {
                imageview.setLayoutX(326);
                imageview.setLayoutY(235);
                address = address + "avatar3";
                break;
            }
            case 4: {
                imageview.setLayoutY(235);
                imageview.setLayoutX(60);
                address = address + "avatar4";
                break;
            }
        }
        imageview.setImage(new Image(new FileInputStream(address + ".png")));
    }

    private void setSize(ImageView imageView) {
        imageView.setFitHeight(143);
        imageView.setFitWidth(143);
    }

    private void setOnMouseClickEvents(ArrayList<ImageView> imageViews, ImageView previousAvatar, int i) {
        ImageView chosenAvatar = imageViews.get(i);
        chosenAvatar.setOnMouseClicked(event -> {
            setSizeForNotSelectedImages(imageViews);
            chosenAvatar.setScaleX(1.1);
            chosenAvatar.setScaleY(1.1);
            previousAvatar.setImage(chosenAvatar.getImage());
            switch (i) {
                case 0: {
                    imageAddress = "src/main/resources/graphicprop/images/avatar1.png";
                    break;
                }
                case 1:{
                    imageAddress = "src/main/resources/graphicprop/images/avatar2.png";
                    break;
                }
                case 2:{
                    imageAddress = "src/main/resources/graphicprop/images/avatar3.png";
                    break;
                }
                case 3:{
                    imageAddress = "src/main/resources/graphicprop/images/avatar4.png";
                    break;
                }
            }
        });
    }

    private void setSizeForNotSelectedImages(ArrayList<ImageView> imageViews) {
        for (ImageView imageView : imageViews) {
            imageView.setScaleX(1);
            imageView.setScaleY(1);
        }
    }



}
