package server.controller;

import client.model.Message;
import client.model.userProp.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import connector.cards.Card;
import connector.cards.MagicCard;
import connector.cards.MonsterCard;
import connector.commands.Command;
import connector.commands.CommandType;
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
        clientInfo = new ClientInfo(clientSocket, netOut);
    }

    @Override
    public void run() {
        Gson yaGson = new GsonBuilder().create();

        while (true) {
            try {
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
                        putCardForTradeCommand.changeCommandID();
                        handleNewCardOnTrade(putCardForTradeCommand);
                        break;
                    }

                    case GET_CARD_FOR_TRADES: {
                        GetCardsOnTradeCommand getCardsOnTradeCommand =
                                yaGson.fromJson(request, GetCardsOnTradeCommand.class);
                        getCardsOnTradeCommand.changeCommandID();
                        handleGetCardsOnTradeCommand(getCardsOnTradeCommand);
                        break;
                    }

                    case LOGOUT: {
                        LogoutCommand logoutCommand =
                                yaGson.fromJson(request, LogoutCommand.class);
                        logoutCommand.changeCommandID();
                        for (User loggedInUser : ClientInfo.getLoggedInUsers()) {
                            if (loggedInUser.getUsername().equals(ClientInfo.getLoginClientHashMap().get(logoutCommand.getToken()).getUser().getUsername())) {
                                ClientInfo.getLoggedInUsers().remove(ClientInfo.getLoginClientHashMap().get(logoutCommand.getToken()).getUser());
                            }
                        }
                        ClientInfo.removeUserFromLoggedIn(logoutCommand.getToken());

                        ChatBoxCommand response = new ChatBoxCommand(CommandType.CHAT, ChatCommandType.UPDATE_NUM_LOGGED_INS, null);
                        response.setNumberOfLoggedIns(ClientInfo.getLoggedInUsers().size());
                        notifyOtherUsers(response);
                        break;
                    }
                    case EXIT: {
                        ExitCommand exitCommand =
                                yaGson.fromJson(request, ExitCommand.class);
                        exitCommand.changeCommandID();
                        ClientInfo.closeUserSocket(exitCommand.getSocket());
                        break;
                    }

                    case CHAT: {
                        ChatBoxCommand chatBoxCommand = yaGson.fromJson(request, ChatBoxCommand.class);
                        chatBoxCommand.changeCommandID();
                        handleChatBox(chatBoxCommand);
                        break;
                    }

                    case SCORE_BOARD: {
                        ScoreBoardCommand scoreBoardCommand = yaGson.fromJson(request, ScoreBoardCommand.class);
                        scoreBoardCommand.changeCommandID();
                        handleScoreBoard(scoreBoardCommand);
                        break;
                    }
                }
            } catch (NoSuchElementException e) {
                break;
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

                    boolean addUser = true;
                    ArrayList<User> loggedInUsers = ClientInfo.getLoggedInUsers();
                    for (User loggedInUser : loggedInUsers) {
                        if (loggedInUser.getUsername().equals(user.getUsername())) {
                            addUser = false;
                            break;
                        }
                    }

                    if (!addUser) {
                        logInCommand.setException(new AlreadyLoggedIn());
                    } else {
                        loggedInUsers.add(user);
                        logInCommand.setUser(user);
                        String token = UUID.randomUUID().toString();
                        logInCommand.setToken(token);
                        ClientInfo.addUserToLoggedIn(clientInfo, token, user);
                        clientInfo.setToken(token);
                    }
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


        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Set<String> tokens = ClientInfo.getLoginClientHashMap().keySet();
            for (String token : tokens) {
                User u = ClientInfo.getUserByToken(token);
                for (ClientInfo info : ClientInfo.getClientInfos()) {
                    if (!u.equals(user)) {
                        if (info.getUser().equals(u)) {
                            ChatBoxCommand chatBoxCommand = new ChatBoxCommand(CommandType.CHAT, ChatCommandType.UPDATE_NUM_LOGGED_INS, null);
                            chatBoxCommand.setNumberOfLoggedIns(ClientInfo.getLoggedInUsers().size());
                            info.getNetOut().format("%s\n", Command.makeJson(chatBoxCommand));
                            info.getNetOut().flush();
                        }
                    }
                }
            }
        }).start();
    }

    private void handleChatBox(ChatBoxCommand chatBoxCommand) {
        ChatCommandType type = chatBoxCommand.getChatCommandType();
        String sentMessage;
        User sender;
        switch (type) {
            case INITIAL_DATA_REQUEST: {
                chatBoxCommand.setAllMessages(MessageDatabase.getInstance().getAllMessages());
                chatBoxCommand.setNumberOfLoggedIns(ClientInfo.getLoginClientHashMap().size());
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
                chatBoxCommand.setNumberOfLoggedIns(ClientInfo.getLoginClientHashMap().size());
                chatBoxCommand.setPinnedMessageID(MessageDatabase.getInstance().getPinnedMessageID());
                chatBoxCommand.setChatCommandType(ChatCommandType.UPDATE_NEW);

                notifyOtherUsers(chatBoxCommand);
                break;
            }
            case OMIT_MESSAGE: {
                String ID = chatBoxCommand.getMessageID();
                MessageDatabase.getInstance().removeFromAllMessages(ID);
                chatBoxCommand.setChatCommandType(ChatCommandType.UPDATE_OMIT);

                notifyOtherUsers(chatBoxCommand);
                break;
            }
            case EDIT_MESSAGE: {
                String ID = chatBoxCommand.getMessageID();
                Message message = MessageDatabase.getInstance().getFromAllMessages(ID);
                message.setMessageString(chatBoxCommand.getSentMessage());
                chatBoxCommand.setChatCommandType(ChatCommandType.UPDATE_EDIT);

                notifyOtherUsers(chatBoxCommand);
                break;
            }
            case PIN_MESSAGE: {
                String ID = chatBoxCommand.getPinnedMessageID();
                MessageDatabase.getInstance().setPinnedMessageID(ID);
                chatBoxCommand.setChatCommandType(ChatCommandType.UPDATE_PIN);


                notifyOtherUsers(chatBoxCommand);
                break;
            }
        }

        chatBoxCommand.setNumberOfLoggedIns(ClientInfo.getLoggedInUsers().size());
        netOut.format("%s\n", Command.makeJson(chatBoxCommand));
        netOut.flush();
    }

    private void notifyOtherUsers(ChatBoxCommand chatBoxCommand) {
        Set<String> IDs  = ClientInfo.getLoginClientHashMap().keySet();
        for (String id : IDs) {
           if (!this.clientInfo.getToken().equals(id)) {
               chatBoxCommand.setNumberOfLoggedIns(ClientInfo.getLoginClientHashMap().size());
                chatBoxCommand.changeCommandID();

               ClientInfo.getLoginClientHashMap().get(id).getNetOut().format("%s\n", Command.makeJson(chatBoxCommand));
               ClientInfo.getLoginClientHashMap().get(id).getNetOut().flush();
           }
        }
    }

    private void handleScoreBoard(ScoreBoardCommand scoreBoardCommand) {
        ArrayList<User> sortedUsers = User.getAllUsers();
        sortedUsers.sort(Comparator.comparing(User::getScore).thenComparing(User::getNickname).reversed());
        int rank = 1, counter = 1;
        User tempUser = null;
        for (User user : sortedUsers) {
            if (counter == 11) break;
            if (tempUser != null) {
                if (user.getScore() < tempUser.getScore()) {
                    rank = counter;
                }
                scoreBoardCommand.addItemToScoreBoardItems(new ScoreboardItem(user.getUsername(), rank, user.getScore()));
                System.out.println(rank + ": " + user.getUsername());
            } else {
                scoreBoardCommand.addItemToScoreBoardItems(new ScoreboardItem(user.getUsername(), 1, user.getScore()));
                System.out.println("1: " + user.getUsername());
            }
            tempUser = user;
            counter++;
        }

        netOut.format("%s\n", Command.makeJson(scoreBoardCommand));
        netOut.flush();
    }

}
