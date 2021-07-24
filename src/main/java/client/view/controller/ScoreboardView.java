package client.view.controller;

import animatefx.animation.FadeIn;
import animatefx.animation.Tada;
import client.controller.menues.menuhandlers.menucontrollers.ScoreboardMenuController;
import client.model.enums.Menu;
import client.model.userProp.ScoreboardItem;
import client.view.ClickButtonHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class ScoreboardView {
    private static ScoreboardView scoreboardView;
    public TableView scoreboard;
    public Button Back;
    public ScoreboardMenuController controller;
    public VBox scoreboardContainer;
    public Button refresh;
    public Label message;

    private TableColumn<ScoreboardItem, String> usernameColumn;
    private TableColumn<ScoreboardItem, String> scoreColumn;
    private TableColumn<ScoreboardItem, String> rankColumn;
    private TableView<ScoreboardItem> tableView;

    {
        controller = ScoreboardMenuController.getInstance();
    }

    public static ScoreboardView getInstance() {
        if (scoreboardView == null) {
            scoreboardView = new ScoreboardView();
        }
        return scoreboardView;
    }

    public void setColumns() {
        rankColumn = new TableColumn<>("Rank");
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        rankColumn.setId("rank-column");
        rankColumn.setPrefWidth(100);
        rankColumn.setResizable(false);

        scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreColumn.setId("score-column");
        scoreColumn.setPrefWidth(160);
        scoreColumn.setResizable(false);

        usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        usernameColumn.setId("username-column");
        usernameColumn.setPrefWidth(230);
        usernameColumn.setResizable(false);
    }

    public TableView<ScoreboardItem> setTableView() {

        ArrayList<ScoreboardItem> scoreboardItemArrayList = controller.setScoreboardItems();
        ObservableList<ScoreboardItem> scoreboardItems = FXCollections.observableArrayList(scoreboardItemArrayList);
        tableView = new TableView<>();
        tableView.setId("scoreboard-table");
        tableView.getColumns().addAll(rankColumn, usernameColumn, scoreColumn);
        tableView.setItems(scoreboardItems);
        tableView.setEditable(true);

        return tableView;
    }

    public void setDetails() {
        setColumns();
        scoreboardContainer.getChildren().add(setTableView());
    }

    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == Back) {
            controller.moveToPage(Back, Menu.WELCOME_MENU);
        } else if (event.getSource() == refresh) {
            refresh();
        }
    }

    public void refresh() {
        message.setText("score board refreshed");
        ScoreboardMenuController.getInstance().displayMessage(message);
        new FadeIn(setTableView()).play();
    }

    public void hoverAnimation(MouseEvent event) {
        ClickButtonHandler.getInstance().play();
        new Tada((Node) event.getSource()).play();
    }
}
