package connector;

import client.model.userProp.User;

public class CardOnTrade {
    private String cardName;
    private int numberOfCard;
    private User user;

    public CardOnTrade(User user, String cardName, int numberOfCard) {
        this.user = user;
        this.cardName = cardName;
        this.numberOfCard = numberOfCard;
    }

    public User getUser() {
        return user;
    }
}
