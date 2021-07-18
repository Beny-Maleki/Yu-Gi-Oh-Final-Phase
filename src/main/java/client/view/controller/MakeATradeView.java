package client.view.controller;

import animatefx.animation.BounceIn;
import animatefx.animation.BounceInUp;
import animatefx.animation.FlipInX;
import animatefx.animation.Tada;
import client.model.cards.CardHouse;
import client.model.enums.Origin;
import client.model.userProp.LoginUser;
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
import java.util.HashMap;

public class MakeATradeView {
    public Label selectedCardDescriptionLabel;
    public Label numberOfTradedCard;
    public ImageView selectedCardImageView;
    public ScrollPane collectionScrollPane;
    public Label numberOfCardInCollection;
    HashMap<String, Integer> cardAndNumberHashMap;
    private Card selectedCard;
    private ArrayList<Card> collection;

    {
        collection = LoginUser.getUser().getCardCollection();
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
        ArrayList<Card> collection = getCollectionWithOutSimilarCard();
        FlowPane collectionFlowPane = new FlowPane();
        collectionFlowPane.setStyle("-fx-background-color: #62260c; -fx-focus-color : transparent");
        collectionScrollPane.setStyle("-fx-background-color: rgba(71,60,60,0.4)");
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
            numberOfCardInCollection.setText("You Have " + cardAndNumberHashMap.get(selectedCard.getName()) + " of this card in your collection");
            new BounceInUp(selectedCardDescriptionLabel).play();
            new BounceIn(numberOfCardInCollection).play();
        });
    }

    private void collectionCardSlotStyler(int i, ImageView imageView) {
        imageView.setFitHeight(160 * 0.7);
        imageView.setFitWidth(109 * 0.7);
        imageView.setStyle("-fx-cursor: hand");
    }


    private CardHouse makeCardHouseAndAssignImage(ImageView imageView, Card card) {
        Image image = Card.getCardImage(card);
        imageView.setImage(image);
        return new CardHouse(card, imageView, image, Origin.DECK_MENU);
    }

    private ArrayList<Card> getCollectionWithOutSimilarCard() {
        cardAndNumberHashMap = new HashMap<>();
        ArrayList<Card> collectionWithOutSimilarCard = new ArrayList<>();
        for (Card card : collection) {
            if (!cardAndNumberHashMap.containsKey(card.getName())) {
                cardAndNumberHashMap.put(card.getName(), 1);
                collectionWithOutSimilarCard.add(card);
            } else {
                cardAndNumberHashMap.put(card.getName(), cardAndNumberHashMap.get(card.getName()) + 1);
            }
        }
        return collectionWithOutSimilarCard;
    }
}