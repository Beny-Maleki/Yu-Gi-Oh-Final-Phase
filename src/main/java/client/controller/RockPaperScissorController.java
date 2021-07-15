package client.controller;

import javafx.scene.Node;
import client.model.enums.GameEnums.PlayerTurn;
import client.model.enums.Menu;

import java.io.IOException;


public class RockPaperScissorController extends Controller{
    private static RockPaperScissorController instance;
    private static int numOfRounds;

    private RockPaperScissorController(){
    }

    public static RockPaperScissorController getInstance() {
        if (instance == null) instance = new RockPaperScissorController();
        return instance;
    }

    public static void setNumOfRounds(int numOfRounds) {
        RockPaperScissorController.numOfRounds = numOfRounds;
    }

    public static int getNumOfRounds() {
        return numOfRounds;
    }

    @Override
    public void moveToPage(Node node, Menu menu) throws IOException {
        super.moveToPage(node, menu);
    }

    public static PlayerTurn recognizeGameWinner(String firstPlayerTool, String secondPlayerTool) {
        if (firstPlayerTool.equals(secondPlayerTool)) return PlayerTurn.None;
        else if (firstPlayerTool.equals("paper")) {
            if (secondPlayerTool.equals("rock")) {
                return PlayerTurn.PLAYER_ONE;
            } else {
                return PlayerTurn.PLAYER_TWO;
            }
        } else if (firstPlayerTool.equals("rock")) {
            if (secondPlayerTool.equals("scissor")) {
                return PlayerTurn.PLAYER_ONE;
            } else {
                return PlayerTurn.PLAYER_TWO;
            }
        } else {
            if (secondPlayerTool.equals("paper")) {
                return PlayerTurn.PLAYER_ONE;
            } else {
                return PlayerTurn.PLAYER_TWO;
            }
        }
    }
}

