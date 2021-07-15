package client.view.menudisplay;

import client.model.enums.Error;
import client.model.enums.MenusMassages.DeckMessages;
import client.model.cards.cardsProp.Card;
import client.model.cards.cardsProp.MagicCard;
import client.model.cards.cardsProp.MonsterCard;
import client.model.userProp.Deck;

public class DeckMenuDisplay {

    public static void showAllDecks(Deck[] decks, Deck activeDeck) {
        System.out.println("Decks:");
        System.out.println("Active deck:");
        if (activeDeck != null) {
            printMainAndSideDecksNums(activeDeck);
        }
        System.out.println("Other decks:");
        if (decks.length > 0) {
            for (Deck deck : decks) {
                if (deck != activeDeck) printMainAndSideDecksNums(deck);
            }
        }
    }

    public static void showOneMainDeck(Card[] cards, String deckName) {
        DeckMenuDisplay.printOnePartOfOneDeck(cards, deckName, "client.Main deck");
    }

    public static void showOneSideDeck(Card[] cards, String deckName) {
        DeckMenuDisplay.printOnePartOfOneDeck(cards, deckName, "Side deck");
    }

    private static void printOnePartOfOneDeck(Card[] cards, String deckName, String part) {
        System.out.println("Deck: " + deckName);
        System.out.println(part + ":");

        System.out.println("Monsters:");
        monsterCardPrinter(cards);

        System.out.println("Spell and Traps:");
        magicCardPrinter(cards);
    }

    public static void printAllCards(Card[] cards) {
        for (Card card : cards) {
            System.out.println(card.getName() + " : " + card.getDescription());
        }
    }

    private static void monsterCardPrinter(Card[] sectionOfDeck) {
        for (Card card : sectionOfDeck) {
            if (card instanceof MonsterCard) {
                String cardName = card.getName();
                String cardDescription = card.getDescription();
                System.out.println(cardName + ": " + cardDescription);
            }
        }
    }

    private static void magicCardPrinter(Card[] sectionOfDeck) {
        for (Card card : sectionOfDeck) {
            if (card instanceof MagicCard) {
                String cardName = card.getName();
                String cardDescription = card.getDescription();
                System.out.println(cardName + ": " + cardDescription);
            }
        }
    }

    private static void printMainAndSideDecksNums(Deck deck) {
        String name = deck.getName();
        int numMainDeck = deck.getMainDeck().size();
        int numSideDeck = deck.getSideDeck().size();
        String validity = (deck.getValidity()) ? "valid" : "invalid";
        System.out.println(name + ": main deck " + numMainDeck + ", side deck " + numSideDeck + ", " + validity);
    }

    public static void display(DeckMessages message) {
        System.out.println(message.getMessage());
    }

    public static void display(Error message) {
        System.out.println(message.toString());
    }


    public static void printCardDetail(Card card) {
        System.out.println(card.getCardDetail());
    }

    public static void display(DeckMessages message, String field) {
        System.out.printf(message.getMessage(), field);
        System.out.println();

    }

    public static void display(Error message, String field) {
        System.out.printf(message.toString(), field);
        System.out.println();
    }

    public static void display(DeckMessages message, String field1, String field2) {
        System.out.printf(message.getMessage(), field1, field2);
        System.out.println();
    }

    public static void display(Error message, String field1, String field2) {
        System.out.printf(message.toString(), field1, field2);
        System.out.println();
    }
}
