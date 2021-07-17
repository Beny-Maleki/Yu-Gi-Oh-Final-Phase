package client.view.controller;

import animatefx.animation.*;
import client.controller.gamecontrollers.GeneralController;
import client.controller.gamecontrollers.gamestagecontroller.BattlePhaseController;
import client.controller.gamecontrollers.gamestagecontroller.DrawPhaseController;
import client.controller.gamecontrollers.gamestagecontroller.MainPhaseController;
import client.controller.gamecontrollers.gamestagecontroller.StandByPhaseController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import client.model.cards.cardsEnum.Magic.MagicAttribute;
import client.model.cards.cardsEnum.Magic.MagicType;
import connector.cards.Card;
import connector.cards.MagicCard;
import connector.cards.MonsterCard;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.enums.GameEnums.TypeOfHire;
import client.model.enums.GameEnums.cardvisibility.MonsterHouseVisibilityState;
import client.model.enums.GameEnums.gamestage.GameMainStage;
import client.model.gameprop.BoardProp.GameHouse;
import client.model.gameprop.BoardProp.HandHouse;
import client.model.gameprop.BoardProp.MagicHouse;
import client.model.gameprop.BoardProp.MonsterHouse;
import client.model.gameprop.GameInProcess;
import client.model.gameprop.Player;
import client.model.gameprop.gamemodel.Game;
import client.model.userProp.User;
import org.jetbrains.annotations.NotNull;
import client.view.AudioHandler;
import client.view.AudioPath;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class GameView {
    public ImageView yourAvatar;
    public ImageView opponentAvatar;
    public Label opponentUsername;
    public Label opponentNickname;
    public Label yourUsername;
    public Label yourNickname;
    public Pane yourHousesContainer;
    public Pane opponentHousesContainer;
    public GridPane yourMonsterHousesGridPane;
    public GridPane yourMagicHousesGridPane;
    public GridPane opponentMagicHousesGridPane;
    public GridPane opponentMonsterHousesGridPane;
    public FlowPane opponentHandContainer;
    public FlowPane yourHandContainer;
    public Pane yourGraveyardPane;
    public Pane opponentGraveyardPane;
    public Pane yourFieldPane;
    public Pane opponentFieldPane;
    public Pane yourDeckPane;
    public Pane opponentDeckPane;
    public ImageView selectedCardImageView;
    public AnchorPane root;
    public Button nextPhase;
    public Label phaseName;
    public Pane drawPhaseBox;
    public Pane standByPhaseBox;
    public Pane firstMainPhaseBox;
    public Pane battlePhaseBox;
    public Pane secondMainPhaseBox;
    public Pane endPhaseBox;
    public AnchorPane cardDescriptionArea;
    public Label currentDeckNumber;
    public Label opponentDeckNumber;
    public Pane field;
    public Label turnShowerUp;
    public Label turnShowerDown;
    public ProgressBar opponentLPBar;
    public ProgressBar yourLPBar;
    public Label opponentLPLabel;
    public Label yourLPLabel;
    private ImageView summonIcon;
    private ImageView setMonsterIcon;
    private ImageView setMagicIcon;
    private ImageView changePositionIcon;
    private ImageView activeMagicIcon;
    private ImageView attackMonsterIcon;
    private Player playerYou;
    private Player playerOpponent;
    private GeneralController controller;
    private DrawPhaseController drawPhaseController;
    private MainPhaseController mainPhaseController;
    private BattlePhaseController battlePhaseController;
    private StandByPhaseController standByPhaseController;
    private Game game;
    private double prevLPYou;
    private double prevLPOpponent;

    {
        controller = new GeneralController();
        drawPhaseController = new DrawPhaseController();
        mainPhaseController = new MainPhaseController();
        battlePhaseController = new BattlePhaseController();
        standByPhaseController = new StandByPhaseController();
    }

    private void initializeIcons() {
        try {
            setSizeForSummonIcon();
            setSizeForSetMonsterIcon();
            setSizeForSetMagicIcon();
            setSizeForChangePositionIcon();
            setSizeForAttackMonsterIcon();
            setSizeForActiveMagic();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        summonIcon.toFront();
        setMonsterIcon.toFront();
    }

    private void setSizeForChangePositionIcon() throws FileNotFoundException {
        changePositionIcon = new ImageView(new Image(new FileInputStream("src/main/resources/graphicprop/images/changePosition.png")));
        changePositionIcon.setFitHeight(40);
        changePositionIcon.setFitWidth(40);
        changePositionIcon.setLayoutY(25);
        changePositionIcon.setLayoutX(25);
    }

    private void setSizeForAttackMonsterIcon() throws FileNotFoundException {
        attackMonsterIcon = new ImageView(new Image(new FileInputStream("src/main/resources/graphicprop/images/attack.png")));
        attackMonsterIcon.setFitWidth(40);
        attackMonsterIcon.setFitHeight(40);
        attackMonsterIcon.setLayoutY(25);
        attackMonsterIcon.setLayoutX(-5);
    }

    private void setSizeForSetMagicIcon() throws FileNotFoundException {
        setMagicIcon = new ImageView(new Image(new FileInputStream("src/main/resources/graphicprop/images/setMagic.png")));
        setMagicIcon.setFitHeight(40);
        setMagicIcon.setFitWidth(40);
        setMagicIcon.setLayoutY(25);
        setMagicIcon.setLayoutX(30);
    }

    private void setSizeForActiveMagic() throws FileNotFoundException {
        activeMagicIcon = new ImageView(new Image(new FileInputStream("src/main/resources/graphicprop/images/activeEffect.png")));
        activeMagicIcon.setFitWidth(40);
        activeMagicIcon.setFitHeight(40);
        activeMagicIcon.setLayoutY(25);
        activeMagicIcon.setLayoutX(1);
    }

    private void setSizeForSetMonsterIcon() throws FileNotFoundException {
        setMonsterIcon = new ImageView(new Image(new FileInputStream("src/main/resources/graphicprop/images/setMonster.png")));
        setMonsterIcon.setFitHeight(40);
        setMonsterIcon.setFitWidth(40);
        setMonsterIcon.setLayoutY(25);
        setMonsterIcon.setLayoutX(30);
    }

    private void setSizeForSummonIcon() throws FileNotFoundException {
        summonIcon = new ImageView(new Image(new FileInputStream("src/main/resources/graphicprop/images/summon.png")));
        summonIcon.setFitWidth(40);
        summonIcon.setFitHeight(40);
        summonIcon.setLayoutY(25);
        summonIcon.setLayoutX(1);
    }

    @FXML
    public void initialize() throws FileNotFoundException {
        game = GameInProcess.getGame();
        playerYou = GameInProcess.getGame().getFirstPlayer();
        playerOpponent = GameInProcess.getGame().getSecondPlayer();

        User you = (User) playerYou.getUser();
        User opponent = (User) playerOpponent.getUser();

        //deActiveActions();


        assert opponent != null;

        setNumberOfDeckCards();
        initializeInfos(you, opponent);

        dynamicProgressBarColors(yourLPBar);
        dynamicProgressBarColors(opponentLPBar);
        yourLPBar.setStyle("-fx-accent: #0073ff");
        opponentLPBar.setStyle("-fx-accent: #0073ff");

        initializeShadowEffect();

        initializeHouses(playerYou, yourMonsterHousesGridPane, yourMagicHousesGridPane);
        initializeHouses(playerOpponent, opponentMonsterHousesGridPane, opponentMagicHousesGridPane);

        initializeHand(playerYou, yourHandContainer, true);
        initializeHand(playerOpponent, opponentHandContainer, false);

        initializeIcons();
        initializeActionsIcon();

        AudioHandler initialSoundOfGame; // stating the game theme music!
        if (AudioHandler.getPlaying() != null) {
            if (!AudioHandler.getPlayingAudioPath().equals(AudioPath.ITS_TIME_TO_DUEL)) {
                initialSoundOfGame = new AudioHandler(AudioPath.ITS_TIME_TO_DUEL);
                AudioHandler.getPlaying().getMediaPlayer().stop();
                playNewMedia(initialSoundOfGame);
            }
        } else {
            initialSoundOfGame = new AudioHandler(AudioPath.ITS_TIME_TO_DUEL);
            playNewMedia(initialSoundOfGame);
        }
    }

    private void initializeActionsIcon() {
        setHoverEffectForIcons(summonIcon);
        setHoverEffectForIcons(setMonsterIcon);
        setHoverEffectForIcons(setMagicIcon);
        setHoverEffectForIcons(attackMonsterIcon);
        setHoverEffectForIcons(changePositionIcon);
        summonIcon.setOnMouseClicked(event -> {
                    String answer = mainPhaseController.hireCard(game, TypeOfHire.SUMMON);
                    if (answer.contains("1") || answer.contains("2")) {
                        showTributeItems();
                    }
                    animateSummon();
                    deActiveActions();
                    restartSelectedCardImage();
                    reloadImages();
                }
        );
        setMonsterIcon.setOnMouseClicked(event -> {
            String answer = mainPhaseController.hireCard(game, TypeOfHire.SET);
            if (answer.contains("1") || answer.contains("2")) {
                showTributeItems();
            }
            animateSummon();
            deActiveActions();
            reloadImages();
            restartSelectedCardImage();

        });
        initializeAttackAction();

        setMagicIcon.setOnMouseClicked(event -> {
            mainPhaseController.hireCard(game, TypeOfHire.SET);
            deActiveActions();
            restartSelectedCardImage();
            reloadImages();
        });

        changePositionIcon.setOnMouseExited(event -> {
            ((MonsterHouse) game.getCardProp().getCardPlace()).changePos();

        });
    }

    private void initializeAttackAction() {
        attackMonsterIcon.setOnMouseClicked(event -> {
            if (game.getPlayer(SideOfFeature.OPPONENT).getBoard().numberOfFullHouse("monster") == 0) {
                battlePhaseController.attackDirect(game);
                updateHealth();
                deActiveActions();
            } else {
                try {
                    field.getScene().setCursor(new ImageCursor(new Image(new FileInputStream("src/main/resources/graphicprop/images/sword.png"))));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                for (MonsterHouse monsterHouse : game.getPlayer(SideOfFeature.OPPONENT).getBoard().getMonsterHouse()) {
                    monsterHouse.setOnMouseEntered(event1 -> {
                        monsterHouse.setScaleX(1.1);
                        monsterHouse.setScaleY(1.1);
                    });
                    monsterHouse.setOnMouseExited(event2 -> {
                        monsterHouse.setScaleX(1.0);
                        monsterHouse.setScaleY(1.0);
                    });

                    monsterHouse.setOnMouseClicked(event3 -> {
                        MonsterHouse attacker = (MonsterHouse) game.getCardProp().getCardPlace();
                        String state = monsterHouse.getState();

                        prevLPYou = yourLPBar.getProgress();
                        prevLPOpponent = opponentLPBar.getProgress();

                        battlePhaseController.attackMonsterHouse(game, monsterHouse);
                        if (monsterHouse.getCard() == null) {
                            Shake shake = new Shake(monsterHouse);
                            shake.getTimeline().setOnFinished(event4 -> {
                                FadeOut fadeOut = new FadeOut(monsterHouse);
                                fadeOut.getTimeline().setOnFinished(event5 -> monsterHouse.removeCard());
                                fadeOut.play();
                            });
                            shake.play();
                        } else {
                            if (state.contains("hidden")) {
                                FlipInX flipInX = new FlipInX(monsterHouse);
                                flipInX.getTimeline().setOnFinished(event1 -> monsterHouse.setImageOfCard(true));
                            }
                        }
                        if (attacker.getCard() == null) {
                            Shake shake = new Shake(attacker);
                            shake.getTimeline().setOnFinished(event4 -> {
                                FadeOut fadeOut = new FadeOut(attacker);
                                fadeOut.getTimeline().setOnFinished(event5 -> attacker.removeCard());
                                fadeOut.play();
                            });
                            shake.play();
                            attacker.removeCard();
                        }

                        field.getScene().setCursor(Cursor.DEFAULT);
                        for (MonsterHouse house : game.getPlayer(SideOfFeature.OPPONENT).getBoard().getMonsterHouse()) {
                            setHandleOnMouseClickedForMagicAndMonsters(house);
                        }
                        deActiveActions();
                        updateHealth();
                    });
                }
            }
        });
    }

    private void updateHealth() {
        double newLPYou = ((double) playerYou.getPlayerLifePoint()) / 8000;
        double newLPOpponent = ((double) playerOpponent.getPlayerLifePoint()) / 8000;

        yourLPBar.setProgress(prevLPYou);
        opponentLPBar.setProgress(prevLPOpponent);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(1000), new KeyValue(yourLPBar.progressProperty(), newLPYou)),
                new KeyFrame(Duration.millis(1000), new KeyValue(opponentLPBar.progressProperty(), newLPOpponent))
        );

        timeline.play();

        yourLPLabel.setText("LP: " + playerYou.getPlayerLifePoint());
        opponentLPLabel.setText("LP: " + playerOpponent.getPlayerLifePoint());

        if (playerOpponent.getPlayerLifePoint() == 0 ||playerYou.getPlayerLifePoint() == 0){
            finishGame();
        }

    }

    private void finishGame() {
        try {
            if (controller.finishRound(game)) {
                FadeOut fadeOut = new FadeOut(root);
                fadeOut.setSpeed(0.5);
                fadeOut.play();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/graphicprop/fxml/mainPage.fxml"));
                Parent parent = loader.load();
                Stage stage = (Stage) field.getScene().getWindow();
                Scene scene = new Scene(parent);
                stage.setScene(scene);
                scene.getRoot().requestFocus();
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setHoverEffectForIcons(ImageView Node) {
        Node.setOnMouseEntered(event -> {
            Node.setScaleX(1.1);
            Node.setScaleY(1.1);
        });

        Node.setOnMouseExited(event -> {
            Node.setScaleX(1);
            Node.setScaleY(1);
        });
    }

    private void playNewMedia(AudioHandler media) {
        media.getMediaPlayer().setCycleCount(1);

        media.getMediaPlayer().setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                AudioHandler gameTheme = new AudioHandler(AudioPath.IN_Duel);
                gameTheme.getMediaPlayer().setCycleCount(MediaPlayer.INDEFINITE);
                AudioHandler.getPlaying().getMediaPlayer().stop();
                gameTheme.play();
            }
        });

        media.play();
    }

    private void setNumberOfDeckCards() {
        currentDeckNumber.setText(String.valueOf(playerYou.getDeck().getMainDeck().size()));
        opponentDeckNumber.setText(String.valueOf(playerOpponent.getDeck().getMainDeck().size()));
    }

    private void initializeShadowEffect() {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setWidth(selectedCardImageView.getFitWidth());
        dropShadow.setHeight(selectedCardImageView.getFitHeight());
        selectedCardImageView.setEffect(dropShadow);
    }

    private void initializeHand(Player player, FlowPane handContainer, boolean isOpponentSide) {
        HandHouse[] handHouses = player.getBoard().getPlayerHand();

        handContainer.setHgap(16);

        for (HandHouse handHouse : handHouses) {
            handHouse.setPrefSize(68, 100);
            handHouse.setLayoutY(30);

            setMouseEnterEventForHand(handHouse);
            setMouseExitEventForHand(handHouse);
            setMouseClickEventForHand(handHouse);

            handHouse.setStyle("-fx-cursor: hand");
            handHouse.setImageOfCard(isOpponentSide);

            handContainer.getChildren().add(handHouse);
        }
    }

    private void setMouseClickEventForHand(HandHouse handHouse) {
        handHouse.setOnMouseClicked(e -> {
            controller.selectCard(game, handHouse);
            if (game.getCardProp().doesBelongToCurrent() && handHouse.getCard() != null) {
                selectedCardImageView.setImage(Card.getCardImage(game.getCardProp().getCard()));
                new FlipInX(selectedCardImageView).play();
                deActiveActions();
                showAvailableActions(handHouse);
            }
        });
    }

    private void deActiveActions() {
        if (summonIcon.getParent() != null)
            ((Pane) summonIcon.getParent()).getChildren().remove(summonIcon);
        if (setMonsterIcon.getParent() != null)
            ((Pane) setMonsterIcon.getParent()).getChildren().remove(setMonsterIcon);
        if (setMagicIcon.getParent() != null)
            ((Pane) setMagicIcon.getParent()).getChildren().remove(setMagicIcon);
        if (changePositionIcon.getParent() != null)
            ((Pane) changePositionIcon.getParent()).getChildren().remove(changePositionIcon);
        if ((attackMonsterIcon.getParent() != null)) {
            ((Pane) attackMonsterIcon.getParent()).getChildren().remove(attackMonsterIcon);
        }
        if ((activeMagicIcon.getParent() != null))
            ((Pane) activeMagicIcon.getParent()).getChildren().remove(activeMagicIcon);
    }

    private void showAvailableActions(HandHouse handHouse) {
        switch (game.getGameMainStage()) {
            case FIRST_MAIN_PHASE:
            case SECOND_MAIN_PHASE: {
                if (handHouse.getCard() instanceof MonsterCard && game.getHiredMonster() == null) {
                    handHouse.getChildren().addAll(summonIcon, setMonsterIcon);
                } else if (handHouse.getCard() instanceof MagicCard) {
                    MagicCard magicCard = (MagicCard) handHouse.getCard();
                    if (game.getPlayer(SideOfFeature.CURRENT).getBoard().numberOfFullHouse("spell") != 5) {
                        if ((magicCard.getMagicAttribute() == MagicAttribute.QUICK_PLAY || magicCard.getTypeOfMagic() == MagicType.TRAP)) {
                            deActiveActions();
                            handHouse.getChildren().addAll(setMagicIcon);
                        } else {
                            deActiveActions();
                            handHouse.getChildren().addAll(setMagicIcon, activeMagicIcon);
                        }
                    }
                }
                break;
            }
        }
    }

    private void showAvailableActions(MonsterHouse monsterHouse) {
        switch (game.getGameMainStage()) {
            case FIRST_MAIN_PHASE:
            case SECOND_MAIN_PHASE: {
                if (monsterHouse.getCard() != null) {
                    if (game.getHiredMonster() != monsterHouse) {
                        monsterHouse.getChildren().add(changePositionIcon);
                    }
                }
                break;
            }
            case BATTLE_PHASE: {
                if (!monsterHouse.getState().contains("den") && !game.isFirstTurnOfTheGame() && !monsterHouse.isMonsterAttacked()) {
                    monsterHouse.getChildren().add(attackMonsterIcon);
                }
                break;
            }
        }
    }

    private void showAvailableActions(MagicHouse magicHouse) {
        MagicCard magicCard = (MagicCard) (magicHouse.getCard());
        if (magicCard.getTypeOfMagic() == MagicType.TRAP ||
                magicCard.getMagicAttribute() == MagicAttribute.QUICK_PLAY) {
            magicHouse.getChildren().add(activeMagicIcon);
            new FadeIn(activeMagicIcon).play();
        } else {
            if (game.getGameMainStage() == GameMainStage.FIRST_MAIN_PHASE || game.getGameMainStage() == GameMainStage.SECOND_MAIN_PHASE) {
                magicHouse.getChildren().add(activeMagicIcon);
                new FadeIn(activeMagicIcon).play();
            }
        }
    }

    private void setMouseExitEventForHand(HandHouse handHouse) {
        handHouse.setOnMouseExited(e -> {
            handHouse.setScaleX(1);
            handHouse.setScaleY(1);

            handHouse.setEffect(null);
        });
    }

    private void setMouseEnterEventForHand(HandHouse handHouse) {
        handHouse.setOnMouseEntered(e -> {
            handHouse.setScaleX(1.3);
            handHouse.setScaleY(1.3);

            DropShadow dropShadow = new DropShadow();
            handHouse.setEffect(dropShadow);
        });
    }

    private void reloadImages() {
        HandHouse[] handHouses = game.getPlayer(SideOfFeature.CURRENT).getBoard().getPlayerHand();
        for (int j = handHouses.length - 1; j >= 1; j--) {
            for (int k = j - 1; k >= 0; k--) {
                if (handHouses[j].doesHaveImage() && !handHouses[k].doesHaveImage()) {
                    try {
                        handHouses[k].setCard(handHouses[j].getCard());
                        handHouses[k].setImageOfCard(true);
                        handHouses[j].removeCard();
                        new FadeInRight(handHouses[k]).play();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    private void initializeHouses(Player player, GridPane monsterHousesGridPane, GridPane magicHousesGridPane) {
        monsterHousesGridPane.setHgap(35);
        monsterHousesGridPane.setPadding(new Insets(10));
        magicHousesGridPane.setHgap(35);
        magicHousesGridPane.setPadding(new Insets(10));

        MonsterHouse[] monsterHouses = player.getBoard().getMonsterHouse();
        for (int i = 0; i < monsterHouses.length; i++) {
            MonsterHouse monsterHouse = monsterHouses[i];
            monsterHousesGridPane.add(monsterHouse, i, 0, 1, 1);

            monsterHouse.setPrefSize(46, 68);
            monsterHouse.setLayoutY(15);
            setHandleOnMouseClickedForMagicAndMonsters(monsterHouse);
            monsterHouse.setMonsterCard(null);
        }

        MagicHouse[] magicHouses = player.getBoard().getMagicHouse();
        for (int i = 0; i < magicHouses.length; i++) {
            MagicHouse magicHouse = magicHouses[i];
            magicHousesGridPane.add(magicHouse, i, 0, 1, 1);
            magicHouse.setPrefSize(46, 68);
            magicHouse.setLayoutY(15);
            setHandleOnMouseClickedForMagicAndMonsters(magicHouse);
        }
    }

    private void setHandleOnMouseClickedForMagicAndMonsters(GameHouse gameHouse) {
        gameHouse.setOnMouseClicked(e -> {
            if (gameHouse.getCardImage() != null) {
                controller.selectCard(game, gameHouse);
                if (!game.getCardProp().doesBelongToCurrent()) {
                    if ((gameHouse.getState().equals("hidden") || gameHouse.getState().equals("defenceHidden"))) {
                        selectedCardImageView.setImage(GameHouse.getBackOfCardImage());
                    } else {
                        selectedCardImageView.setImage(Card.getCardImage(game.getCardProp().getCard()));
                    }
                } else {
                    if (gameHouse instanceof MonsterHouse) {
                        selectedCardImageView.setImage(Card.getCardImage(game.getCardProp().getCard()));
                        deActiveActions();
                        showAvailableActions((MonsterHouse) gameHouse);
                    } else {
                        deActiveActions();
                        showAvailableActions((MagicHouse) gameHouse);
                    }
                }
                new FlipInX(selectedCardImageView).play();
            }
            //TODO: make a field "selectedCard : Card" which holds the selected card!
        });
    }


    private void initializeInfos(User you, User opponent) {
        try {
            yourAvatar.setImage(new Image(new FileInputStream(you.getAvatarAddress())));
            opponentAvatar.setImage(new Image(new FileInputStream(opponent.getAvatarAddress())));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        yourUsername.setText("Username: " + you.getUsername());
        yourNickname.setText("Nickname: " + you.getNickname());

        opponentUsername.setText("Username: " + opponent.getUsername());
        opponentNickname.setText("Nickname: " + opponent.getNickname());
    }


    private void showGraveYard(Player player) {
        Pane graveYardPane = new Pane();
        int sizeOfFlowPane = player.getBoard().getGraveYard().getDestroyedCards().size();

        FlowPane graveYardFlowPane = makePopUpFlowPane(sizeOfFlowPane);


        ArrayList<Card> graveYardArrList = player.getBoard().getGraveYard().getDestroyedCards();
        for (Card card : graveYardArrList) {
            ImageView imageView = setImageOfCard(card);
            setPopUpCardsEffects(imageView);

            graveYardFlowPane.getChildren().add(imageView);
        }

        addNodesToPopUpPage(graveYardPane, player, graveYardFlowPane);
    }

    @NotNull
    private ImageView setImageOfCard(Card card) {
        ImageView imageView;
        imageView = new ImageView(Card.getCardImage(card));
        imageView.setFitWidth(123);
        imageView.setFitHeight(180);
        return imageView;
    }

    @NotNull
    private ScrollPane makePopUpScrollPane() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(700);
        scrollPane.setPrefHeight(190);
        scrollPane.setLayoutX(30);
        scrollPane.setLayoutY(60);
        scrollPane.setPrefViewportHeight(190);
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.toFront();
        return scrollPane;
    }

    private void setPopUpCardsEffects(ImageView imageView) {
        mouseHoverEvent(imageView);

        imageView.setOnMouseExited(e -> {
            imageView.setScaleX(1);
            imageView.setScaleY(1);

            imageView.setEffect(null);

        });
        imageView.setOnMouseClicked(e -> selectedCardImageView = imageView);
    }

    @NotNull
    private FlowPane makePopUpFlowPane(int sizeOfFlowPane) {
        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(20);
        flowPane.setVgap(10);
        flowPane.setPrefWrapLength(sizeOfFlowPane * (14.222 + 123) + 28.444);
        flowPane.setPrefHeight(190);
        flowPane.setPadding(new Insets(14));
        flowPane.setStyle("-fx-background-image: url('/graphicprop/images/dirtyBoardBG.jpg'); -fx-background-size: cover");
        return flowPane;
    }

    public void showTributeItems() {
        Integer tributeNumber = game.getTributeNumber();
        ArrayList<MonsterHouse> tributeMonsters = new ArrayList<>();
        Pane tributePane = new Pane();
        Player player = game.getPlayer(SideOfFeature.CURRENT);
        int sizeOfFlowPane = player.getBoard().numberOfFullHouse("monster");

        FlowPane tributeFlowPane = makePopUpFlowPane(sizeOfFlowPane);


        MonsterHouse[] monsterHouses = player.getBoard().getMonsterHouse();
        for (MonsterHouse house : monsterHouses) {
            if (house.getCard() != null) {
                if (house != game.getHiredMonster()) {
                    ImageView imageView = setImageOfCard(house.getCard());

                    setPopUpCardsEffectsForTribute(imageView, house, tributeMonsters);
                    tributeFlowPane.getChildren().add(imageView);
                }
            }
        }
        Button tribute = new Button();
        setStyleForTributeButton(tributeNumber, tributeMonsters, tributePane, tribute);
        addNodesToPopUpPage(tributePane, player, tributeFlowPane);
    }

    private void setStyleForTributeButton(int numberOfTributes, ArrayList<MonsterHouse> tributeMonsters, Pane tributePane, Button tribute) {
        tribute.setText("tribute");
        setButtonDetail(tribute);
        tribute.setLayoutY(276);
        tribute.setLayoutX(671);
        tributePane.getChildren().add(tribute);
        tribute.setPrefWidth(90);
        tribute.setOnMouseClicked(e -> {
            if (numberOfTributes == tributeMonsters.size()) {
                for (MonsterHouse tributeMonster : tributeMonsters) {
                    game.getPlayer(SideOfFeature.CURRENT).getBoard().moveCardToGraveYard(tributeMonster.getCard());
                }
                new ZoomOut(tributePane).play();
                root.getChildren().remove(tributePane);
                for (Node child : root.getChildren()) {
                    child.setDisable(false);
                    child.setOpacity(1);
                }
            }
        });
    }

    private void setButtonDetail(Button tribute) {
        tribute.setOpacity(0.6);
        tribute.setStyle("-fx-background-color: red; -fx-border-color: black; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand");
        tribute.setPrefHeight(10);
        tribute.setPrefWidth(15);
        tribute.setOnMouseEntered(e -> tribute.setOpacity(1));
        tribute.setOnMouseExited(e -> tribute.setOpacity(0.6));
    }

    private void setPopUpCardsEffectsForTribute(ImageView imageView, MonsterHouse house,
                                                ArrayList<MonsterHouse> tributeMonsters) {

        mouseHoverEvent(imageView);

        imageView.setOnMouseClicked(e -> {
            if (!tributeMonsters.contains(house))
                tributeMonsters.add(house);
            imageView.setScaleY(1.5);
            imageView.setScaleX(1.5);
        });
    }

    private void mouseHoverEvent(ImageView imageView) {
        imageView.setOnMouseEntered(e -> {
            imageView.setScaleX(1.2);
            imageView.setScaleY(1.2);

            DropShadow dropShadow = new DropShadow();
            imageView.setEffect(dropShadow);
        });
    }

    private void addNodesToPopUpPage(Pane tributePane, Player player, FlowPane tributeFlowPane) {
        Button closeButton = seCloseButtonPopUp(tributePane);


        Label title = setPopUpName();
        title.setText(player.getUser().getNickname() + "monsters");
        ScrollPane scrollPane = makePopUpScrollPane();

        tributeFlowPane.setMinWidth(700);
        scrollPane.setContent(tributeFlowPane);

        tributePane.toFront();
        tributePane.setPrefHeight(300);
        tributePane.setPrefWidth(760);
        tributePane.setStyle("-fx-background-image: url('/graphicprop/images/brickwall.jpg'); -fx-background-size: cover");
        tributePane.setLayoutX(150);
        tributePane.setLayoutY(200);

        tributePane.getChildren().addAll(closeButton, title, scrollPane);

        for (Node child : root.getChildren()) {
            child.setDisable(true);
            child.setOpacity(0.3);
        }
        root.getChildren().add(tributePane);
        new ZoomIn(tributePane).play();
    }

    @NotNull
    private Label setPopUpName() {
        Label title = new Label();
        title.setLayoutX(30);
        title.setLayoutY(30);
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 20; -fx-text-fill: white");
        return title;
    }

    @NotNull
    private Button seCloseButtonPopUp(Pane graveYardPane) {
        Button closeButton = new Button();
        closeButton.setText("X");
        setButtonDetail(closeButton);
        closeButton.setOnMouseClicked(e -> {
            new ZoomOut(graveYardPane).play();
            root.getChildren().remove(graveYardPane);
            for (Node child : root.getChildren()) {
                child.setDisable(false);
                child.setOpacity(1);
            }
        });
        return closeButton;
    }

    private void animateDraw() {
        Player toChangePlayer;
        Pane toImitate;
        if (game.getPlayer(SideOfFeature.CURRENT).equals(playerYou)) {
            toChangePlayer = playerYou;
            toImitate = yourDeckPane;
        } else {
            toChangePlayer = playerOpponent;
            toImitate = opponentDeckPane;
        }

        ImageView dummy = new ImageView();
        dummy.setImage(GameHouse.getBackOfCardImage());

        dummy.setLayoutY(toImitate.getLayoutY());
        dummy.setLayoutX(toImitate.getLayoutX());
        dummy.toFront();

        dummy.setFitWidth(74);
        dummy.setFitHeight(100);

        field.getChildren().add(dummy);
        ZoomIn zoomIn = new ZoomIn(dummy);
        zoomIn.setSpeed(2);
        zoomIn.getTimeline().setOnFinished(e -> {
            AnimationFX animationFX;
            if (toChangePlayer.equals(playerYou)) {
                animationFX = new FadeOutDown(dummy);
            } else {
                animationFX = new FadeOutUp(dummy);
            }
            animationFX.setSpeed(3);
            animationFX.getTimeline().setOnFinished(e1 -> {
                field.getChildren().remove(dummy);

                turnPlayerHandCard();
            });
            animationFX.play();
        });

        zoomIn.play();
    }

    private void animateSummon() {
        MonsterHouse summoned = game.getHiredMonster();

        if (summoned.getState().equals(MonsterHouseVisibilityState.OO.toString())) {
            summoned.setImageOfCard(true);

        } else if (summoned.getState().equals(MonsterHouseVisibilityState.DH.toString())) {
            summoned.setImageOfCard(false);
        }
        new ZoomIn(summoned).play();
    }

    private void swapColorForChangeTurn() {
        if (turnShowerDown.getText().equals("You")) {
            turnShowerDown.setText("Opponent");
            turnShowerUp.setText("You");
        } else {
            turnShowerUp.setText("Opponent");
            turnShowerDown.setText("You");
        }
        if (endPhaseBox.getStyle().contains("red")) {
            swapPhaseNameButton("-fx-background-image: url('graphicprop/images/greenPhaseBg.png')");
        } else if (endPhaseBox.getStyle().contains("green")) {
            swapPhaseNameButton("-fx-background-image: url('graphicprop/images/redPhaseBg.png')");
        } else {
            swapPhaseNameButton("-fx-background-image: url('graphicprop/images/redPhaseBg.png')");
        }
        if (cardDescriptionArea.getStyle().contains("youUser")) {
            cardDescriptionArea.setStyle("-fx-background-image: url('graphicprop/images/opponentUserInfoBgjpeg.jpeg')");
        } else if (cardDescriptionArea.getStyle().contains("opponentUser")) {
            cardDescriptionArea.setStyle("-fx-background-image: url('graphicprop/images/youUserInfoBGjpeg.jpeg')");
        } else {
            cardDescriptionArea.setStyle("-fx-background-image: url('graphicprop/images/opponentUserInfoBgjpeg.jpeg')");
        }
        new FadeIn(cardDescriptionArea).play();
    }

    private void turnPlayerHandCard() {
        for (HandHouse house : game.getPlayer(SideOfFeature.CURRENT).getBoard().getPlayerHand()) {
            house.setImageOfCard(true);
            FlipInX flipInX = new FlipInX(house);
            flipInX.setSpeed(0.5);
            flipInX.play();
        }
        for (HandHouse house : game.getPlayer(SideOfFeature.OPPONENT).getBoard().getPlayerHand()) {
            house.setImageOfCard(false);
            FlipInX flipInX = new FlipInX(house);
            flipInX.setSpeed(0.5);
            flipInX.play();
        }
    }

    private void swapPhaseNameButton(String s) {

        drawPhaseBox.setStyle(s);
        standByPhaseBox.setStyle(s);
        firstMainPhaseBox.setStyle(s);
        battlePhaseBox.setStyle(s);
        secondMainPhaseBox.setStyle(s);
        endPhaseBox.setStyle(s);

        new BounceIn(drawPhaseBox).play();
        new BounceIn(standByPhaseBox).play();
        new BounceIn(firstMainPhaseBox).play();
        new BounceIn(battlePhaseBox).play();
        new BounceIn(secondMainPhaseBox).play();
        new BounceIn(endPhaseBox).play();


    }

    private void setScaleForCurrentPhase(String currentPhase) {
        setNormalScale();
        switch (currentPhase) {
            case "draw phase": {
                drawPhaseBox.setScaleX(1.2);
                drawPhaseBox.setScaleY(1.2);
                break;
            }
            case "first main phase": {
                firstMainPhaseBox.setScaleX(1.2);
                firstMainPhaseBox.setScaleY(1.2);
                break;
            }
            case "second main phase": {
                secondMainPhaseBox.setScaleX(1.2);
                secondMainPhaseBox.setScaleY(1.2);
                break;
            }

            case "battle phase": {
                battlePhaseBox.setScaleX(1.2);
                battlePhaseBox.setScaleY(1.2);
                break;
            }

            case "stand by phase": {
                standByPhaseBox.setScaleX(1.2);
                standByPhaseBox.setScaleY(1.2);
                break;
            }

            case "end phase": {
                endPhaseBox.setScaleX(1.2);
                endPhaseBox.setScaleY(1.2);
                break;
            }
        }
    }

    private void setNormalScale() {
        drawPhaseBox.setScaleX(1.0);
        drawPhaseBox.setScaleY(1.0);

        firstMainPhaseBox.setScaleX(1.0);
        firstMainPhaseBox.setScaleY(1.0);


        secondMainPhaseBox.setScaleX(1.0);
        secondMainPhaseBox.setScaleY(1.0);


        battlePhaseBox.setScaleX(1.0);
        battlePhaseBox.setScaleY(1.0);


        standByPhaseBox.setScaleX(1.0);
        standByPhaseBox.setScaleY(1.0);

        endPhaseBox.setScaleX(1.0);
        endPhaseBox.setScaleY(1.0);

    }

    private void dynamicProgressBarColors(ProgressBar bar) {
        String CRITICAL = "-fx-accent: C86176;";
        String DANGER = "-fx-accent: B181A1;";
        String MODERATE = "-fx-accent: AC91B9;";
        String GOOD = "-fx-accent: #0073ff;";

        bar.progressProperty().addListener(new ChangeListener<>() {
            Flash flash = new Flash(bar);

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double progress = newValue == null ? 0 : newValue.doubleValue();
                if (progress < 0.3) {
                    setBarStyleClass(bar, CRITICAL);
                    flash.setCycleCount(AnimationFX.INDEFINITE);
                    flash.setSpeed(0.3);
                    flash.play();
                } else if (progress < 0.6) {
                    flash.stop();
                    setBarStyleClass(bar, DANGER);
                } else if (progress < 0.8) {
                    flash.stop();
                    setBarStyleClass(bar, MODERATE);
                } else {
                    flash.stop();
                    setBarStyleClass(bar, GOOD);
                }
            }

            private void setBarStyleClass(ProgressBar bar, String barStyleClass) {
                bar.setStyle(barStyleClass);
            }
        });
    }

    public void run(MouseEvent mouseEvent) throws FileNotFoundException {
        if (mouseEvent.getSource() == yourGraveyardPane) {
            showGraveYard(playerYou);
        } else if (mouseEvent.getSource() == opponentGraveyardPane) {
            showGraveYard(playerOpponent);
        } else if (mouseEvent.getSource() == nextPhase) {
            deActiveActions();
            phaseName.setText(controller.nextPhase(game));
            setNumberOfDeckCards();
            new BounceIn(phaseName).play();
            if (phaseName.getText().equals("draw phase")) {
                animateDraw();
                swapColorForChangeTurn();
            }
            setScaleForCurrentPhase(phaseName.getText());
            restartSelectedCardImage();
        }
    }

    private void directAttack() {
    }

    private void restartSelectedCardImage() {
        selectedCardImageView.setImage(GameHouse.getBackOfCardImage());
        deActiveActions();
        new FlipInX(selectedCardImageView).play();
    }

}
