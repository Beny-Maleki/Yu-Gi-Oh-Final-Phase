package client.model.cards.cardsActions.monsterActionChildren;

import client.model.cards.cardsActions.Action;
import connector.cards.MonsterCard;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.gameprop.Player;
import client.model.gameprop.gamemodel.Game;

public class MakeAttackerMonsterAtkZero extends Action {

    {
        name = this.getClass().getSimpleName();
    }

    public MakeAttackerMonsterAtkZero() {
    }

    @Override
    public void active(Game game) {
        Player opponent = game.getPlayer(SideOfFeature.OPPONENT);
        MonsterCard suijin = (MonsterCard) game.getCardProp().getCard(); // attacked monster
        //
        isActivatedBefore = true;
    }
}
