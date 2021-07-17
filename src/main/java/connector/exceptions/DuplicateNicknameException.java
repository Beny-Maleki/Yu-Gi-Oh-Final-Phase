package connector.exceptions;

public class DuplicateNicknameException extends RuntimeException {
    public DuplicateNicknameException() {
        super("User with this nickname already exists");
    }

}
