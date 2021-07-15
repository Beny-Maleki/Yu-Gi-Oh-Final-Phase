package client.model.gameprop.turnBasedObserver;

import client.model.gameprop.Player;

public class AvoidDrawingForSomeTurnsObserver extends TurnObserver{

    private Player player;

    public AvoidDrawingForSomeTurnsObserver(int numOfTurns, Player player) {
        super();
        this.remainedTurns = 2 * numOfTurns;
        this.setPlayer(player);
    }

    @Override
    public void update() {
        super.update();
        if (remainedTurns == 0) { // finalize of this observer!
            player.isAllowedToDraw = true;

            turnObservers.remove(this); // observation done successfully!
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
