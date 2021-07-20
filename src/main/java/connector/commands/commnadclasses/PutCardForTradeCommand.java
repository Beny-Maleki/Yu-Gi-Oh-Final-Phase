package connector.commands.commnadclasses;

import connector.CardForTrade;
import connector.commands.Command;
import connector.commands.CommandType;

public class PutCardForTradeCommand extends Command {
    private final CardForTrade cardForTrade;

    public PutCardForTradeCommand(CommandType commandType, String token, CardForTrade cardForTrade) {
        super(commandType);
        setToken(token);
        this.cardForTrade = cardForTrade;
    }

    public CardForTrade getCardForTrade() {
        return cardForTrade;
    }
}
