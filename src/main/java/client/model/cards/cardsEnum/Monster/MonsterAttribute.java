package client.model.cards.cardsEnum.Monster;


public enum MonsterAttribute {
    DARK("DARK"),
    EARTH("EARTH"),
    FIRE("FIRE"),
    LIGHT("LIGHT"),
    WATER("WATER"),
    WIND("WIND");
    String enumToString;

    MonsterAttribute(String enumToString) {
        this.enumToString = enumToString;
    }

    public static MonsterAttribute assignAttribute(String attribute) {
        switch (attribute) {
            case "DARK": {
                return DARK;
            }
            case "EARTH": {
                return EARTH;
            }
            case "FIRE": {
                return FIRE;
            }
            case "LIGHT": {
                return LIGHT;
            }
            case "WATER": {
                return WATER;
            }
            case "WIND": {
                return WIND;
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


