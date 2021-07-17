package client.controller.gamecontrollers;

import client.controller.gamecontrollers.gamestagecontroller.DrawPhaseController;
import client.controller.gamecontrollers.gamestagecontroller.handlers.activeeffect.ActiveEffectChain;
import connector.cards.Card;
import connector.cards.MonsterCard;
import client.model.enums.GameEnums.CardLocation;
import client.model.enums.GameEnums.GameError;
import client.model.enums.GameEnums.GamePhaseEnums.General;
import client.model.enums.GameEnums.PlayerTurn;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.enums.GameEnums.cardvisibility.MagicHouseVisibilityState;
import client.model.enums.GameEnums.cardvisibility.MonsterHouseVisibilityState;
import client.model.enums.GameEnums.gamestage.GameMainStage;
import client.model.gameprop.BoardProp.GraveYard;
import client.model.gameprop.BoardProp.MagicHouse;
import client.model.gameprop.BoardProp.MonsterHouse;
import client.model.gameprop.GameInProcess;
import client.model.gameprop.Player;
import client.model.gameprop.Selectable;
import client.model.gameprop.SelectedCardProp;
import client.model.gameprop.gamemodel.Game;
import client.view.game.UserInterface;

import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneralController {

    private static GeneralController instance;

    public static GeneralController getInstance() {
        if (instance == null) instance = new GeneralController();
        return instance;
    }

    public String showGraveYard(Game game, String command) {
        Player player;
        if (command.contains("--opponent")) {
            player = game.getPlayer(SideOfFeature.OPPONENT);
        } else {
            player = game.getPlayer(SideOfFeature.CURRENT);
        }

        StringBuilder graveYardDisplay = new StringBuilder();

        GraveYard graveYard = player.getBoard().getGraveYard();

        if (graveYard.getDestroyedCards().size() == 0) {
            graveYardDisplay.append("graveyard empty");
        } else {
            for (Card destroyedCard : graveYard.getDestroyedCards()) {
                graveYardDisplay.append(destroyedCard.getName()).append(":").
                        append(destroyedCard.getDescription()).append("\n");
            }
            graveYardDisplay.deleteCharAt(graveYardDisplay.length() - 1);
        }
        return graveYardDisplay.toString();
    }

    public void selectCard(Game game, Selectable selectable) {
        boolean isCardBelongToCurrent = game.getPlayer(SideOfFeature.CURRENT).getBoard().doesBelong(selectable);
        SelectedCardProp<Selectable> selectedCard = new SelectedCardProp<>(isCardBelongToCurrent, selectable);
        game.setCardProp(selectedCard);
    }

    private String deSelectCard(Game game) {
        if (game.getCardProp() != null) {
            game.setCardProp(null);
            return General.CARD_DESELECT_SUCCESSFULLY.toString();
        } else {
            return GameError.INVALID_DESELECT_CARD_REQUEST.toString();
        }
    }

    private String showSelectedCard(Game game) {

        SelectedCardProp cardProp = game.getCardProp();
        String output;

        if (cardProp == null) output = General.NO_CARD_SELECTED.toString();
        else if (!cardProp.doesBelongToCurrent()) {
            if (cardProp.getLocation().equals(CardLocation.SPELL_ZONE)) {
                MagicHouse magicHouse = (MagicHouse) cardProp.getCardPlace();
                if (magicHouse.getState().equals(MagicHouseVisibilityState.H)) {
                    output = GameError.INVALID_SHOW_CARD_REQUEST.toString();
                } else {
                    output = cardProp.getCard().getCardDetail();
                }
            } else {
                MonsterHouse monsterHouse = (MonsterHouse) cardProp.getCardPlace();
                if (monsterHouse.getState().equals(MonsterHouseVisibilityState.DH)) {
                    output = GameError.INVALID_SHOW_CARD_REQUEST.toString();
                } else {
                    output = cardProp.getCard().getCardDetail();
                    if (cardProp.getCard() instanceof MonsterCard && cardProp.getLocation() == CardLocation.MONSTER_ZONE) {
                        monsterHouse = (MonsterHouse) cardProp.getCardPlace();
                        int additionalAttack = monsterHouse.getAdditionalAttack();
                        int additionalDefence = monsterHouse.getAdditionalDefence();
                        return output + "\n additional Attack : "
                                + additionalAttack
                                + "\n additional Defence : " + additionalDefence;
                    }
                }
            }
        } else {
            output = cardProp.getCard().getCardDetail();
            if (cardProp.getCard() instanceof MonsterCard && cardProp.getLocation() == CardLocation.MONSTER_ZONE) {
                MonsterHouse monsterHouse = (MonsterHouse) cardProp.getCardPlace();
                int additionalAttack = monsterHouse.getAdditionalAttack();
                int additionalDefence = monsterHouse.getAdditionalDefence();
                return output + "\n additional Attack : "
                        + additionalAttack
                        + "\n additional Defence : " + additionalDefence;
            }

        }
        return output;
    }

    public String nextPhase(Game game) throws FileNotFoundException {
        game.goToNextPhase();
        String output = game.getGameMainStage().getPhaseName();
        if (game.getGameMainStage().equals(GameMainStage.DRAW_PHASE)) {
            if (game.doesPlayerHavePermissionToDraw()) {
                new DrawPhaseController().draw(false);
            }
        }  // ActivationInOpponentTurn.getInstance().activeEffects(game);

        return output;
    }

    public boolean finishRound(Game game) throws FileNotFoundException {
      game.finishRound(game.getTurnOfGame());
        return roundOrGameResults(game);
    }


    private boolean roundOrGameResults(Game game) {
        return game.isGameFinished();
    }

    private String drawBoard(Game game) {
        UserInterface drawer = new UserInterface(game);
        return drawer.drawBoard();
    }

    private String drawSideDeck(Game game, Player player) {
        UserInterface drawer = new UserInterface(game);
        return drawer.showSideDeck(player);
    }

    private String process(String generalMessage, String name) {
        if (generalMessage.contains("StAgE")) {
            generalMessage = generalMessage.replace("StAgE", name);
        }
        return generalMessage;
    }

    public String activeEffect(Game game) throws FileNotFoundException {
        ActiveEffectChain chain = new ActiveEffectChain();
        return chain.request(game);
    }

    private String runCheatCode(String cheatCode) throws FileNotFoundException {
        // increase money is implemented in main client.controller!
        if (cheatCode.contains("winner")) {
            Game game = GameInProcess.getGame();
            PlayerTurn playerTurn = game.getTurnOfGame();

            if (playerTurn.equals(PlayerTurn.PLAYER_ONE)) {
                game.finishRound(PlayerTurn.PLAYER_TWO);
            } else if (playerTurn.equals(PlayerTurn.PLAYER_TWO)) {
                game.finishRound(PlayerTurn.PLAYER_ONE);
            }
            return null;
            //return roundOrGameResults(game);
        } else if (cheatCode.contains("draw")) {
            return DrawPhaseController.getInstance().draw(true);
        } else if (cheatCode.contains("increase LP")) {
            String regex = "cheat code: increase LP -> (?<amount>\\d+)";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(cheatCode);

            String output;
            if (matcher.matches()) {
                String amount = matcher.group("amount");
                int amountInt = Integer.parseInt(amount);

                Game game = GameInProcess.getGame();
                Player currPlayer = game.getPlayer(SideOfFeature.CURRENT);

                int LPAfterIncrease = currPlayer.getPlayerLifePoint() + amountInt;
                if (LPAfterIncrease <= 8000) {
                    currPlayer.setPlayerLifePoint(LPAfterIncrease);
                    output = "Your LP increased by " + amountInt + " and now is " + currPlayer.getPlayerLifePoint();
                } else {
                    output = "cheat denied! Don't be greedy :) Maximum possible LP is 8000!";
                }
            } else {
                output = "cheat denied! You were close :) but the format wasn't completely true!";
            }

            return output;
        } else {
            return "cheat denied! Cheat with such pattern doesn't exist!";
        }
    }

    protected String processAnswer(Game game, String rawOutPut) throws FileNotFoundException {
        game.checkRoundFinishCondition();
        if (game.isRoundFinish()) {
            return rawOutPut + "\n" + finishRound(game);
        }
        return rawOutPut;
    }
}
