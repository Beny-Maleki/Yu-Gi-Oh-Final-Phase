package client.model.cards;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import connector.cards.Card;
import client.model.enums.Origin;

import java.util.ArrayList;

public class CardHouse {
    private static ArrayList<CardHouse> cardHousesInShopMenu;
    private static ArrayList<CardHouse> cardHousesInDeckMenu;

    static {
        cardHousesInShopMenu = new ArrayList<>();
        cardHousesInDeckMenu = new ArrayList<>();
    }

    private Card card;
    private ImageView imageView;
    private Image image;

    public CardHouse(Card card, ImageView imageView, Image image, Origin origin) {
        this.card = card;
        this.imageView =imageView;
        this.image = image;
        if (origin == Origin.SHOP_MENU) cardHousesInShopMenu.add(this); // this two should be cleared each time you enter shop!
        else if (origin == Origin.DECK_MENU) cardHousesInShopMenu.add(this);
    }

    public Card getCard() {
        return card;
    }

    public Image getImage() {
        return image;
    }

    public static ArrayList<CardHouse> getCardHousesInShopMenu() {
        return cardHousesInShopMenu;
    }
}
