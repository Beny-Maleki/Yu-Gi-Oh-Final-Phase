package connector.LogicExeption;

public class RegisterException extends MyException {
    protected RegisterException(ExceptionType type, String message) {
        super(type, message);
    }
}
