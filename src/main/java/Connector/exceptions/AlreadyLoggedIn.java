package Connector.exceptions;

public class AlreadyLoggedIn extends RuntimeException{
    public AlreadyLoggedIn() {
       super("You Already have Logged In");
    }
}
