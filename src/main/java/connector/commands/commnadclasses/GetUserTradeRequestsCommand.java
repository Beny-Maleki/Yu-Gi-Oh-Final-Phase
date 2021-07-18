package connector.commands.commnadclasses;

import connector.TradeRequest;
import connector.commands.Command;
import connector.commands.CommandType;

import java.util.ArrayList;

public class GetUserTradeRequestsCommand extends Command {
    private ArrayList<TradeRequest> requests;

    public GetUserTradeRequestsCommand(CommandType commandType, String token) {
        super(commandType);
        this.token = token;
    }

    public void setRequests(ArrayList<TradeRequest> requests) {
        this.requests = requests;
    }

    public ArrayList<TradeRequest> getRequests() {
        return requests;
    }
}
