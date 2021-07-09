package controller.menues.menuhandlers.menucontrollers;

import controller.Controller;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.DataBase;
import model.cards.cardsActions.Action;
import model.cards.cardsEnum.Magic.MagicAttribute;
import model.cards.cardsEnum.Magic.MagicType;
import model.cards.cardsEnum.Magic.RestrictionTypeInAdding;
import model.cards.cardsEnum.Monster.MonsterAttribute;
import model.cards.cardsEnum.Monster.MonsterRace;
import model.cards.cardsEnum.Monster.MonsterType;
import model.cards.cardsProp.MagicCard;
import model.cards.cardsProp.MonsterCard;
import model.enums.Menu;
import model.events.Event;
import model.userProp.LoginUser;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;

public class CardCreatorController extends Controller {
    public boolean isFieldsFilled(TextField magicName, ChoiceBox<MagicAttribute> magicAttributeChoiceBox, ChoiceBox<MagicType> magicTypeChoiceBox, Label magicError) {
        boolean result = magicAttributeChoiceBox.getValue() != null && magicTypeChoiceBox.getValue() != null && !magicName.getText().equals("");
        if (!result) {
            magicError.setText("please fill all the fields");
            displayMessage(magicError);
        }
        return result;
    }

    public boolean isFieldsFilled(TextField monsterName, ChoiceBox<MonsterAttribute> monsterAttributeChoiceBox, ChoiceBox<MonsterRace> monsterRaceChoiceBox, ChoiceBox<MonsterType> monsterTypeChoiceBox
            , TextField monsterAttack, TextField monsterDefense, Label monsterError) {
        boolean result = !monsterName.getText().equals("") && monsterAttributeChoiceBox.getValue() != null && monsterRaceChoiceBox.getValue() != null && monsterTypeChoiceBox.getValue() != null
                && !monsterAttack.getText().equals("") && !monsterDefense.getText().equals("");
        if (!result) {
            monsterError.setText("please fill all the fields");
            displayMessage(monsterError);
        }
        return result;
    }

    public void createMonster(String name, MonsterType monsterType, MonsterRace monsterRace, MonsterAttribute monsterAttribute, String attack,
                              String defense, String price, String description, String level, Label message) throws IOException {
        if (description.equals("")) {
            message.setText("please fill description first");
            displayMessage(message);
        } else {
            new MonsterCard(name, level, monsterAttribute.toString(), monsterRace.toString(), monsterType.toString(), attack, defense, description, price.split(" ")[1]);
            DataBase.getInstance().saveMonsters();
            message.setText("you create monster successfully");
            displayMessage(message);
            moveToPage(message, Menu.CARD_CREATOR_PAGE);
        }
    }

    public void addActions(MagicCard currentMagicCard, ArrayList<Action> actions, ArrayList<Event> triggers, Label message) {
        actions.addAll(currentMagicCard.getActionsOfMagic());
        triggers.addAll(currentMagicCard.getTriggers());
        message.setText("effects added successfully");
        displayMessage(message);
    }

    public void removeActions(MagicCard currentMagicCard, ArrayList<Action> actions, ArrayList<Event> triggers, Label message) {
        actions.removeAll(currentMagicCard.getActionsOfMagic());
        triggers.removeAll(currentMagicCard.getTriggers());
        message.setText("effects removed successfully");
        displayMessage(message);
    }

    public void updatePrice(ArrayList<Action> actions, RestrictionTypeInAdding restrictionTypeInAdding, MagicType magicType, Label price) {
        int priceInt = 800;
        priceInt += actions.size() * 2000;
        if (restrictionTypeInAdding.toString().equals("Unlimited")) {
            priceInt += 1000;
        }
        if (magicType.toString().equals("Spell")) {
            priceInt += 500;
        } else {
            priceInt += 1000;
        }
        price.setText("Price: " + priceInt);
    }

    public void createMagic(String name, String typeOfMagic, String magicAttribute, String description, String typeOfRestriction, String price, ArrayList<Action> actions, ArrayList<Event> triggers, Label message) throws IOException {
        message.setText("you creat magic successfully and 10% of price costs you");
        int priceInt = Integer.parseInt(price.split(" ")[1]);
        MagicCard magicCard = new MagicCard(name, typeOfMagic, magicAttribute, description, typeOfRestriction, String.valueOf(priceInt));
        magicCard.setActionsOfMagic(actions);
        magicCard.setTriggers(triggers);
        DataBase.getInstance().saveMagics();
        LoginUser.getUser().changeBalance((priceInt / 10) * -1);
        displayMessage(message);
        moveToPage(message, Menu.CARD_CREATOR_PAGE);
    }
}
