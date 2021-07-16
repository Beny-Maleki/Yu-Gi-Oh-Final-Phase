package Connector.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Command {
    private CommandType commandType;

    public Command(CommandType commandType) {
        this.commandType = commandType;
    }

    public static String makeJson(Command command) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(command);
    }

    public CommandType getCommandType() {
        return commandType;
    }
}
