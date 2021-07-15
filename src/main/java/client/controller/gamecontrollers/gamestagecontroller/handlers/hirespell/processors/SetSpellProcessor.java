package client.controller.gamecontrollers.gamestagecontroller.handlers.hirespell.processors;

import animatefx.animation.ZoomIn;
import client.controller.gamecontrollers.gamestagecontroller.handlers.hirespell.SpellProcessor;
import client.model.cards.cardsEnum.Magic.MagicAttribute;
import client.model.cards.cardsProp.MagicCard;
import client.model.enums.GameEnums.CardLocation;
import client.model.enums.GameEnums.GamePhaseEnums.MainPhase;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.enums.GameEnums.cardvisibility.MagicHouseVisibilityState;
import client.model.gameprop.BoardProp.HandHouse;
import client.model.gameprop.BoardProp.MagicHouse;
import client.model.gameprop.BoardProp.PlayerBoard;
import client.model.gameprop.gamemodel.Game;

public class SetSpellProcessor extends SpellProcessor {
    public SetSpellProcessor(SpellProcessor processor) {
        super(processor);
    }

    public String process(Game game) {
        MagicCard magicCard = (MagicCard) game.getCardProp().getCard();
        PlayerBoard board = game.getPlayer(SideOfFeature.CURRENT).getBoard();
        if (magicCard.getMagicAttribute() == MagicAttribute.FIELD) {
            MagicHouse magicHouse = board.getFieldHouse();
            MagicCard previousFieldSpell = magicHouse.getMagicCard();
            if (previousFieldSpell != null) {
                board.moveCardToGraveYard(0, CardLocation.FIELD_ZONE);
            }
            return placeCardInBoard(game, magicCard, magicHouse);
        }
        for (MagicHouse magicHouse : board.getMagicHouse()) {
            if (magicHouse.getMagicCard() == null) {
                return placeCardInBoard(game, magicCard, magicHouse);
            }
        }
        try {
            throw new Exception("wrong process");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String placeCardInBoard(Game game, MagicCard magicCard, MagicHouse magicHouse) {
        magicHouse.setMagicCard(magicCard);
        magicHouse.setImageOfCard(false);
        magicHouse.setState(MagicHouseVisibilityState.H);
        new ZoomIn(magicHouse).play();
        game.setCardProp(null);
        HandHouse[] playerHand = game.getPlayer(SideOfFeature.CURRENT).getBoard().getPlayerHand();
        for (HandHouse handHouse : playerHand) {
            if (handHouse.getCard() == magicCard) {
                handHouse.removeCard();
                break;
            }
        }
        return MainPhase.SET_SUCCESSFULLY.toString();
    }
}
