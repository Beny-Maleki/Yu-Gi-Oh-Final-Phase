package client.exceptions;

public class CardNotFoundException extends Exception{
    public CardNotFoundException() {
        super();
    }

    public CardNotFoundException(String message) {
        super(message);
    }
}
