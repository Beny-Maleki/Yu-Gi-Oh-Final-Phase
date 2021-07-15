package client.model.cards.cardsActions.magicActionChildren;

import client.model.cards.cardsActions.Action;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.gameprop.Player;
import client.model.gameprop.gamemodel.Game;
import client.model.gameprop.turnBasedObserver.AvoidDrawingForSomeTurnsObserver;

public class AvoidOpponentsCardDraw extends Action {
    private int numOfTurns;

    public AvoidOpponentsCardDraw(int numOfTurns) {
        this.setNumOfTurns(numOfTurns);
    }

    {
        name = this.getClass().getSimpleName();
    }

    public void setNumOfTurns(int numOfTurns) {
        this.numOfTurns = numOfTurns;
    }

    @Override
    public void active(Game game) {
        Player opponent = game.getPlayer(SideOfFeature.OPPONENT);

        opponent.isAllowedToDraw = false;

        new AvoidDrawingForSomeTurnsObserver(numOfTurns, opponent); // let the game watch it!
    }
}
