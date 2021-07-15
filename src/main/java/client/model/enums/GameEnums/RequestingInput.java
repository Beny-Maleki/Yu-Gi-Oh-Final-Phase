package client.model.enums.GameEnums;

public enum RequestingInput {
    FROM_GRAVEYARD("Enter the name of card you want to summon from graveyard"),
    GUESS_CARD("Declare a card name:"),
    CHOOSE_FIELD_CARD("Declare a field card:"),
    CHOOSE_FROM_OPPO_MONSTER_HOUSES("Declare one of your opponents monsters:"),
    DOES_PLAYER_WANT_TO_ACTIVE_SPELL("Do you want to active "),
    MAGIC_CARD_TO_DESTROY("Declare a magic card to destroy:"),
    SET_EQUIPED_MONSTER("Declare a monster card you want to equip:"),
    INVALID_ANSWER("your answer is invalid try again !"),
    ERROR_OF_INVALID_MONSTER_CARD_EQUIPPED("Invalid Monster! Press Enter to retry");

    private final String message;

    RequestingInput(String message) {
        this.message = message;
    }


    public String toString() {
        return message;
    }
}
