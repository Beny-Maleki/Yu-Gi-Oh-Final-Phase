package client.model.cards.cardsActions.magicActionChildren;

import client.controller.gamecontrollers.GetStringInputFromView;
import client.model.cards.cardsActions.Action;
import connector.cards.MagicCard;
import connector.cards.MonsterCard;
import client.model.enums.GameEnums.RequestingInput;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.gameprop.Player;
import client.model.gameprop.existenceBasedObserver.EquipCardObserver;
import client.model.gameprop.existenceBasedObserver.ExistenceObserver;
import client.model.gameprop.gamemodel.Game;

import java.util.ArrayList;

public class ChangingSomeRaceEquipedMonsterDefence extends Action implements ChangingSomethingByEquipCard{
    private final ArrayList<String> typesToChangeDefence;
    private final int changeDefence;
    private final int addOrMinus;
    private MonsterCard equipedMonster;

    {
        name = this.getClass().getSimpleName();
    }

    public ChangingSomeRaceEquipedMonsterDefence(int changeDefence, int addOrMinus, ArrayList<String> typesToChangeDefence) {
        this.changeDefence = changeDefence;
        this.addOrMinus = addOrMinus;
        this.typesToChangeDefence = typesToChangeDefence;
    }

    public MonsterCard getEquipedMonster() {
        return equipedMonster;
    }

    @Override
    public void active(Game game) {
        Player ownerOfCard = game.getPlayer(SideOfFeature.CURRENT);

        MagicCard equipMagicCard = (MagicCard) game.getCardProp().getCard(); // the last selected Card is the spell Card!

        equipedMonster = Action.equipAMonsterWithSpell(this, game);

        if (typesToChangeDefence.contains(equipedMonster.getRace().toString())) {
           String result = change(changeDefence, equipedMonster, equipMagicCard, "Defence", ownerOfCard);

           if (result.equals("Successful")) {
               ArrayList<ExistenceObserver> existenceObservers = ExistenceObserver.getExistenceObservers();
               EquipCardObserver lastObserver = (EquipCardObserver) existenceObservers.get(existenceObservers.size() - 1);
               // it can be guaranteed that the last observer is related to this action!
               lastObserver.setToRevertAction(this);

               isActivatedBefore = true;
           } else {
               active(game);
           }
        } else {
            GetStringInputFromView.getInputFromView(RequestingInput.ERROR_OF_INVALID_MONSTER_CARD_EQUIPPED);
            active(game);
        }
    }

}
