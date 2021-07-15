package client.model.gameprop;

import client.model.cards.cardsProp.Card;

public interface Selectable {
    Card getCard();
    int getIndex();

    String getState();
}
