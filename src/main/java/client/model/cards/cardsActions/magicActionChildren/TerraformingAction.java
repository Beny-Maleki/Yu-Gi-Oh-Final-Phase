package client.model.cards.cardsActions.magicActionChildren;

import client.model.cards.cardsActions.Action;
import client.model.cards.cardsEnum.Magic.MagicAttribute;
import connector.cards.Card;
import connector.cards.MagicCard;
import client.model.enums.GameEnums.RequestingInput;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.gameprop.BoardProp.HandHouse;
import client.model.gameprop.GameInProcess;
import client.model.gameprop.Player;
import client.model.gameprop.gamemodel.Game;
import client.view.game.GetStringInput;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TerraformingAction extends Action {

    {
        name = this.getClass().getSimpleName();
    }

    @Override
    public void active(Game game) throws FileNotFoundException {
        Player player = game.getPlayer(SideOfFeature.CURRENT);
        ArrayList<Card> mainDeck = player.getDeck().getMainDeck();
        ArrayList<Card> toShowCards = new ArrayList<>();

        HandHouse[] hand = player.getBoard().getPlayerHand();
        if (hand.length <= 6) {
            for (Card card : mainDeck) { // seeking "FIELD" spells in deck...
                if (card instanceof MagicCard) {
                    if (((MagicCard) card).getMagicAttribute() == MagicAttribute.FIELD) {
                        toShowCards.add(card);
                    }
                }
            }

            // TODO: showing cards to player in CLI!!!

            String selectedName = "";
            while (selectedName.length() == 0) { // I/O dialog...
                selectedName = GetStringInput.getInput(RequestingInput.CHOOSE_FIELD_CARD); // INCOMPLETE output!
            }

            for (Card card : mainDeck) { // actual process of Action...
                if (card.getName().equals(selectedName)) {
                    GameInProcess.getGame().moveCardFromDeckToHand(card);
                }
            }
        }


    }
}
