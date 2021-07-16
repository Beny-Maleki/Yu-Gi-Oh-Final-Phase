package Connector.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Command {
    private CommandType commandType;
    private RuntimeException exception;

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

    public void setException(RuntimeException exception) {
        this.exception = exception;
    }

    public RuntimeException getException() {
        return exception;
    }
}
