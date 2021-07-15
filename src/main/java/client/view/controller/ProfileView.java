package client.view.controller;

import animatefx.animation.FadeIn;
import animatefx.animation.Wobble;
import client.controller.menues.menuhandlers.menucontrollers.ProfilePageController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import client.model.enums.Menu;
import client.model.userProp.LoginUser;
import client.model.userProp.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ProfileView {
    private static ProfileView profileView;
    private User user;
    public Button changeNickname;
    public Button changePassword;
    public Button exit;
    public Pane userInfo;
    public Label username;
    public Label money;
    public Label score;
    public Label nickname;
    public TextField oldPassword;
    public TextField newNickname;
    public Button cancel;
    public Button change;
    public Label passwordError;
    public Label nicknameError;
    public TextField newPassword;
    public ImageView userAvatar;
    ProfilePageController controller;

    {
        controller = new ProfilePageController();
    }

    @FXML
    public void initialize() throws FileNotFoundException {
        user = LoginUser.getUser();
        username.setText("Username : " + user.getUsername());
        nickname.setText("Nickname : " + user.getNickname());
        score.setText("Total Scores : " + user.getScore());
        money.setText("Balance : " + user.getBalance());
        newNickname.setVisible(false);
        newPassword.setVisible(false);
        oldPassword.setVisible(false);
        cancel.setVisible(false);
        change.setVisible(false);
            userAvatar.setImage(new Image(new FileInputStream(user.getAvatarAddress())));
    }

    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == exit) {
            controller.moveToPage(exit, Menu.MAIN_MENU);
        } else if (event.getSource() == changeNickname) {
            shiftNicknameNodes(false);
            shiftButtons(false);
            new FadeIn(newNickname).play();
        } else if (event.getSource() == changePassword) {
            shiftPasswordsNodes(false);
            shiftButtons(false);
            new FadeIn(oldPassword).play();
        } else if (event.getSource() == cancel) {
            backToProfileView();
        } else if (event.getSource() == change) {
            if (newNickname.isVisible()) {
                nicknameError.setText(controller.changeNickname(newNickname.getText()));
                nickname.setText("Nickname : " + user.getNickname());
                controller.displayMessage(nicknameError);
                nicknameError.setStyle("-fx-font-size: 14; -fx-text-fill: #a4e26d");
            } else {
                passwordError.setText(controller.changePassword(oldPassword.getText(), newPassword.getText()));
                controller.displayMessage(passwordError);
            }
            backToProfileView();
        }
    }

    private void backToProfileView() {
        shiftButtons(true);
        if (newNickname.isVisible()) {
            shiftNicknameNodes(true);
        } else {
            shiftPasswordsNodes(true);
        }
    }


    private void shiftButtons(boolean shiftValue) {
        exit.setVisible(shiftValue);
        changeNickname.setVisible(shiftValue);
        changePassword.setVisible(shiftValue);
        change.setVisible(!shiftValue);
        cancel.setVisible(!shiftValue);
        if (shiftValue) {
            new FadeIn(exit).play();
            new FadeIn(changeNickname).play();
            new FadeIn(changePassword).play();
        } else {
            new FadeIn(change).play();
            new FadeIn(cancel).play();
        }
    }

    private void shiftNicknameNodes(boolean shiftValue) {
        nickname.setVisible(shiftValue);
        newNickname.setVisible(!shiftValue);
    }

    private void shiftPasswordsNodes(boolean shiftValue) {
        username.setVisible(shiftValue);
        nickname.setVisible(shiftValue);
        oldPassword.setVisible(!shiftValue);
        newPassword.setVisible(!shiftValue);
    }


    public void hoverAnimation(MouseEvent event) {
        new Wobble((Node) event.getSource()).play();
    }
}
