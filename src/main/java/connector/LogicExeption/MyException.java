package connector.LogicExeption;

public class MyException extends RuntimeException {
    private final ExceptionType type;

    protected MyException(ExceptionType type, String message) {
        super(message);
        this.type = type;
    }

    public ExceptionType getType() {
        return type;
    }
}
