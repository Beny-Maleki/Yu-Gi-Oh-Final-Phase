package client.model.cards.cardsActions.magicActionChildren;

import client.model.cards.cardsActions.Action;
import client.model.cards.cardsProp.MonsterCard;
import client.model.enums.GameEnums.RequestingInput;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.gameprop.BoardProp.MonsterHouse;
import client.model.gameprop.BoardProp.PlayerBoard;
import client.model.gameprop.Player;
import client.model.gameprop.gamemodel.Game;
import client.model.gameprop.turnBasedObserver.ChangeTeamForOneTurn;
import client.view.game.GetStringInput;

public class ChangeTeamOfMonsterCard extends Action {
    {
        name = this.getClass().getSimpleName();
    }

    @Override
    public void active(Game game) {
        Player currentPlayer = game.getPlayer(SideOfFeature.CURRENT);
        Player opponentPlayer = game.getPlayer(SideOfFeature.OPPONENT);

        PlayerBoard oppoBoard = opponentPlayer.getBoard();
        if (oppoBoard.numberOfFullHouse("monster") == 0) {

        } else {

            MonsterHouse[] oppoPlayerMonsterHouses = oppoBoard.getMonsterHouse();
            MonsterHouse[] currPlayerMonsterHouses = currentPlayer.getBoard().getMonsterHouse();
            String selectedCardName = "";

            while(selectedCardName.length() == 0) { // inputting selected card
                selectedCardName = GetStringInput.getInput(RequestingInput.CHOOSE_FROM_OPPO_MONSTER_HOUSES);
                // TODO: output properly!!!
            }

            for (MonsterHouse oppoPlayerMonsterHouse : oppoPlayerMonsterHouses) { // seeking for the selected card...
                MonsterCard card = oppoPlayerMonsterHouse.getMonsterCard();

                if (card.getName().equals(selectedCardName)) { // found the wanted Card!
                    oppoPlayerMonsterHouse.setMonsterCard(null); // emptying monster house
                    for (MonsterHouse monsterHouse : currPlayerMonsterHouses) { // setting the card at the first empty monsterHouse
                        if (monsterHouse.getMonsterCard() == null) {
                            monsterHouse.setMonsterCard(card);
                        }
                    }

                    //Now we should make an observer for this action!
                    new ChangeTeamForOneTurn(card, currentPlayer, opponentPlayer);
                    break;
                }
            }

        }


    }
}
