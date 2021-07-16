package Connector.exceptions;

public class DuplicateUsernameException extends RuntimeException {
    public DuplicateUsernameException() {
        super("User with this username already exists");
    }
}
