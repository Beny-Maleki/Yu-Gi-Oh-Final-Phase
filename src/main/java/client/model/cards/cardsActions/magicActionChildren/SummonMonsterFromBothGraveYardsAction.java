package client.model.cards.cardsActions.magicActionChildren;

import client.controller.gamecontrollers.GeneralController;
import client.controller.gamecontrollers.GetStringInputFromView;
import client.exceptions.CardNotFoundException;
import client.model.cards.cardsActions.Action;
import client.model.cards.cardsProp.MonsterCard;
import client.model.enums.GameEnums.RequestingInput;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.gameprop.BoardProp.PlayerBoard;
import client.model.gameprop.gamemodel.Game;

public class SummonMonsterFromBothGraveYardsAction extends Action {
    {
        name = this.getClass().getSimpleName();
    }

    @Override
    public void active(Game game) {
        PlayerBoard currentPlayerBoard = game.getPlayer(SideOfFeature.CURRENT).getBoard();
        PlayerBoard opponentPlayerBoard = game.getPlayer(SideOfFeature.OPPONENT).getBoard();

        String graveYardViewer = "Your GraveYard\n" +
                GeneralController.getInstance().showGraveYard(game, "--current") +
                "\n------------------------------------\nYour Enemy GraveYard\n" +
                GeneralController.getInstance().showGraveYard(game, "--opponent");
        System.out.println(graveYardViewer);

        String cardToSummon = GetStringInputFromView.getInputFromView(RequestingInput.FROM_GRAVEYARD);
        MonsterCard summonedMonster;
        try {
            summonedMonster = opponentPlayerBoard.getGraveYard().getMonsterCardFromGraveyardByName(cardToSummon);
            currentPlayerBoard.summonMonster(summonedMonster);
            opponentPlayerBoard.getGraveYard().removeCardFromGraveYard(summonedMonster);
        } catch (CardNotFoundException e) {
            try {
                summonedMonster = currentPlayerBoard.getGraveYard().getMonsterCardFromGraveyardByName(cardToSummon);
                currentPlayerBoard.summonMonster(summonedMonster);
                currentPlayerBoard.getGraveYard().removeCardFromGraveYard(summonedMonster);
            } catch (CardNotFoundException cardNotFoundException) {
                cardNotFoundException.printStackTrace();
                active(game);
            }
        }
        isActivatedBefore = true;
    }
}
