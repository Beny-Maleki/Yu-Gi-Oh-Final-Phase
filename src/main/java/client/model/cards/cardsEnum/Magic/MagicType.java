package client.model.cards.cardsEnum.Magic;

public enum MagicType {
    SPELL("Spell"),
    TRAP("Trap");

    String typeToString;

    MagicType(String typeToString) {
        this.typeToString = typeToString;
    }

    public static MagicType setType(String typeOfMagic) {

        switch (typeOfMagic) {
            case "Spell": {
                return SPELL;
            }
            case "Trap": {
                return TRAP;
            }
            default:
                return null;
        }

    }

    @Override
    public String toString() {
        return typeToString;
    }
}
