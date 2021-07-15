package client.controller.gamecontrollers.gamestagecontroller;

import client.controller.gamecontrollers.GeneralController;
import client.model.cards.cardsProp.Card;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.gameprop.BoardProp.HandHouse;
import client.model.gameprop.GameInProcess;
import client.model.gameprop.Player;
import client.model.gameprop.gamemodel.Game;
import client.model.userProp.Deck;

import java.io.FileNotFoundException;

public class DrawPhaseController extends GeneralController {

    private static DrawPhaseController instance;

    public DrawPhaseController() {
    }

    public static DrawPhaseController getInstance() {
        if (instance == null) instance = new DrawPhaseController();
        return instance;
    }

    public String draw(boolean isCheating) throws FileNotFoundException {
        Game game = GameInProcess.getGame();
        Player player = game.getPlayer(SideOfFeature.CURRENT);
        if (!isCheating) {
            if (game.doesPlayerHavePermissionToDraw() && player.isAllowedToDraw && player.getBoard().numberOfFullHouse("hand") < 6) {
                game.setPlayerDrawInTurn();
                return chooseCardFromDeckAndPlaceToHand(player);
            }
        } else {
            return chooseCardFromDeckAndPlaceToHand(player);
        }
        return null;
    }

    private String chooseCardFromDeckAndPlaceToHand(Player player) throws FileNotFoundException {
        Deck playerDeck = player.getDeck();
        Card newCard = playerDeck.getMainDeck().get(0);
        playerDeck.removeCardFromMainDeck(newCard);
        try {
            HandHouse house = player.getBoard().getFirstEmptyHouse();
            house.setCard(newCard);
        } catch (NullPointerException e) {
            return "";
        }
        return newCard.getName();
    }

    public String process(String message, String name) {
        if (message.contains("_CN_")) {
            message = message.replace("_CN_", name);
        }
        return message;
    }
}
