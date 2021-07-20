package connector.commands.commnadclasses;

import connector.CardForTrade;
import connector.commands.Command;
import connector.commands.CommandType;

import java.util.ArrayList;

public class GetCardsOnTradeCommand extends Command {
    private ArrayList<CardForTrade> cardForTrades;

    public GetCardsOnTradeCommand(CommandType commandType) {
        super(commandType);
    }

    public ArrayList<CardForTrade> getCardForTrades() {
        return cardForTrades;
    }

    public void setCardForTrades(ArrayList<CardForTrade> cardForTrades) {
        this.cardForTrades = cardForTrades;
    }
}
