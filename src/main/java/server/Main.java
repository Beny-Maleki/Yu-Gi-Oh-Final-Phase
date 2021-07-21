package server;

import client.model.userProp.Deck;
import client.model.userProp.User;
import com.google.gson.Gson;
import server.controller.ServerGate;

import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ServerDataBase.getInstance().restoreDate();
        ServerGate.initialize();
    }

    public void saveData() throws IOException {
        FileWriter writer = new FileWriter("ServerResources//Decks.Json");
        writer.write(new Gson().toJson(Deck.getAllDecks()));
        writer.close();
        writer = new FileWriter("ServerResources//Users.Json");
        writer.write(new Gson().toJson(User.getAllUsers()));
        writer.close();
    }
}
