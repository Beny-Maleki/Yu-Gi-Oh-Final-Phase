package client.controller.menues.menuhandlers.menucontrollers;

import com.google.gson.Gson;
import client.controller.Controller;
import javafx.scene.control.Label;
import server.ServerDataBase;
import connector.cards.Card;
import connector.cards.MagicCard;
import connector.cards.MonsterCard;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImportExportMenuController extends Controller {
//    public static String update(String command) {
//        String cardname = "a";
//        if (command.matches("import card .+")) {
//            Matcher matcher = Regex.getMatcher(command, "import card (.+)");
//            if (matcher.find()) {
//                cardname = matcher.group(1);
//            }
//            String path = "ServerResources\\ImportExport\\" + cardname + ".json";
//            try {
//                String json = new String(Files.readAllBytes(Paths.get(path)));
//                Card card;
//                if (json.contains("typeOfMagic")) {
//                    card = new Gson().fromJson(json, MagicCard.class);
//                } else {
//                    card = new Gson().fromJson(json, MonsterCard.class);
//                }
//                if (card instanceof MagicCard) {
//                    updateMagicCards((MagicCard) card);
//                } else {
//                    updateMonsterCards((MonsterCard) card);
//                }
//            } catch (IOException e) {
//                return "could not import because there is no json file";
//            }
//            return "import done rerun the program";
//        } else {
//            Matcher matcher = Regex.getMatcher(command, "export card (.+)");
//            if (matcher.find()) {
//                cardname = matcher.group(1);
//            }
//            Card card = Card.getCardByName(cardname);
//            if (card == null) {
//                return "card not found";
//            }
//            try {
//                File currentDirFile = new File(".");
//                String helper = currentDirFile.getAbsolutePath();
//                String path = helper.substring(0, helper.length() - 1);
//                path = path + "ServerResources\\ImportExport\\" + cardname + ".json";
//                File file = new File(path);
//                file.createNewFile();
//                FileWriter fileWriter = new FileWriter(path);
//                String json = new Gson().toJson(card);
//                fileWriter.write(json);
//                fileWriter.close();
//            } catch (IOException e) {
//                return "error occurred";
//            }
//            return "export done rerun the program";
//        }
//    }

    public void exportCard(Card card, Label message) {
        try {
                File currentDirFile = new File(".");
                String helper = currentDirFile.getAbsolutePath();
                String path = helper.substring(0, helper.length() - 1);
                path = path + "ServerResources\\ImportExport";
                new File(path).mkdirs();
                path = path + "\\" + card.getName() + ".json";
                File file = new File(path);
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(path);
                String json = new Gson().toJson(card);
                fileWriter.write(json);
                fileWriter.close();
                message.setText("export done PLEASE RERUN THE PROGRAM");
            } catch (IOException e) {
                message.setText("error occurred");
            }
    }

    public void importCard(String cardName, Label message) {
        if (cardName.equals("")) {
            message.setText("please fill the field first");
            return;
        }
        String path = "ServerResources\\ImportExport\\" + cardName + ".json";
            try {
                String json = new String(Files.readAllBytes(Paths.get(path)));
                Card card;
                if (json.contains("typeOfMagic")) {
                    card = new Gson().fromJson(json, MagicCard.class);
                } else {
                    card = new Gson().fromJson(json, MonsterCard.class);
                }
                if (card instanceof MagicCard) {
                    ServerDataBase.getInstance().saveMagics((MagicCard) card);
                } else {
                    ServerDataBase.getInstance().saveMonsters((MonsterCard) card);
                }
                message.setText("import done PLEASE RERUN THE PROGRAM");
            } catch (IOException e) {
                message.setText("could not import because there is no json file");
            }
    }
//
//    private static void updateMagicCards(MagicCard magicCard) {
//        ArrayList<MagicCard> magicCards;
//        magicCards = MagicCard.getMagicCards();
//        if (!magicCards.contains(magicCard)) {
//            magicCards.add(magicCard);
//        }
//        ServerDataBase.getInstance().saveMagicsToJson(magicCards);
//    }
//
//    private static void updateMonsterCards(MonsterCard monsterCard) {
//        ArrayList<MonsterCard> monsterCards;
//        monsterCards = (ArrayList<MonsterCard>) MonsterCard.getMonsterCards();
//        if (!monsterCards.contains(monsterCard)) {
//            monsterCards.add(monsterCard);
//        }
//        ServerDataBase.getInstance().saveMonstersToJson(monsterCards);
//    }
}
