package client.model.gameprop.existenceBasedObserver;

import connector.cards.Card;
import connector.cards.MagicCard;
import connector.cards.MonsterCard;
import client.model.gameprop.BoardProp.MagicHouse;
import client.model.gameprop.BoardProp.MonsterHouse;
import client.model.gameprop.Player;

import java.util.ArrayList;

public abstract class ExistenceObserver {
    protected static ArrayList<ExistenceObserver> existenceObservers;

    static {
        existenceObservers = new ArrayList<>();
    }

    protected Card observedCard;
    protected Player ownerOfCard;

    public ExistenceObserver(Card observedCard, Player ownerOfCard) {
        this.ownerOfCard = ownerOfCard;
        this.observedCard = observedCard;

        existenceObservers.add(this);
    }

    public static ArrayList<ExistenceObserver> getExistenceObservers() {
        return existenceObservers;
    }

    public static void clearExistenceObserver() {
        existenceObservers.clear();
    }

    public abstract void update();

    public boolean notExists() {
        if (observedCard != null) { // card is assigned to observer!
            if (observedCard instanceof MonsterCard) {
                MonsterHouse[] monsterHouses = ownerOfCard.getBoard().getMonsterHouse();

                for (MonsterHouse monsterHouse : monsterHouses) {
                    MonsterCard monsterCard = monsterHouse.getMonsterCard();
                    if (observedCard.equals(monsterCard)) {
                        return false;
                    }
                }

            } else if (observedCard instanceof MagicCard) {
                MagicHouse[] magicHouses = ownerOfCard.getBoard().getMagicHouse();

                for (MagicHouse magicHouse : magicHouses) {
                    MagicCard magicCard = magicHouse.getMagicCard();
                    if (observedCard.equals(magicCard)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
