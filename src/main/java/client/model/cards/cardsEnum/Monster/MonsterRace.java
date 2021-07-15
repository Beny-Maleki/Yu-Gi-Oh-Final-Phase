package client.model.cards.cardsEnum.Monster;

public enum MonsterRace {
    BEAST_WARRIOR("Beast-Warrior"),
    WARRIOR("Warrior"),
    AQUA("Aqua"),
    FIEND("Fiend"),
    BEAST("Beast"),
    PYRO("Pyro"),
    SPELLCASTER("Spellcaster"),
    THUNDER("Thunder"),
    DRAGON("Dragon"),
    MACHINE("Machine"),
    ROCK("Rock"),
    INSECT("Insect"),
    CYBERSE("Cyberse"),
    FAIRY("Fairy"),
    SEA_SERPENT("Sea Serpent");
    String MonsterRaceToString;

    MonsterRace(String monsterRaceToString) {
        this.MonsterRaceToString = monsterRaceToString;
    }

    public static MonsterRace assignRace(String race) {
        switch (race) {
            case "Beast-Warrior": {
                return BEAST_WARRIOR;
            }
            case "Warrior": {
                return WARRIOR;
            }
            case "Aqua": {
                return AQUA;
            }
            case "Fiend": {
                return FIEND;
            }
            case "Beast": {
                return BEAST;
            }
            case "Pyro": {
                return PYRO;
            }
            case "Spellcaster": {
                return SPELLCASTER;
            }
            case "Thunder": {
                return THUNDER;
            }
            case "Dragon": {
                return DRAGON;
            }
            case "Machine": {
                return MACHINE;
            }
            case "Rock": {
                return ROCK;
            }
            case "Insect": {
                return INSECT;
            }
            case "Cyberse": {
                return CYBERSE;
            }
            case "Fairy": {
                return FAIRY;
            }
            case "Sea Serpent": {
                return SEA_SERPENT;
            }
            default: {
                return null;
            }
        }
    }

    @Override
    public String toString() {
        return MonsterRaceToString;
    }
}
