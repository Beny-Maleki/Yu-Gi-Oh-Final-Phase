package client.model.events.eventChildren;

import client.model.enums.GameEnums.SideOfFeature;
import client.model.events.Event;
import client.model.gameprop.BoardProp.MagicHouse;
import client.model.gameprop.BoardProp.MonsterHouse;
import client.model.gameprop.BoardProp.PlayerBoard;
import client.model.gameprop.gamemodel.Game;

import java.io.FileNotFoundException;

public class CurrentMonsterWantsToAttack extends Event {
    private static CurrentMonsterWantsToAttack instance;

    private CurrentMonsterWantsToAttack() {
    }

    public static CurrentMonsterWantsToAttack getInstance() {
        if (instance == null) {
            instance = new CurrentMonsterWantsToAttack();
        }
        return instance;
    }

    @Override
    public void activeEffects(Game game) throws FileNotFoundException {
        PlayerBoard opponentPlayerBoard = game.getPlayer(SideOfFeature.OPPONENT).getBoard();
        for (MagicHouse magicHouse : opponentPlayerBoard.getMagicHouse()) {
            magicHouse.getMagicCard().activeEffectsByEvent(this, game);
        }
        for (MonsterHouse monsterHouse : opponentPlayerBoard.getMonsterHouse()) {
            monsterHouse.getMonsterCard().activeEffectsByEvent(this, game);
        }
    }
}
