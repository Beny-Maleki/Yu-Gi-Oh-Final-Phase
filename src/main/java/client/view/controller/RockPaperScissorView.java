package client.view.controller;

import animatefx.animation.FadeInLeft;
import animatefx.animation.FadeInUp;
import animatefx.animation.SlideInDown;
import animatefx.animation.SlideInUp;
import client.controller.RockPaperScissorController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import client.model.enums.GameEnums.PlayerTurn;
import client.model.enums.Menu;
import client.model.gameprop.GameInProcess;
import client.model.gameprop.Player;
import client.model.gameprop.gamemodel.Game;
import client.model.userProp.LoginUser;
import client.model.userProp.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class RockPaperScissorView {
    private final String blockPic = "src/main/resources/graphicprop/images/RockPaperScissor/empty.png";
    public Label nicknameDown;
    public Label nicknameUp;
    public ImageView frameUp;
    public ImageView frameDown;
    public ImageView scissor;
    public ImageView rock;
    public ImageView paper;
    public ImageView scissorUp;
    public ImageView rockUp;
    public ImageView paperUp;
    public AnchorPane root;
    RockPaperScissorController controller = RockPaperScissorController.getInstance();
    private User userOne;
    private User userTwo;

    private String userOneChoice;
    private String userTwoChoice;


    @FXML
    public void initialize() throws FileNotFoundException {
        userOne = LoginUser.getUser();
        userTwo = LoginUser.getOpponent();

        setAvatars(userOne.getAvatarAddress(), userTwo.getAvatarAddress());

        setNicknames(userOne.getNickname(), userTwo.getNickname());

        rock.setStyle("-fx-cursor: hand");
        scissor.setStyle("-fx-cursor: hand");
        paper.setStyle("-fx-cursor: hand");

        hoverOnImage(rock);
        hoverOnImage(scissor);
        hoverOnImage(paper);

        blockUpBox();
    }

    private void setNicknames(String forDownNickname, String forUpNickname) {
        nicknameDown.setText(forDownNickname);
        nicknameUp.setText(forUpNickname);
    }

    private void setAvatars(String forFrameDown, String forFrameUp) throws FileNotFoundException {
        frameDown.setImage(new Image(new FileInputStream(forFrameDown)));
        frameUp.setImage(new Image(new FileInputStream(forFrameUp)));
    }

    private void blockUpBox() {
        try {
            FileInputStream fileInputStream;
            Image image;
            fileInputStream = new FileInputStream(blockPic);
            image = new Image(fileInputStream);

            rockUp.setImage(image);
            paperUp.setImage(image);
            scissorUp.setImage(image);

            new FadeInLeft(frameDown).play();
            new FadeInLeft(frameUp).play();
            new FadeInLeft(nicknameDown).play();
            new FadeInLeft(nicknameUp).play();


            new SlideInDown(rockUp).play();
            new SlideInDown(paperUp).play();
            new SlideInDown(scissorUp).play();

            new SlideInUp(rock).play();
            new SlideInUp(paper).play();
            new SlideInUp(scissor).play();

            rock.setOnMouseClicked(e -> {
                userOneChoice = "rock";
                showAnimationOfRock();
                changeTurn();
            });

            paper.setOnMouseClicked(e -> {
                userOneChoice = "paper";
                showAnimationOfPaper();
                changeTurn();
            });

            scissor.setOnMouseClicked(e -> {
                userOneChoice = "scissor";
                showAnimationOfScissors();
                changeTurn();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void changeTurn() {
        try {
            setAvatars(userTwo.getAvatarAddress(), userOne.getAvatarAddress());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        setNicknames(userTwo.getNickname(), userOne.getNickname());

        new FadeInLeft(frameDown).play();
        new FadeInLeft(frameUp).play();
        new FadeInLeft(nicknameDown).play();
        new FadeInLeft(nicknameUp).play();

        new SlideInUp(rock).play();
        new SlideInUp(paper).play();
        new SlideInUp(scissor).play();

        new SlideInDown(rockUp).play();
        new SlideInDown(paperUp).play();
        new SlideInDown(scissorUp).play();

        rock.setOnMouseClicked(e -> {
            userTwoChoice = "rock";
            showAnimationOfRock();
            analyzeResult();
        });

        paper.setOnMouseClicked(e -> {
            userTwoChoice = "paper";
            showAnimationOfPaper();
            analyzeResult();
        });

        scissor.setOnMouseClicked(e -> {
            userTwoChoice = "scissor";
            showAnimationOfScissors();
            analyzeResult();
        });

    }

    private void analyzeResult() {
        PlayerTurn result = RockPaperScissorController.recognizeGameWinner(userOneChoice, userTwoChoice);
        if (result == PlayerTurn.None) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Draw!", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                try {
                    initialize();
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        } else if (result == PlayerTurn.PLAYER_ONE) {
            makeGame(userOne, userTwo);
        } else if (result == PlayerTurn.PLAYER_TWO) {
            makeGame(userTwo, userOne);
        }
    }

    private void makeGame(User playerOneUser, User playerTwoUser) {
        Player playerOne;
        Player playerTwo;
        try {
            playerOne = new Player(playerOneUser, 0);
            playerTwo = new Player(playerTwoUser, 0);
            GameInProcess.setGame(new Game(playerOne, playerTwo, RockPaperScissorController.getNumOfRounds()));
            try {
                controller.moveToPage(rock, Menu.GAME_PAGE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    private void hoverOnImage(ImageView image) {
        image.setOnMouseEntered(event -> {
            (image).setScaleX(1.1);
            (image).setScaleY(1.1);
        });
        image.setOnMouseExited(event -> {
            (image).setScaleX(1.0);
            (image).setScaleY(1.0);
        });
    }


    private void showAnimationOfRock() {
        ImageView choiceImageView = null;
        choiceImageView = setImageView(null, "src/main/resources/graphicprop/images/RockPaperScissor/rockVisual.png");


        root.getChildren().remove(choiceImageView);
    }


    private void showAnimationOfPaper() {
        ImageView choiceImageView = null;
        choiceImageView = setImageView(null, "src/main/resources/graphicprop/images/RockPaperScissor/paperVisual.png");


        root.getChildren().remove(choiceImageView);
    }

    private void showAnimationOfScissors() {
        ImageView choiceImageView = null;
        choiceImageView = setImageView(null, "src/main/resources/graphicprop/images/RockPaperScissor/scissorVisual.png");

        root.getChildren().remove(choiceImageView);
    }

    private ImageView setImageView(ImageView choiceImageView, String s) {
        try {
            FileInputStream fileInputStream = new FileInputStream(s);
            Image image = new Image(fileInputStream);


            choiceImageView = new ImageView();
            choiceImageView.setLayoutX(415);
            choiceImageView.setLayoutY(275);
            choiceImageView.setFitHeight(150);
            choiceImageView.setFitWidth(170);

            choiceImageView.setImage(image);
            choiceImageView.toFront();
            root.getChildren().add(choiceImageView);
            FadeInUp fadeInUp = new FadeInUp(choiceImageView);
            fadeInUp.setSpeed(0.1);
            fadeInUp.play();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return choiceImageView;
    }
}
