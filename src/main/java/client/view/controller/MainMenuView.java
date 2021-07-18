package client.view.controller;

import client.controller.menues.menuhandlers.menucontrollers.MainMenuController;
import client.model.enums.Menu;
import client.view.AudioHandler;
import client.view.AudioPath;
import client.view.ClickButtonHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MainMenuView {

    public Button duel;
    public Button decksConstruction;
    public Button shop;
    public Button logout;
    public Button profile;
    private final MainMenuController controller;
    public Button createCard;
    public Button importExport;

    {
        controller = new MainMenuController();
    }

    @FXML
    public void initialize() {
        AudioHandler mainTheme; // stating the game theme music!
        if (AudioHandler.getPlaying() != null) {
            if (!AudioHandler.getPlayingAudioPath().equals(AudioPath.MAIN_MENU)) {
                mainTheme = new AudioHandler(AudioPath.MAIN_MENU);
                AudioHandler.getPlaying().getMediaPlayer().stop();
                mainTheme.play();
            }
        } else {
            mainTheme =  new AudioHandler(AudioPath.MAIN_MENU);
            mainTheme.play();
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
        }
    }

    public void soundEffect(MouseEvent event) {
        ClickButtonHandler.getInstance().play();
    }
}
