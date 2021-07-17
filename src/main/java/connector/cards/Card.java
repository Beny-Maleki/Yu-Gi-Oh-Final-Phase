package connector.cards;

import client.model.events.Event;
import client.model.gameprop.gamemodel.Game;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public abstract class Card {
    public static int numberOfCard;
    public static int numberOfOriginalCards;


    protected int ID;
    protected String name;
    protected String number; // on card's picture
    protected int price;
    protected String description;

    public Card(String name, String description, String price) {
        setName(name);
        setDescription(description);
        setPrice(price);
        setID(numberOfCard);
        numberOfCard++;
    }


    public Card() {
    }

    public static void setNumberOfOriginalCards(int numberOfOriginalCards) {
        Card.numberOfOriginalCards = numberOfOriginalCards;
    }

    public static void setNumberOfCard(int numberOfCard) {
        Card.numberOfCard = numberOfCard;
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
