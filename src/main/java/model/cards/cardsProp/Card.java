package model.cards.cardsProp;

import model.events.Event;
import model.gameprop.gamemodel.Game;

import java.util.ArrayList;
import java.util.List;

public abstract class Card {
    protected static List<Card> cards;
    protected static int numberOfCard;

    static {
        cards = new ArrayList<>();
        numberOfCard = 115;
    }

    protected int ID;
    protected String name;
    protected String number; // on card's picture
    protected int price;
    protected String description;


    public Card(String name, String description, String price) {
        setName(name);
        setDescription(description);
        setPrice(price);
        cards.add(this);
        ID = numberOfCard;
        numberOfCard++;
    }

    public Card() {
    }

    public abstract Card getSimilarCard();


    public static int getCardPriceByName(String name) {
        for (Card card : cards) {
            if (card.name.equals(name)) {
                return card.price;
            }
        }
        return -1; // No such card notExists.
    }

    public static String getDescriptionByName(String name) {
        for (Card card : cards) {
            if (card.name.equals(name)) {
                return card.description;
            }
        }
        return null; // No such card notExists.
    }

    public static void addMagicsToCards(ArrayList<MagicCard> magicCards) {
        cards.addAll(magicCards);
    }

    public static void addMonstersToCards(ArrayList<MonsterCard> monsterCards) {
        cards.addAll(monsterCards);
    }

    public static List<Card> getCards() {
        return cards;
    }

    public abstract String getCardDetail();

    public abstract String getCardDetailWithEnters();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = Integer.parseInt(price);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void activeEffectsByEvent(Event event, Game game) {
    }

    public abstract Card getCopy(); // semi duplicate code in overrides; cause -> Card is abstract and not constructable!

    public static Card getCardById(int ID) {
        for (Card card : cards) {
            if (card.ID == ID) {
                return card;
            }
        }
        return null;
    }

    public String getCardDescriptionWithEnters() {
        if (description.length() > 40) {
            int startIndex = 0;
            int endIndex = 0;
            StringBuilder result = new StringBuilder();
            while (endIndex < description.length()) {
                int nextEndOfLineIndex = Math.min(startIndex + 35, description.length() - 1);

                if(description.charAt(nextEndOfLineIndex) != ' ') {
                    if (nextEndOfLineIndex == description.length() - 1) {
                        result.append(description, startIndex, nextEndOfLineIndex);
                        break;
                    }

                    int lastSpaceIndex = 0;
                    for (int i = startIndex; i < nextEndOfLineIndex; i++) {
                        if (description.charAt(i) == ' ') {
                            lastSpaceIndex = i;
                        }
                    }

                    result.append(description, startIndex, lastSpaceIndex).append("\n");
                    startIndex = lastSpaceIndex + 1;
                    endIndex = lastSpaceIndex;
                } else {
                    result.append(description, startIndex, nextEndOfLineIndex).append("\n");
                    startIndex = nextEndOfLineIndex + 1;
                    endIndex = nextEndOfLineIndex;
                }
            }
            return result.toString();
        } else {
            return description;
        }
    }



    public Integer getID() {
        return ID;
    }

}
