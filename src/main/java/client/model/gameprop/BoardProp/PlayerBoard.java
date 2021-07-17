package client.model.gameprop.BoardProp;

import client.exceptions.CardNotFoundException;
import connector.cards.Card;
import connector.cards.MagicCard;
import connector.cards.MonsterCard;
import client.model.enums.GameEnums.CardLocation;
import client.model.enums.GameEnums.cardvisibility.MonsterHouseVisibilityState;
import client.model.gameprop.Selectable;

public class PlayerBoard {
    MagicHouse[] magicHouse;
    MonsterHouse[] monsterHouse;
    HandHouse[] playerHand;
    MagicHouse fieldHouse;
    GraveYard graveYard;

    {
        initializeBoardHouses();
        graveYard = new GraveYard();
    }

    public MagicHouse[] getMagicHouse() {
        return magicHouse;
    }

    public MonsterHouse[] getMonsterHouse() {
        return monsterHouse;
    }

    public HandHouse[] getPlayerHand() {
        return playerHand;
    }

    public void removeCardFromPlayerHand(Card card) {
        for (HandHouse handHouse : playerHand) {
            if (handHouse.getCard() == card) {
                handHouse.removeCard();
            }
        }
    }

    public HandHouse getFirstEmptyHouse() {
        for (HandHouse handHouse : playerHand) {
            if (handHouse.getCard() == null) {
                return handHouse;
            }
        }
        return null;
    }


    public GraveYard getGraveYard() {
        return graveYard;
    }

    private void initializeBoardHouses() {
        monsterHouse = new MonsterHouse[5];
        for (int i = 0; i < monsterHouse.length; i++) {
            monsterHouse[i] = new MonsterHouse(i);
        }
        magicHouse = new MagicHouse[5];
        for (int i = 0; i < magicHouse.length; i++) {
            magicHouse[i] = new MagicHouse(i);
        }
        fieldHouse = new MagicHouse(5);
        playerHand = new HandHouse[6];
        for (int i = 0; i < playerHand.length; i++) {
            playerHand[i] = new HandHouse(i);
        }
    }

    public MagicHouse getFieldHouse() {
        return fieldHouse;
    }

    public Card getCard(Integer address, CardLocation location) {
        switch (location) {
            case PLAYER_HAND:
                return playerHand[address].getCard();
            case FIELD_ZONE:
                return fieldHouse.getCard();
            case SPELL_ZONE:
                return magicHouse[address].getCard();
            case MONSTER_ZONE:
                return monsterHouse[address].getCard();
            default:
                return null;
        }
    }

    public void moveCardToGraveYard(int address, CardLocation location) {
        if (location.equals(CardLocation.MONSTER_ZONE)) {
            Card card = monsterHouse[address].getMonsterCard();
            monsterHouse[address].setMonsterCard(null);
            graveYard.addCardToGraveYard(card);
        } else if (location.equals(CardLocation.SPELL_ZONE)) {
            Card card = magicHouse[address].getMagicCard();
            magicHouse[address].setMagicCard(null);
            graveYard.addCardToGraveYard(card);
        } else {
            Card card = fieldHouse.getMagicCard();
            fieldHouse.setMagicCard(null);
            graveYard.addCardToGraveYard(card);
        }
    }

    public void moveCardToGraveYard(Card card) {
        if (card instanceof MonsterCard) {
            MonsterHouse monsterHouse = MonsterHouse.getMonsterHouseByMonsterCard((MonsterCard) card);
            assert monsterHouse != null;
            monsterHouse.removeCard();
            graveYard.addCardToGraveYard(card);
        } else if (card instanceof MagicCard) {
            MagicHouse magicHouse = MagicHouse.getMagicHouseByMagicCard((MagicCard) card);
            assert magicHouse != null;
            magicHouse.removeCard();
            graveYard.addCardToGraveYard(card);
        }
    }

    public int numberOfFullHouse(String typeOfHouse) {
        int counter = 0;
        if (typeOfHouse.equals("monster")) {
            for (MonsterHouse house : monsterHouse) {
                if (!house.getState().equals("empty")) {
                    counter++;
                }
            }
        } else if (typeOfHouse.equals("spell")) {
            for (MagicHouse house : magicHouse) {
                if (!house.getState().equals("empty")) {
                    counter++;
                }
            }
        } else {
            for (HandHouse house : playerHand) {
                if (house.getCard() != null) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public void summonMonster(MonsterCard monsterCard) {
        for (MonsterHouse house : monsterHouse) {
            if (house.getMonsterCard() == null) {
                house.setMonsterCard(monsterCard);
                house.setState(MonsterHouseVisibilityState.OO);
                return;
            }
        }
    }

    public MagicCard getMagicCardByName(String name) throws CardNotFoundException {
        for (MagicHouse house : magicHouse) {
            if (house.getMagicCard().getName().equals(name)) {
                return house.getMagicCard();
            }
        }
        throw new CardNotFoundException("magic card not found!");
    }

    public MonsterCard getMonsterCardByName(String name) throws CardNotFoundException {
        for (MonsterHouse house : monsterHouse) {
            if (house.getMonsterCard().getName().equals(name)) {
                return house.getMonsterCard();
            }
        }
        throw new CardNotFoundException("monster card not found!");
    }

    public boolean doesRitualCardAvailable() {
        for (HandHouse house : playerHand) {
            if (house.getCard().getName().equals("Advanced Ritual Art")) {
                return true;
            }
        }
        for (MagicHouse house : magicHouse) {
            if (house.getMagicCard().getName().equals("Advanced Ritual Art")) {
                return true;
            }
        }
        return false;
    }

    public void removeRitualSummonCard() {
        for (int i = 0; i < playerHand.length; i++) {
            if (playerHand[i].getCard().getName().equals("Advanced Ritual Art")) {
                moveCardToGraveYard(i, CardLocation.PLAYER_HAND);
            }
        }
        for (int i = 0; i < magicHouse.length; i++) {
            if (magicHouse[i].getMagicCard().getName().equals("Advanced Ritual Art")) {
                moveCardToGraveYard(i, CardLocation.SPELL_ZONE);
            }
        }
    }

    public boolean doesBelong(Selectable selectable) {
        for (MagicHouse house : magicHouse) {
            if (house == selectable) {
                return true;
            }
        }
        for (MonsterHouse house : monsterHouse) {
            if (house == selectable) {
                return true;
            }
        }

        for (HandHouse house : playerHand) {
            if (house == selectable) {
                return true;
            }
        }
        return false;
    }
}
