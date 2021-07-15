package client.model.cards.cardsEnum.Monster;

public enum MonsterType {
    NORMAL("Normal"),
    EFFECT("Effect"),
    RITUAL("Ritual");

    String enumToString;

    MonsterType(String enumToString) {
        this.enumToString = enumToString;
    }

    public static MonsterType assignType(String type) {
        switch (type) {
            case "Normal": {
                return NORMAL;
            }
            case "Effect": {
                return EFFECT;
            }
            case "Ritual": {
                return RITUAL;
            }
            default: {
                return null;
            }
        }
    }

    @Override
    public String toString() {
        return enumToString;
    }
}
