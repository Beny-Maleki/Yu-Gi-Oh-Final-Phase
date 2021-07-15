package client.model.cards.cardsActions.magicActionChildren;

import client.model.cards.cardsActions.Action;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.gameprop.BoardProp.MonsterHouse;
import client.model.gameprop.BoardProp.PlayerBoard;
import client.model.gameprop.gamemodel.Game;

public class DestroyAllBoardMonsters extends Action {
    {
        name = this.getClass().getSimpleName();
    }

    @Override
    public void active(Game game) {
        PlayerBoard opponentBoard = game.getPlayer(SideOfFeature.OPPONENT).getBoard();
        for (MonsterHouse monsterHouse : opponentBoard.getMonsterHouse()) {
            if (monsterHouse.getMonsterCard() != null) {
                opponentBoard.getGraveYard().addCardToGraveYard(monsterHouse.getMonsterCard());
                monsterHouse.setMonsterCard(null);
            }
        }
        PlayerBoard currentBoard = game.getPlayer(SideOfFeature.CURRENT).getBoard();
        for (MonsterHouse monsterHouse : currentBoard.getMonsterHouse()) {
            if (monsterHouse.getMonsterCard() != null) {
                currentBoard.getGraveYard().addCardToGraveYard(monsterHouse.getMonsterCard());
                monsterHouse.setMonsterCard(null);
            }
        }
        isActivatedBefore = true;
    }
}
