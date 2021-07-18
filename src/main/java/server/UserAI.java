package server;

import client.model.enums.GameEnums.SideOfFeature;
import client.model.enums.GameEnums.cardvisibility.MonsterHouseVisibilityState;
import client.model.events.Event;
import client.model.events.eventChildren.ManuallyActivation;
import client.model.gameprop.BoardProp.HandHouse;
import client.model.gameprop.BoardProp.MagicHouse;
import client.model.gameprop.BoardProp.MonsterHouse;
import client.model.gameprop.Player;
import client.model.gameprop.gamemodel.Game;
import client.model.userProp.Deck;
import client.model.userProp.DifficultyLevel;
import client.model.userProp.FatherUser;
import connector.cards.Card;
import connector.cards.MagicCard;
import connector.cards.MonsterCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class UserAI extends FatherUser {
    private DifficultyLevel AIDifficulty;
    private Deck playerDeck;

    {
        score = 0;
        nickname = "Robot";
    }

    public UserAI(DifficultyLevel AIDifficulty) {
        this.AIDifficulty = AIDifficulty;
        randomValidDeck();
    }

    private void randomValidDeck() {

        int weakMonsterNumber = 0;
        int normalMonsterNumber = 0;
        int strongMonsterNumber = 0;
        int weakCounter = 0;
        int normalCounter = 0;
        int strongCounter = 0;
        switch (AIDifficulty) {
            case EASY:
                weakMonsterNumber = 10;
                normalMonsterNumber = 10;
                strongMonsterNumber = 10;
                break;
            case NORMAL:
                weakMonsterNumber = 7;
                normalMonsterNumber = 12;
                strongMonsterNumber = 13;
                break;
            case HARD:
                weakMonsterNumber = 5;
                normalMonsterNumber = 15;
                strongMonsterNumber = 13;
                break;
        }
//TODO the process of getting card is wrong
        List<MonsterCard> monsterCards = ServerDataAnalyse.getInstance().getGameMonsterCards();
        List<MagicCard> magicCards = ServerDataAnalyse.getInstance().getGameMagicCards();
        Collections.shuffle(monsterCards);
        Collections.shuffle(magicCards);
        for (MonsterCard card : monsterCards) {
            if (card.getLevel() < 5 && weakCounter != weakMonsterNumber) {
                activeDeck.addCardToMainDeck(card);
                weakCounter++;
            } else if (card.getLevel() < 7 && normalCounter != normalMonsterNumber) {
                activeDeck.addCardToMainDeck(card);
                normalCounter++;
            } else if (strongCounter != strongMonsterNumber) {
                activeDeck.addCardToMainDeck(card);
                strongCounter++;
            }
        }
        for (int i = 0; i < 10; i++) {
            activeDeck.addCardToMainDeck(magicCards.get(i));
        }
    }

    public String mainPhaseCommand(Game game) {
        String result = "";
        Random rand = new Random();
        Player aiPlayer = game.getPlayer(SideOfFeature.CURRENT);
        int random = rand.nextInt(6);
        if (random == 0) {//summon monster
            MonsterCard monster = null;
            int elementOfHand = 0;
            for (HandHouse house : aiPlayer.getBoard().getPlayerHand()) {//TODO fix bug in hand
                Card card = house.getCard();
                elementOfHand++;
                if (card instanceof MonsterCard) {
                    monster = (MonsterCard) card;
                    break;
                }
            }
            if (monster != null) {
                result = "select --hand " + elementOfHand + "\nsummon\n";
            }
        } else if (random == 1) {//set a card
            int randomCard = rand.nextInt(aiPlayer.getBoard().getPlayerHand().length) + 1;
            result = "select --hand " + randomCard + "\nset\n";
        } else if (random == 2) {
            int randomCard = rand.nextInt(aiPlayer.getBoard().getPlayerHand().length) + 1;
            int attackOrDefense = rand.nextInt(2);
            if (attackOrDefense == 0) {
                result = "select --hand " + randomCard + "\nset --position attack\n";
            } else {
                result = "select --hand " + randomCard + "\nset --position defense\n";
            }
        } else if (random == 3) {
            int elementOfHouse = 0;
            for (MonsterHouse monsterHouse : aiPlayer.getBoard().getMonsterHouse()) {
                elementOfHouse++;
                if (monsterHouse.getState().equals(MonsterHouseVisibilityState.DH)) {
                    break;
                }
            }
            result = "select --monster " + elementOfHouse + "\nflip-summon\n";
        } else if (random == 4) {
            result = "select --spell " + getValidSpellToActiveEffect(aiPlayer) + "\nactive effect\n";
        } else {
            result = "next phase";
        }
        return result;
    }

    public String battlePhaseCommand(Game game) {
        String result = "";
        Random rand = new Random();
        Player aiPlayer = game.getPlayer(SideOfFeature.CURRENT);
        int random = rand.nextInt(4);
        if (random == 0) {
            result = "select --monster " + getValidMonsterElementToAttack(aiPlayer) + "\nattack direct\n";
        } else if (random == 1) {
            result = "select --monster " + getValidMonsterElementToAttack(aiPlayer) + "\nattack " + getValidTargetMonster(game.getPlayer(SideOfFeature.OPPONENT)) + "\n";
        } else if (random == 2) {
            result = "select --spell " + getValidSpellToActiveEffect(aiPlayer) + "\nactive effect\n";
        } else {
            result = "next phase";
        }
        return result;
    }

    public String generalCommand(Game game) {
        String result = "";
        Random rand = new Random();
        Player aiPlayer = game.getPlayer(SideOfFeature.CURRENT);
        int random = rand.nextInt(2);
        if (random == 0) {
            result = "select --spell " + getValidSpellToActiveEffect(aiPlayer) + "\nactive effect\n";
        } else {
            result = "next phase";
        }
        return result;
    }

    private int getValidSpellToActiveEffect(Player aiPlayer) {
        Random rand = new Random();
        int elementOfHouse = 0;
        ArrayList<Integer> validElements = new ArrayList<>();
        for (MagicHouse magicHouse : aiPlayer.getBoard().getMagicHouse()) {
            elementOfHouse++;
            if (magicHouse.getMagicCard() != null && isManuallyActivationCard(magicHouse)) {
                validElements.add(elementOfHouse);
            }
        }
        int selectCard = rand.nextInt(validElements.size());
        return validElements.get(selectCard);
    }

    private boolean isManuallyActivationCard(MagicHouse magicHouse) {
        for (Event trigger : magicHouse.getMagicCard().getTriggers()) {
            if (trigger instanceof ManuallyActivation) return true;
        }
        return false;
    }

    private int getValidTargetMonster(Player opponentPlayer) {
        Random rand = new Random();
        int elementOfHouse = 0;
        ArrayList<Integer> validElements = new ArrayList<>();
        for (MonsterHouse monsterHouse : opponentPlayer.getBoard().getMonsterHouse()) {
            elementOfHouse++;
            if (monsterHouse.getMonsterCard() != null) {
                validElements.add(elementOfHouse);
            }
        }
        int selectCard = rand.nextInt(validElements.size());
        return validElements.get(selectCard);
    }

    private int getValidMonsterElementToAttack(Player aiPlayer) {
        Random rand = new Random();
        int elementOfHouse = 0;
        ArrayList<Integer> validElements = new ArrayList<>();
        for (MonsterHouse monsterHouse : aiPlayer.getBoard().getMonsterHouse()) {
            elementOfHouse++;
            if (monsterHouse.getMonsterCard() != null && monsterHouse.getState().equals(MonsterHouseVisibilityState.OO)) {
                validElements.add(elementOfHouse);
            }
        }
        int selectCard = rand.nextInt(validElements.size());
        return validElements.get(selectCard);
    }

    @Override
    public Deck getActiveDeck() {
        return null;
    }
}
