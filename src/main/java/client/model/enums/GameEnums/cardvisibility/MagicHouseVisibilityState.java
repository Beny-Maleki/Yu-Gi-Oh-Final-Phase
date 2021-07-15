package client.model.enums.GameEnums.cardvisibility;

public enum MagicHouseVisibilityState {
    E("empty"),
    O("offensive"),
    H("hidden");
    String stateToString;

    MagicHouseVisibilityState(String stateToString) {
        this.stateToString = stateToString;
    }

    @Override
    public String toString() {
        return stateToString;
    }
}
