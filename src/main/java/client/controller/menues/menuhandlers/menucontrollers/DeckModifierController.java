package client.controller.menues.menuhandlers.menucontrollers;

import client.controller.Controller;
import client.model.enums.MenusMassages.DeckMessages;
import client.model.userProp.Deck;
import client.model.userProp.LoginUser;
import client.model.userProp.User;
import client.view.menudisplay.DeckMenuDisplay;
import connector.cards.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class DeckModifierController extends Controller {
    private static DeckModifierController instance;

    public DeckModifierController() {
    }

    public static DeckModifierController getInstance() {
        if (instance == null) instance = new DeckModifierController();
        return instance;
    }

    public static ArrayList<Card> cardNameAlphabetSorter(ArrayList<Card> cards) {
        Card[] sortedCards = cards.toArray(new Card[0]);
        Comparator<Card> cardNameSorter = Comparator.comparing(Card::getName);

        Arrays.sort(sortedCards, cardNameSorter);

        return new ArrayList<>(Arrays.asList(sortedCards));
    }

    public static void addCardToMainDeck(Card card, String deckName) {
        User user = LoginUser.getUser();
        Deck selectedDeck = user.getDeckByName(deckName);

        selectedDeck.addCardToMainDeck(card);
        if (user.getCardCollection().contains(card))
            user.removeCardFromUserCollection(card);
        else if (selectedDeck.getSideDeck().contains(card))
            selectedDeck.removeCardFromSideDeck(card);
    }

    public static void addCardToSideDeck(Card card, String deckName) {
        User user = LoginUser.getUser();
        Deck selectedDeck = user.getDeckByName(deckName);

        selectedDeck.addCardToSideDeck(card);
        if (user.getCardCollection().contains(card))
            user.removeCardFromUserCollection(card);
        else if (selectedDeck.getMainDeck().contains(card)) {
            selectedDeck.removeCardFromMainDeck(card);
        }
    }

    public static void removeCardFromMainDeck(Card card, String deckName) {
        Deck selectedDeck = LoginUser.getUser().getDeckByName(deckName);

        selectedDeck.removeCardFromMainDeck(card);
        LoginUser.getUser().addCard(card);
    }

    public static void removeCardFromSideDeck(Card card, String deckName) {
        Deck selectedDeck = LoginUser.getUser().getDeckByName(deckName);
        selectedDeck.removeCardFromSideDeck(card);
        LoginUser.getUser().addCard(card);
        DeckMenuDisplay.display(DeckMessages.SUCCESSFULLY_REMOVE_CARD_FROM_DECK);
    }
}
