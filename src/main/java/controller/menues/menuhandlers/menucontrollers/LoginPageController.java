package controller.menues.menuhandlers.menucontrollers;

import controller.Controller;
import javafx.scene.control.Label;
import model.Exceptions.EmptyTextFieldException;
import model.enums.Error;
import model.enums.Menu;
import model.userProp.LoginUser;
import model.userProp.User;
import model.userProp.UserInfoType;

import java.io.IOException;

public class LoginPageController extends Controller {

    public void login(String username, String password, Label message) {
        message.setStyle("-fx-text-fill: red ; -fx-font-size: 15");

        try {
            if (username.equals("") || password.equals("")) throw new EmptyTextFieldException();
            User user = User.getUserByUserInfo(username, UserInfoType.USERNAME);
            if (null != user) {
                if (user.isPasswordMatch(password)) {
                    if (LoginUser.getUser() == null) {
                        LoginUser.setUser(user);
                        moveToPage(message, Menu.MAIN_MENU);
                        return;
                    } else {
                        message.setText(Error.INVALID_LOGIN.toString());
                    }
                } else {
                    message.setText(Error.INVALID_USER_OR_PASS.toString());
                }
            } else message.setText(Error.INVALID_USER_OR_PASS.toString());
        } catch (EmptyTextFieldException | IOException e) {
            message.setText(e.getMessage());
        }
        displayMessage(message);
    }
}
