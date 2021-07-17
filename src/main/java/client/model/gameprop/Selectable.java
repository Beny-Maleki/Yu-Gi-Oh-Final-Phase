package client.model.gameprop;

import connector.cards.Card;

public interface Selectable {
    Card getCard();
    int getIndex();

    String getState();
}
