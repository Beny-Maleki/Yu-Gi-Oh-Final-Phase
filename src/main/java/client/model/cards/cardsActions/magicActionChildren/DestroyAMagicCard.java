package client.model.cards.cardsActions.magicActionChildren;

import client.controller.gamecontrollers.GetStringInputFromView;
import client.exceptions.CardNotFoundException;
import client.model.cards.cardsActions.Action;
import client.model.cards.cardsProp.MagicCard;
import client.model.enums.GameEnums.RequestingInput;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.gameprop.BoardProp.MagicHouse;
import client.model.gameprop.BoardProp.PlayerBoard;
import client.model.gameprop.gamemodel.Game;

public class DestroyAMagicCard extends Action {
    {
        name = this.getClass().getSimpleName();
    }

    @Override
    public void active(Game game) {
        PlayerBoard opponentBoard = game.getPlayer(SideOfFeature.OPPONENT).getBoard();
        PlayerBoard currentBoard = game.getPlayer(SideOfFeature.CURRENT).getBoard();
        String magicToDestroy = GetStringInputFromView.getInputFromView(RequestingInput.MAGIC_CARD_TO_DESTROY);
        try {
            MagicCard cardToDestroy = opponentBoard.getMagicCardByName(magicToDestroy);
            opponentBoard.getGraveYard().addCardToGraveYard(cardToDestroy);
            for (MagicHouse magicHouse : opponentBoard.getMagicHouse()) {
                if (magicHouse.getMagicCard().getName().equals(magicToDestroy)) {
                    magicHouse.setMagicCard(null);
                }
            }
        }
        catch (CardNotFoundException e) {
            try {
                MagicCard cardToDestroy = currentBoard.getMagicCardByName(magicToDestroy);
                currentBoard.getGraveYard().addCardToGraveYard(cardToDestroy);
                for (MagicHouse magicHouse : currentBoard.getMagicHouse()) {
                    if (magicHouse.getMagicCard().getName().equals(magicToDestroy)) {
                        magicHouse.setMagicCard(null);
                    }
                }
            } catch (CardNotFoundException cardNotFoundException) {
                cardNotFoundException.printStackTrace();
                active(game);
            }
        }
        isActivatedBefore = true;
    }
}
