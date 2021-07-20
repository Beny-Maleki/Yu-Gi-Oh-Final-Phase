package client.view.controller;

import animatefx.animation.*;
import client.controller.menues.menuhandlers.menucontrollers.MainMenuController;
import client.model.enums.Menu;
import client.view.AudioHandler;
import client.view.AudioPath;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainMenuView {

    private final MainMenuController controller;
    public Button duel;
    public Button decksConstruction;
    public Button shop;
    public Button logout;
    public Button profile;
    public Button createCard;
    public Button importExport;
    public ImageView tradeButton;
    public AnchorPane tradePopUp;
    public AnchorPane root;
    public Label tradeLabel;
    public Pane holder;

    {
        controller = new MainMenuController();
    }

    @FXML
    public void initialize() {
        tradePopUp.setVisible(false);
        tradeButton.setOnMouseEntered(event -> {
            new Tada(tradeButton).play();
        });
        AudioHandler mainTheme; // stating the game theme music!
        if (AudioHandler.getPlaying() != null) {
            if (!AudioHandler.getPlayingAudioPath().equals(AudioPath.MAIN_MENU)) {
                mainTheme = new AudioHandler(AudioPath.MAIN_MENU);
                AudioHandler.getPlaying().getMediaPlayer().stop();
                mainTheme.play();
            }
        } else {
            mainTheme = new AudioHandler(AudioPath.MAIN_MENU);
            mainTheme.play();
        }
        if (!controller.getPlayerTradeRequest()) {
            Pulse pulse = new Pulse(tradeButton);
            pulse.setCycleCount(AnimationFX.INDEFINITE);
            pulse.play();
            Flash flash = new Flash(tradeLabel);
            flash.setCycleCount(AnimationFX.INDEFINITE);
            flash.setSpeed(0.4);
            flash.play();
        }
    }

    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == shop) {
            controller.moveToPage(shop, Menu.SHOP_MENU);
        } else if (event.getSource() == decksConstruction) {
            controller.moveToPage(decksConstruction, Menu.DECKS_VIEW);
        } else if (event.getSource() == logout) {
            controller.logout();
            controller.moveToPage(logout, Menu.WELCOME_MENU);
        } else if (event.getSource() == profile) {
            controller.moveToPage(profile, Menu.PROFILE_MENU);
        } else if (event.getSource() == duel) {
            controller.moveToPage(duel, Menu.DUEL_PAGE);
        } else if (event.getSource() == createCard) {
            controller.moveToPage(createCard, Menu.CARD_CREATOR_PAGE);
        } else if (event.getSource() == importExport) {
            controller.moveToPage(importExport, Menu.IMPORT_EXPORT);
        } else if (event.getSource() == tradeButton) {
            playTradePopUpAnimation();
        }
    }

    private void playTradePopUpAnimation() {
        new BounceIn(tradePopUp).play();
        tradePopUp.setVisible(true);
    }
}
