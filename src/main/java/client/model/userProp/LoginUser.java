package client.model.userProp;

public class LoginUser {
    public static User user;
    public static User opponent;
    public static User getUser() {
        return user;
    }

    public static User getOpponent() {
        return opponent;
    }

    public static void setOpponent(User opponent) {
        LoginUser.opponent = opponent;
    }

    public static void setUser(User user) {
        LoginUser.user = user;
    }
}
