package server.controller;

import Connector.commands.Command;
import Connector.commands.LogInCommand;
import Connector.commands.RegisterCommand;
import Connector.exceptions.AlreadyLoggedIn;
import Connector.exceptions.DuplicateNicknameException;
import Connector.exceptions.DuplicateUsernameException;
import Connector.exceptions.NotMatchingUserAndPass;
import client.model.enums.Error;
import client.model.enums.Menu;
import client.model.userProp.LoginUser;
import client.model.userProp.User;
import client.model.userProp.UserInfoType;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

import java.io.IOException;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;
import java.util.UUID;

public class ClientHandler implements Runnable {
    private Scanner netIn;
    private Formatter netOut;
    private ClientInfo clientInfo;


    ClientHandler(Socket clientSocket) throws IOException {
        netIn = new Scanner(clientSocket.getInputStream());
        netOut = new Formatter(clientSocket.getOutputStream());

        clientInfo = new ClientInfo(clientSocket);
    }

    @Override
    public void run() {
        YaGson yaGson = new YaGsonBuilder().create();

        while (true) {
            String request = netIn.nextLine();
            Command command = yaGson.fromJson(request, Command.class);

            switch (command.getCommandType()) {
                case REGISTER: {
                    RegisterCommand registerCommand = yaGson.fromJson(request, RegisterCommand.class);
                    handleRegister(registerCommand);
                    break;
                }
                case LOGIN: {
                    LogInCommand logInCommand = yaGson.fromJson(request, LogInCommand.class);
                    handleLogIn(logInCommand);
                    break;
                }
            }

        }
    }

    private void handleRegister(RegisterCommand registerCommand) {
        String username = registerCommand.getUsername();
        String nickname = registerCommand.getNickname();
        String password = registerCommand.getPassword();
        String imageAddress = registerCommand.getImageAddress();

        if (null != User.getUserByUserInfo(username, UserInfoType.USERNAME)) {
            registerCommand.setException(new DuplicateUsernameException());
        } else if (null != User.getUserByUserInfo(nickname, UserInfoType.NICKNAME)) {
            registerCommand.setException(new DuplicateNicknameException());
        } else {
            new User(username, password, nickname, imageAddress);
            registerCommand.setException(null);
        }

        netOut.format("%s\n", Command.makeJson(registerCommand));
        netOut.flush();
    }

    private void handleLogIn(LogInCommand logInCommand) {
        String username = logInCommand.getUsername();
        String password = logInCommand.getPassword();

        User user = User.getUserByUserInfo(username, UserInfoType.USERNAME);
        if (null != user) {
            if (user.isPasswordMatch(password)) {
                if (LoginUser.getUser() == null) {
                    logInCommand.setUser(user);
                    String token = UUID.randomUUID().toString();

                    logInCommand.setToken(token);
                    ClientInfo.addUserToLoggedIn(clientInfo);
                } else {
                    logInCommand.setException(new AlreadyLoggedIn());
                }
            } else {
                logInCommand.setException(new NotMatchingUserAndPass());
            }
        } else {
            logInCommand.setException(new NotMatchingUserAndPass());
        }

        netOut.format("%s\n", Command.makeJson(logInCommand));
        netOut.flush();
    }
}