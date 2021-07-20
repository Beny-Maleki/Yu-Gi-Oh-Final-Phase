package client.view.controller;

import animatefx.animation.Wobble;
import client.controller.menues.menuhandlers.menucontrollers.CardCreatorController;
import client.model.cards.cardsEnum.Monster.MonsterAttribute;
import client.model.cards.cardsEnum.Monster.MonsterRace;
import client.model.cards.cardsEnum.Monster.MonsterType;
import client.model.enums.Menu;
import client.view.ClickButtonHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ChooseMonsterActionsView {
    public Label price;
    public TextArea description;
    public Button create;
    public Button back;
    public Label message;
    public Label details;
    private String name;
    private MonsterAttribute monsterAttribute;
    private MonsterType monsterType;
    private MonsterRace monsterRace;
    private String attack;
    private String defense;
    private Integer level;
    CardCreatorController controller;

    {
        controller = new CardCreatorController();
    }

    public void setDetails(String name, MonsterAttribute monsterAttribute, MonsterType monsterType, MonsterRace monsterRace
            , String attack, String defense, Integer level) {
        this.name = name;
        this.monsterAttribute = monsterAttribute;
        this.monsterType = monsterType;
        this.monsterRace = monsterRace;
        this.attack = attack;
        this.defense = defense;
        this.level = level;
        this.price.setText(calculatePrice());
        setDetailOfCard(details);
    }

    private void setDetailOfCard(Label details) {
        String detailOfCard = "Name: " + name +
                "\nLevel: " + level +
                "\nType: " + monsterRace.toString() +
                "\nATK: " + attack +
                "\nDEF: " + defense;
        details.setText(detailOfCard);
    }

    private String calculatePrice() {
        int attackInt = Integer.parseInt(attack), defenseInt = Integer.parseInt(defense);
        int priceInt = attackInt + defenseInt + level * 200;
        return "Price: " + priceInt;
    }

    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == back) {
            controller.moveToPage(back, Menu.CARD_CREATOR_PAGE);
        } else if (event.getSource() == create) {
            controller.createMonster(name, monsterType, monsterRace, monsterAttribute, attack, defense, price.getText(), description.getText(), String.valueOf(level), message);
            description.clear();
        }
    }

    public void hoverAnimation(MouseEvent event) {
        ClickButtonHandler.getInstance().play();
        new Wobble((Node) event.getSource()).play();
    }
}
