package client.controller.menues.menuhandlers.menucontrollers;

import client.UserDataBase;
import client.model.userProp.LoginUser;
import client.network.Client;
import client.network.ClientSender;
import connector.CardForTrade;
import connector.commands.CommandType;
import connector.commands.commnadclasses.PutCardForTradeCommand;

import java.util.ArrayList;

public class TradePageController {
    public void putCardForTrade(int numberOfTradedCard, String nameOfTradedCard) {
        CardForTrade cardForTrade = new CardForTrade(LoginUser.getUser(), nameOfTradedCard, numberOfTradedCard);
        PutCardForTradeCommand message =
                new PutCardForTradeCommand(CommandType.PUT_CARD_FOR_TRADE, Client.getClient().getToken(), cardForTrade);
        ClientSender.getSender().sendMessage(message);
        updateUserCardCollection(nameOfTradedCard, numberOfTradedCard);
    }

    private void updateUserCardCollection(String cardName, int numberOfCardsOnTrade) {
        ArrayList<Integer> userCardCollection = LoginUser.getUser().getUserCardCollectionInInteger();
        int removedCard = 0;
        for (int i = userCardCollection.size() - 1; i >= 0; i--) {
            if (removedCard == numberOfCardsOnTrade) {
                break;
            }
            if (UserDataBase.getInstance().getCardById(userCardCollection.get(i)).getName().equals(cardName)) {
                userCardCollection.remove(i);
                removedCard++;
            }
        }
    }
}
