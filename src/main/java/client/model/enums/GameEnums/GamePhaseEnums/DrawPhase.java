package client.model.enums.GameEnums.GamePhaseEnums;

public enum DrawPhase {
    ADD_NEW_CARD("new card added to the hand : _CN_");
    String drawMessageToString;

    DrawPhase(String drawMessageToString) {
        this.drawMessageToString = drawMessageToString;
    }

    public String toString() {
        return drawMessageToString;
    }
}
