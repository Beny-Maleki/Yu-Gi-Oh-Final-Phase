package client.model.gameprop.BoardProp;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import client.model.gameprop.Selectable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public abstract class GameHouse extends Pane implements Selectable {
    private static Image backOfCardImage;

    static {
        try {
            backOfCardImage = new Image(new FileInputStream("src/main/resources/graphicprop/images/backOfCard.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean changePos;
    protected ImageView cardImageFrame;
    protected int index;

    public GameHouse() {
        cardImageFrame = new ImageView();
        cardImageFrame.setFitWidth(46);
        cardImageFrame.setFitHeight(68);
        this.getChildren().add(cardImageFrame);
    }

    public static Image getBackOfCardImage() {
        return backOfCardImage;
    }

    public abstract Image getCardImage();

    public ImageView getCardImageFrame() {
        return cardImageFrame;
    }
}
