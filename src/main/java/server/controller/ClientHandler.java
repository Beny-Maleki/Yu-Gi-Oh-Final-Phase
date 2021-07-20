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
import connector.commands.commnadclasses.*;
import connector.exceptions.AlreadyLoggedIn;
import connector.exceptions.DuplicateNicknameException;
import connector.exceptions.DuplicateUsernameException;
import connector.exceptions.NotMatchingUserAndPass;
import server.MessageDatabase;
import server.ServerDataAnalyser;

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
                    registerCommand.changeCommandID();
                    handleRegister(registerCommand);
                    break;
                }
                case LOGIN: {
                    LogInCommand logInCommand = yaGson.fromJson(request, LogInCommand.class);
                    logInCommand.changeCommandID();
                    handleLogIn(logInCommand);
                    break;
                }

                case GET_USER_CARD: {
                    GetUsersCardCommand getUsersCardCommand = yaGson.fromJson(request, GetUsersCardCommand.class);
                    getUsersCardCommand.changeCommandID();
                    handleUserCardRequest(getUsersCardCommand);
                    break;
                }
                case GET_USER_TRADE_REQUEST: {
                    GetUserTradeRequestsCommand getUserTradeRequestsCommand =
                            yaGson.fromJson(request, GetUserTradeRequestsCommand.class);
                    getUserTradeRequestsCommand.changeCommandID();
                    handleUserTradeRequests(getUserTradeRequestsCommand);
                    break;
                }

                case PUT_CARD_FOR_TRADE: {
                    PutCardForTradeCommand putCardForTradeCommand =
                            yaGson.fromJson(request, PutCardForTradeCommand.class);
                    handleNewCardOnTrade(putCardForTradeCommand);
                    break;
                }

                case GET_CARD_FOR_TRADES: {
                    GetCardsOnTradeCommand getCardsOnTradeCommand =
                            yaGson.fromJson(request, GetCardsOnTradeCommand.class);
                    handleGetCardsOnTradeCommand(getCardsOnTradeCommand);
                    break;
                }

                case CHAT: {
                    ChatBoxCommand chatBoxCommand = yaGson.fromJson(request, ChatBoxCommand.class);
                    chatBoxCommand.changeCommandID();
                    handleChatBox(chatBoxCommand);
                    break;
                }
            }

        }
    }

    private void handleGetCardsOnTradeCommand(GetCardsOnTradeCommand getCardsOnTradeCommand) {
        getCardsOnTradeCommand.setCardForTrades(ServerDataAnalyser.getInstance().getCardsOnTrades());
        netOut.format("%s\n", Command.makeJson(getCardsOnTradeCommand));
        netOut.flush();
    }

    private void handleNewCardOnTrade(PutCardForTradeCommand putCardForTradeCommand) {
        ServerDataAnalyser.getInstance().putCardForTrade(putCardForTradeCommand.getCardForTrade());
        //update user interfaces
    }

    private void handleUserTradeRequests(GetUserTradeRequestsCommand getUserTradeRequestsCommand) {
        String token = getUserTradeRequestsCommand.getToken();
        User user = ClientInfo.getUserByToken(token);
        getUserTradeRequestsCommand.setRequests(ServerDataAnalyser.getInstance().findAllTradeRequestOfThisUser(user));
        netOut.format("%s\n", Command.makeJson(getUserTradeRequestsCommand));
        netOut.flush();
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
                ArrayList<Card> mainDeckCards = ServerDataAnalyser.getInstance().convertIDsToCard(deck.getMainDeckCardsID());
                ArrayList<Card> sideDeckCards = ServerDataAnalyser.getInstance().convertIDsToCard(deck.getSideDeckCardsID());
                separateMonsterAndMagics(monsterCards, magicCards, mainDeckCards);
                separateMonsterAndMagics(monsterCards, magicCards, sideDeckCards);
            }
        }
        getUsersCardCommand.setUserMonsterCard(monsterCards);
        getUsersCardCommand.setUserMagicCard(magicCards);
        netOut.format("%s\n", Command.makeJson(getUsersCardCommand));
        netOut.flush();
    }

    private void separateMonsterAndMagics(List<MonsterCard> monsterCards, List<MagicCard> magicCards, ArrayList<Card> mainDeck) {
        for (Card card : mainDeck) {
            if (card instanceof MonsterCard) {
                monsterCards.add((MonsterCard) card);
            } else {
                magicCards.add((MagicCard) card);
            }
        }
    }

    private void separateUserCollectionCard(User user, List<MonsterCard> monsterCards, List<MagicCard> magicCards) {
        ArrayList<Card> collectionCards = ServerDataAnalyser.getInstance().convertIDsToCard(user.getUserCardCollectionInInteger());
        separateMonsterAndMagics(monsterCards, magicCards, collectionCards);
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
            new User(username, nickname, password, imageAddress);
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
        ChatCommandType type = chatBoxCommand.getChatCommandType();
        String sentMessage;
        User sender;
        switch (type) {
            case INITIAL_DATA_REQUEST:
            case UPDATE: {
                chatBoxCommand.setAllMessages(MessageDatabase.getInstance().getAllMessages());
                chatBoxCommand.setPinnedMessageID(MessageDatabase.getInstance().getPinnedMessageID());
                break;
            }
            case NEW_MESSAGE: {
                sentMessage = chatBoxCommand.getSentMessage();
                sender = chatBoxCommand.getSender();

                boolean isInReplyToAnother = chatBoxCommand.isInReplyToAnother();
                String IDInReplyTo = null;
                if (isInReplyToAnother) IDInReplyTo = chatBoxCommand.getIDInReplyTo();

                new Message(sentMessage, sender, isInReplyToAnother, IDInReplyTo);
                chatBoxCommand.setAllMessages(MessageDatabase.getInstance().getAllMessages());
                break;
            }
            case OMIT_MESSAGE: {
                String ID = chatBoxCommand.getMessageID();
                MessageDatabase.getInstance().removeFromAllMessages(ID);
                break;
            }
            case EDIT_MESSAGE: {
                String ID = chatBoxCommand.getMessageID();
                Message message = MessageDatabase.getInstance().getFromAllMessages(ID);
                message.setMessageString(chatBoxCommand.getSentMessage());
            }
            case PIN_MESSAGE: {
                String ID = chatBoxCommand.getMessageID();
                Message toPin = MessageDatabase.getInstance().getFromAllMessages(ID);
                MessageDatabase.getInstance().setPinnedMessageID(ID);
            }
        }

        chatBoxCommand.setNumberOfLoggedIns(ClientInfo.getLoginClientHashMap().size());

        netOut.format("%s\n", Command.makeJson(chatBoxCommand));
        netOut.flush();
    }

}
