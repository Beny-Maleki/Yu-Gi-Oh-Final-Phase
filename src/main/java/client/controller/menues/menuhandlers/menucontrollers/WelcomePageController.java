package client.controller.menues.menuhandlers.menucontrollers;


import client.controller.Controller;
import client.network.Client;

import java.io.IOException;

public class WelcomePageController extends Controller {


    private static WelcomePageController controller;

    private WelcomePageController() {
    }

    public static WelcomePageController getInstance() {
        if (controller == null) controller = new WelcomePageController();
        return controller;
    }


    public void exit() throws IOException {
        Client.getClient().getSocket().close();
        //ClientSender.getSender().sendMessage(new ExitCommand(CommandType.EXIT, Client.getClient().getSocket()));
        System.exit(0);
    }
}
