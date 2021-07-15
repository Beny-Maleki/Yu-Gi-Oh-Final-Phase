package client.controller.menues.menuhandlers.menucontrollers;

import client.controller.Controller;
import client.model.enums.Error;
import client.model.enums.MenusMassages.Main;
import client.model.userProp.LoginUser;
import client.view.menudisplay.MainMenuDisplay;

import java.util.regex.Matcher;

public class MainMenuController extends Controller {
    public static void showCurrentMenu() {
        MainMenuDisplay.display(Main.CURRENT_MENU);
    }

    public static void invalidCommand() {
        MainMenuDisplay.display(Error.INVALID_COMMAND);
    }

    public static void enterMenu(Matcher matcher) {
    }

    public void logout() {
        LoginUser.setUser(null);
    }

}
