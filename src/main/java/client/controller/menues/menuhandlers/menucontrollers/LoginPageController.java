package client.controller.menues.menuhandlers.menucontrollers;

import Connector.commands.CommandType;
import Connector.commands.LogInCommand;
import client.controller.Controller;
import client.network.ClientListener;
import client.network.ClientSender;
import javafx.scene.control.Label;
import client.model.Exceptions.EmptyTextFieldException;
import client.model.enums.Error;
import client.model.enums.Menu;
import client.model.userProp.LoginUser;
import client.model.userProp.User;
import client.model.userProp.UserInfoType;

import java.io.IOException;

public class LoginPageController extends Controller {

    public void login(String username, String password, Label message) {
        message.setStyle("-fx-text-fill: red ; -fx-font-size: 15");

        try {
            if (username.equals("") || password.equals("")) throw new EmptyTextFieldException();

            ClientSender.getSender().sendMessage(new LogInCommand(CommandType.LOGIN, username, password));

            handleProgressBar();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            LogInCommand response = (LogInCommand) ClientListener.getServerResponse();
            if (responseException != null) {
                LoginUser.setUser(response.getUser());
                message.setText(responseException.getMessage());
                responseException = null;
            } else {
                moveToPage(message, Menu.MAIN_MENU);
            }
        } catch (EmptyTextFieldException | IOException e) {
            message.setText(e.getMessage());
        }
        message.setStyle("-fx-text-fill: red ; -fx-font-size: 15");
        displayMessage(message);
    }
}
