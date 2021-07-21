package client.controller.menues.menuhandlers.menucontrollers;

import client.UserDataBase;
import client.model.userProp.LoginUser;
import client.network.Client;
import client.network.ClientListener;
import client.network.ClientSender;
import connector.CardForTrade;
import connector.commands.CommandType;
import connector.commands.commnadclasses.GetCardsOnTradeCommand;
import connector.commands.commnadclasses.PutCardForTradeCommand;

import java.util.ArrayList;


public class TradePageController {
    private static TradePageController instance;

    public static TradePageController getInstance() {
        if (instance == null) {
            instance = new TradePageController();
        }
        return instance;
    }

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

    public ArrayList<CardForTrade> getNewCardOnTrade(ArrayList<CardForTrade> cardsOnTrade) {
        ClientSender.getSender().sendMessage(new GetCardsOnTradeCommand(CommandType.GET_CARD_FOR_TRADES));
        try {
            while (ClientListener.getServerResponse().getCommandType() != CommandType.GET_CARD_FOR_TRADES) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        GetCardsOnTradeCommand getCardsOnTradeCommand = (GetCardsOnTradeCommand) ClientListener.getServerResponse();
        ArrayList<CardForTrade> allTradeRequest = getCardsOnTradeCommand.getCardForTrades();
        UserDataBase.getInstance().addElementsToCardOnTrades(allTradeRequest);
        ArrayList<CardForTrade> newTradeRequest = new ArrayList<>();
        if (LoginUser.getUser() == null) return null;
        for (CardForTrade cardForTrade : allTradeRequest) {
            if (!cardsOnTrade.contains(cardForTrade) && cardForTrade.getUser() != LoginUser.getUser()) {
                newTradeRequest.add(cardForTrade);
            }
        }
        return newTradeRequest;
    }

    public void updateUserDataBase() {
        GetCardsOnTradeCommand command = new GetCardsOnTradeCommand(CommandType.GET_CARD_FOR_TRADES);
        ClientListener.setCurrentCommandID(command.getCommandID());
        ClientListener.setServerResponse(command);
        ClientSender.getSender().sendMessage(command);
        waitForServerResponse();
        GetCardsOnTradeCommand response = (GetCardsOnTradeCommand) ClientListener.getServerResponse();
        UserDataBase.getInstance().addElementsToCardOnTrades(response.getCardForTrades());
    }

    private void waitForServerResponse() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {
            if (!ClientListener.getCurrentCommandID().equals(ClientListener.getServerResponse().getCommandID())) break;
        }
        ClientListener.setCurrentCommandID(ClientListener.getServerResponse().getCommandID());
    }

    public ArrayList<CardForTrade> getCardsOnTrade() {
        return UserDataBase.getInstance().getCardsForTrade();
    }
}
