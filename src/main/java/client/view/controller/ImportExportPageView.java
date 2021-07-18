package client.view.controller;

import animatefx.animation.Wobble;
import client.controller.menues.menuhandlers.menucontrollers.ImportExportMenuController;
import client.model.enums.Menu;
import client.view.ClickButtonHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ImportExportPageView {
    public Button importButton;
    public Button exportButton;
    private final ImportExportMenuController controller;
    public Button back;

    {
        controller = new ImportExportMenuController();
    }

    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == importButton) {
            controller.moveToPage(importButton, Menu.IMPORT);
        } else if (event.getSource() == exportButton) {
            controller.moveToPage(exportButton, Menu.EXPORT);
        } else if (event.getSource() == back) {
            controller.moveToPage(back, Menu.MAIN_MENU);
        }
    }

    public void hoverAnimation(MouseEvent event) {
        ClickButtonHandler.getInstance().play();
        new Wobble((Node) event.getSource()).play();
    }
}
