package client.model.cards.cardsEnum.Magic;

public enum MagicAttribute {
    RITUAL("Ritual"),
    QUICK_PLAY("Quick-play"),
    FIELD("Field"),
    EQUIP("Equip"),
    CONTINUOUS("Continous"),
    COUNTER("Coutner"),
    ANTI_TRAP("Anti-Trap"),
    NORMAL("Normal");

    String enumToString;

    MagicAttribute(String enumToString) {
        this.enumToString = enumToString;
    }

    public static MagicAttribute setAttribute(String attribute) {
        switch (attribute) {
            case "Ritual": {
                return RITUAL;
            }
            case "Quick-play": {
                return QUICK_PLAY;
            }
            case "Field": {
                return FIELD;
            }
            case "Continuous": {
                return CONTINUOUS;
            }
            case "Counter": {
                return COUNTER;
            }
            case "Anti-Trap": {
                return ANTI_TRAP;
            }
            case "Normal": {
                return NORMAL;
            }
            case "Equip": {
                return EQUIP;
            }
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return enumToString;
    }
}
