package client.model.enums.rockpaperscissor;

public enum RockPaperScissorMessage {
    PLAYER_ONE_CHOOSE("%s Choose Your Weapon Rock, Paper Or Scissor"),
    PLAYER_TWO_CHOOSE("%s Choose Your Weapon Rock, Paper Or Scissor"),
    DRAW_MESSAGE("The game result is draw try one more time"),
    PLAYER_ONE_WIN_MESSAGE("%s won !!! start first"),
    PLAYER_TWO_WIN_MESSAGE("%s  won !!! start first ");
    String messageToString;

    RockPaperScissorMessage(String messageToString) {
        this.messageToString = messageToString;
    }

    @Override
    public String toString() {
        return messageToString;
    }
}
