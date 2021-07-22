package client.view.controller;

import animatefx.animation.*;
import client.controller.menues.menuhandlers.menucontrollers.ChatRoomController;
import client.controller.menues.menuhandlers.menucontrollers.MainMenuController;
import client.model.enums.Menu;
import client.model.userProp.LoginUser;
import client.model.userProp.OnWorkThreads;
import client.view.AudioHandler;
import client.view.AudioPath;
import client.view.ClickButtonHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
    public AnchorPane chatRoomPopUp;

    public Label tradeLabel = new Label("Trade");
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
        chatRoomPopUp.setVisible(false);
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
        LoginUser.setOnlineThread(OnWorkThreads.TRADE_THREAD);
        new BounceIn(tradePopUp).play();
        tradePopUp.setVisible(true);
    }

    private void showChatRoom() {
        ChatRoomController.getInstance().initializeData();
        for (Node child : root.getChildren()) {
            if (child != chatRoomPopUp) {
                child.setDisable(true);
            }
        }
        new BounceIn(chatRoomPopUp).play();
        chatRoomPopUp.setVisible(true);
    }

    public void showChatRoomPanel(MouseEvent mouseEvent) {
        showChatRoom();
    }


    public void soundEffect(MouseEvent event) {
        ClickButtonHandler.getInstance().play();
    }
}
