package connector.commands.commnadclasses;

import connector.commands.Command;
import connector.commands.CommandType;

public class LogoutCommand extends Command {
    public LogoutCommand(CommandType commandType, String token) {
        super(commandType);
        setToken(token);
    }

}
