package client.controller.menues.menuhandlers.menucontrollers;

import client.controller.Controller;
import client.model.cards.cardsProp.Card;
import client.model.enums.Error;
import client.model.enums.MenusMassages.DeckMessages;
import client.model.userProp.Deck;
import client.model.userProp.LoginUser;
import client.model.userProp.User;
import client.view.menudisplay.DeckMenuDisplay;

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

    public static void showCurrent() {
        DeckMenuDisplay.display(DeckMessages.CURRENT_MENU);
    }

    public static void createDeck(String name) {
        if (LoginUser.getUser().getDeckByName(name) != null) {
            DeckMenuDisplay.display(Error.REPETITIOUS_DECK_NAME, name);
        } else {
            Deck deck = new Deck(name);
            DeckMenuDisplay.display(DeckMessages.SUCCESSFULLY_CREATE_DECK);
        }
    }

    public static void deleteDeck(String name) {
        if (LoginUser.getUser().getDeckByName(name) == null) {
            DeckMenuDisplay.display(Error.NOT_FOUND_DECK_NAME, name);
        } else {
            Deck selectedDeck = LoginUser.getUser().getDeckByName(name);
            selectedDeck.deleteDeckFromOwner();
            DeckMenuDisplay.display(DeckMessages.SUCCESSFULLY_DELETE_DECK);
        }
    }

    public static void activateDeck(String name) {
        if (LoginUser.getUser().getDeckByName(name) == null) {
            DeckMenuDisplay.display(Error.NOT_FOUND_DECK_NAME, name);
        } else {
            Deck selectedDeck = LoginUser.getUser().getDeckByName(name);
            LoginUser.getUser().setActiveDeck(selectedDeck);
            DeckMenuDisplay.display(DeckMessages.SUCCESSFULLY_ACTIVATE_DECK);
        }
    }

    public static void showAllDecks() {
        ArrayList<Deck> decks = LoginUser.getUser().getAllUserDecksId();
        Deck activeDeck = LoginUser.getUser().getActiveDeck();
        Deck[] sortedDecks = DeckModifierController.deckNameAlphabetSorter(decks);
        DeckMenuDisplay.showAllDecks(sortedDecks, activeDeck);
    }

//    public static void showOneMainDeck(String deckName) {
//        Deck deck = LoginUser.getUser().getDeckByName(deckName);
//        if (deck == null) {
//            DeckMenuDisplay.display(Error.NOT_FOUND_DECK_NAME, deckName);
//        } else {
//            Card[] sortedMainDeck = DeckModifierController.cardNameAlphabetSorter(deck.getMainDeck());
//            DeckMenuDisplay.showOneMainDeck(sortedMainDeck, deckName);
//        }
//    }

//    public static void showOneSideDeck(String deckName) {
//        Deck deck = LoginUser.getUser().getDeckByName(deckName);
//        if (deck == null) {
//            DeckMenuDisplay.display(Error.NOT_FOUND_DECK_NAME, deckName);
//        } else {
//            Card[] sortedSideDeck = DeckModifierController.cardNameAlphabetSorter(deck.getSideDeck());
//            DeckMenuDisplay.showOneSideDeck(sortedSideDeck, deckName);
//        }
//    }

//    public static void showAllCardsOfUser() {
//        User user = LoginUser.getUser();
//        ArrayList<Card> unionOfDecksAndCollection;
//
//        unionOfDecksAndCollection = new ArrayList<>(user.getUserCardCollection());
//        for (Deck deck : user.getAllUserDecksId()) {
//            unionOfDecksAndCollection.addAll(deck.getMainDeck());
//            unionOfDecksAndCollection.addAll(deck.getSideDeck());
//        }
//
//        Card[] sortedCards = DeckModifierController.cardNameAlphabetSorter(unionOfDecksAndCollection);
//        DeckMenuDisplay.printAllCards(sortedCards);
//    }

    public static Deck[] deckNameAlphabetSorter(ArrayList<Deck> decks) {
        Deck[] sortedDecks = decks.toArray(new Deck[0]);
        Comparator<Deck> deckNameSorter = Comparator.comparing(Deck::getName);

        Arrays.sort(sortedDecks, deckNameSorter);
        return sortedDecks;
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
