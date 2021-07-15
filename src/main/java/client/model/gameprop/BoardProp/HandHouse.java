package client.model.gameprop.BoardProp;

import animatefx.animation.FadeOutRight;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import client.model.cards.cardsProp.Card;
import client.model.gameprop.Selectable;

import java.io.FileNotFoundException;

public class HandHouse extends Pane implements Selectable {
    ImageView cardImage;
    Card card;
    int index;

    public HandHouse(int i) {
        cardImage = new ImageView();
        cardImage.setFitHeight(100);
        cardImage.setFitWidth(68);
        this.index = i;
        this.getChildren().add(cardImage);
    }

    @Override
    public Card getCard() {
        return card;
    }

    public void setCard(Card card) throws FileNotFoundException {
        this.card = card;
    }

    public void setImageOfCard(boolean isVisible) {
        if (card != null) {
            if (isVisible) {
                this.cardImage.setImage(Card.getCardImage(card));
            } else {
                this.cardImage.setImage(GameHouse.getBackOfCardImage());
            }
        }else{
            cardImage.setImage(null);
        }
    }

    @Override
    public int getIndex() {
        return 0;
    }

    @Override
    public String getState() {
        return "hand";
    }

    public void removeCard() {
        card = null;
        new FadeOutRight(cardImage);
        cardImage.setImage(null);
    }

    public boolean doesHaveImage() {
        return card != null;
    }

}
