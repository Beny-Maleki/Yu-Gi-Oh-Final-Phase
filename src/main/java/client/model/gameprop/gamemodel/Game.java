package client.model.gameprop.gamemodel;

import client.model.cards.cardsProp.Card;
import client.model.enums.GameEnums.PlayerTurn;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.enums.GameEnums.TypeOfHire;
import client.model.enums.GameEnums.gamestage.GameMainStage;
import client.model.enums.GameEnums.gamestage.GameSideStage;
import client.model.gameprop.BoardProp.HandHouse;
import client.model.gameprop.BoardProp.MonsterHouse;
import client.model.gameprop.GameInProcess;
import client.model.gameprop.Player;
import client.model.gameprop.SelectedCardProp;
import client.model.gameprop.existenceBasedObserver.ExistenceObserver;
import client.model.gameprop.turnBasedObserver.TurnObserver;
import client.model.userProp.Deck;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private final int numberOfGameRounds;
    private boolean isGameFinished;
    private boolean isRoundFinish;
    private int roundNumber;
    private Player firstPlayer;
    private Player secondPlayer;
    private Turn turn;
    private GameSideStage gameSideStage;
    private GameMainStage gameMainStage;
    private ArrayList<Integer> playerOneHealthAtMatchFinishing;
    private ArrayList<Integer> playerTwoHealthAtMatchFinishing;

    {
        roundNumber = 1;
        isGameFinished = false;
        isRoundFinish = false;
        gameMainStage = GameMainStage.DRAW_PHASE;
        gameSideStage = GameSideStage.START_STAGE;
    }

    public Game(Player firstPlayer, Player secondPlayer, int numberOfRounds) {
        setFirstPlayer(firstPlayer);
        setSecondPlayer(secondPlayer);
        turn = new Turn(PlayerTurn.PLAYER_ONE, true);
        this.numberOfGameRounds = numberOfRounds;
        if (numberOfRounds == 3) {
            playerOneHealthAtMatchFinishing = new ArrayList<>();
            playerTwoHealthAtMatchFinishing = new ArrayList<>();
        }
    }

    public boolean doesPlayerHavePermissionToDraw() {
        return !turn.isCardDraw();
    }

    public void setPlayerDrawInTurn() {
        turn.setCardDraw();
    }

    public Player getPlayer(SideOfFeature turn) {
        switch (turn) {
            case CURRENT: {
                if (this.turn.getPlayerWithTurn() == (PlayerTurn.PLAYER_ONE)) {
                    return firstPlayer;
                } else {
                    return secondPlayer;
                }
            }
            case OPPONENT: {
                if (this.turn.getPlayerWithTurn() == (PlayerTurn.PLAYER_TWO)) {
                    return firstPlayer;
                } else {
                    return secondPlayer;
                }
            }
            default:
                return null;
        }
    }

    public MonsterHouse getHiredMonster() {
        return turn.getMonsterHouseOfHiredMonster();
    }

    public void setHiredMonster(MonsterHouse monsterHouse) {
        turn.setMonsterHouseOfHiredMonster(monsterHouse);
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public GameMainStage getGameMainStage() {
        return gameMainStage;
    }

    public GameSideStage getGameSideStage() {
        return gameSideStage;
    }

    public void setGameSideStage(GameSideStage gameSideStage) {
        this.gameSideStage = gameSideStage;
    }

    public SelectedCardProp getCardProp() {
        return turn.getSelectedCardProp();
    }

    public void setCardProp(SelectedCardProp cardProp) {
        turn.selectedCardProp = cardProp;
    }

    public void goToNextPhase() {
        gameMainStage = GameMainStage.getNextPhase(gameMainStage.getPhaseNumber());
        if (gameMainStage.equals(GameMainStage.DRAW_PHASE))
            resetLastTurnData();
    }

    public PlayerTurn getTurnOfGame() {
        return turn.getPlayerWithTurn();
    }

    public boolean isRoundFinish() {
        return isRoundFinish;
    }

    public void finishRound(PlayerTurn looserTurn) throws FileNotFoundException {
        switch (looserTurn) {
            case PLAYER_ONE: {
                secondPlayer.increaseWinningRound();
                turn = new Turn(PlayerTurn.PLAYER_TWO, false);
                break;
            }
            case PLAYER_TWO: {
                firstPlayer.increaseWinningRound();
                turn = new Turn(PlayerTurn.PLAYER_TWO, false);
                break;
            }
        }
        if (roundNumber == numberOfGameRounds || firstPlayer.getNumberOfWinningRound() == 2 || secondPlayer.getNumberOfWinningRound() == 2) {
            finishGame(looserTurn);
        } else {
            playerOneHealthAtMatchFinishing.add(firstPlayer.getPlayerLifePoint());
            playerTwoHealthAtMatchFinishing.add(secondPlayer.getPlayerLifePoint());

            firstPlayer = new Player(firstPlayer.getUser(), firstPlayer.getNumberOfWinningRound());
            secondPlayer = new Player(secondPlayer.getUser(), secondPlayer.getNumberOfWinningRound());

            gameSideStage = GameSideStage.EX_CHANGE_WITH_SIDE_DECK_FOR_PLAYER_ONE;

            TurnObserver.clearTurnObserver();
            ExistenceObserver.clearExistenceObserver();
        }
        roundNumber++;
    }

    public void finishGame(PlayerTurn looserTurn) {
        switch (looserTurn) {
            case PLAYER_ONE: {
                calculatePlayersBonus(secondPlayer, firstPlayer);
                break;
            }
            case PLAYER_TWO:
                calculatePlayersBonus(firstPlayer, secondPlayer);
        }

    }

    private void calculatePlayersBonus(Player winner, Player looser) {
        if (numberOfGameRounds == 1) {
            looser.getUser().changeBalance(100);
            winner.getUser().increaseScore(1000);
            winner.getUser().changeBalance(1000 + winner.playerLifePoint);
        } else {
            looser.getUser().changeBalance(300);
            winner.getUser().increaseScore(3000);
            int maxHealthOfWinnerPlayerInGame = 0;
            int previous = 0;
            if (winner == firstPlayer) {
                for (Integer health : playerOneHealthAtMatchFinishing) {
                    maxHealthOfWinnerPlayerInGame = Math.max(previous, health);
                }
            } else {
                for (Integer health : playerTwoHealthAtMatchFinishing) {
                    maxHealthOfWinnerPlayerInGame = Math.max(previous, health);
                }
            }
            winner.getUser().changeBalance(3000 + maxHealthOfWinnerPlayerInGame);
        }
        isGameFinished = true;
    }

    public Player getWinner() {
        if (firstPlayer.getNumberOfWinningRound() > secondPlayer.getNumberOfWinningRound())
            return firstPlayer;
        else
            return secondPlayer;
    }

    public Player getLooser() {
        if (firstPlayer.getNumberOfWinningRound() > secondPlayer.getNumberOfWinningRound())
            return secondPlayer;
        else
            return firstPlayer;
    }

    public int getTributeNumber() {
        return turn.getTributeNumber();
    }

    private void resetLastTurnData() {
        ArrayList<TurnObserver> turnObservers = TurnObserver.getTurnObservers();

        if (turnObservers != null && turnObservers.size() > 0) {
            for (int i = turnObservers.size() - 1; i == 0; i--) {
                turnObservers.get(i).update();
            }
        }

        for (MonsterHouse monsterHouse : firstPlayer.getBoard().getMonsterHouse()) {
            monsterHouse.setMonsterAttacked(false);
        }

        for (MonsterHouse monsterHouse : secondPlayer.getBoard().getMonsterHouse()) {
            monsterHouse.setMonsterAttacked(false);
        }

        if (turn.getPlayerWithTurn() == PlayerTurn.PLAYER_ONE) {
            turn = new Turn(PlayerTurn.PLAYER_TWO, false);
        } else turn = new Turn(PlayerTurn.PLAYER_ONE, false);
    }

    public void setTributeSize(int numberOfCard) {
        turn.setTributeNumber(numberOfCard);
    }

    public boolean isHiredMonsterRitual() {
        return turn.isHiredMonsterRitual;
    }

    public void setHiredMonsterRitual(boolean isMonsterRitual) {
        turn.setHiredMonsterRitual(isMonsterRitual);
    }

    public TypeOfHire getTypeOfMonsterHire() {
        return turn.getTypeOfHighLevelMonsterHire();
    }

    public void setTypeOfMonsterHire(TypeOfHire typeOfMonsterHire) {
        turn.setTypeOfHighLevelMonsterHire(typeOfMonsterHire);
    }

    public void moveCardFromDeckToHand(Card card) throws FileNotFoundException {
        Player player = GameInProcess.getGame().getPlayer(SideOfFeature.CURRENT);
        Deck deck = player.getDeck();
        HandHouse[] hand = player.getBoard().getPlayerHand();

        deck.removeCardFromMainDeck(card);
        for (HandHouse handHouse : hand) {
            if (handHouse.getCard() == null) {
                handHouse.setCard(card);
            }
        }

        Collections.shuffle(deck.getMainDeck());
    }

    public boolean isGameFinished() {
        return isGameFinished;
    }

    public boolean isFirstTurnOfTheGame() {
        return turn.isFirstTurn();
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void checkRoundFinishCondition() {
        if (firstPlayer.getPlayerLifePoint() == 0 ||
                secondPlayer.getPlayerLifePoint() == 0 ||
                firstPlayer.getDeck().getMainDeck().size() == 0 ||
                secondPlayer.getDeck().getMainDeck().size() == 0) {
            isRoundFinish = true;
        }
    }
}
