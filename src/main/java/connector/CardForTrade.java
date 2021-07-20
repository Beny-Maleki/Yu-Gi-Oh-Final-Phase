package connector;

import client.model.userProp.User;

public class CardForTrade {
    private final String cardName;
    private final int numberOfCard;
    private final User user;

    public CardForTrade(User user, String cardName, int numberOfCard) {
        this.user = user;
        this.cardName = cardName;
        this.numberOfCard = numberOfCard;
    }

    public User getUser() {
        return user;
    }

    public int getNumberOfCard() {
        return numberOfCard;
    }

    public String getCardName() {
        return cardName;
    }
}
