package client.model.gameprop.existenceBasedObserver;

import client.model.cards.cardsActions.Action;
import client.model.cards.cardsActions.magicActionChildren.ChangingMonsterAttackAction;
import client.model.cards.cardsActions.magicActionChildren.ChangingMonsterAttackWithGraveyardsMonster;
import client.model.cards.cardsActions.magicActionChildren.ChangingMonsterDefenceAction;
import connector.cards.Card;
import connector.cards.MagicCard;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.gameprop.BoardProp.MagicHouse;
import client.model.gameprop.GameInProcess;
import client.model.gameprop.Player;
import client.model.gameprop.gamemodel.Game;

public class FieldCardObserver extends ExistenceObserver {
    protected MagicCard observedFieldCard;
    protected Action toRevertAction;

    public FieldCardObserver(Card observedCard, Player ownerOfCard) {
        super(observedCard, ownerOfCard);
        setObservedFieldCard((MagicCard) observedCard);
    }

    public void setObservedFieldCard(MagicCard observedFieldCard) {
        this.observedFieldCard = observedFieldCard;
    }

    public void setToRevertAction(Action toRevertAction) {
        this.toRevertAction = toRevertAction;
    }

    @Override
    public void update() {
        if (this.notExists()) {
            revertActionAsFinalize();
            existenceObservers.remove(this);
        } else {
            performActionForNewCards();
        }
    }

    @Override
    public boolean notExists() {
        Game game = GameInProcess.getGame();
        Player current = game.getPlayer(SideOfFeature.CURRENT);
        Player opponent = game.getPlayer(SideOfFeature.OPPONENT);

        MagicHouse fieldHouseCurr = current.getBoard().getFieldHouse();
        MagicHouse fieldHouseOppo = opponent.getBoard().getFieldHouse();

        boolean isObservedCardInCurrFieldHouse;
        boolean isObservedCardInOppoFieldHouse;
        if (fieldHouseCurr.getMagicCard() == null && fieldHouseOppo.getMagicCard() == null) {
            return true;
        } else if (fieldHouseCurr.getMagicCard() == null) {
            return !fieldHouseOppo.getMagicCard().equals(observedCard);
        } else if (fieldHouseOppo.getMagicCard() == null) {
            return !fieldHouseCurr.getMagicCard().equals(observedCard);
        } else {
            isObservedCardInCurrFieldHouse = fieldHouseCurr.getMagicCard().equals(observedCard);
            isObservedCardInOppoFieldHouse = fieldHouseOppo.getMagicCard().equals(observedCard);
            return !(isObservedCardInCurrFieldHouse || isObservedCardInOppoFieldHouse);
        }
    }

    private void performActionForNewCards() {
        if (toRevertAction instanceof ChangingMonsterDefenceAction) {
            ChangingMonsterDefenceAction action = (ChangingMonsterDefenceAction) toRevertAction;
            action.change(action.getFinalChangeValue(), action.getChangeWhichTeamMonsterDefence(), action.getTypesToChangeDefence(), ownerOfCard, "Defence");
        } else if (toRevertAction instanceof ChangingMonsterAttackAction) {
            ChangingMonsterAttackAction action = (ChangingMonsterAttackAction) toRevertAction;
            action.change(action.getFinalChangeValue(), action.getChangeWhichTeamMonsterAttack(), action.getTypesToChangeAttack(), ownerOfCard, "Attack");
        } else if (toRevertAction instanceof ChangingMonsterAttackWithGraveyardsMonster) {
            ChangingMonsterAttackWithGraveyardsMonster action = (ChangingMonsterAttackWithGraveyardsMonster) toRevertAction;
            action.change(action.getFinalChangeValue(), action.getChangeWhichTeamMonstersAttack(), action.getTypesToChangeAttack(), ownerOfCard, "Attack");
        } // else: impossible :)
    }

    private void revertActionAsFinalize() {
        if (toRevertAction instanceof ChangingMonsterDefenceAction) {
            ChangingMonsterDefenceAction action = (ChangingMonsterDefenceAction) toRevertAction;
            action.revertChange(action.getChangeWhichTeamMonsterDefence(), action.getFinalChangeValue() * (-1), "Defence");
        } else if (toRevertAction instanceof ChangingMonsterAttackAction) {
            ChangingMonsterAttackAction action = (ChangingMonsterAttackAction) toRevertAction;
            action.revertChange(action.getChangeWhichTeamMonsterAttack(), action.getFinalChangeValue() * (-1), "Attack");
        } else if (toRevertAction instanceof ChangingMonsterAttackWithGraveyardsMonster) {
            ChangingMonsterAttackWithGraveyardsMonster action = (ChangingMonsterAttackWithGraveyardsMonster) toRevertAction;
            action.revertChange(action.getChangeWhichTeamMonstersAttack(), action.getFinalChangeValue() * (-1), "Attack");
        } // else: impossible :)
    }
}
