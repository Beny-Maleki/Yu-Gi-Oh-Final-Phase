package client.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class TradePopUpView {
    public Pane container;
    public Button makeATrade;
    public Button acceptATrade;
    public Button findATrade;
    public AnchorPane makeATradePage;

    @FXML
    public void initialize() {
        container.getChildren().add(makeATradePage);
    }


    public void makeATradeBut(MouseEvent event) {

    }
}
