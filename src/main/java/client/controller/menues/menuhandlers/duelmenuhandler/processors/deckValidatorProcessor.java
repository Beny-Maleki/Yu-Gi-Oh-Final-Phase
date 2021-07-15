package client.controller.menues.menuhandlers.duelmenuhandler.processors;

import client.controller.menues.menuhandlers.duelmenuhandler.DuelProcessor;
import client.model.enums.MenusMassages.Duel;
import client.model.userProp.Deck;
import client.model.userProp.User;
import client.model.userProp.UserInfoType;

public class deckValidatorProcessor extends DuelProcessor {
    public deckValidatorProcessor(DuelProcessor processor) {
        super(processor);
    }

    public Duel process(String[] data) {
        User userOne = User.getUserByUserInfo(data[0], UserInfoType.USERNAME);
        User userTwo = User.getUserByUserInfo(data[1], UserInfoType.USERNAME);
        Deck userOneActiveDeck;
        Deck userTwoActiveDeck;

        assert userOne != null;
        if ((userOneActiveDeck = userOne.getActiveDeck()) == null) {
            return Duel.USER_ONE_NO_ACTIVE_DECK;
        }

        assert userTwo != null;
        if ((userTwoActiveDeck = userTwo.getActiveDeck()) == null) {
            return Duel.USER_TWO_NO_ACTIVE_DECK;
        }
        if (userOneActiveDeck.getMainDeck().size() < 40) {
            return Duel.USER_ONE_INVALID_ACTIVE_DECK;
        }
        if (userTwoActiveDeck.getMainDeck().size() < 40) {
            return Duel.USER_TWO_INVALID_ACTIVE_DECK;
        }

        return super.process(data);
    }
}
