package client.view;

import client.controller.ImportScanner;
import client.controller.RockPaperScissorController;
import client.model.enums.GameEnums.PlayerTurn;
import client.model.enums.rockpaperscissor.PaperRockScissorError;
import client.model.enums.rockpaperscissor.RockPaperScissorMessage;
import client.view.menudisplay.RockPaperScissorDisplay;

public class RockPaperScissorGame {
    public static PlayerTurn run(String playerOneName, String playerTwoName) {
        String playerOneChoice;
        String playerTwoChoice;
        while (true) {
            RockPaperScissorDisplay.display(RockPaperScissorMessage.PLAYER_ONE_CHOOSE, playerOneName);

            playerOneChoice = ImportScanner.getInput().toLowerCase();
            while (chooseWrongTool(playerOneChoice)) {
                playerOneChoice = ImportScanner.getInput().toLowerCase();
            }
            RockPaperScissorDisplay.display(RockPaperScissorMessage.PLAYER_TWO_CHOOSE, playerTwoName);

            playerTwoChoice = ImportScanner.getInput().toLowerCase();
            while (chooseWrongTool(playerTwoChoice)) {
                playerOneChoice = ImportScanner.getInput().toLowerCase();
            }

            PlayerTurn firstPlayer = RockPaperScissorController.
                    recognizeGameWinner(playerOneChoice, playerTwoChoice);
            if (firstPlayer.equals(PlayerTurn.PLAYER_ONE)) {
                RockPaperScissorDisplay.display(RockPaperScissorMessage.PLAYER_ONE_WIN_MESSAGE, playerOneName);
                return firstPlayer;
            } else if (firstPlayer.equals(PlayerTurn.PLAYER_TWO)) {
                RockPaperScissorDisplay.display(RockPaperScissorMessage.PLAYER_TWO_WIN_MESSAGE, playerTwoName);
                return firstPlayer;
            }
            RockPaperScissorDisplay.display(RockPaperScissorMessage.DRAW_MESSAGE);
        }
    }

    private static boolean chooseWrongTool(String weapon) {
        if (!weapon.equals("paper") && !weapon.equals("rock") && !weapon.equals("scissor")) {
            RockPaperScissorDisplay.display(PaperRockScissorError.WRONG_WEAPON_CHOOSE);
            return true;
        }
        return false;
    }
}
