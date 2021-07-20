package client.view.controller;

import animatefx.animation.Wobble;
import client.controller.menues.menuhandlers.menucontrollers.ImportExportMenuController;
import client.model.enums.Menu;
import client.view.ClickButtonHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ImportPageView {
    public Button importButton;
    public Label message;
    public Button back;
    public ImportExportMenuController controller;
    public TextField cardName;

    {
        controller = new ImportExportMenuController();
    }

    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == back) {
            controller.moveToPage(back, Menu.IMPORT_EXPORT);
        } else if (event.getSource() == importButton) {
            controller.importCard(cardName.getText(), message);
        }
    }

    public void hoverAnimation(MouseEvent event) {
        ClickButtonHandler.getInstance().play();
        new Wobble((Node) event.getSource()).play();
    }
}
