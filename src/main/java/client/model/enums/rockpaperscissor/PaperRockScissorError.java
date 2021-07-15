package client.model.enums.rockpaperscissor;

public enum PaperRockScissorError {
    WRONG_WEAPON_CHOOSE("No such a weapon !! (you can only  choose rock paper or scissor");

    String errorMessageToString;

    PaperRockScissorError(String errorMessageToString) {
        this.errorMessageToString = errorMessageToString;
    }

    @Override
    public String toString() {
        return errorMessageToString;
    }
}
