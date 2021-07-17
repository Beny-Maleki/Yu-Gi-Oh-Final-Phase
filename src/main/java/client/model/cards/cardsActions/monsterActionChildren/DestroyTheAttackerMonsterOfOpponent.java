package client.model.cards.cardsActions.monsterActionChildren;

import client.model.cards.cardsActions.Action;
import connector.cards.MonsterCard;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.gameprop.BoardProp.MonsterHouse;
import client.model.gameprop.Player;
import client.model.gameprop.gamemodel.Game;

public class DestroyTheAttackerMonsterOfOpponent extends Action {

    {
        name = this.getClass().getName();
    }

    public DestroyTheAttackerMonsterOfOpponent() {

    }

    @Override
    public void active(Game game) {
        MonsterCard attacker = (MonsterCard) game.getCardProp().getCard();
        Player opponent = game.getPlayer(SideOfFeature.OPPONENT);
        MonsterHouse[] monsterHouses = opponent.getBoard().getMonsterHouse();

        for (MonsterHouse monsterHouse : monsterHouses) {
            MonsterCard monsterCard = monsterHouse.getMonsterCard();
            if (monsterCard != null) {
                opponent.getBoard().getGraveYard().addCardToGraveYard(monsterCard);
                monsterHouse.setMonsterCard(null);
            }
        }
    }
}
