package client.controller.gamecontrollers.gamestagecontroller.handlers.activeeffect.processors;

import client.controller.gamecontrollers.gamestagecontroller.handlers.activeeffect.ActiveEffectProcessor;
import client.model.cards.cardsEnum.Magic.MagicAttribute;
import client.model.cards.cardsProp.MagicCard;
import client.model.enums.GameEnums.CardLocation;
import client.model.enums.GameEnums.GamePhaseEnums.General;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.enums.GameEnums.cardvisibility.MagicHouseVisibilityState;
import client.model.events.eventChildren.ManuallyActivation;
import client.model.gameprop.BoardProp.MagicHouse;
import client.model.gameprop.SelectedCardProp;
import client.model.gameprop.gamemodel.Game;

import java.io.FileNotFoundException;

public class ActiveSpellProcessor extends ActiveEffectProcessor {


    public ActiveSpellProcessor(ActiveEffectProcessor processor) {
        super(processor);
    }

    public String process(Game game) throws FileNotFoundException {
        SelectedCardProp cardProp = game.getCardProp();
        MagicCard magicCard = (MagicCard) cardProp.getCard();
        if (cardProp.getLocation().equals(CardLocation.PLAYER_HAND)) {//activation of magic from hand
                if (magicCard.getMagicAttribute() == MagicAttribute.FIELD) {
                    MagicHouse house = game.getPlayer(SideOfFeature.CURRENT).getBoard().getFieldHouse();
                    MagicCard previousFieldSpell = house.getMagicCard();
                    if (previousFieldSpell != null) {
                        game.getPlayer(SideOfFeature.CURRENT).getBoard().moveCardToGraveYard(0, CardLocation.FIELD_ZONE);
                    }
                    setCardOnBoard(game, magicCard, house);
                    game.getPlayer(SideOfFeature.CURRENT).getBoard().removeCardFromPlayerHand(cardProp.getCard());
                } else {
                    for (MagicHouse house : game.getPlayer(SideOfFeature.CURRENT).getBoard().getMagicHouse()) {
                        if (house.getMagicCard() == null) {
                            game.getPlayer(SideOfFeature.CURRENT).getBoard().removeCardFromPlayerHand(cardProp.getCard());
                            setCardOnBoard(game, magicCard, house);
                            magicCard.setActivated(true);
                            break;
                        }
                }
            }
        }

        ManuallyActivation.getInstance().activeEffects(game);//activation of magic from board ( magic houses and field house)
        magicCard.setActivated(true);
        ((MagicHouse) cardProp.getCardPlace()).setState(MagicHouseVisibilityState.O);
        ((MagicHouse) cardProp.getCardPlace()).setImageOfCard(true);
        game.setCardProp(null);
        return General.SPELL_ACTIVATED_SUCCESSFULLY.toString();
    }

    private void setCardOnBoard(Game game, MagicCard magicCard, MagicHouse house) {
        if (magicCard.getMagicAttribute() != MagicAttribute.FIELD &&
                magicCard.getMagicAttribute() != MagicAttribute.CONTINUOUS &&
                magicCard.getMagicAttribute() != MagicAttribute.EQUIP &&
                magicCard.getMagicAttribute() != MagicAttribute.COUNTER) {
            game.getPlayer(SideOfFeature.CURRENT).getBoard().moveCardToGraveYard(magicCard);
        } else {
            house.setMagicCard(magicCard);
            house.setImageOfCard(true);
        }

    }
}
