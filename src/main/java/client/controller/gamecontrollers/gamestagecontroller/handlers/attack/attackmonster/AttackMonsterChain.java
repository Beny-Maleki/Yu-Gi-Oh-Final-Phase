package client.controller.gamecontrollers.gamestagecontroller.handlers.attack.attackmonster;

import client.controller.gamecontrollers.gamestagecontroller.handlers.attack.attackmonster.processors.AttackProcessor;
import client.model.gameprop.BoardProp.MonsterHouse;
import client.model.gameprop.gamemodel.Game;

public class AttackMonsterChain {
    AttackMonsterProcessor processor;

    public AttackMonsterChain() {
        buildChain();
    }

    private void buildChain() {
        processor = new AttackProcessor(null);
    }

    public String request(MonsterHouse monsterHouse, Game game) {
        return processor.process(monsterHouse, game);
    }


}
