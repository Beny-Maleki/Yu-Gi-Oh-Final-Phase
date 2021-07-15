package client.view.controller;


public class DeckMenu {
    private static DeckMenu deckMenu;

    private DeckMenu() {
    }

    public static DeckMenu getInstance() {
        if (deckMenu == null) {
            deckMenu = new DeckMenu();
        }
        return deckMenu;
    }

}
