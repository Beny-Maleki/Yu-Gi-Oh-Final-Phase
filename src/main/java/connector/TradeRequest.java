package connector;

import client.model.userProp.User;

public class TradeRequest {
    private CardForTrade tradeRequest;
    private User user;
    private String cardName;
    private int numberOfCard;

    public TradeRequest(CardForTrade tradeRequest, User user, String cardName, int numberOfCard) {
        this.tradeRequest = tradeRequest;
        this.user = user;
        this.cardName = cardName;
        this.numberOfCard = numberOfCard;
    }

    public CardForTrade getTradeRequest() {
        return tradeRequest;
    }

    public User getUser() {
        return user;
    }

    public String getCardName() {
        return cardName;
    }

    public int getNumberOfCard() {
        return numberOfCard;
    }
}
