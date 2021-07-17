package client.view.game;

import connector.cards.Card;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.gameprop.BoardProp.*;
import client.model.gameprop.Player;
import client.model.gameprop.gamemodel.Game;

import java.util.concurrent.atomic.AtomicInteger;

public class UserInterface {

    Game game;
    Player opponentPlayer;
    Player currentPlayer;

    public UserInterface(Game game) {
        this.game = game;
        currentPlayer = game.getPlayer(SideOfFeature.CURRENT);
        opponentPlayer = game.getPlayer(SideOfFeature.OPPONENT);
    }

    public String drawBoard() {
        StringBuilder mapDisplay = new StringBuilder();
        drawOpponentPlayerBoard(mapDisplay);
        mapDisplay.append("\n--------------------------");
        drawCurrentPlayerBoard(mapDisplay);
        return mapDisplay.toString();
    }

    public String showSideDeck(Player player) {
        StringBuilder sideDeckDisplay = new StringBuilder();
        sideDeckDisplay.append("\n<CHANGE YOUR STRATEGY>\n\n");
        sideDeckDisplay.append("Player : ").append(player.getUser().getNickname()).append("\n---------------------------------" +
                "\nPlayer Side deck\n");
        AtomicInteger counter = new AtomicInteger(1);
        if (player.getDeck().getSideDeck().size() == 0) {
            sideDeckDisplay.append("YOUR SIDE DECK IS EMPTY \n");
        } else {
            player.getDeck().getSideDeck().forEach((Card card) -> {
                        sideDeckDisplay.append(counter.get()).append(" ) ").append(card.getName()).append("\n");
                        counter.addAndGet(1);
                    }
            );
        }
        sideDeckDisplay.append("---------------------------------\n").
                append("# Show client.Main Deck\n").
                append("\t# Switch <SideDeck Card Num> -> <MaiDeck Card Num>\n").
                append("\t\t# Finish"
                );
        return sideDeckDisplay.toString();
    }

    public String showMainDeck(Player player) {
        StringBuilder mainDeckDisplay = new StringBuilder();
        AtomicInteger counter = new AtomicInteger(1);
        player.getDeck().getMainDeck().forEach((Card card) -> {
            mainDeckDisplay.append(counter.get()).append(" ) ").append(card.getName()).append("\n");
            counter.addAndGet(1);
        });
        return mainDeckDisplay.toString();
    }

    private void drawOpponentPlayerBoard(StringBuilder mapDisplay) {
        mapDisplay.append(opponentPlayer.getUser().getNickname()).append(" : ").
                append(opponentPlayer.getPlayerLifePoint()).append("\n");

        PlayerBoard board = opponentPlayer.getBoard();
        for (HandHouse ignored : board.getPlayerHand()) {
            mapDisplay.append("\tc");
        }
        mapDisplay.append("\n").append(opponentPlayer.
                getDeck().getMainDeck().size()).append("\n");

        MagicHouse[] magicHouses = board.getMagicHouse();
        MonsterHouse[] monsterHouses = board.getMonsterHouse();

        int[] houseOrientation = {4, 2, 1, 3, 5};
        displayGameHouses(mapDisplay, magicHouses, houseOrientation);
        mapDisplay.append("\n");
        displayGameHouses(mapDisplay, monsterHouses, houseOrientation);

        mapDisplay.append("\n\n").append(board.getGraveYard().getDestroyedCards().size()).
                append("\t\t\t\t\t").
                append(board.getFieldHouse().getState());
    }

    private void drawCurrentPlayerBoard(StringBuilder mapDisplay) {
        MagicHouse[] magicHouses = currentPlayer.getBoard().getMagicHouse();
        MonsterHouse[] monsterHouses = currentPlayer.getBoard().getMonsterHouse();
        PlayerBoard board = currentPlayer.getBoard();
        mapDisplay.append("\n").append(board.getGraveYard().getDestroyedCards().size()).
                append("\t\t\t\t\t").
                append(board.getFieldHouse().getState()).append("\n\n");

        int[] houseOrientation = {5, 3, 1, 2, 4};
        displayGameHouses(mapDisplay, monsterHouses, houseOrientation);
        mapDisplay.append("\n");
        displayGameHouses(mapDisplay, magicHouses, houseOrientation);

        mapDisplay.append("\n");
        mapDisplay.append("\n").append(currentPlayer.
                getDeck().getMainDeck().size()).append("\n");


        for (HandHouse ignored : currentPlayer.getBoard().getPlayerHand()) {
            mapDisplay.append("\tc");
        }
        mapDisplay.append("\n").append(currentPlayer.getUser().getNickname()).append(" : ").
                append(currentPlayer.getPlayerLifePoint());
    }

    private void displayGameHouses(StringBuilder mapDisplay, GameHouse[] house, int[] orientation) {

    }

}
