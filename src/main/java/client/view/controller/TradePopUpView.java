package client.view.controller;

import animatefx.animation.BounceOut;
import animatefx.animation.FadeIn;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class TradePopUpView {
    public Pane container;
    public Button makeATrade;
    public Button acceptATrade;
    public Button findATrade;
    public AnchorPane makeATradePage;
    public ImageView exitIcon;
    public Button exitBut;
    public AnchorPane popUp;

    @FXML
    public void initialize() {
        makeATradePage.setVisible(false);
        container.getChildren().add(makeATradePage);
        exitBut.setOnMouseEntered(event -> {
            if (!exitIcon.isVisible()) {
                exitIcon.setVisible(true);
                new FadeIn(exitIcon).play();
            }
        });

        exitBut.setOnMouseExited(event -> {
            BounceOut bounce = new BounceOut(exitIcon);
            bounce.getTimeline().setOnFinished(event1 -> exitIcon.setVisible(false));
            bounce.play();
        });

        exitBut.setOnMouseClicked(event -> {
            popUp.setVisible(false);
        });
    }


    public void makeATradeBut() {
        makeATradePage.setVisible(true);
        new FadeIn(makeATradePage).play();
    }

}
