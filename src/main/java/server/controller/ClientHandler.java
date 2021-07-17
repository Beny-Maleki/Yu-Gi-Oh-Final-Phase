package server.controller;

import client.model.Message;
import client.model.userProp.Deck;
import client.model.userProp.LoginUser;
import client.model.userProp.User;
import client.model.userProp.UserInfoType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import connector.cards.Card;
import connector.cards.MagicCard;
import connector.cards.MonsterCard;
import connector.commands.Command;
import connector.commands.commnadclasses.ChatBoxCommand;
import connector.commands.commnadclasses.GetUsersCardCommand;
import connector.commands.commnadclasses.LogInCommand;
import connector.commands.commnadclasses.RegisterCommand;
import connector.exceptions.AlreadyLoggedIn;
import connector.exceptions.DuplicateNicknameException;
import connector.exceptions.DuplicateUsernameException;
import connector.exceptions.NotMatchingUserAndPass;
import server.MessageDatabase;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class ClientHandler implements Runnable {
    private final Scanner netIn;
    private final Formatter netOut;
    private final ClientInfo clientInfo;


    ClientHandler(Socket clientSocket) throws IOException {
        netIn = new Scanner(clientSocket.getInputStream());
        netOut = new Formatter(clientSocket.getOutputStream());
        clientInfo = new ClientInfo(clientSocket);
    }

    @Override
    public void run() {
        Gson yaGson = new GsonBuilder().create();

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

                case GET_USER_CARD: {
                    GetUsersCardCommand getUsersCardCommand = yaGson.fromJson(request, GetUsersCardCommand.class);
                    handleUserCardRequest(getUsersCardCommand);
                    break;
                }

                case CHAT: {
                    ChatBoxCommand chatBoxCommand = yaGson.fromJson(request, ChatBoxCommand.class);
                    handleChatBox(chatBoxCommand);
                    break;
                }
            }

        }
    }

    private void handleUserCardRequest(GetUsersCardCommand getUsersCardCommand) {
        String token = getUsersCardCommand.getToken();
        User user = ClientInfo.getUserByToken(token);
        List<MonsterCard> monsterCards = new ArrayList<>();
        List<MagicCard> magicCards = new ArrayList<>();
        assert user != null;
        separateUserCollectionCard(user, monsterCards, magicCards);
        for (Deck deck : user.getAllUserDecks()) {
            if (deck != null) {
                separateDeckCards(monsterCards, magicCards, deck.getMainDeck());
                separateDeckCards(monsterCards, magicCards, deck.getSideDeck());
            }
        }
        getUsersCardCommand.setUserMonsterCard(monsterCards);
        getUsersCardCommand.setUserMagicCard(magicCards);
        netOut.format("%s\n", Command.makeJson(getUsersCardCommand));
        netOut.flush();
    }

    private void separateDeckCards(List<MonsterCard> monsterCards, List<MagicCard> magicCards, ArrayList<Card> mainDeck) {
        for (Card card : mainDeck) {
            if (card instanceof MonsterCard) {
                monsterCards.add((MonsterCard) card);
            } else {
                magicCards.add((MagicCard) card);
            }
        }
    }

    private void separateUserCollectionCard(User user, List<MonsterCard> monsterCards, List<MagicCard> magicCards) {
        separateDeckCards(monsterCards, magicCards, user.getCardCollection());
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
            new User(username, nickname,password ,  imageAddress);
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
                    ClientInfo.addUserToLoggedIn(clientInfo, token, user);
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

    private void handleChatBox(ChatBoxCommand chatBoxCommand) {
        boolean haveSentNewMessage = chatBoxCommand.haveSentMessage();
        boolean haveOmittedMessage = chatBoxCommand.haveOmittedMessage();
        boolean haveEditedMessage = chatBoxCommand.haveEditedMessage();
        boolean havePinnedMessage = chatBoxCommand.havePinnedMessage();

        String sentMessage;
        User sender;
        if (haveSentNewMessage) {
            sentMessage = chatBoxCommand.getSentMessage();
            sender = chatBoxCommand.getSender();

            boolean isInReplyToAnother = chatBoxCommand.isInReplyToAnother();
            String IDInReplyTo = null;
            if (isInReplyToAnother) IDInReplyTo = chatBoxCommand.getIDInReplyTo();

            new Message(sentMessage, sender, isInReplyToAnother, IDInReplyTo);
        } else if (haveOmittedMessage) {
            String ID = chatBoxCommand.getMessageID();
            MessageDatabase.getInstance().removeFromAllMessages(ID);
        } else if (haveEditedMessage) {
            String ID = chatBoxCommand.getMessageID();
            Message message = MessageDatabase.getInstance().getFromAllMessages(ID);
            message.setMessage(chatBoxCommand.getSentMessage());
        } else if (havePinnedMessage) {
            String ID = chatBoxCommand.getMessageID();
            Message toPin = MessageDatabase.getInstance().getFromAllMessages(ID);
            MessageDatabase.getInstance().putToPinnedMessages(ID, toPin);
        }

        chatBoxCommand.setNumberOfLoggedIns(ClientInfo.getLoggedInClients().size());

        netOut.format("%s\n", ChatBoxCommand.toJson(chatBoxCommand));
        netOut.flush();
    }

}
