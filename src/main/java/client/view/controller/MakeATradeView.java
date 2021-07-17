package client.view.controller;

import animatefx.animation.BounceInUp;
import animatefx.animation.FlipInX;
import animatefx.animation.Tada;
import client.UserCardCollection;
import client.model.cards.CardHouse;
import client.model.enums.Origin;
import connector.cards.Card;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MakeATradeView {
    public Label selectedCardDescriptionLabel;
    public Label numberOfCard;
    public ImageView selectedCardImageView;
    public ScrollPane collectionScrollPane;
    private Card selectedCard;

    @FXML
    public void initialize() {
        selectedCard = null;

        try {
            selectedCardImageView.
                    setImage(new Image(new FileInputStream
                            ("src/main/resources/graphicprop/images/backOfCard.jpg")));
        } catch (FileNotFoundException ignored) {
        }

        initializeScrollBar();
    }

    private void initializeScrollBar() {
        ArrayList<Card> collection = (ArrayList<Card>) UserCardCollection.getCards();
        FlowPane collectionFlowPane = new FlowPane();

        for (int i = 0; i < collection.size(); i++) {
            Card card = collection.get(i);
            ImageView imageView = new ImageView();

            handleOnMouseEntered(imageView);
            handleOnMouseExited(imageView);

            collectionCardSlotStyler(i, imageView);

            CardHouse cardHouse = makeCardHouseAndAssignImage(imageView, card);

            handleOnMouseClick(imageView, cardHouse);

            collectionFlowPane.getChildren().add(imageView);

        }
        configureScrollPane(collectionFlowPane);
    }

    private void configureScrollPane(FlowPane collectionFlowPane) {
        collectionFlowPane.setMinWidth(1000);
        collectionScrollPane.setContent(collectionFlowPane);
        collectionScrollPane.setPrefViewportHeight(190);
        collectionScrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        collectionScrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private void handleOnMouseEntered(ImageView imageView) {
        setPropertyForImageView(imageView);
    }

    static void setPropertyForImageView(ImageView imageView) {
        imageView.setOnMouseEntered(mouseEvent -> {
            imageView.setScaleX(1.1);
            imageView.setScaleY(1.1);

            DropShadow dropShadow = new DropShadow();
            dropShadow.setWidth(imageView.getFitWidth());
            dropShadow.setHeight(imageView.getFitHeight());
            imageView.setEffect(dropShadow);

        });
    }


    private void handleOnMouseExited(ImageView imageView) {
        imageView.setOnMouseExited(mouseEvent -> {
            imageView.setScaleX(1);
            imageView.setScaleY(1);

            imageView.setEffect(null);
        });
    }

    private void handleOnMouseClick(ImageView imageView, CardHouse cardHouse) {
        imageView.setOnMouseClicked(mouseEvent -> {

            selectedCardImageView.setImage(cardHouse.getImage());
            new Tada(imageView).play();
            new FlipInX(selectedCardImageView).play();

            selectedCard = cardHouse.getCard();

            selectedCardDescriptionLabel.setText(selectedCard.getCardDetailWithEnters());
            new BounceInUp(selectedCardDescriptionLabel).play();
        });
    }

    private void collectionCardSlotStyler(int i, ImageView imageView) {
        imageView.setFitHeight(160);
        imageView.setFitWidth(109);
        imageView.setStyle("-fx-cursor: hand");
    }


    private CardHouse makeCardHouseAndAssignImage(ImageView imageView, Card card) {
        Image image = Card.getCardImage(card);
        imageView.setImage(image);
        return new CardHouse(card, imageView, image, Origin.DECK_MENU);
    }
}