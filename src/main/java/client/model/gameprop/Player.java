package client.model.gameprop;

import client.model.gameprop.BoardProp.HandHouse;
import client.model.gameprop.BoardProp.PlayerBoard;
import client.model.userProp.Deck;
import client.model.userProp.FatherUser;

import java.io.FileNotFoundException;

public class Player {
    public boolean isAllowedToDraw;
    public int playerLifePoint;
    FatherUser user;
    PlayerBoard board;
    Deck deck;
    int numberOfWinningRound;

    {
        isAllowedToDraw = true;
        playerLifePoint = 8000;
    }

    public Player(FatherUser user, int numberOfWinningRound) throws FileNotFoundException {
        setUser(user);
        board = new PlayerBoard();
        deck = user.getActiveDeck().getCopy();
        gameSetUp();
        this.numberOfWinningRound = numberOfWinningRound;
    }

    public FatherUser getUser() {
        return user;
    }

    private void setUser(FatherUser user) {
        this.user = user;
    }

    public int getPlayerLifePoint() {
        return this.playerLifePoint;
    }

    public void setPlayerLifePoint(int playerLifePoint) {
        this.playerLifePoint = playerLifePoint;
    }

    public Deck getDeck() {
        return deck;
    }

    public PlayerBoard getBoard() {
        return board;
    }

    public void changePlayerLifePoint(int amount) {
        playerLifePoint -= amount;
        if (playerLifePoint < 0)
            playerLifePoint = 0;
    }

    private void gameSetUp() throws FileNotFoundException {
        HandHouse[] hand = board.getPlayerHand();
        for (int i = 0; i < 5; i++) {
            hand[i].setCard(deck.getMainDeck().get(i));
        }

        for (int i = 0; i < 5; i++) {
            deck.removeCardFromMainDeck(deck.getMainDeck().get(i));
        }

    }

    public int getNumberOfWinningRound() {
        return numberOfWinningRound;
    }

    public void increaseWinningRound() {
        numberOfWinningRound++;
    }

}
