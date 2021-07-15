package client.model.enums.MenusMassages;

public enum Register {

    CURRENT_MENU("Login Menu"),
    SUCCESSFULLY_USER_CREATE("user created successfully!"),
    SUCCESSFULLY_LOGIN("user logged in successfully!"),
    SUCCESSFULLY_LOGOUT("user logged out successfully!"),
    SUCCESSFULLY_ENTER_MENU("you entered Register client.controller successfully"),
    SUCCESSFULLY_EXIT_MENU("Register client.controller exited successfully");


    private final String registerMessage;

    Register(String registerMessage) {
        this.registerMessage = registerMessage;
    }

    public String getRegisterMessage() {
        return registerMessage;
    }
}
