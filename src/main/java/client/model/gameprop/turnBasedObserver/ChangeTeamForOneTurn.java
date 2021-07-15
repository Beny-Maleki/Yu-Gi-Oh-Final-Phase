package client.model.gameprop.turnBasedObserver;

import client.model.cards.cardsProp.Card;
import client.model.cards.cardsProp.MonsterCard;
import client.model.enums.GameEnums.cardvisibility.MonsterHouseVisibilityState;
import client.model.gameprop.BoardProp.MonsterHouse;
import client.model.gameprop.Player;


public class ChangeTeamForOneTurn extends TurnObserver{
    Card card;
    Player actionActivator;
    Player opponent;

    public ChangeTeamForOneTurn(Card card, Player actionActivator, Player opponent) {
        super();
        this.setActionActivator(actionActivator);
        this.setCard(card);
        this.setOpponent(opponent);
        this.remainedTurns = 1;
    }

    public void setActionActivator(Player actionActivator) {
        this.actionActivator = actionActivator;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    @Override
    public void update() {
        super.update();
        if (remainedTurns == 0) {
            MonsterHouse[] oppoMonsterHouses = opponent.getBoard().getMonsterHouse();
            MonsterHouse[] activatorMonsterHouses = actionActivator.getBoard().getMonsterHouse();

            for (MonsterHouse iterator : activatorMonsterHouses) { // deleting card from Effect Activators MonsterHouse
                if (iterator.getMonsterCard().equals(this.card)) { // identifying the occupied monsterHouse
                    iterator.setMonsterCard(null);
                }
            }

            for (MonsterHouse iterator : oppoMonsterHouses) {
                if (iterator.getMonsterCard() == null) { // empty monster house...
                    iterator.setMonsterCard(((MonsterCard) this.card));
                    iterator.setState(MonsterHouseVisibilityState.OO);
                }
            }

            turnObservers.remove(this); // observation finished successfully!
        }
    }

}
