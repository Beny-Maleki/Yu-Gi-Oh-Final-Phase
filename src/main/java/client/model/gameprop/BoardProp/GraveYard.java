package client.model.gameprop.BoardProp;

import client.exceptions.CardNotFoundException;
import connector.cards.Card;
import connector.cards.MagicCard;
import connector.cards.MonsterCard;

import java.util.ArrayList;

public class GraveYard {
    ArrayList<Card> destroyedCards;

    {
        destroyedCards = new ArrayList<>();
    }

    public ArrayList<Card> getDestroyedCards() {
        return destroyedCards;
    }

    public void addCardToGraveYard(Card card) {
        if (card instanceof MagicCard) ((MagicCard) card).setActivated(false);
        destroyedCards.add(card);
    }

    public void removeCardFromGraveYard(Card card) {
        destroyedCards.remove(card);
    }

    public MonsterCard getMonsterCardFromGraveyardByName(String name) throws CardNotFoundException {
        for (Card destroyedCard : destroyedCards) {
            if (destroyedCard.getName().equals(name)) {
                if (destroyedCard instanceof MonsterCard) {
                    return (MonsterCard) destroyedCard;
                }
            }
        }
        throw new CardNotFoundException("monster card not found!");
    }
}
