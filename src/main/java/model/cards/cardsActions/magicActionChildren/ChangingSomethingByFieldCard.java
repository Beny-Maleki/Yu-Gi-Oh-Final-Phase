package model.cards.cardsActions.magicActionChildren;

import model.cards.cardsProp.MagicCard;
import model.cards.cardsProp.MonsterCard;
import model.enums.GameEnums.SideOfFeature;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.GameInProcess;
import model.gameprop.Player;
import model.gameprop.existenceBasedObserver.FieldCardObserver;
import model.gameprop.gamemodel.Game;

import java.util.ArrayList;

public interface ChangingSomethingByFieldCard {
    default void change(int change, ArrayList<SideOfFeature> changeWhichTeamMonsterPower,
                        ArrayList<String> typesToChangePower, Player ownerOfCard, String whichPower) {

        Game game = GameInProcess.getGame();

        for (SideOfFeature sideOfFeature : changeWhichTeamMonsterPower) {
            MonsterHouse[] monsterHouses = game.getPlayer(sideOfFeature).getBoard().getMonsterHouse();

            for (MonsterHouse monsterHouse : monsterHouses) {
                MonsterCard monsterCard = monsterHouse.getMonsterCard();
                if (monsterCard != null) {
                    if (typesToChangePower.contains(monsterHouse.getMonsterCard().getRace().toString())) {


                        if (whichPower.equals("Attack")) {
                            monsterCard.setAttack(monsterCard.getAttack() + change);
                        } else if (whichPower.equals("Defence")) {
                            monsterCard.setDefence(monsterCard.getDefence() + change);
                        }

                        monsterHouse.setHaveBeenImpactedByField(true);
                        // Finished the job! Let's go home... Wait!NO! It continues...
                    }
                }
            }
        }

        MagicCard fieldCardOfPlayer = ownerOfCard.getBoard().getFieldHouse().getMagicCard();
        new FieldCardObserver(fieldCardOfPlayer, ownerOfCard); // Let the game observe this field card!

    }

    default void revertChange(ArrayList<SideOfFeature> playersToRevert, int revertValue, String whichPower) {
        Game game = GameInProcess.getGame();

        for (SideOfFeature sideOfFeature : playersToRevert) {
            MonsterHouse[] monsterHouses = game.getPlayer(sideOfFeature).getBoard().getMonsterHouse();

            for (MonsterHouse monsterHouse : monsterHouses) {
                if (monsterHouse.getHaveBeenImpactedByField()) { // it should be reverted!
                    if (whichPower.equals("Defence")) {
                        monsterHouse.setAdditionalDefence(monsterHouse.getAdditionalDefence() - revertValue);
                    } else if (whichPower.equals("Attack")) {
                        monsterHouse.setAdditionalAttack(monsterHouse.getAdditionalAttack() - revertValue);
                    }

                }

            }
        }

    }
}
