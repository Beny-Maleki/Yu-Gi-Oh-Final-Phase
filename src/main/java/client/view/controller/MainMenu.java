package client.view.controller;

import com.sanityinc.jargs.CmdLineParser;
import client.model.enums.Error;
import client.model.enums.MenusMassages.Main;
import client.controller.ImportScanner;
import client.controller.menues.menuhandlers.menucontrollers.MainMenuController;
import client.model.userProp.LoginUser;
import client.model.userProp.User;
import client.view.Regex;
import client.view.menudisplay.MainMenuDisplay;

import java.io.IOException;
import java.util.regex.Matcher;

public class MainMenu {
    private static MainMenu mainMenu;

    public static MainMenu getInstance() {
        if (mainMenu == null) {
            mainMenu = new MainMenu();
        }
        return mainMenu;
    }

    private static void recognizeCommand(String command) throws CmdLineParser.OptionException, IOException {
        Matcher matcher;
        if ((matcher = Regex.getMatcher(command, Regex.menuEnter)).matches()) {
            MainMenuController.enterMenu(matcher);
        } else if (command.equals("client.controller show-current")) {
            MainMenuController.showCurrentMenu();
        } else if (command.startsWith("cheat code: ")){
            matcher = Regex.getMatcher(command, Regex.moneyCheat);

            if (matcher.matches()) {
                String amount = matcher.group("amount");

                int amountInt;
                try {
                    amountInt = Integer.parseInt(amount);
                } catch (NumberFormatException e) {
                    MainMenuDisplay.display(Error.CHEAT_DENIED);
                    return;
                }

                User loggedInUser = LoginUser.getUser();

                loggedInUser.changeBalance(amountInt);

                int newBalance = loggedInUser.getBalance();

                MainMenuDisplay.display(Main.MONEY_CHEAT_ACCEPTED, String.valueOf(amountInt), String.valueOf(newBalance));
            } else {
                MainMenuDisplay.display(Error.CHEAT_DENIED);
            }
        } else {
            MainMenuController.invalidCommand();
        }
    }

    public void run() throws CmdLineParser.OptionException, IOException {
        String command;
        while (true) {
            command = ImportScanner.getInput();
            if (command.equals("client.controller exit")) {
                MainMenuDisplay.display(Main.SUCCESSFULLY_EXIT_MENU);
                break;
            } else if (command.equals("user logout")) {
                LoginUser.setUser(null);
                MainMenuDisplay.display(Main.SUCCESSFULLY_LOGOUT);
                break;
            }
            recognizeCommand(command);
        }

    }
}
