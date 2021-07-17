package client.controller.gamecontrollers.gamestagecontroller.handlers.hirespell.processors;

import client.controller.gamecontrollers.gamestagecontroller.handlers.hirespell.SpellProcessor;
import client.model.cards.cardsEnum.Magic.MagicAttribute;
import connector.cards.MagicCard;
import client.model.enums.GameEnums.GamePhaseEnums.MainPhase;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.gameprop.BoardProp.PlayerBoard;
import client.model.gameprop.gamemodel.Game;

public class BoardProcessor extends SpellProcessor {
    public BoardProcessor(SpellProcessor processor) {
        super(processor);
    }

    public String process(Game game) {
        MagicCard magicCard = (MagicCard) game.getCardProp().getCard();
        if (magicCard.getMagicAttribute() != MagicAttribute.FIELD) {
            PlayerBoard board = game.getPlayer(SideOfFeature.CURRENT).getBoard();
            if (board.numberOfFullHouse("spell") == 5) return MainPhase.BOARD_IS_FULL.toString();
        }
        return super.process(game);
    }
}
