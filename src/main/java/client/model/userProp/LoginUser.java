package client.model.userProp;

public class LoginUser {
    private static User user;
    private static User opponent;
    private static OnWorkThreads onlineThread;

    static {
        onlineThread = OnWorkThreads.NONE;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        LoginUser.user = user;
    }

    public static User getOpponent() {
        return opponent;
    }

    public static void setOpponent(User opponent) {
        LoginUser.opponent = opponent;
    }

    public static OnWorkThreads getOnlineThread() {
        return onlineThread;
    }

    public static void setOnlineThread(OnWorkThreads onlineThread) {
        LoginUser.onlineThread = onlineThread;
    }
}
