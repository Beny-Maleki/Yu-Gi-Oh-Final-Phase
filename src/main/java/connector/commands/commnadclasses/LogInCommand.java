package connector.commands.commnadclasses;

import connector.commands.Command;
import connector.commands.CommandType;
import client.model.userProp.User;

public class LogInCommand extends Command {
    private String username;
    private String password;
    private User user;

    public LogInCommand(CommandType commandType, String username, String password) {
        super(commandType);
        setUsername(username);
        setPassword(password);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
