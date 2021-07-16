package client.controller;

import Connector.commands.Command;
import animatefx.animation.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import client.model.enums.Menu;
import client.view.controller.ScoreboardView;

import java.io.IOException;

public abstract class Controller {
    Parent parent;

    protected static RuntimeException responseException;
    private static Command responseCommand;

    ProgressBar loadingPB;
    Label loading;

    {
        loadingPB = new ProgressBar();
        loading = new Label("loading...");

        loadingPB.setLayoutX(400);
        loadingPB.setLayoutY(350);
        loadingPB.setPrefHeight(15);
        loadingPB.setPrefWidth(200);

        loadingPB.setProgress(0);

        loading.setLayoutX(400);
        loading.setLayoutY(370);

        loading.setVisible(false);
        loadingPB.setVisible(false);

    }


    public static void setResponseException(RuntimeException responseException) {
        Controller.responseException = responseException;
    }

    public static RuntimeException getResponseException() {
        return responseException;
    }

    public static void setResponseCommand(Command responseCommand) {
        Controller.responseCommand = responseCommand;
    }

    public static Command getResponseCommand() {
        return responseCommand;
    }

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

    public void handleProgressBar() {
        loading.setVisible(true);
        loadingPB.setVisible(true);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(1000), new KeyValue(loadingPB.progressProperty(), 1))
        );

        timeline.setOnFinished(e -> {
            loadingPB.setProgress(0);
            loading.setVisible(false);
            loading.setVisible(false);
        });

        timeline.play();
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
