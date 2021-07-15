package client.model.userProp;

import client.model.cards.cardsProp.Card;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class Deck {
    private static int totalNumberOfDeck;
    private static ArrayList<Deck> allDecks;
    private static int numberOfOriginalCards;

    static {
        allDecks = new ArrayList<>();
        totalNumberOfDeck = 0;
    }

    private final ArrayList<Integer> mainDeck;
    private final ArrayList<Integer> sideDeck;
    private String name;
    private String ID;
    private boolean isDeckActivated;

    {
        mainDeck = new ArrayList<>();
        sideDeck = new ArrayList<>();
        isDeckActivated = false;
    }

    public Deck(String name) {
        setName(name);
        allDecks.add(this);
        totalNumberOfDeck++;
        setID();
    }

    public static void setNumberOfOriginalCards(int numberOfOriginalCards) {
        Deck.numberOfOriginalCards = numberOfOriginalCards;
    }

    public static Deck getDeckById(String ID) {
        for (Deck deck : Deck.allDecks) {
            if (deck.ID.equals(ID))
                return deck;
        }
        return null;
    }

    public static ArrayList<Deck> getAllDecks() {
        return allDecks;
    }

    public static void setAllDecks(ArrayList<Deck> allDecks) {
        Deck.allDecks = allDecks;
    }

    public static void serialize() {
        for (Deck deck : allDecks) {
            ArrayList<Integer> mainDeck = deck.mainDeck;
            ArrayList<Integer> sideDeck = deck.sideDeck;
            findSimilarCard(mainDeck);
            findSimilarCard(sideDeck);
        }
    }

    static void findSimilarCard(ArrayList<Integer> mainDeck) {
        for (int i = 0; i < mainDeck.size(); i++) {
            Integer ID = mainDeck.get(i);
            if (ID >= numberOfOriginalCards) {
                for (Card card : Card.getOriginalCard()) {
                    Card similarCard = Card.getCardById(ID);
                    assert similarCard != null;
                    if (card.getName().equals(similarCard.getName())) {
                        mainDeck.set(i, card.getID());
                        break;
                    }
                }
            }
        }
    }

    public static void deSerialize() {
        HashMap<Integer, Boolean> isIDSeenBefore = Card.getIsSeenBefore();
        for (Deck deck : allDecks) {
            ArrayList<Integer> mainDeck = deck.mainDeck;
            ArrayList<Integer> sideDeck = deck.sideDeck;
            findSimilarCard(isIDSeenBefore, mainDeck);
            findSimilarCard(isIDSeenBefore, sideDeck);
        }

    }

    private static void findSimilarCard(HashMap<Integer, Boolean> isIDSeenBefore, ArrayList<Integer> sideDeck) {
        for (int i = 0; i < sideDeck.size(); i++) {
            Integer ID = sideDeck.get(i);
            if (isIDSeenBefore.containsKey(ID)) {
                Card card = Card.getCardById(ID);
                assert card != null;
                card.getSimilarCard();
              sideDeck.set(i, Card.newSimilarCard());
            } else {
                isIDSeenBefore.put(ID, true);
            }
        }
    }

    public ArrayList<Card> getMainDeck() {
        ArrayList<Card> mainDeckCards = new ArrayList<>();
        for (Integer ID : mainDeck) {
            mainDeckCards.add(Card.getCardById(ID));
        }
        return mainDeckCards;
    }

    public ArrayList<Card> getSideDeck() {
        ArrayList<Card> sideDeckCards = new ArrayList<>();
        for (Integer ID : sideDeck) {
            sideDeckCards.add(Card.getCardById(ID));
        }
        return sideDeckCards;
    }

    public boolean getValidity() {
        return (mainDeck.size() >= 40);
    }

    public Deck getCopy() { // Somehow "Prototype pattern" is implemented
        Deck copy = new Deck(this.name);
        Deck.getAllDecks().remove(copy);
        copy.isDeckActivated = this.isDeckActivated;

        for (Card card : this.getMainDeck()) {
            copy.mainDeck.add(card.getCopy().getID());
        }

        for (Card card : this.getSideDeck()) {
            copy.sideDeck.add(card.getCopy().getID());
        }

        return copy;
    }

    public void setDeckActivated(boolean deckActivated) {
        this.isDeckActivated = deckActivated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void removeCardFromMainDeck(Card card) {
        mainDeck.remove(card.getID());
    }

    public void removeCardFromSideDeck(Card card) {
        this.sideDeck.remove(card.getID());
    }

    public void addCardToMainDeck(Card card) {
        this.mainDeck.add(card.getID());
    }

    public void addCardToSideDeck(Card card) {
        this.sideDeck.add(card.getID());
    }

    public void deleteDeckFromOwner() {
        allDecks.remove(this);
    }

    public int numOfCardOccurrence(String cardName, String where) {
        int mainDeckCounter = 0;
        int sideDeckCounter = 0;
        if (where.equals("main deck") || where.equals("both decks")) {
            for (Card card : this.getMainDeck()) { // mainDeck count:
                if (card.getName().equals(cardName)) {
                    mainDeckCounter++;
                }
            }
        }

        if (where.equals("side deck") || where.equals("both decks")) {
            for (Card card : this.getSideDeck()) { // sideDeck count:
                if (card.getName().equals(cardName)) {
                    sideDeckCounter++;
                }
            }
        }
        return mainDeckCounter + sideDeckCounter; // in "single deck" situations, one of the counters would automatically be zero.
    }

    public void switchCardBetweenMainDeckAndSideDeck(int mainDeckCardNum, int sideDeckCardNum) {
        int mainDeckCardId = mainDeck.get(mainDeckCardNum);
        int sideDeckCardId = sideDeck.get(sideDeckCardNum);

        mainDeck.set(mainDeckCardNum, sideDeckCardId);
        sideDeck.set(sideDeckCardNum, mainDeckCardId);
    }

    public void setID() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime time = LocalDateTime.now();
        ID = format.format(time) + totalNumberOfDeck;
    }

    public String getID() {
        return ID;
    }

    public boolean isDeckActivated() {
        return isDeckActivated;
    }
}
