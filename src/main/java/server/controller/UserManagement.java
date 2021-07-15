package server.controller;

import client.model.enums.Error;
import client.model.userProp.User;
import client.model.userProp.UserInfoType;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class UserManagement extends Controller {
    private static UserManagement instance;

    public static UserManagement getInstance() {
        if (instance == null) instance = new UserManagement();
        return instance;
    }

    @Override
    public String run(String command) {
        Matcher matcher = null;
        ArrayList<UserPatterns> userPatterns = UserPatterns.getUserPatterns();

        for (UserPatterns userPattern : userPatterns) {
            matcher = matcher(userPattern.getVal(), command);

            if (matcher.matches()) {
               String result = "This is a default response! method didn't work!";

               try {
                    result = (String) userPattern.getMethod().invoke(matcher);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }

                return result;
            }
        }

        if (matcher == null) { // Invalid command! format of transporting data was wrong!
            System.out.println("Invalid request format!");
        }

        return "failed to process request!";
    }

    public String createUser(Matcher matcher) {
        String username = matcher.group("username");
        String nickname = matcher.group("nickname");
        String password = matcher.group("password");
        String imageName = matcher.group("imageName");

        if (null != User.getUserByUserInfo(username, UserInfoType.USERNAME)) {
            return processOutPut(Error.INVALID_USERNAME.toString(), username);
        } else if (null != User.getUserByUserInfo(nickname, UserInfoType.NICKNAME)) {
            return processOutPut(Error.INVALID_NICKNAME.toString(), nickname);
        } else {
            new User(username, password, nickname, imageName);
            return "successful";
        }
    }

    private String processOutPut(String error, String name) {
        if (error.contains("U_N")) {
            error = error.replace("U_N", name);
        } else if (error.contains("N_N")) {
            error = error.replace("N_N", name);
        }
        return error;
    }
}
