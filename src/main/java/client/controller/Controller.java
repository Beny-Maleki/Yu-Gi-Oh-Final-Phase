package client.controller;

import animatefx.animation.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import client.model.enums.Menu;
import client.view.controller.ScoreboardView;

import java.io.IOException;

public abstract class Controller {
    Parent parent;

    public void moveToPage(Node node, Menu menu) throws IOException {
        Stage stage;
        Scene scene;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(menu.getAddress()));
        parent = loader.load();
        stage = (Stage) node.getScene().getWindow();
        scene = new Scene(parent);
        if (menu == Menu.SCOREBOARD_MENU) {
            ScoreboardView controller = loader.getController();
            controller.setDetails();
        }
        stage.setScene(scene);
        scene.getRoot().requestFocus();
        loadPane(menu);
        stage.show();
    }

    private void loadPane(Menu page) {

        switch (page) {
            case SCOREBOARD_MENU:
            case WELCOME_MENU:
            case PROFILE_MENU:
            case REGISTER_MENU:
            case MAIN_MENU:
            case DUEL_PAGE:
            case ROCK_PAPER_SCISSOR_PAGE:
            case PROFILE_CHANGE_NICKNAME: {
                new FadeIn(parent).play();
                break;
            }
            case PROFILE_CHANGE_PASSWORD: {
                new SlideInLeft(parent).play();
                break;
            }

            case LOGIN_MENU:
            case DECKS_VIEW: {
                new SlideInDown(parent).play();
                break;
            }
            case DECK_MODIFIER: {
                new SlideInRight(parent).play();
                break;
            }
            case SHOP_MENU: {
                new JackInTheBox(parent).play();
                break;
            }
        }
    }

    public void displayMessage(Label message) {
        Timeline timeline = new Timeline();

        timeline.getKeyFrames().add(new KeyFrame(
                Duration.seconds(3), (ActionEvent event) -> new FadeOut(message).play()
        ));

        timeline.getKeyFrames().add(new KeyFrame(
                Duration.seconds(3.5), (ActionEvent event) -> {
            message.setText("");
            new FadeIn(message).play();
        }));
        timeline.setCycleCount(1);
        timeline.play();
    }

}
