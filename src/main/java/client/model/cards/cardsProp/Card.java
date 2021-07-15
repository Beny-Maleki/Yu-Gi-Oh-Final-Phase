package client.model.cards.cardsProp;

import javafx.scene.image.Image;
import client.model.events.Event;
import client.model.gameprop.gamemodel.Game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Card {
    private static final HashMap<Integer, Boolean> IS_SEEN_BEFORE;
    protected static List<Card> cards;
    public static int numberOfCard;
    public static int numberOfOriginalCards;

    static {
        IS_SEEN_BEFORE = new HashMap<>();
        cards = new ArrayList<>();
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
        setID(numberOfCard);
        numberOfCard++;
    }


    public Card() {
    }

    public static HashMap<Integer, Boolean> getIsSeenBefore() {
        return IS_SEEN_BEFORE;
    }

    public static int getCardPriceByName(String name) {
        for (Card card : cards) {
            if (card.name.equals(name)) {
                return card.price;
            }
        }
        return -1; // No such card notExists.
    }

    public static void setNumberOfOriginalCards(int numberOfOriginalCards) {
        Card.numberOfOriginalCards = numberOfOriginalCards;
    }

    public static String getDescriptionByName(String name) {
        for (Card card : cards) {
            if (card.name.equals(name)) {
                return card.description;
            }
        }
        return null; // No such card notExists.
    }

    public static void setNumberOfCard(int numberOfCard) {
        Card.numberOfCard = numberOfCard;
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

    public static ArrayList<Card> getOriginalCard() {
        ArrayList<Card> originalCard = new ArrayList<>();
        cards.forEach((card -> {
            if (card.getID() < Card.numberOfOriginalCards) {
                originalCard.add(card);
            }
        }));
        return originalCard;
    }

    public static Card getCardById(int ID) {
        for (Card card : cards) {
            if (card.ID == ID) {
                return card;
            }
        }
        System.out.println("cant find " + ID);
        return null;
    }

    public static Integer newSimilarCard() {
        return numberOfCard - 1;

    }

    public static Image getCardImage(Card card) {
        FileInputStream fileInputStream = null;
        String nameWithoutSpace = card.getName().replaceAll("\\s+", "");
        try {
            if (card instanceof MonsterCard) {
                fileInputStream = new FileInputStream("src/main/resources/graphicprop/images/Cards/Monsters/" + nameWithoutSpace + ".jpg");
            } else{
                fileInputStream = new FileInputStream("src/main/resources/graphicprop/images/Cards/SpellTrap/" + nameWithoutSpace + ".jpg");
            }
        } catch (FileNotFoundException e) {
                try {
                    if (card instanceof MonsterCard) {
                        fileInputStream = new FileInputStream("src/main/resources/graphicprop/images/Cards/Monsters/newMonster.jpg");
                    }
                    else {
                        fileInputStream = new FileInputStream("src/main/resources/graphicprop/images/Cards/SpellTrap/newMagic.jpg");
                    }
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
        }
        assert fileInputStream != null;
        return new Image(fileInputStream);
    }

    public abstract Card getSimilarCard();

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

    public String getDescriptionWithDifferentLine() {
        StringBuilder result = new StringBuilder();
        String[] arrayRes = description.split(" ");
        for (int i = 0; i < arrayRes.length; i++) {
            if (i % 7 == 0 && i != 0) {
                result.append("\n");
            } else {
                result.append(arrayRes[i]).append(" ");
            }
        }
        return result.toString();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void activeEffectsByEvent(Event event, Game game) throws FileNotFoundException {
    }

    public abstract Card getCopy(); // semi duplicate code in overrides; cause -> Card is abstract and not constructable!

    public String getCardDescriptionWithEnters() {
        if (description.length() > 40) {
            int startIndex = 0;
            int endIndex = 0;
            StringBuilder result = new StringBuilder();
            while (endIndex < description.length()) {
                int nextEndOfLineIndex = Math.min(startIndex + 35, description.length() - 1);

                if (description.charAt(nextEndOfLineIndex) != ' ') {
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

    public void setID(int ID) {
        this.ID = ID;
    }
}
