package client.controller.gamecontrollers.gamestagecontroller;

import com.sanityinc.jargs.CmdLineParser;
import client.controller.gamecontrollers.GeneralController;
import client.controller.gamecontrollers.gamestagecontroller.handlers.changeposition.ChangePosChain;
import client.controller.gamecontrollers.gamestagecontroller.handlers.flipsummon.FlipSummonChain;
import client.controller.gamecontrollers.gamestagecontroller.handlers.hiremonster.HireMonsterChain;
import client.controller.gamecontrollers.gamestagecontroller.handlers.hirespell.SetSpellChain;
import client.model.cards.cardsProp.MagicCard;
import client.model.cards.cardsProp.MonsterCard;
import client.model.enums.GameEnums.GamePhaseEnums.MainPhase;
import client.model.enums.GameEnums.TypeOfHire;
import client.model.enums.GameEnums.WantedPos;
import client.model.enums.GameEnums.gamestage.GameMainStage;
import client.model.enums.GameEnums.gamestage.GameSideStage;
import client.model.gameprop.BoardProp.MonsterHouse;
import client.model.gameprop.GameInProcess;
import client.model.gameprop.SelectedCardProp;
import client.model.gameprop.gamemodel.Game;

import java.io.FileNotFoundException;

public class MainPhaseController extends GeneralController {
    private static MainPhaseController instance;

    public MainPhaseController() {
    }

    public static MainPhaseController getInstance() {
        if (instance == null) instance = new MainPhaseController();
        return instance;
    }

    public String run(String command) throws CmdLineParser.OptionException, FileNotFoundException {
        Game game = GameInProcess.getGame();
        String output = null;
        if (game.getGameSideStage().equals(GameSideStage.NONE)) {
            if (game.getGameMainStage().equals(GameMainStage.FIRST_MAIN_PHASE) ||
                    game.getGameMainStage().equals(GameMainStage.SECOND_MAIN_PHASE)) {
                if (command.equals("summon")) {
                    output = hireCard(game, TypeOfHire.SUMMON);
                } else if (command.equals("set")) {
                    output = hireCard(game, TypeOfHire.SET);
                } else if (command.startsWith("set --position")) {
                    MonsterHouse hiredMonsterHouse = game.getHiredMonster();
                    output = changePosition(game.getCardProp(), command, hiredMonsterHouse);
                } else if (command.equals("flip-summon")) {
                    output = flipSummon(game);
                }
            } else {
                output = "you can’t do this action in this phase";
            }
        } else output = "back to game first";
        return processAnswer(game, output);
    }

    public String hireCard(Game game, TypeOfHire type) {

        String answerToRequest;
        SelectedCardProp cardProp = game.getCardProp();
        if (cardProp.getCard() instanceof MonsterCard) {
            HireMonsterChain chain = new HireMonsterChain();
            if ((answerToRequest = chain.request(game, type)) != null) {
                return answerToRequest;
            }
        } else if (cardProp.getCard() instanceof MagicCard) {
            if (type != TypeOfHire.SET) return MainPhase.CANT_SUMMON_MAGIC.toString();
            SetSpellChain chain = new SetSpellChain();
            if ((answerToRequest = chain.request(game)) != null) {
                return answerToRequest;
            }
        }
        return null;
    }


    public String changePosition(SelectedCardProp cardProp, String command, MonsterHouse hiredMonsterHouse) {
        ChangePosChain chain = new ChangePosChain();
        if (command.contains("attack")) {
            return chain.request(cardProp, WantedPos.ATTACK, hiredMonsterHouse);
        } else {
            return chain.request(cardProp, WantedPos.DEFENCE, hiredMonsterHouse);
        }
    }

    private String flipSummon(Game game) {
        FlipSummonChain chain = new FlipSummonChain();
        return chain.request(game);
    }

}
