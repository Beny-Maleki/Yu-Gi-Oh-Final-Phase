package model.gameprop;

import model.gameprop.gamemodel.Game;

public class GameInProcess {
    private static Game game;

    public static void setGame(Game game) {
        GameInProcess.game = game;
    }

    public static Game getGame() {
        return game;
    }
}
