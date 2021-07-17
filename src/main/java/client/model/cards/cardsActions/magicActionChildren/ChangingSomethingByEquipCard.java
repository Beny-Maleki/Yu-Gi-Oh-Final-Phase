package client.model.cards.cardsActions.magicActionChildren;

import client.controller.gamecontrollers.GetStringInputFromView;
import connector.cards.MagicCard;
import connector.cards.MonsterCard;
import client.model.enums.GameEnums.RequestingInput;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.enums.GameEnums.cardvisibility.MonsterHouseVisibilityState;
import client.model.gameprop.BoardProp.MonsterHouse;
import client.model.gameprop.GameInProcess;
import client.model.gameprop.Player;
import client.model.gameprop.existenceBasedObserver.EquipCardObserver;
import client.model.gameprop.gamemodel.Game;

public interface ChangingSomethingByEquipCard {
    default String change(int changeValue, MonsterCard equippedCard, MagicCard equipCard, String whichPower, Player ownerOfCard) {
        if (!isMonsterFaceUp(equippedCard)) {
            GetStringInputFromView.getInputFromView(RequestingInput.ERROR_OF_INVALID_MONSTER_CARD_EQUIPPED);
            return "Error";
        }

        MonsterHouse monsterHouseOfEquipped = MonsterHouse.getMonsterHouseByMonsterCard(equippedCard);

        if (whichPower.equals("Attack")) {
            assert monsterHouseOfEquipped != null;
            monsterHouseOfEquipped.setAdditionalAttack(monsterHouseOfEquipped.getAdditionalAttack() + changeValue);
        } else if (whichPower.equals("Defence")) {
            assert monsterHouseOfEquipped != null;
            monsterHouseOfEquipped.setAdditionalDefence(monsterHouseOfEquipped.getAdditionalDefence() + changeValue);
        }

        equippedCard.setEquippedWith(equipCard);

        new EquipCardObserver(equippedCard, ownerOfCard);
        return "Successful";
    }

    default boolean isMonsterFaceUp(MonsterCard monsterCard) {
        Game game = GameInProcess.getGame();
        Player ownerOfCard = game.getPlayer(SideOfFeature.CURRENT);
        MonsterHouse[] monsterHouses = ownerOfCard.getBoard().getMonsterHouse();
        for (MonsterHouse monsterHouse : monsterHouses) {
            if (monsterHouse.getMonsterCard().equals(monsterCard)) {
                if (!monsterHouse.getState().equals(MonsterHouseVisibilityState.DH)) { // isn't defensive hidden!
                    return true;
                }
            }
        }
        return false;
    }

}
