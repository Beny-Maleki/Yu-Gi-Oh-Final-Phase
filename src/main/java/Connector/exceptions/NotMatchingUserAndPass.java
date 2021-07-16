package Connector.exceptions;

public class NotMatchingUserAndPass extends RuntimeException {
    public NotMatchingUserAndPass() {
        super("Username and password don't match");
    }
}
