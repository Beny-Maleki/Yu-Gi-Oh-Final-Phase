package client.view.menudisplay;

import client.model.enums.Error;
import client.model.enums.MenusMassages.ShopMessages;
import connector.cards.Card;

public class ShopMenuDisplay {
    public static void display(ShopMessages message) {
            System.out.println(message.getMessage());
    }

    public static void display(Error message) {
            System.out.println(message.toString());
    }

    public static void display(ShopMessages message, String field) {
            System.out.printf(message.getMessage(), field);
            System.out.println();
    }

    public static void display(Error message, String field) {
            System.out.printf(message.toString(), field);
            System.out.println();
    }

    public static void printCardDetail(Card card){
        System.out.println( card.getCardDetail());
    }

    public static void printAllCards(Card[] cards) {
        for (Card card : cards) {
            System.out.println(card.getName() + ": " + card.getPrice());
        }
    }

}
