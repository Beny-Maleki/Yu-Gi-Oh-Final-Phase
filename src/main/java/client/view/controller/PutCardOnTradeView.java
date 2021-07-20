package client.view.controller;

import animatefx.animation.*;
import client.controller.menues.menuhandlers.menucontrollers.TradePageController;
import client.model.cards.CardHouse;
import client.model.enums.Origin;
import client.model.userProp.LoginUser;
import connector.cards.Card;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Polygon;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class PutCardOnTradeView {
    private final TradePageController controller;
    public Label selectedCardDescriptionLabel;
    public Label numberOfTradedCard;
    public ImageView selectedCardImageView;
    public ScrollPane collectionScrollPane;
    public Label numberOfCardInCollection;
    public Polygon increaseNumberBut;
    public Button tradeButt;
    public Polygon decreaseNumberBut;
    HashMap<String, Integer> cardAndNumberHashMap;
    private Card selectedCard;
    private ArrayList<Card> collection;
    private int numberOfTradingCard;

    {
        collection = LoginUser.getUser().getCardCollection();
        controller = new TradePageController();
        numberOfTradingCard = 1;
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
        setDefaultPicForCard();
        initializeScrollBar();
        initializeCardNumberControllerButton();
        initializeTradeButtonBehavior();
    }

    private void setDefaultPicForCard() {
        try {
            selectedCardImageView.
                    setImage(new Image(new FileInputStream
                            ("src/main/resources/graphicprop/images/backOfCard.jpg")));
            new BounceInDown(selectedCardImageView).play();
        } catch (FileNotFoundException ignored) {
        }

    }

    private void initializeTradeButtonBehavior() {
        tradeButt.setOnMouseClicked(event -> {
            controller.putCardForTrade(numberOfTradingCard, selectedCard.getName());
            updatePage();
        });
    }

    private void updatePage() {
        collection = LoginUser.getUser().getCardCollection();
        initializeScrollBar();
        selectedCard = null;
        FadeOut fadeOut = runFadeOutAnimation();
        fadeOut.getTimeline().setOnFinished(event -> setPagePropertyVisibility(false));
        fadeOut.play();
        setDefaultPicForCard();
        initializeCardNumberControllerButton();
    }

    @NotNull
    private FadeOut runFadeOutAnimation() {
        new FadeOut(selectedCardDescriptionLabel).play();
        new FadeOut(tradeButt).play();
        new FadeOut(increaseNumberBut).play();
        new FadeOut(decreaseNumberBut).play();
        new FadeOut(numberOfTradedCard).play();
        FadeOut fadeOut = new FadeOut(numberOfCardInCollection);
        return fadeOut;
    }

    private void setPagePropertyVisibility(boolean state) {
        selectedCardDescriptionLabel.setVisible(state);
        tradeButt.setVisible(state);
        increaseNumberBut.setVisible(state);
        decreaseNumberBut.setVisible(state);
        numberOfTradedCard.setVisible(state);
        numberOfCardInCollection.setVisible(state);
    }

    private void initializeCardNumberControllerButton() {
        increaseNumberBut.setOnMouseClicked(event -> {
            int numberOfCard = cardAndNumberHashMap.get(selectedCard.getName());
            if (numberOfCard > numberOfTradingCard) {
                numberOfTradingCard++;
                numberOfTradedCard.setText(numberOfTradingCard + "X");
            }
        });
        decreaseNumberBut.setOnMouseClicked(event -> {
            if (numberOfTradingCard != 1) {
                numberOfTradingCard--;
                numberOfTradedCard.setText(numberOfTradingCard + "X");
            }
        });
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

            collectionCardSlotStyler(imageView);

            CardHouse cardHouse = makeCardHouseAndAssignImage(imageView, card);

            handleOnMouseClick(imageView, cardHouse);

            collectionFlowPane.getChildren().add(imageView);
            new FadeInUp(imageView).play();
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
            numberOfTradingCard = 1;
            numberOfTradedCard.setText("1X");
            new FadeIn(tradeButt).play();
            setPagePropertyVisibility(true);

            selectedCardImageView.setImage(cardHouse.getImage());
            new Tada(imageView).play();
            new FlipInX(selectedCardImageView).play();

            selectedCard = cardHouse.getCard();

            selectedCardDescriptionLabel.setText(selectedCard.getCardDetailWithEnters());
            numberOfCardInCollection.setText("You Have (" + cardAndNumberHashMap.get(selectedCard.getName()) + ") of this card in your collection");
            new BounceInUp(selectedCardDescriptionLabel).play();
            new BounceInLeft(numberOfCardInCollection).play();
        });
    }

    private void collectionCardSlotStyler(ImageView imageView) {
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