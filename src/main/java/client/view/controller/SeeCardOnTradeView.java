package client.view.controller;

import animatefx.animation.BounceInDown;
import animatefx.animation.FadeIn;
import client.controller.menues.menuhandlers.menucontrollers.TradePageController;
import client.model.userProp.LoginUser;
import connector.CardForTrade;
import connector.cards.Card;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SeeCardOnTradeView {
    private final TradePageController controller;
    private final Timeline timeline = new Timeline();
    public ScrollPane scrollPane;
    public VBox container;
    private ArrayList<CardForTrade> cardsOnTrade;

    {
        cardsOnTrade = new ArrayList<>();
        controller = new TradePageController();
    }

    @FXML
    public void initialize() {
//        autoUpdatePane();
        makeAPageForEachRequest(cardsOnTrade);
    }

    private void makeAPageForEachRequest(ArrayList<CardForTrade> cardForTrades) {

        cardForTrades.forEach(cardForTrade -> {
            if (cardForTrade.getUser() != LoginUser.getUser()) {
                HBox hBox = new HBox();
                setStyleForRequestBox(hBox);
                Pane userInfo = new Pane();
                setUserInfoPaneStyle(userInfo);
                addUserInfoToPane(cardForTrade, userInfo);
                Pane cardOnTradeDetail = new Pane();
                setSizeForCardDetailPane(cardOnTradeDetail);
                addDecorationImages(cardOnTradeDetail);
                addTradedCardImage(cardForTrade, cardOnTradeDetail);
                addNumberOfCardLabel(cardForTrade, cardOnTradeDetail);
                Pane requestPane = new Pane();
                addRequestButton(requestPane);
                hBox.getChildren().addAll(userInfo, cardOnTradeDetail, requestPane);
                container.getChildren().add(hBox);
                FadeIn fadeIn = new FadeIn(hBox);
                fadeIn.play();
            }
        });
    }

    private void addRequestButton(Pane requestPane) {
        Button requestButton = new Button("Request");
        requestButton.setLayoutX(32);
        requestButton.setLayoutY(12);
        requestButton.setPrefWidth(72);
        requestButton.setPrefHeight(25);
        requestPane.getChildren().add(requestButton);
        requestButton.setOnMouseClicked(event -> {
            //Open request Page
        });
    }

    private void addNumberOfCardLabel(CardForTrade cardForTrade, Pane cardOnTradeDetail) {
        Label theNumberOfCard = new Label(cardForTrade.getNumberOfCard() + "X");
        theNumberOfCard.setStyle("-fx-background-radius: 200");
        theNumberOfCard.setPrefWidth(25);
        theNumberOfCard.setPrefHeight(25);
        theNumberOfCard.setLayoutY(12);
        theNumberOfCard.setLayoutX(120);
        cardOnTradeDetail.getChildren().add(theNumberOfCard);
    }

    private void addTradedCardImage(CardForTrade cardForTrade, Pane cardOnTradeDetail) {
        ImageView cardOnTradeImageView = new ImageView(Card.getCardImage(cardForTrade.getCardName()));
        setSizeForCardOnTrade(cardOnTradeImageView);
        cardOnTradeDetail.getChildren().add(cardOnTradeImageView);
    }

    private void addDecorationImages(Pane cardOnTradeDetail) {
        for (int i = 0; i < 4; i++) {
            ImageView imageView = new ImageView();
            setDefaultPicForCard(imageView);
            imageView.setLayoutY(4);
            imageView.setLayoutX(7 * (i + 1));
            imageView.setFitWidth(34);
            imageView.setFitHeight(41);
            cardOnTradeDetail.getChildren().add(imageView);
        }
    }

    private void setSizeForCardOnTrade(ImageView cardOnTradeImageView) {
        cardOnTradeImageView.setLayoutX(81);
        cardOnTradeImageView.setLayoutY(4);
        cardOnTradeImageView.setFitHeight(41);
        cardOnTradeImageView.setFitWidth(34);
    }

    private void setDefaultPicForCard(ImageView imageView) {
        try {
            imageView.
                    setImage(new Image(new FileInputStream
                            ("src/main/resources/graphicprop/images/backOfCard.jpg")));
            new BounceInDown(imageView).play();
        } catch (FileNotFoundException ignored) {
        }

    }

    private void setSizeForCardDetailPane(Pane cardOnTradeDetail) {
        cardOnTradeDetail.setPrefHeight(48);
        cardOnTradeDetail.setPrefWidth(147);
    }

    private void addUserInfoToPane(CardForTrade cardForTrade, Pane userInfo) {
        ImageView avatar = null;
        try {
            avatar = new ImageView(new Image(new FileInputStream(cardForTrade.getUser().getAvatarAddress())));
            avatar.setFitHeight(48);
            avatar.setFitWidth(48);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Label userNickname = new Label(cardForTrade.getUser().getNickname());
        userNickname.setPrefWidth(80);
        userNickname.setPrefHeight(20);
        userNickname.setStyle("-fx-background-radius: 20");
        userNickname.setLayoutX(80);
        userNickname.setLayoutY(13);
        userInfo.getChildren().addAll(avatar, userNickname);
    }

    private void setUserInfoPaneStyle(Pane userInfo) {
        userInfo.setPrefWidth(178);
        userInfo.setPrefHeight(48);
    }

    private void setStyleForRequestBox(HBox hBox) {
        hBox.setStyle("-fx-background-color: black; -fx-background-radius: 80");
        hBox.setPrefWidth(400);
        hBox.setPrefHeight(48);
    }

//    private void autoUpdatePane() {
//        timeline.getKeyFrames().add(new KeyFrame(
//                Duration.millis(5000), event -> {
//            ArrayList<CardForTrade> cards = controller.getNewCardOnTrade(cardsOnTrade);
//            if (cards == null) timeline.stop();
//            else {
//                makeAPageForEachRequest(cards);
//                cardsOnTrade = UserDataBase.getInstance().getCardsForTrade();
//            }
//        }
//        ));
//        timeline.setCycleCount(Animation.INDEFINITE);
//        timeline.play();
//    }

//    public void stopThread() {
//        timeline.stop();
//    }
}
