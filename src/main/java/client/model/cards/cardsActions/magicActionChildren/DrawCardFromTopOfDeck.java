package client.model.cards.cardsActions.magicActionChildren;

import client.controller.gamecontrollers.gamestagecontroller.DrawPhaseController;
import client.model.cards.cardsActions.Action;
import client.model.gameprop.gamemodel.Game;

import java.io.FileNotFoundException;

public class DrawCardFromTopOfDeck extends Action {
    int numOfDraws;

    {
        name = this.getClass().getSimpleName();
    }

    public DrawCardFromTopOfDeck(int numOfDraws){
        this.numOfDraws = numOfDraws;
    }

    @Override
    public void active(Game game) throws FileNotFoundException {
        for (int i = 0; i < numOfDraws; i++) {
            (DrawPhaseController.getInstance()).draw(true);
        }
    }
}
