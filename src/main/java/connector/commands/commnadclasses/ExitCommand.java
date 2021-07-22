package connector.commands.commnadclasses;

import connector.commands.Command;
import connector.commands.CommandType;

import java.net.Socket;

public class ExitCommand extends Command {
    private final Socket socket;

    public ExitCommand(CommandType commandType, Socket socket) {
        super(commandType);
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }
}
