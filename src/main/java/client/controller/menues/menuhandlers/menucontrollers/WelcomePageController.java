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
        User.serialize();
        Deck.serialize();
        FileWriter writer = new FileWriter("jsonResources//Decks.Json");
        writer.write(new Gson().toJson(Deck.getAllDecks()));
        writer.close();
        writer = new FileWriter("jsonResources//Users.Json");
        writer.write(new Gson().toJson(User.getAllUsers()));
        writer.close();
    }

    public void exit() throws IOException {
        saveData();
        System.exit(0);
    }
}
