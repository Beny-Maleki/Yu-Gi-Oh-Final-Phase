package client;

import connector.TradeRequest;
import connector.cards.Card;
import connector.cards.MagicCard;
import connector.cards.MonsterCard;

import java.util.ArrayList;
import java.util.List;

public class UserDataBase {
    private static UserDataBase instance;
    private final List<Card> USER_CARDS;
    private List<MonsterCard> userMonsterCards;
    private List<MagicCard> userMagicCards;
    private ArrayList<TradeRequest> requests;

    {
        USER_CARDS = new ArrayList<>();
        userMonsterCards = new ArrayList<>();
        userMagicCards = new ArrayList<>();
    }

    public static UserDataBase getInstance() {
        if (instance == null) {
            instance = new UserDataBase();
        }
        return instance;
    }

    public List<Card> getCards() {
        return USER_CARDS;
    }

    public Card getCardById(int ID) {
        for (Card card : USER_CARDS) {
            System.out.println("card name is " + card.getName() + "and the card id is  " + card.getID());
            if (card.getID() == ID) {
                return card;
            }
        }
        System.out.println("cant find this  " + ID);
        return null;
    }

    public List<MagicCard> getUserMagicCards() {
        return userMagicCards;
    }

    public void setUserMagicCards(List<MagicCard> userMagicCards) {
        UserDataBase.getInstance().userMagicCards = userMagicCards;
        USER_CARDS.addAll(userMagicCards);
    }

    public List<MonsterCard> getUserMonsterCards() {
        return userMonsterCards;
    }

    public void setUserMonsterCards(List<MonsterCard> userMonsterCards) {
        UserDataBase.getInstance().userMonsterCards = userMonsterCards;
        USER_CARDS.addAll(userMonsterCards);
    }

    public void setUserTradeRequests(ArrayList<TradeRequest> requests) {
        this.requests = requests;
    }
}
