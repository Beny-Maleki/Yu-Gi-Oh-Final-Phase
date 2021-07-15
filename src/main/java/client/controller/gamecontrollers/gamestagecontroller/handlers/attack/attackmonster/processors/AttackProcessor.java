package client.controller.gamecontrollers.gamestagecontroller.handlers.attack.attackmonster.processors;

import client.controller.gamecontrollers.gamestagecontroller.handlers.attack.attackmonster.AttackMonsterProcessor;
import client.model.cards.cardsProp.MonsterCard;
import client.model.enums.GameEnums.GamePhaseEnums.BattlePhase;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.enums.GameEnums.cardvisibility.MonsterHouseVisibilityState;
import client.model.enums.GameEnums.gamestage.GameMainStage;
import client.model.gameprop.BoardProp.MonsterHouse;
import client.model.gameprop.Player;
import client.model.gameprop.SelectedCardProp;
import client.model.gameprop.gamemodel.Game;

public class AttackProcessor extends AttackMonsterProcessor {
    public AttackProcessor(AttackMonsterProcessor processor) {
        super(processor);
    }

    public String process(MonsterHouse target, Game game) {
        SelectedCardProp offensive = game.getCardProp();
        MonsterCard offensiveCard = (MonsterCard) offensive.getCard();
        MonsterCard targetCard = target.getMonsterCard();
        MonsterHouse offensiveCardPlace = (MonsterHouse) offensive.getCardPlace();
        Player opponent = game.getPlayer(SideOfFeature.OPPONENT);
        Player current = game.getPlayer(SideOfFeature.CURRENT);

        if (game.getGameMainStage() != GameMainStage.BATTLE_PHASE)
            return BattlePhase.ATTACK_NOT_IN_BATTLE_PHASE.toString();

        offensiveCardPlace.setMonsterAttacked(true);

        int practicalAttackForOffensive = offensiveCard.getAttack() +
                ((MonsterHouse) offensive.getCardPlace()).getAdditionalAttack();
        int practicalAttackForTarget = targetCard.getAttack() +
                target.getAdditionalAttack();
        int practicalDefenceForTarget = targetCard.getDefence() +
                target.getAdditionalDefence();
        String output;
        int damageAmount;
        if (target.getState().equals("offensiveOccupied")) {

            damageAmount = practicalAttackForOffensive - practicalAttackForTarget;
            if (damageAmount > 0) {
                successfulAttackToOffensiveMonster(target, targetCard, opponent, damageAmount);
                output = BattlePhase.SUCCESSFUL_ATTACK_OFFENSIVE.toString();
            } else if (damageAmount == 0) {
                drawAttackToOffensiveMonster(target, offensiveCard, targetCard, offensiveCardPlace, opponent, current);
                output = BattlePhase.BOTH_CARD_ELIMINATE.toString();
            } else {
                defeatAttackToOffensiveMonster(offensiveCard, offensiveCardPlace, opponent, current, damageAmount);
                output = BattlePhase.DEFEAT_ATTACK_OO_TARGET.toString();
            }
        } else {
            damageAmount = practicalAttackForOffensive - practicalDefenceForTarget;
            if (damageAmount > 0) {
                target.setMonsterCard(null);
                opponent.getBoard().getGraveYard().addCardToGraveYard(offensiveCard);
                output = BattlePhase.SUCCESSFUL_ATTACK_ON_DEFENCE.toString();
            } else if (damageAmount == 0) {
                target.setState(MonsterHouseVisibilityState.DO);
                output = BattlePhase.NO_CARD_ELIMINATE.toString();
            } else {
                current.changePlayerLifePoint(damageAmount * -1);
                output = BattlePhase.DEFEAT_ATTACK_ON_DEFENCE.toString();
            }
        }
        output = modify(output, damageAmount);
        if (target.getState().equals("defenceHidden")) {
            return output + revealCard(target.getMonsterCard().getName());
        }
        return output;


    }

    private String revealCard(String name) {
        return "\nthe hidden monster card revealed : " + name;
    }

    private void defeatAttackToOffensiveMonster(MonsterCard offensiveCard, MonsterHouse offensiveCardPlace,
                                                Player opponent, Player current, int damage) {
        opponent.getBoard().getGraveYard().addCardToGraveYard(offensiveCard);
        offensiveCardPlace.setMonsterCard(null);
        current.changePlayerLifePoint(damage * -1);
    }

    private void drawAttackToOffensiveMonster(MonsterHouse target, MonsterCard offensiveCard, MonsterCard targetCard, MonsterHouse offensiveCardPlace, Player opponent, Player current) {
        offensiveCardPlace.setMonsterCard(null);
        target.setMonsterCard(null);
        opponent.getBoard().getGraveYard().addCardToGraveYard(offensiveCard);
        current.getBoard().getGraveYard().addCardToGraveYard(targetCard);
    }

    private void successfulAttackToOffensiveMonster(MonsterHouse target, MonsterCard targetCard, Player opponent, int attackDifference) {
        opponent.getBoard().getGraveYard().addCardToGraveYard(targetCard);
        target.setMonsterCard(null);
        opponent.changePlayerLifePoint(attackDifference);
    }

    private String modify(String message,
                          int damage) {
        if (message.contains("OO_DEFEAT")) {
            message = message.replace("OO_DEFEAT", String.valueOf(damage));
        } else if (message.contains("D_DEFEAT")) {
            message = message.replace("D_DEFEAT", String.valueOf(damage));
        } else if (message.contains("OO_WIN")) {
            message = message.replace("OO_WIN", String.valueOf(damage));
        }
        return message;
    }
}
