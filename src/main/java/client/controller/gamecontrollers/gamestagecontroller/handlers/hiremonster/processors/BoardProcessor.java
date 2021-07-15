package client.controller.gamecontrollers.gamestagecontroller.handlers.hiremonster.processors;

import client.controller.gamecontrollers.gamestagecontroller.handlers.hiremonster.MonsterProcessor;
import client.model.enums.GameEnums.GamePhaseEnums.MainPhase;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.enums.GameEnums.TypeOfHire;
import client.model.gameprop.BoardProp.PlayerBoard;
import client.model.gameprop.gamemodel.Game;

public class BoardProcessor extends MonsterProcessor {
    public BoardProcessor(MonsterProcessor processor) {
        super(processor);
    }

    public String process(Game game, TypeOfHire type) {
        PlayerBoard board = game.getPlayer(SideOfFeature.CURRENT).getBoard();
        if (game.getHiredMonster() != null) return MainPhase.HIRE_MONSTER_BEFORE.toString();
        if (board.numberOfFullHouse("monster") == 5) return MainPhase.BOARD_IS_FULL.toString();
        return super.process(game, type);
    }

}
