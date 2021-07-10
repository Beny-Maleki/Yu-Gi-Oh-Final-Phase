package model.enums.GameEnums.cardvisibility;

public enum MonsterHouseVisibilityState {
    E("empty"),
    OO("offensiveHidden"),
    DO("defenceOccupied"),
    DH("defenceHidden");

    String stateToString;

    MonsterHouseVisibilityState(String stateToString) {
        this.stateToString = stateToString;
    }

    @Override
    public String toString() {
        return stateToString;
    }
}
