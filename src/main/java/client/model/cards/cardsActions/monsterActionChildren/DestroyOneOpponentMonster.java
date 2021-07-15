package client.model.cards.cardsActions.monsterActionChildren;

import client.controller.gamecontrollers.GetStringInputFromView;
import client.model.cards.cardsActions.Action;
import client.model.cards.cardsProp.MonsterCard;
import client.model.enums.GameEnums.RequestingInput;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.gameprop.BoardProp.MonsterHouse;
import client.model.gameprop.Player;
import client.model.gameprop.gamemodel.Game;

public class DestroyOneOpponentMonster extends Action {
    {
        name = this.getClass().getSimpleName();
    }

    @Override
    public void active(Game game) {
        String nameOfTargetMonster = GetStringInputFromView.getInputFromView(RequestingInput.CHOOSE_FROM_OPPO_MONSTER_HOUSES);
        Player opponent = game.getPlayer(SideOfFeature.OPPONENT);
        MonsterHouse[] monsterHouses = opponent.getBoard().getMonsterHouse();

        for (MonsterHouse monsterHouse : monsterHouses) {
            MonsterCard monsterCard = monsterHouse.getMonsterCard();
            if (monsterCard != null) { // checking emptiness of a house!
                if (monsterCard.getName().equals(nameOfTargetMonster)) { // detected the target!
                    opponent.getBoard().getGraveYard().addCardToGraveYard(monsterCard); // send target to graveyard
                    monsterHouse.setMonsterCard(null); // make empty
                    break;
                }
            }
        }
        isActivatedBefore = true;
    }
}
