package server;

import client.model.userProp.Deck;
import client.model.userProp.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import connector.cards.Card;
import connector.cards.MagicCard;
import connector.cards.MonsterCard;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ServerDataBase {
    private static ServerDataBase instance;

    private ServerDataBase() {
    }

    public static ServerDataBase getInstance() {
        if (instance == null) instance = new ServerDataBase();
        return instance;
    }

    public void updateID(Card card) {
        Objects.requireNonNull(ServerCardCollection.getCardById(Card.numberOfOriginalCards)).setID(card.getID());
        card.setID(Card.numberOfOriginalCards);
    }

    public void saveMonsters(MonsterCard monsterCard) {
        updateID(monsterCard);
        ArrayList<MonsterCard> monsterCards = new ArrayList<>();
        monsterCards = loadMonsterCards(monsterCards);
        monsterCards.add(monsterCard);
        saveMonstersToJson(monsterCards);
    }

    public void saveMagics(MagicCard magicCard) {
        updateID(magicCard);
        ArrayList<MagicCard> magicCards = new ArrayList<>();
        magicCards = loadMagicCards(magicCards);
        magicCards.add(magicCard);
        saveMagicsToJson(magicCards);
    }

    public void saveMagicsToJson(ArrayList<MagicCard> magicCards) {
        String json = new Gson().toJson(magicCards);
        Writer writer;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("ServerResources\\MagicCard.json"), StandardCharsets.UTF_8));
            writer.write(json);
            writer.close();
        } catch (IOException ignore) {
        }
    }

    public void saveMonstersToJson(ArrayList<MonsterCard> monsterCards) {
        String json = new Gson().toJson(monsterCards);
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("ServerResources\\MonsterCard.json"), StandardCharsets.UTF_8));
            writer.write(json);
            writer.close();
        } catch (IOException ignore) {
        }
    }

    public void restoreDate() {
        ArrayList<MonsterCard> monsterCards = new ArrayList<>();
        ArrayList<MagicCard> magicCards = new ArrayList<>();
        magicCards = loadMagicCards(magicCards);
        monsterCards = loadMonsterCards(monsterCards);
        Card.setNumberOfCard(magicCards.size() + monsterCards.size());
        Card.setNumberOfOriginalCards(magicCards.size() + monsterCards.size());
        Deck.setNumberOfOriginalCards(magicCards.size() + monsterCards.size());
        ServerCardCollection.setGameMagicCards(magicCards);
        loadUsers();
        //card Json
        //saveCardsToJson(monsterCards, magicCards);
    }

    private void saveCardsToJson(ArrayList<MonsterCard> monsterCards, ArrayList<MagicCard> magicCards) {
        String json = new Gson().toJson(magicCards);
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("ServerResources\\MagicCard.json"), StandardCharsets.UTF_8));
            writer.write(json);
        } catch (IOException ex) {
            // Report
        } finally {
            try {
                assert writer != null;
                writer.close();
            } catch (Exception ex) {//*ignore*//*}
            }
            json = new Gson().toJson(monsterCards);
            try {
                writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream("ServerResources\\MonsterCard.json"), StandardCharsets.UTF_8));
                writer.write(json);
            } catch (IOException ex) {
                // Report
            } finally {
                try {
                    writer.close();
                } catch (Exception ex) {//*ignore*//*}
                }


                //User Json
                loadUserAndDeckJson();
            }
        }
    }


    private ArrayList<MonsterCard> loadMonsterCards(ArrayList<MonsterCard> monsterCards) {
        try {
            String json = new String(Files.readAllBytes(Paths.get("src/main/java/server/ServerResources/MonsterCard.json")));
            monsterCards = new Gson().fromJson(json,
                    new TypeToken<List<MonsterCard>>() {
                    }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return monsterCards;
    }

    private ArrayList<MagicCard> loadMagicCards(ArrayList<MagicCard> magicCards) {
        try {
            String json = new String(Files.readAllBytes(Paths.get("src/main/java/server/ServerResources/MagicCard.json")));
            magicCards = new Gson().fromJson(json,
                    new TypeToken<List<MagicCard>>() {
                    }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return magicCards;
    }

    private void loadUsers() {
        //User Json
        loadUserAndDeckJson();
    }

    private void loadUserAndDeckJson() {
        String json;
        try {
            json = new String(Files.readAllBytes(Paths.get("src/main/java/server/ServerResources/Decks.json")));
            ArrayList<Deck> decks;
            decks = new Gson().fromJson(json,
                    new TypeToken<List<Deck>>() {
                    }.getType()
            );
            Deck.setAllDecks(decks);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            json = new String(Files.readAllBytes(Paths.get("src/main/java/server/ServerResources/Users.json")));
            ArrayList<User> users;
            users = new Gson().fromJson(json,
                    new TypeToken<List<User>>() {
                    }.getType()
            );
            User.setAllUsers(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}