package server;

import client.model.userProp.User;
import connector.CardForTrade;
import connector.TradeRequest;
import connector.cards.Card;
import connector.cards.MagicCard;
import connector.cards.MonsterCard;

import java.util.ArrayList;
import java.util.List;

public class ServerDataAnalyser {
    private static ServerDataAnalyser instance;
    private final List<Card> GAME_CARDS;
    private List<MonsterCard> gameMonsterCards;
    private List<MagicCard> gameMagicCards;
    private ArrayList<TradeRequest> tradeRequests;
    private ArrayList<CardForTrade> cardsOnTrades;

    {
        GAME_CARDS = new ArrayList<>();
        tradeRequests = new ArrayList<>();
        cardsOnTrades = new ArrayList<>();
    }

    public static ServerDataAnalyser getInstance() {
        if (instance == null) {
            instance = new ServerDataAnalyser();
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
        ServerDataAnalyser.getInstance().gameMagicCards = gameMagicCards;
        GAME_CARDS.addAll(gameMagicCards);
    }

    public List<MonsterCard> getGameMonsterCards() {
        return gameMonsterCards;
    }

    public void setGameMonsterCards(List<MonsterCard> gameMonsterCards) {
        ServerDataAnalyser.getInstance().gameMonsterCards = gameMonsterCards;
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


    public void putCardForTrade(CardForTrade cardForTrade) {
        cardsOnTrades.add(cardForTrade);
    }

    public ArrayList<CardForTrade> getCardsOnTrades() {
        return cardsOnTrades;
    }
}
