package client.view.controller;

import animatefx.animation.FadeIn;
import client.controller.menues.menuhandlers.menucontrollers.RegisterPageController;
import client.model.enums.Menu;
import client.view.ClickButtonHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class RegisterView {
    private final RegisterPageController controller;
    public TextField username;
    public TextField password;
    public TextField nickname;
    public Button register;
    public Button back;
    public Label message;
    public ImageView avatarFrame;
    public AnchorPane mainPane;
    public Pane registerBox;
    private Pane avatarPane;

    {
        controller = new RegisterPageController();
    }

    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == register) {
            controller.createUser(username.getText(), password.getText(), nickname.getText(), message);
        } else if (event.getSource() == back) {
            controller.moveToPage(back, Menu.WELCOME_MENU);
        } else if (event.getSource() == avatarFrame) {
            avatarPane = controller.makeAvatarSelector(avatarFrame, registerBox);
            avatarPane.setStyle("-fx-background-color: #191929;" +
                    " -fx-border-radius: 20; -fx-background-radius: 20 ; -fx-border-color: blue; -fx-opacity: 0.75");
            FadeIn fadeIn = new FadeIn(avatarPane);

            mainPane.getChildren().add(avatarPane);
            fadeIn.play();
        }
    }


    public void soundEffect(MouseEvent event) {
        ClickButtonHandler.getInstance().play();
    }


}
