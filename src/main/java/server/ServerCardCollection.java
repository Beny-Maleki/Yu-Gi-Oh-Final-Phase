package server;

import connector.cards.Card;
import connector.cards.MagicCard;
import connector.cards.MonsterCard;

import java.util.ArrayList;
import java.util.List;

public class ServerCardCollection {
    private static final List<Card> GAME_CARDS;
    private static List<MonsterCard> gameMonsterCards;
    private static List<MagicCard> gameMagicCards;

    static {
        GAME_CARDS = new ArrayList<>();
    }

    public static List<Card> getCards() {
        return GAME_CARDS;
    }

    public static Card getCardById(int ID) {
        for (Card card : GAME_CARDS) {
            if (card.getID() == ID) {
                return card;
            }
        }
        return null;
    }

    public static void setGameMagicCards(List<MagicCard> gameMagicCards) {
        ServerCardCollection.gameMagicCards = gameMagicCards;
        GAME_CARDS.addAll(gameMagicCards);
    }

    public static void setGameMonsterCards(List<MonsterCard> gameMonsterCards) {
        ServerCardCollection.gameMonsterCards = gameMonsterCards;
        GAME_CARDS.addAll(gameMonsterCards);
    }

    public static List<MagicCard> getGameMagicCards() {
        return gameMagicCards;
    }

    public static List<MonsterCard> getGameMonsterCards() {
        return gameMonsterCards;
    }
}
