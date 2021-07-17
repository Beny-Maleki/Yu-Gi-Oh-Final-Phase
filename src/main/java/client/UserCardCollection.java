package client;

import connector.cards.Card;
import connector.cards.MagicCard;
import connector.cards.MonsterCard;

import java.util.ArrayList;
import java.util.List;

public class UserCardCollection {
    private static final List<Card> USER_CARDS;
    private static List<MonsterCard> userMonsterCards;
    private static List<MagicCard> userMagicCards;

    static {
        USER_CARDS = new ArrayList<>();
        userMonsterCards = new ArrayList<>();
        userMagicCards = new ArrayList<>();
    }

    public static List<Card> getCards() {
        return USER_CARDS;
    }

    public static Card getCardById(int ID) {
        for (Card card : USER_CARDS) {
            if (card.getID() == ID) {
                return card;
            }
        }
        System.out.println("cant find " + ID);
        return null;
    }

    public static List<MagicCard> getUserMagicCards() {
        return userMagicCards;
    }

    public static void setUserMagicCards(List<MagicCard> userMagicCards) {
        UserCardCollection.userMagicCards = userMagicCards;
        USER_CARDS.addAll(userMagicCards);
    }

    public static List<MonsterCard> getUserMonsterCards() {
        return userMonsterCards;
    }

    public static void setUserMonsterCards(List<MonsterCard> userMonsterCards) {
        UserCardCollection.userMonsterCards = userMonsterCards;
        USER_CARDS.addAll(userMonsterCards);
    }
}
