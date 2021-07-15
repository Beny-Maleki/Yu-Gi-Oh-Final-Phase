package client.view.controller;

import animatefx.animation.*;
import client.controller.menues.menuhandlers.menucontrollers.ImportExportMenuController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import client.model.cards.CardHouse;
import client.model.enums.Menu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExportPageView extends ShopView{
    public AnchorPane gridPaneBGPane;
    public Label numberOfSlide;
    public Label message;
    public ImageView selectedCardImageView;
    public Label cardDescription;
    public Button prevButton;
    public Button nextButton;
    public Button back;
    public Button exportCard;

    private final ImportExportMenuController controller;

    {
        controller = new ImportExportMenuController();
    }

    @FXML
    public void initialize() {
        selectedCard = null;
        currentSlideNum = 0;

        drawSlides();

        try {
            selectedCardImageView.
                    setImage(new Image(new FileInputStream
                            ("src/main/resources/graphicprop/images/Cards/Monsters/Unknown.jpg")));
        } catch (FileNotFoundException ignored) {
        }

        numberOfSlide.setText("1");

        shownOnStage = slidesOfShopCards.get(0);
        gridPaneBGPane.getChildren().add(shownOnStage);
    }

    @Override
    public void run(MouseEvent event) {
        if (event.getSource() == prevButton) {
            previousSlide();
        } else if (event.getSource() == nextButton) {
            nextSlide();
        } else if (event.getSource() == back) {
            try {
                controller.moveToPage(back, Menu.IMPORT_EXPORT);
            } catch (IOException ignored) {
            }
        } else if (event.getSource() == exportCard) {
            controller.exportCard(selectedCard, message);
        }
    }

    @Override
    protected void handleMouseOnClickEvent(ImageView imageView, CardHouse cardHouse) {
        imageView.setOnMouseClicked(mouseEvent -> {
            selectedCardImageView.setImage(cardHouse.getImage());
            new Tada(imageView).play();
            new FlipInX(selectedCardImageView).play();

            selectedCard = cardHouse.getCard();

            cardDescription.setText(selectedCard.getCardDetailWithEnters());
            new BounceInUp(cardDescription).play();

        });
    }
}
