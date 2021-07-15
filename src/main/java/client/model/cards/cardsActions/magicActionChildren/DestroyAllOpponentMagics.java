package client.model.cards.cardsActions.magicActionChildren;

import client.model.cards.cardsActions.Action;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.gameprop.BoardProp.MagicHouse;
import client.model.gameprop.BoardProp.PlayerBoard;
import client.model.gameprop.gamemodel.Game;

public class DestroyAllOpponentMagics extends Action {
    {
        name = this.getClass().getSimpleName();
    }

    @Override
    public void active(Game game) {
        PlayerBoard opponentBoard = game.getPlayer(SideOfFeature.OPPONENT).getBoard();
        for (MagicHouse magicHouse : opponentBoard.getMagicHouse()) {
            if (magicHouse.getMagicCard() != null) {
                opponentBoard.getGraveYard().addCardToGraveYard(magicHouse.getMagicCard());
                magicHouse.setMagicCard(null);
            }
        }
        isActivatedBefore = true;
    }
}
