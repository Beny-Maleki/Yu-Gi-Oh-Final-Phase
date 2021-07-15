package client.model.gameprop.BoardProp;

import animatefx.animation.FadeOut;
import javafx.scene.image.Image;
import client.model.cards.cardsProp.Card;
import client.model.cards.cardsProp.MagicCard;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.enums.GameEnums.cardvisibility.MagicHouseVisibilityState;
import client.model.gameprop.GameInProcess;
import client.model.gameprop.Player;
import client.model.gameprop.gamemodel.Game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MagicHouse extends GameHouse {
    MagicCard magicCard;
    MagicHouseVisibilityState state;

    {
        changePos = false;
        magicCard = null;
        state = MagicHouseVisibilityState.E;
    }

    public MagicHouse(int index) {
        super();
        this.index = index;
    }

    public static MagicHouse getMagicHouseByMagicCard(MagicCard magicCard) {
        Game game = GameInProcess.getGame();

        ArrayList<SideOfFeature> sides = new ArrayList<>();
        sides.add(SideOfFeature.CURRENT);
        sides.add(SideOfFeature.OPPONENT);

        for (SideOfFeature side : sides) {
            Player player = game.getPlayer(side);
            MagicHouse[] magicHouses = player.getBoard().getMagicHouse();

            for (MagicHouse magicHouse : magicHouses) {
                if (magicHouse.getMagicCard().equals(magicCard)) {
                    return magicHouse;
                }
            }
        }
        return null;
    }

    public String getState() {
        return state.toString();
    }

    public void setState(MagicHouseVisibilityState state) {
        this.state = state;
    }

    @Override
    public Image getCardImage() {
        switch (state) {
            case E:
                return null;
            case H:
                return GameHouse.getBackOfCardImage();
            default:
                return Card.getCardImage(this.getCard());
        }
    }

    public MagicCard getMagicCard() {
        return magicCard;
    }

    public void setMagicCard(MagicCard magicCard) {
        this.magicCard = magicCard;
    }

    @Override
    public Card getCard() {
        return magicCard;
    }

    private void setCard(Card card) throws FileNotFoundException {
        String name = card.getName();
        String nameWithoutSpace = name.replaceAll("\\s+", "");
        Image image = null;
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream("src/main/resources/graphicprop/images/Cards/SpellTrap/" + nameWithoutSpace + ".jpg");
            image = new Image(fileInputStream);
            cardImageFrame.setImage(image);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        }
    }


    public void setImageOfCard(boolean isVisible) {
        if (isVisible) {
            this.cardImageFrame.setImage(Card.getCardImage(magicCard));
        } else {
            this.cardImageFrame.setImage(GameHouse.getBackOfCardImage());
        }
    }

    @Override
    public int getIndex() {
        return index;
    }

    public void removeCard() {
        magicCard = null;
        cardImageFrame.setImage(null);
        new FadeOut(this).play();
    }
}
