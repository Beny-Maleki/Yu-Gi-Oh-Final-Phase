package client.model.cards.cardsEnum.Magic;

public enum RestrictionTypeInAdding {
    UNLIMITED("Unlimited"),
    LIMITED("Limited");
    String enumToString;

    RestrictionTypeInAdding(String enumToString) {
        this.enumToString = enumToString;
    }

    public static RestrictionTypeInAdding setSpeed(String speed) {
        switch (speed) {
            case "Unlimited": {
                return UNLIMITED;
            }
            case "Limited": {
                return LIMITED;
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
