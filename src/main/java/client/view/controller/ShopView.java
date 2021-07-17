package client.view.controller;

import animatefx.animation.*;
import client.controller.menues.menuhandlers.menucontrollers.ShopMenuController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import client.model.cards.CardHouse;
import connector.cards.Card;
import connector.cards.MagicCard;
import connector.cards.MonsterCard;
import client.model.enums.Menu;
import client.model.enums.Origin;
import client.model.userProp.LoginUser;
import client.model.userProp.User;
import server.ServerCardCollection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ShopView {

    public AnchorPane gridPaneBGPane;
    public Label numberOfSlide;
    public Label message;
    public ImageView selectedCardImageView;
    public Label cardDescription;
    public Button buyButton;
    public Label numberOfCards;
    public Button prevButton;
    public Button nextButton;
    public Label balanceOfUser;
    public Label price;
    public Button back;

    protected ArrayList<GridPane> slidesOfShopCards;
    protected int currentSlideNum;
    protected GridPane shownOnStage;
    protected Card selectedCard;

    @FXML
    public void initialize() {
        selectedCard = null;
        currentSlideNum = 0;

        balanceOfUser.setText(String.valueOf(LoginUser.getUser().getBalance()));

        buyButton.setDisable(true);

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

    public void run(MouseEvent event) {
        if (event.getSource() == buyButton) {
            buy();
        } else if (event.getSource() == prevButton) {
            previousSlide();
        } else if (event.getSource() == nextButton) {
            nextSlide();
        } else if (event.getSource() == back) {
            try {
                ShopMenuController.getInstance().moveToPage(back, Menu.MAIN_MENU);
            } catch (IOException ignored) {
            }
        }
    }

    public void drawSlides() {
        slidesOfShopCards = new ArrayList<>();
        //TODO change to response
        ArrayList<Card> cards = ShopMenuController.cardNameAlphabetSorter(ServerCardCollection.getCards());
        int numOfSlides = (cards.size() / 15);
        if (cards.size() % 15 != 0) numOfSlides++; // for now because of additional not used cards there is one empty page left empty!

        for (int i = 0; i < numOfSlides; i++) { // Creating Gridpane
            slidesOfShopCards.add(new GridPane());
        }

        for (int i = 0; i < numOfSlides; i++) {
            GridPane current = slidesOfShopCards.get(i);
            current.setPrefWidth(521);
            current.setPrefHeight(438);

            outer:
            for (int j = 0; j < 3; j++) { // this 3*5 loop is for one page (3 rows & 5 columns) -> j:row & k:column
               for (int k = 0; k < 5; k++) {
                   if((15 * i) + (j * 5) + k == cards.size()) break outer; // configuring last slide!

                   Card card = cards.get((15 * i) + (j * 5) + k);

                   Image image;
                   try {
                       image = getImageOfCard(card);
                   } catch (FileNotFoundException ignored) {
                       cards.remove(card);
                       k--;
                       continue;
                   }


                   ImageView imageView = new ImageView(image);
                   CardHouse cardHouse = new CardHouse(card, imageView, image, Origin.SHOP_MENU);// creating a wrapper for pic and related card!

                   setSizeAndCoordinates(k, imageView);

                   imageView.setStyle("-fx-cursor: hand");

                   handleMouseEnteredEvent(imageView);

                   handleMouseExitedEvent(imageView);

                   handleMouseOnClickEvent(imageView, cardHouse);

                   slidesOfShopCards.get(i).add(imageView, k, j);
               }
            }
        }
    }

    private void setSizeAndCoordinates(int k, ImageView imageView) {
        imageView.setFitWidth(98);
        imageView.setFitHeight(140);
        imageView.setX((imageView.getFitWidth() + 5) * k + 5);
        imageView.setY((imageView.getFitHeight() + 4.5) * k + 4.5);
    }

    protected void handleMouseOnClickEvent(ImageView imageView, CardHouse cardHouse) {
        imageView.setOnMouseClicked(mouseEvent -> {
            selectedCardImageView.setImage(cardHouse.getImage());
            new Tada(imageView).play();
            new FlipInX(selectedCardImageView).play();

            selectedCard = cardHouse.getCard();

            cardDescription.setText(selectedCard.getCardDetailWithEnters());
            new BounceInUp(cardDescription).play();

            price.setText(String.valueOf(selectedCard.getPrice()));
            new FlipInY(price).play();

            User loggedInUser = LoginUser.getUser();
            buyButton.setDisable(selectedCard.getPrice() > loggedInUser.getBalance());

            int count = 0;
            count = ShopMenuController.countCardInUserProperties(loggedInUser, count, selectedCard);
            if (count != 0) {
                numberOfCards.setText("You have " + count + " number of this card");
            } else {
                numberOfCards.setText("You don't have any of this card");
            }
            new FadeInUp(numberOfCards).play();

        });
    }

    private void handleMouseExitedEvent(ImageView imageView) {
        imageView.setOnMouseExited(mouseEvent -> {
            imageView.setScaleX(1);
            imageView.setScaleY(1);

            imageView.setEffect(null);
        });
    }

    private void handleMouseEnteredEvent(ImageView imageView) {
        imageView.setOnMouseEntered(mouseEvent -> {
            imageView.setScaleX(1.1);
            imageView.setScaleY(1.1);

            DropShadow dropShadow = new DropShadow();
            dropShadow.setWidth(imageView.getFitWidth());
            dropShadow.setHeight(imageView.getFitHeight());
            imageView.setEffect(dropShadow);

            imageView.toFront();

        });
    }

    private Image getImageOfCard(Card card) throws FileNotFoundException {
        String name = card.getName();
        String nameWithoutSpace = name.replaceAll("\\s+", "");
        Image image = null;
        FileInputStream fileInputStream;
        try {
            if (card instanceof MonsterCard) {
                fileInputStream = new FileInputStream("src/main/resources/graphicprop/images/Cards/Monsters/" + nameWithoutSpace + ".jpg");
                image = new Image(fileInputStream);
            } else if (card instanceof MagicCard) {
                fileInputStream = new FileInputStream("src/main/resources/graphicprop/images/Cards/SpellTrap/" + nameWithoutSpace + ".jpg");
                image = new Image(fileInputStream);
            }
        } catch (FileNotFoundException e) {
            if (card instanceof MonsterCard) {
                fileInputStream = new FileInputStream("src/main/resources/graphicprop/images/Cards/Monsters/newMonster.jpg");
            } else {
                fileInputStream = new FileInputStream("src/main/resources/graphicprop/images/Cards/SpellTrap/newMagic.jpg");
            }
            image = new Image(fileInputStream);
        }
        return image;
    }

    public void nextSlide() {

        if (currentSlideNum == slidesOfShopCards.size() - 1) message.setText("This is the last slide!");
        else {
            new FadeOutDown(message);
            message.setText("");

            gridPaneBGPane.getChildren().remove(shownOnStage);
            new FadeOutLeft(gridPaneBGPane).play();

            shownOnStage = slidesOfShopCards.get(currentSlideNum + 1);
            gridPaneBGPane.getChildren().add(shownOnStage);
            new FadeInLeft(gridPaneBGPane).play();

            currentSlideNum++;
            numberOfSlide.setText(String.valueOf(currentSlideNum + 1));

            new SlideOutUp(numberOfSlide).play();
            new SlideInUp(numberOfSlide).play();
        }
    }

    public void previousSlide() {
        if (currentSlideNum == 0) message.setText("This is the first slide!");
        else {
            new FadeOutDown(message);
            message.setText("");

            gridPaneBGPane.getChildren().remove(shownOnStage);
            new FadeOutRight(gridPaneBGPane).play();

            shownOnStage = slidesOfShopCards.get(currentSlideNum - 1);
            gridPaneBGPane.getChildren().add(shownOnStage);
            new FadeInRight(gridPaneBGPane).play();

            currentSlideNum--;
            numberOfSlide.setText(String.valueOf(currentSlideNum + 1));

            new SlideOutDown(numberOfSlide).play();
            new SlideInDown(numberOfSlide).play();
        }
    }

    public void buy() {
        User loggedInUser = LoginUser.getUser();
        ShopMenuController.buyCard(selectedCard);

        balanceOfUser.setText(String.valueOf(loggedInUser.getBalance()));
        new FadeInDown(balanceOfUser).play();

        numberOfCards.setText("You have " + ShopMenuController.countCardInUserProperties(loggedInUser, 0, selectedCard) + " number of this card");
        new FadeInLeft(numberOfCards).play();

        buyButton.setDisable(loggedInUser.getBalance() < selectedCard.getPrice());

    }
}
