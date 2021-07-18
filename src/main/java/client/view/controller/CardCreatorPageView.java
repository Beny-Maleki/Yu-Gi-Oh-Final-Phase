package client.view.controller;

import animatefx.animation.Wobble;
import animatefx.animation.ZoomOut;
import client.controller.menues.menuhandlers.menucontrollers.CardCreatorController;
import client.model.cards.cardsEnum.Magic.MagicAttribute;
import client.model.cards.cardsEnum.Magic.MagicType;
import client.model.cards.cardsEnum.Magic.RestrictionTypeInAdding;
import client.model.cards.cardsEnum.Monster.MonsterAttribute;
import client.model.cards.cardsEnum.Monster.MonsterRace;
import client.model.cards.cardsEnum.Monster.MonsterType;
import client.model.enums.Menu;
import client.view.ClickButtonHandler;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class CardCreatorPageView {
    public Pane monsterPane;
    public ChoiceBox<MonsterRace> monsterRace;
    public TextField monsterName;
    public TextField monsterAttack;
    public TextField monsterDefense;
    public ChoiceBox<MonsterType> monsterType;
    public ChoiceBox<MonsterAttribute> monsterAttribute;
    public Button createMonster;
    public Pane magicPane;
    public TextField magicName;
    public ChoiceBox<MagicAttribute> magicAttribute;
    public ChoiceBox<MagicType> magicType;
    public Button chooseMagicActions;
    public Label monsterError;
    public Label magicError;
    public ChoiceBox<Integer> level;
    public ChoiceBox<RestrictionTypeInAdding> restrictionType;
    private final CardCreatorController controller;
    public Button backButton;
    public AnchorPane root;

    {
        controller = new CardCreatorController();
    }

    public void transferDataToMagicChooseAction(ChooseMagicActionsView controller) {
        controller.setDetails(magicName.getText(), magicType.getValue(), magicAttribute.getValue(), restrictionType.getValue());
    }

    public void transferDataToMonsterChooseAction(ChooseMonsterActionsView controller) {
        controller.setDetails(monsterName.getText(), monsterAttribute.getValue(), monsterType.getValue(), monsterRace.getValue()
        , monsterAttack.getText(), monsterDefense.getText(), level.getValue());
    }

    @FXML
    public void initialize() {
        monsterRace.getItems().addAll(FXCollections.observableArrayList(MonsterRace.values()));
        monsterAttribute.getItems().addAll(FXCollections.observableArrayList(MonsterAttribute.values()));
        monsterType.getItems().addAll(FXCollections.observableArrayList(MonsterType.NORMAL, MonsterType.RITUAL));
        magicAttribute.getItems().addAll(FXCollections.observableArrayList(MagicAttribute.values()));
        magicType.getItems().addAll(FXCollections.observableArrayList(MagicType.values()));
        level.getItems().addAll(FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11));
        restrictionType.getItems().addAll(FXCollections.observableArrayList(RestrictionTypeInAdding.values()));
    }

    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == chooseMagicActions) {
            if (controller.isFieldsFilled(magicName, magicAttribute, magicType, magicError)) {
                Stage stage;
                Scene scene;
                FXMLLoader loader = new FXMLLoader(getClass().getResource(Menu.CHOOSE_MAGIC_ACTIONS.getAddress()));
                Parent parent = loader.load();
                stage = (Stage) chooseMagicActions.getScene().getWindow();
                scene = new Scene(parent);
                ChooseMagicActionsView controller = loader.getController();
                transferDataToMagicChooseAction(controller);
                stage.setScene(scene);
                scene.getRoot().requestFocus();
                stage.show();
            }
        } else if (event.getSource() == createMonster) {
            if (controller.isFieldsFilled(monsterName, monsterAttribute, monsterRace, monsterType, monsterAttack, monsterDefense, monsterError)) {
                Stage stage;
                Scene scene;
                FXMLLoader loader = new FXMLLoader(getClass().getResource(Menu.CHOOSE_MONSTER_ACTIONS.getAddress()));
                Parent parent = loader.load();
                stage = (Stage) createMonster.getScene().getWindow();
                scene = new Scene(parent);
                ChooseMonsterActionsView controller = loader.getController();
                transferDataToMonsterChooseAction(controller);
                stage.setScene(scene);
                scene.getRoot().requestFocus();
                stage.show();
            }
        } else if (event.getSource() == backButton) {
            new ZoomOut(root).play();
            controller.moveToPage(backButton, Menu.MAIN_MENU);
        }
    }

    public void hoverAnimation(MouseEvent event) {
        ClickButtonHandler.getInstance().play();
        new Wobble((Node) event.getSource()).play();
    }
}
