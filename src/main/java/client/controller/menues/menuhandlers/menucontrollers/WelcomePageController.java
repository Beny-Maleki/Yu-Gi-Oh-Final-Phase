package client.controller.menues.menuhandlers.menucontrollers;


import com.google.gson.Gson;
import client.controller.Controller;
import client.model.userProp.Deck;
import client.model.userProp.User;

import java.io.FileWriter;
import java.io.IOException;

public class WelcomePageController extends Controller {


    private static WelcomePageController controller;

    private WelcomePageController() {
    }

    public static WelcomePageController getInstance() {
        if (controller == null) controller = new WelcomePageController();
        return controller;
    }

    public void saveData() throws IOException {
        FileWriter writer = new FileWriter("ServerResources//Decks.Json");
        writer.write(new Gson().toJson(Deck.getAllDecks()));
        writer.close();
        writer = new FileWriter("ServerResources//Users.Json");
        writer.write(new Gson().toJson(User.getAllUsers()));
        writer.close();
    }

    public void exit() throws IOException {
        saveData();
        System.exit(0);
    }
}
