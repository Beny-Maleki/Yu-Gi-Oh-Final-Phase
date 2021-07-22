package connector.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.UUID;

public  class Command {
    private CommandType commandType;
    private RuntimeException exception;
    protected String token;
    protected String commandID;

    public Command(CommandType commandType) {
        commandID = UUID.randomUUID().toString();
        this.commandType = commandType;
    }


    public String getCommandID() {
        return commandID;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
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

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public static String makeJson(Command command) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(command);
    }

    public void changeCommandID() {
        commandID = UUID.randomUUID().toString();
    }
}
