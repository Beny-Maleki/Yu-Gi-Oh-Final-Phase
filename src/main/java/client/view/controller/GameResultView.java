package client.view.controller;

import animatefx.animation.Tada;
import client.model.gameprop.Player;
import client.view.AudioHandler;
import client.view.AudioPath;
import client.view.ClickButtonHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class GameResultView {
    public Label winner;
    public Button back;

    public void setDetails(Player player, String winOrLose) {
        winner.setText(player.getUser().getNickname());
        AudioPath audioPath;
        if (winOrLose.equals("win")) {
            audioPath = AudioPath.WIN;
        } else {
            audioPath = AudioPath.LOSE;
        }
        AudioHandler winnerTheme;
        if (AudioHandler.getPlaying() != null) {
            if (!AudioHandler.getPlayingAudioPath().equals(audioPath)) {
                winnerTheme = new AudioHandler(audioPath);
                AudioHandler.getPlaying().getMediaPlayer().stop();
                winnerTheme.play();
            }
        } else {
            winnerTheme = new AudioHandler(audioPath);
            winnerTheme.play();
        }
    }

    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == back) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/graphicprop/fxml/mainPage.fxml"));
            Parent parent = loader.load();
            Stage stage = (Stage) back.getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            scene.getRoot().requestFocus();
            stage.show();
        }
    }

    public void hoverAnimation(MouseEvent event) {
        ClickButtonHandler.getInstance().play();
        new Tada((Node) event.getSource()).play();
    }
}
