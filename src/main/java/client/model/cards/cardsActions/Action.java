package client.model.cards.cardsActions;

import client.controller.gamecontrollers.GetStringInputFromView;
import client.exceptions.CardNotFoundException;
import client.model.cards.cardsActions.magicActionChildren.ChangingEquipedMonsterAttack;
import client.model.cards.cardsActions.magicActionChildren.ChangingEquippedMonsterDefence;
import client.model.cards.cardsActions.magicActionChildren.ChangingSomeRaceEquipedMonsterAttack;
import client.model.cards.cardsActions.magicActionChildren.ChangingSomeRaceEquipedMonsterDefence;
import client.model.cards.cardsProp.MagicCard;
import client.model.cards.cardsProp.MonsterCard;
import client.model.enums.GameEnums.RequestingInput;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.gameprop.BoardProp.PlayerBoard;
import client.model.gameprop.gamemodel.Game;

import java.io.FileNotFoundException;

public class Action {
    protected String name;
    protected boolean isActivatedBefore;

    public void active(Game game) throws FileNotFoundException {
    }

    public static MonsterCard equipAMonsterWithSpell(Action action, Game game) { // Because of Polymorphism and JSON problems the best approach was this way!!!

        MonsterCard equipedMonster;

        {
            equipedMonster = null;
        }

        ChangingEquipedMonsterAttack changingEquipedMonsterAttack;
        ChangingSomeRaceEquipedMonsterAttack changingSomeRaceEquipedMonsterAttack;
        ChangingEquippedMonsterDefence changingEquippedMonsterDefence;
        ChangingSomeRaceEquipedMonsterDefence changingSomeRaceEquipedMonsterDefence;
        if (action instanceof ChangingEquipedMonsterAttack) {
            changingEquipedMonsterAttack = (ChangingEquipedMonsterAttack) action;
            equipedMonster = changingEquipedMonsterAttack.getEquipedMonster();

        } else if (action instanceof ChangingSomeRaceEquipedMonsterAttack) {
            changingSomeRaceEquipedMonsterAttack = (ChangingSomeRaceEquipedMonsterAttack) action;
            equipedMonster = changingSomeRaceEquipedMonsterAttack.getEquipedMonster();

        } else if (action instanceof  ChangingEquippedMonsterDefence) {
            changingEquippedMonsterDefence = (ChangingEquippedMonsterDefence) action;
            equipedMonster = changingEquippedMonsterDefence.getEquipedMonster();

        } else if (action instanceof ChangingSomeRaceEquipedMonsterDefence) {
            changingSomeRaceEquipedMonsterDefence = (ChangingSomeRaceEquipedMonsterDefence) action;
            equipedMonster = changingSomeRaceEquipedMonsterDefence.getEquipedMonster();

        } // Down cast of "action" to appropriate subclass of "Action" and evaluation of equipMonster


        if (equipedMonster == null) {
            String nameOfMonster = GetStringInputFromView.getInputFromView(RequestingInput.SET_EQUIPED_MONSTER);
            PlayerBoard currentPlayerboard = game.getPlayer(SideOfFeature.CURRENT).getBoard();
            try {
                equipedMonster = currentPlayerboard.getMonsterCardByName(nameOfMonster);
                equipedMonster.setEquippedWith((MagicCard) game.getCardProp().getCard());
            } catch (CardNotFoundException e) {
                equipAMonsterWithSpell(action, game);
            }
        }

        return equipedMonster;
    }
}
