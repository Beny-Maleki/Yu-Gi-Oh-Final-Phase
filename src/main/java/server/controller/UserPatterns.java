package server.controller;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class UserPatterns {
    private static ArrayList<UserPatterns> userPatterns;
    public static UserPatterns REGISTER;

    static {
        userPatterns = new ArrayList<>();
        // creating instances...
        REGISTER = new UserPatterns ("user create --username (?<username>.+) --nickname (?<nickname>.+) --password (?<password>.+)",
                "createUser");
    }

    private final String val;
    private Method method;
    private static final UserManagement controller = UserManagement.getInstance();

    UserPatterns(String val, String methodName) {
        this.val = val;
        try {
            this.method = controller.getClass().getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            System.out.println("Method not found! Wrong methodName declaration in UserPatterns.");
        }

        userPatterns.add(this);
    }

    public String getVal() {
        return this.val;
    }

    public Method getMethod() {
        return this.method;
    }

    public static ArrayList<UserPatterns> getUserPatterns() {
        return userPatterns;
    }
}
