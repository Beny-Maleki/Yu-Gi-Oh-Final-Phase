package client.view.controller;

import animatefx.animation.*;
import client.controller.menues.menuhandlers.menucontrollers.DeckModifierController;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import client.model.Exceptions.EmptyTextFieldException;
import client.model.enums.Menu;
import client.model.userProp.Deck;
import client.model.userProp.LoginUser;
import client.model.userProp.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class DecksView {
    private static final ArrayList<Node> DECK_NOTIFICATION;
    private static final User USER;

    static {
        USER = LoginUser.getUser();
        DECK_NOTIFICATION = new ArrayList<>();
    }

    public Pane DeckOneHolder;
    public Pane DeckTwoHolder;
    public Pane DeckFourHolder;
    public Pane DeckThreeHolder;
    public Pane DescriptionArea;
    public Button backButton;
    private ArrayList<Pane> deckHolders;
    private final DeckModifierController controller;

    {
        controller = new DeckModifierController();
    }

    @FXML
    public void initialize() throws FileNotFoundException {
        deckHolders =
                new ArrayList<>(Arrays.asList(DeckOneHolder, DeckTwoHolder, DeckThreeHolder, DeckFourHolder));

        deckHolders.forEach(this::handleMouseEnteredEvent);
        deckHolders.forEach(this::handleMouseExitedEvent);
        deckHolders.forEach(this::handleMouseClickEvent);
        ArrayList<Boolean> unlockedDeckHolders = USER.getUnlockedDeckHolders();
        for (int i = 0; i < unlockedDeckHolders.size(); i++) {
            if (!unlockedDeckHolders.get(i)) {
                FileInputStream fileInputStream = new FileInputStream("src/main/resources/graphicprop/images/LockIcon.png");
                Image image = new Image(fileInputStream);
                ImageView imageView = new ImageView();
                imageView.setImage(image);
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
                imageView.setLayoutX(55);
                imageView.setLayoutY(90);
                deckHolders.get(i).setStyle("-fx-background-color : #a9a8a8; -fx-background-radius: 15; -fx-opacity: 0.75");
                deckHolders.get(i).getChildren().add(imageView);
            } else if (USER.getAllUserDecksId().get(i) != null) {
                showPackOfCards(i);
            }
        }


    }

    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == backButton) {
            controller.moveToPage(backButton, Menu.MAIN_MENU);
        }
    }

    private void showPackOfCards(int i) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/graphicprop/images/packOfCard.png");
        Image image = new Image(fileInputStream);
        ImageView imageView = new ImageView(image);
        imageView.setLayoutY(11);
        imageView.setFitWidth(241);
        imageView.setFitHeight(270);
        new RotateIn(imageView).play();
        deckHolders.get(i).getChildren().add(imageView);
    }

    private void handleMouseExitedEvent(Pane pane) {
        pane.setOnMouseExited(mouseEvent -> {
            pane.setScaleX(1);
            pane.setScaleY(1);
            pane.setEffect(null);
        });
    }

    private void handleMouseEnteredEvent(Pane pane) {
        pane.setOnMouseEntered(mouseEvent -> {
            pane.setScaleX(1.05);
            pane.setScaleY(1.105);

            DropShadow dropShadow = new DropShadow();
            dropShadow.setWidth(pane.getWidth());
            dropShadow.setHeight(pane.getHeight());
            pane.setEffect(dropShadow);

            pane.toFront();

        });
    }

    private void handleMouseClickEvent(Pane pane) {
        pane.setOnMouseClicked(mouseEvent -> {
            ArrayList<Boolean> unlockedDeckHolders = LoginUser.getUser().getUnlockedDeckHolders();
            ArrayList<Deck> allUserDeck = USER.getAllUserDecksId();
            removeNotification();
            switch (pane.getId()) {
                case "DeckOneHolder": {
                    if (allUserDeck.get(0) == null) {
                        emptyHolder(0);
                    } else {
                        Deck deck = USER.getAllUserDecksId().get(0);
                        try {
                            fullHolder(deck, 0);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case "DeckTwoHolder": {
                    if (!unlockedDeckHolders.get(1)) {
                        lockedHolder(1);
                    } else if (allUserDeck.get(1) == null) {
                        emptyHolder(1);
                    } else {
                        Deck deck = USER.getAllUserDecksId().get(1);
                        try {
                            fullHolder(deck, 1);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case "DeckThreeHolder": {
                    if (!unlockedDeckHolders.get(2)) {
                        lockedHolder(2);
                    } else if (allUserDeck.get(2) == null) {
                        emptyHolder(2);
                    } else {
                        Deck deck = USER.getAllUserDecksId().get(2);
                        try {
                            fullHolder(deck, 2);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case "DeckFourHolder": {
                    if (!unlockedDeckHolders.get(3)) {
                        lockedHolder(3);
                    } else if (allUserDeck.get(3) == null) {
                        emptyHolder(3);
                    } else {
                        Deck deck = USER.getAllUserDecksId().get(3);
                        try {
                            fullHolder(deck, 3);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
            }
        });
    }

    private void lockedHolder(int i) {
        Label lockMessage = new Label("The Deck Holder is Locked");
        lockMessage.setLayoutX(27);
        lockMessage.setLayoutY(112);
        lockMessage.setPrefWidth(191);
        lockMessage.setPrefHeight(68);
        lockMessage.setStyle("-fx-font-size: 15 ; -fx-text-fill: white; -fx-alignment: center");
        DECK_NOTIFICATION.add(lockMessage);

        Label priceMessage = new Label("PRICE : " + i * 1000);
        priceMessage.setLayoutX(34);
        priceMessage.setLayoutY(201);
        priceMessage.setPrefWidth(165);
        priceMessage.setPrefHeight(17);
        priceMessage.setStyle("-fx-font-size: 15 ; -fx-text-fill: white; -fx-alignment: center");
        DECK_NOTIFICATION.add(priceMessage);

        Button unlockButton = new Button("Unlock Holder");
        setNodesPosition(unlockButton, 27, 290, 179, 25);
        styleButton(unlockButton);
        DECK_NOTIFICATION.add(unlockButton);
        setActionForUnlockButton(i, unlockButton);
        new BounceIn(lockMessage).play();
        new BounceIn(priceMessage).play();
        new BounceIn(unlockButton).play();

        DescriptionArea.getChildren().addAll(DECK_NOTIFICATION);
    }

    private void setActionForUnlockButton(int i, Button unlockButton) {
        unlockButton.setOnMouseClicked(event -> {
            if (USER.getBalance() >= i * 1000) {
                USER.changeBalance(i * 1000);
                USER.unlockDeckHolder(i);
                removeNotification();
                emptyHolder(i);
                deckHolders.get(i).getChildren().forEach(node -> {
                    FadeOut fadeOut = new FadeOut(node);
                    fadeOut.setSpeed(0.5);
                    fadeOut.getTimeline().setOnFinished(event1 -> deckHolders.get(i).getChildren().remove(node));
                    fadeOut.play();
                });
                changeColorOfBackGround(deckHolders.get(i));
                deckHolders.get(i).setStyle("-fx-opacity: 1");
            } else {
                Label label = new Label("Not enough money");
                label.setStyle("-fx-text-fill: #f3ff00; -fx-alignment: center");
                Flash flash = new Flash(label);
                flash.setCycleCount(6);
                flash.setSpeed(0.5);
                flash.play();
                setNodesPosition(label, 27, 270, 179, 25);
                DECK_NOTIFICATION.add(label);
                DescriptionArea.getChildren().add(label);
            }
        });
    }

    private void changeColorOfBackGround(Pane holder) {
        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(1000));
                setInterpolator(Interpolator.EASE_OUT);
            }

            @Override
            protected void interpolate(double frac) {
                Color paneColor = new Color(0.66, 0.66, 0.66, 1 - frac);
                holder.setBackground(new Background(new BackgroundFill(paneColor, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        };
        animation.play();
    }

    private void styleButton(Button button) {
        button.setStyle("-fx-text-fill: #A61210; -fx-background-radius: 15; -fx-focus-color : transparent");
        button.setOnMouseEntered(event ->
                button.setStyle("-fx-background-color: #A61210; -fx-background-radius: 15; -fx-text-fill: white; -fx-focus-color : transparent"));
        button.setOnMouseExited(event -> {
            button.setStyle("-fx-text-fill: #A61210; -fx-background-radius: 15; -fx-focus-color : transparent");
            new FlipInX(button).play();
        });
    }

    private void setNodesPosition(Node node, int xLayout, int yLayout, int width, int height) {
        node.setLayoutX(xLayout);
        node.setLayoutY(yLayout);
        if (node instanceof Label) {
            ((Label) node).setPrefHeight(height);
            ((Label) node).setPrefWidth(width);
        } else if (node instanceof Button) {
            ((Button) node).setPrefHeight(height);
            ((Button) node).setPrefWidth(width);
        } else if (node instanceof TextField) {
            ((TextField) node).setPrefHeight(height);
            ((TextField) node).setPrefWidth(width);
        }
    }

    public void emptyHolder(int i) {
        Label emptyHolderMessage = new Label("Deck Holder is Empty");
        setNodesPosition(emptyHolderMessage, 27, 112, 191, 68);
        emptyHolderMessage.setStyle("-fx-font-size: 15 ; -fx-text-fill: white; -fx-alignment: center");
        DECK_NOTIFICATION.add(emptyHolderMessage);

        Button createNewDeckButton = new Button("Create New Deck");
        setNodesPosition(createNewDeckButton, 27, 190, 179, 25);
        styleButton(createNewDeckButton);
        DECK_NOTIFICATION.add(createNewDeckButton);

        createNewDeckButton.setOnMouseClicked(event -> {
            new FadeOut(createNewDeckButton).play();
            TextField deckNameTextField = new TextField();
            new BounceIn(deckNameTextField).play();
            deckNameTextField.setPromptText("Deck Name");
            setNodesPosition(deckNameTextField, 27, 190, 180, 25);
            deckNameTextField.setStyle("-fx-text-fill: red; -fx-alignment: center; -fx-background-radius: 15; -fx-focus-color: transparent");
            DECK_NOTIFICATION.add(deckNameTextField);
            DescriptionArea.getChildren().add(deckNameTextField);
            Button makeDeckButton = new Button("Make");
            new BounceIn(makeDeckButton).play();
            setNodesPosition(makeDeckButton, 27, 230, 180, 25);
            styleButton(makeDeckButton);
            DECK_NOTIFICATION.add(makeDeckButton);
            DescriptionArea.getChildren().add(makeDeckButton);
            makeDeckButton.setOnMouseClicked(event1 -> {
                try {
                    if (deckNameTextField.getText().equals("")) throw new EmptyTextFieldException();
                    Deck newDeck = new Deck(deckNameTextField.getText());
                    USER.addDeckId(newDeck.getID(), i);
                    removeNotification();
                    showPackOfCards(i);
                    fullHolder(newDeck, i);
                } catch (EmptyTextFieldException e) {

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        });

        new BounceIn(emptyHolderMessage).play();
        new BounceIn(createNewDeckButton).play();
        DescriptionArea.getChildren().addAll(DECK_NOTIFICATION);
    }

    public void fullHolder(Deck deck, int i) throws FileNotFoundException {
        Label deckName = new Label("Deck Name: " + deck.getName());
        setNodesPosition(deckName, 0, 74, 300, 21);
        deckName.setStyle("-fx-font-size: 15 ; -fx-text-fill: white; -fx-alignment: center");
        new BounceIn(deckName).play();
        DECK_NOTIFICATION.add(deckName);

        ArrayList<ImageView> cards = new ArrayList<>();
        for (int j = 0; j < 4; j++) {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/graphicprop/images/Cards/Monsters/Unknown.jpg");
            Image image = new Image(fileInputStream);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(75);
            imageView.setFitWidth(52);
            cards.add(imageView);
        }

        cards.get(0).setLayoutX(9);
        cards.get(0).setLayoutY(99);

        new RollIn(cards.get(0)).play();

        cards.get(1).setLayoutX(19);
        cards.get(1).setLayoutY(116);

        new RollIn(cards.get(1)).play();

        cards.get(2).setLayoutX(28);
        cards.get(2).setLayoutY(136);

        new RollIn(cards.get(2)).play();

        cards.get(3).setLayoutX(37);
        cards.get(3).setLayoutY(154);

        new RollIn(cards.get(3)).play();

        Label mainDeckSize = new Label("client.Main Deck size: " + deck.getMainDeck().size());
        setNodesPosition(mainDeckSize, 121, 128, 106, 17);
        mainDeckSize.setPrefWidth(Region.USE_COMPUTED_SIZE);
        mainDeckSize.setStyle("-fx-font-size: 12 ; -fx-text-fill: white");
        new BounceIn(mainDeckSize).play();
        DECK_NOTIFICATION.add(mainDeckSize);

        Label sideDeckSize = new Label("Side Deck size: " + deck.getSideDeck().size());
        setNodesPosition(sideDeckSize, 121, 165, 106, 17);
        sideDeckSize.setStyle("-fx-font-size: 12 ; -fx-text-fill: white");
        new BounceIn(sideDeckSize).play();
        DECK_NOTIFICATION.add(sideDeckSize);

        Label isActive = new Label();
        setNodesPosition(isActive, 121, 205, 100, 17);
        if (USER.getActiveDeck() == deck) {
            isActive.setText("deck is active");
            isActive.setStyle("-fx-font-size: 12 ; -fx-text-fill: #00ff08");
        } else {
            isActive.setText("deck is not active");
            isActive.setStyle("-fx-font-size: 12 ; -fx-text-fill: #fffe00");
        }

        DECK_NOTIFICATION.add(isActive);

        Flash flash = new Flash(isActive);
        flash.setSpeed(0.5);
        flash.setCycleCount(AnimationFX.INDEFINITE);
        flash.play();

        Label isDeckValid = new Label();
        setNodesPosition(isDeckValid, 18, 245, 110, 17);
        if (deck.getValidity()) {
            isDeckValid.setText("deck is valid");
            isDeckValid.setStyle("-fx-font-size: 12 ; -fx-text-fill: #00ff08");
        } else {
            isDeckValid.setText("deck is not valid");
            isDeckValid.setStyle("-fx-font-size: 12 ; -fx-text-fill: #fffe00");
        }
        DECK_NOTIFICATION.add(isDeckValid);
        new BounceIn(isDeckValid).play();

        Button removeDeckButton = new Button("Remove Deck");
        setNodesPosition(removeDeckButton, 18, 286, 91, 25);
        styleButton(removeDeckButton);
        DECK_NOTIFICATION.add(removeDeckButton);
        removeDeckButton.setOnMouseClicked(event -> {
            USER.removeDeck(i);

            deck.getMainDeck().forEach(USER::addCard);
            deck.getSideDeck().forEach(USER::addCard);

            deckHolders.get(i).getChildren().forEach(node -> new FadeOut(node).play());
            removeNotification();
            emptyHolder(i);
        });
        new BounceIn(removeDeckButton).play();

        Button modifyButton = new Button("Modify Deck");
        setNodesPosition(modifyButton, 130, 286, 88, 25);
        styleButton(modifyButton);
        DECK_NOTIFICATION.add(modifyButton);
        modifyButton.setOnMouseClicked(event -> {
            try {
                USER.setDeckOnModify(deck);
                controller.moveToPage(modifyButton, Menu.DECK_MODIFIER);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        new BounceIn(modifyButton).play();

        Button activeButton = new Button("Active");
        setNodesPosition(activeButton, 92, 324, 55, 25);
        styleButton(activeButton);
        activeButton.setOnMouseExited(event ->
                {
                    activeButton.setText("Active");
                    new FlipInX(activeButton).play();
                    activeButton.setStyle("-fx-text-fill: #A61210; -fx-background-radius: 15; -fx-focus-color : transparent");
                }
        );
        if (!deck.getValidity()) {
            activeButton.setOnMouseEntered(event -> {
                activeButton.setStyle("-fx-background-color: #fab700; -fx-background-radius: 15");
                activeButton.setText("Invalid");
            });
        } else {
            USER.setActiveDeck(deck);
            isActive.setText("deck is active");
            isActive.setStyle("-fx-font-size: 12 ; -fx-text-fill: #00ff08");
        }
        DECK_NOTIFICATION.add(activeButton);

        new BounceIn(activeButton).play();


        DECK_NOTIFICATION.addAll(cards);
        DescriptionArea.getChildren().addAll(DECK_NOTIFICATION);

    }

    private void removeNotification() {
        DescriptionArea.getChildren().forEach(node -> {
            if (DECK_NOTIFICATION.contains(node)) {
                new FadeOutDownBig(node).play();
                DECK_NOTIFICATION.remove(node);
            }
        });
        DECK_NOTIFICATION.clear();
    }
}
