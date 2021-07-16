package Connector.commands;

import Connector.commands.Command;
import Connector.commands.CommandType;

public class RegisterCommand extends Command {
    private String username;
    private String nickname;
    private String password;
    private String imageAddress;

    public RegisterCommand(CommandType commandType, String username, String nickname, String password, String imageAddress) {
        super(commandType);
        setUsername(username);
        setPassword(password);
        setNickname(nickname);
        setImageAddress(imageAddress);
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

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    public String getImageAddress() {
        return imageAddress;
    }

}
