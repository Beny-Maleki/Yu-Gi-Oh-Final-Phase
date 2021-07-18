package server;

import client.model.userProp.User;
import connector.TradeRequest;
import connector.cards.Card;
import connector.cards.MagicCard;
import connector.cards.MonsterCard;

import java.util.ArrayList;
import java.util.List;

public class ServerDataAnalyse {
    private static ServerDataAnalyse instance;
    private final List<Card> GAME_CARDS;
    private List<MonsterCard> gameMonsterCards;
    private List<MagicCard> gameMagicCards;
    private ArrayList<TradeRequest> tradeRequests;

    {
        GAME_CARDS = new ArrayList<>();
        tradeRequests = new ArrayList<>();
    }

    public static ServerDataAnalyse getInstance() {
        if (instance == null) {
            instance = new ServerDataAnalyse();
        }
        return instance;
    }

    public ArrayList<Card> convertIDsToCard(ArrayList<Integer> IDsArrayList) {
        ArrayList<Card> cards = new ArrayList<>();
        for (Integer integer : IDsArrayList) {
            cards.add(getCardById(integer));
        }
        return cards;
    }

    public List<Card> getCards() {
        return GAME_CARDS;
    }

    public Card getCardById(int ID) {
        for (Card card : GAME_CARDS) {
            if (card.getID() == ID) {
                return card;
            }
        }
        return null;
    }

    public List<MagicCard> getGameMagicCards() {
        return gameMagicCards;
    }

    public void setGameMagicCards(List<MagicCard> gameMagicCards) {
        ServerDataAnalyse.getInstance().gameMagicCards = gameMagicCards;
        GAME_CARDS.addAll(gameMagicCards);
    }

    public List<MonsterCard> getGameMonsterCards() {
        return gameMonsterCards;
    }

    public void setGameMonsterCards(List<MonsterCard> gameMonsterCards) {
        ServerDataAnalyse.getInstance().gameMonsterCards = gameMonsterCards;
        GAME_CARDS.addAll(gameMonsterCards);
    }

    public ArrayList<TradeRequest> findAllTradeRequestOfThisUser(User user) {
        ArrayList<TradeRequest> requests = new ArrayList<>();
        for (TradeRequest tradeRequest : tradeRequests) {
            if (tradeRequest.getTradeRequest().getUser() == user) {
                requests.add(tradeRequest);
            }
        }
        return requests;
    }


}
