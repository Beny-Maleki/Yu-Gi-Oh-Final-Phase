package client.model.Exceptions;

public class EmptyTextFieldException extends RuntimeException {
    public EmptyTextFieldException() {
        super("You must fill all the fields first");
    }
}
