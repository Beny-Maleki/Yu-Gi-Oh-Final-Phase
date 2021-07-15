package client.model.cards.cardsActions.magicActionChildren;

import client.controller.gamecontrollers.GeneralController;
import client.model.cards.cardsActions.Action;
import client.model.gameprop.gamemodel.Game;

import java.io.FileNotFoundException;

public class EndBattlePhaseAction extends Action {
    {
        name = this.getClass().getSimpleName();
    }

    @Override
    public void active(Game game) throws FileNotFoundException {
        GeneralController.getInstance().nextPhase(game);
        isActivatedBefore = true;
    }
}
