package client.controller.gamecontrollers.gamestagecontroller;

import client.controller.gamecontrollers.GeneralController;
import client.controller.gamecontrollers.gamestagecontroller.handlers.attack.attackdirect.AttackDirectChain;
import client.controller.gamecontrollers.gamestagecontroller.handlers.attack.attackmonster.AttackMonsterChain;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.gameprop.BoardProp.MonsterHouse;
import client.model.gameprop.GameInProcess;
import client.model.gameprop.gamemodel.Game;

import java.io.FileNotFoundException;

public class BattlePhaseController extends GeneralController {
    private static BattlePhaseController instance;

    public BattlePhaseController() {
    }

    public static BattlePhaseController getInstance() {
        if (instance == null) instance = new BattlePhaseController();
        return instance;
    }

    public String run(String command) throws FileNotFoundException {
        Game game = GameInProcess.getGame();
        String output = null;
        if (command.equals("attack direct")) {
            output = attackDirect(game);
        } else if (command.startsWith("attack")) {
            int address = Character.getNumericValue(command.charAt(7));
            output = attackMonsterHouse(game,
                    game.getPlayer(SideOfFeature.OPPONENT).getBoard().getMonsterHouse()[address - 1]);
        }
        return processAnswer(game, output);
    }


    public String attackMonsterHouse(Game game, MonsterHouse target) {
        AttackMonsterChain chain = new AttackMonsterChain();

        game.setCardProp(null);
        return chain.request(target, game);
    }

    public String attackDirect(Game game) {
        AttackDirectChain chain = new AttackDirectChain();
        return chain.request(game);
    }
}
