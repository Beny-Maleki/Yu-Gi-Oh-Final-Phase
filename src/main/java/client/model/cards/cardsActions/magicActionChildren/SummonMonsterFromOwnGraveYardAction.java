package client.model.cards.cardsActions.magicActionChildren;

import client.controller.gamecontrollers.GeneralController;
import client.controller.gamecontrollers.GetStringInputFromView;
import client.exceptions.CardNotFoundException;
import client.model.cards.cardsActions.Action;
import connector.cards.MonsterCard;
import client.model.enums.GameEnums.RequestingInput;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.gameprop.BoardProp.PlayerBoard;
import client.model.gameprop.GameInProcess;
import client.model.gameprop.gamemodel.Game;

public class SummonMonsterFromOwnGraveYardAction extends Action {
    {
        name = this.getClass().getSimpleName();
    }

    @Override
    public void active(Game game) {
        PlayerBoard currentPlayerboard = game.getPlayer(SideOfFeature.CURRENT).getBoard();
        GeneralController.getInstance().showGraveYard(GameInProcess.getGame(), "--current");
        String cardToSummon = GetStringInputFromView.getInputFromView(RequestingInput.FROM_GRAVEYARD);
        MonsterCard summonedMonster;
        try {
            summonedMonster = currentPlayerboard.getGraveYard().getMonsterCardFromGraveyardByName(cardToSummon);
            currentPlayerboard.summonMonster(summonedMonster);
            currentPlayerboard.getGraveYard().removeCardFromGraveYard(summonedMonster);
        } catch (CardNotFoundException e) {
            e.printStackTrace();
            active(game);
        }
        isActivatedBefore = true;
    }
}
