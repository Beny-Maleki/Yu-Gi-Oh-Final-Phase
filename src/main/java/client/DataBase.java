package client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import client.model.cards.cardsProp.Card;
import client.model.cards.cardsProp.MagicCard;
import client.model.cards.cardsProp.MonsterCard;
import client.model.userProp.Deck;
import client.model.userProp.User;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class DataBase {
    private static DataBase instance;

    private DataBase() {
    }

    public static DataBase getInstance() {
        if (instance == null) instance = new DataBase();
        return instance;
    }

    public void updateID(Card card) {
        Card.getCardById(Card.numberOfOriginalCards).setID(card.getID());
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
                    new FileOutputStream("jsonResources\\MagicCard.json"), StandardCharsets.UTF_8));
            writer.write(json);
            writer.close();
        } catch (IOException ignore) {
        }
    }

    public void saveMonstersToJson(ArrayList<MonsterCard> monsterCards) {
        String json = new Gson().toJson(monsterCards);
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("jsonResources\\MonsterCard.json"), StandardCharsets.UTF_8));
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
//        setMagicCardsDetail(magicCards);
        MagicCard.setMagicCards(magicCards);
        setMonsterCardsDetail(monsterCards);
        loadUsers();
        deserializeData();
        //card Json
//        String json = new Gson().toJson(magicCards);
//        Writer writer = null;
//        try {
//            writer = new BufferedWriter(new OutputStreamWriter(
//                    new FileOutputStream("jsonResources\\MagicCard.json"), StandardCharsets.UTF_8));
//            writer.write(json);
//        } catch (IOException ex) {
//            // Report
//        } finally {
//            try {
//                assert writer != null;
//                writer.close();
//            } catch (Exception ex) {//*ignore*//*}
//            }
//            json = new Gson().toJson(monsterCards);
//            try {
//                writer = new BufferedWriter(new OutputStreamWriter(
//                        new FileOutputStream("jsonResources\\MonsterCard.json"), StandardCharsets.UTF_8));
//                writer.write(json);
//            } catch (IOException ex) {
//                // Report
//            } finally {
//                try {
//                    writer.close();
//                } catch (Exception ex) {//*ignore*//*}
//                }
//
//
//                //User Json
//                try {
//                    json = new String(Files.readAllBytes(Paths.get("jsonResources\\Decks.Json")));
//                    ArrayList<Deck> decks;
//                    decks = new Gson().fromJson(json,
//                            new TypeToken<List<Deck>>() {
//                            }.getType()
//                    );
//                    Deck.setAllDecks(decks);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    json = new String(Files.readAllBytes(Paths.get("jsonResources\\Users.Json")));
//                    ArrayList<User> users;
//                    users = new Gson().fromJson(json,
//                            new TypeToken<List<User>>() {
//                            }.getType()
//                    );
//                    User.setAllUsers(users);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

    private void deserializeData() {
        User.deSerialize();
        Deck.deSerialize();
    }

    private void setMonsterCardsDetail(ArrayList<MonsterCard> monsterCards) {
        try (CSVReader reader = new CSVReader(new FileReader("csvFile\\Monster.csv"))) {
            String[] infoRow;
            int counter = 0;
            reader.readNext(); // dummy read to skip the title row
            while ((infoRow = reader.readNext()) != null) {
                MonsterCard monsterCard = monsterCards.get(counter);
                monsterCard.setDetails(infoRow[0], infoRow[1], infoRow[2],
                        infoRow[3], infoRow[4], infoRow[5],
                        infoRow[6], infoRow[7], infoRow[8]);
                counter++;
//                MonsterCard monsterCard = new MonsterCard(infoRow[0], infoRow[1], infoRow[2],
//                        infoRow[3], infoRow[4], infoRow[5],
//                        infoRow[6], infoRow[7], infoRow[8]);
//                monsterCards.add(monsterCard);
            }
            MonsterCard.setMonsterCards(monsterCards);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setMagicCardsDetail(ArrayList<MagicCard> magicCards) {
        try (CSVReader reader = new CSVReader(new FileReader("csvFile\\SpellTrap.csv"))) {
            String[] lineInArray;
            int counter = 0;
            while ((lineInArray = reader.readNext()) != null) {
                MagicCard magicCard = magicCards.get(counter);
                magicCard.setDetails(lineInArray[0], lineInArray[1],
                        lineInArray[2], lineInArray[3],
                        lineInArray[4], lineInArray[5]);
                counter++;
//              magicCards.add(new MagicCard(lineInArray[0], lineInArray[1],
//                       lineInArray[2], lineInArray[3],
//                       lineInArray[4], lineInArray[5]));
            }
            MagicCard.setMagicCards(magicCards);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<MonsterCard> loadMonsterCards(ArrayList<MonsterCard> monsterCards) {
        try {
            String json = new String(Files.readAllBytes(Paths.get("jsonResources\\MonsterCard.json")));
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
            String json = new String(Files.readAllBytes(Paths.get("jsonResources\\MagicCard.json")));
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
        String json;
        try {
            json = new String(Files.readAllBytes(Paths.get("jsonResources\\Decks.Json")));
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
            json = new String(Files.readAllBytes(Paths.get("jsonResources\\Users.Json")));
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