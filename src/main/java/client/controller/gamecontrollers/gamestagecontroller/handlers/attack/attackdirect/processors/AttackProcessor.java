package client.controller.gamecontrollers.gamestagecontroller.handlers.attack.attackdirect.processors;

import client.controller.gamecontrollers.gamestagecontroller.handlers.attack.attackdirect.AttackDirectProcessor;
import connector.cards.MonsterCard;
import client.model.enums.GameEnums.GamePhaseEnums.BattlePhase;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.gameprop.BoardProp.MonsterHouse;
import client.model.gameprop.Player;
import client.model.gameprop.SelectedCardProp;
import client.model.gameprop.gamemodel.Game;

public class AttackProcessor extends AttackDirectProcessor {
    public AttackProcessor(AttackDirectProcessor processor) {
        super(processor);
    }

    public String process(Game game) {
        SelectedCardProp cardProp = game.getCardProp();
        Player target = game.getPlayer(SideOfFeature.OPPONENT);
        boolean isFirstTurnOfTheGame = game.isFirstTurnOfTheGame();
        if (isFirstTurnOfTheGame) {
            return BattlePhase.CANT_ATTACK_ON_FIRST_TURN.toString();
        }
        if (target.getBoard().numberOfFullHouse("monster") != 0) {
            return BattlePhase.CANT_ATTACK_DIRECT.toString();
        }
        MonsterCard monsterCard = (MonsterCard) cardProp.getCard();
        target.changePlayerLifePoint(monsterCard.getAttack());
        MonsterHouse monsterHouse = (MonsterHouse) cardProp.getCardPlace();

        monsterHouse.setMonsterAttacked(true);
        int practicalAttack = ((MonsterHouse) cardProp.getCardPlace()).getAdditionalAttack() + monsterCard.getAttack();
        return "you opponent receives " + practicalAttack + " battle damage";
    }
}
