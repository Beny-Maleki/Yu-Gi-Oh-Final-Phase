package client.model.userProp;

import client.UserDataBase;
import connector.cards.Card;

import java.util.ArrayList;
import java.util.Arrays;

public class User extends FatherUser {

    private static ArrayList<User> allUsers;

    static {
        allUsers = new ArrayList<>();
    }

    private final ArrayList<String> allUserDecksId;
    private final ArrayList<Integer> cardCollection;
    private final ArrayList<Boolean> unlockedDeckHolders;
    private String username;
    private String password;
    private String avatarAddress;
    private Deck deckOnModify;

    {
        avatarAddress = "src/main/resources/graphicprop/images/avatar1.png";
        deckOnModify = null;
        unlockedDeckHolders = new ArrayList<>(Arrays.asList(true, false, false, false));
        allUserDecksId = new ArrayList<>(Arrays.asList(null, null, null, null));
        cardCollection = new ArrayList<>();
        activeDeck = getActiveDeck();
    }

    {
        balance = 1000000;
        score = 0;
    }

    public User(String username, String nickname, String password, String imageAddress) {
        setUsername(username);
        setNickname(nickname);
        setPassword(password);
        setAvatarAddress(imageAddress);
        allUsers.add(this);
    }

    public static User getUserByUserInfo(String info, UserInfoType userInfoType) {
        switch (userInfoType) {
            case USERNAME: {
                for (User user : allUsers) {
                    if (user.getUsername().equals(info)) {
                        return user;
                    }
                }
                break;
            }
            case NICKNAME: {
                for (User user : allUsers) {
                    if (user.getNickname().equals(info))
                        return user;
                }
                break;
            }
        }
        return null;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static void setAllUsers(ArrayList<User> users) {
        allUsers = users;
    }


    public String getAvatarAddress() {
        if (avatarAddress == null) {
            avatarAddress = "src/main/resources/graphicprop/images/avatar1.png";
        }
        return avatarAddress;
    }

    public void setAvatarAddress(String avatarAddress) {
        this.avatarAddress = avatarAddress;
    }

    public Deck getDeckByName(String name) {

        for (String ID : allUserDecksId) {
            Deck deck = Deck.getDeckById(ID);
            assert deck != null;
            if (deck.getName().equals(name)) {
                return deck;
            }
        }
        return null;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public boolean isPasswordMatch(String password) {
        return password.equals(this.password);
    }

    public ArrayList<Deck> getAllUserDecks() {
        ArrayList<Deck> allUserDecks = new ArrayList<>();
        for (String deckId : allUserDecksId) {
            allUserDecks.add(Deck.getDeckById(deckId));
        }
        return allUserDecks;
    }

    public ArrayList<Integer> getAllUserDecksIdInInteger() {
        return this.cardCollection;
    }

    public ArrayList<Card> getCardCollection() {
        ArrayList<Card> cards = new ArrayList<>();
        for (Integer ID : cardCollection) {
            cards.add(UserDataBase.getInstance().getCardById(ID));
        }
        System.out.println(cards);
        return cards;
    }

    public ArrayList<Integer> getUserCardCollectionInInteger() {
        return cardCollection;
    }

    public void addCard(Card card) {
        cardCollection.add(card.getID());
    }

    public void removeCardFromUserCollection(Card card) {
        cardCollection.remove(card.getID());
    }

    public void addDeckId(String ID, int place) {
        allUserDecksId.set(place, ID);
    }

    public ArrayList<Boolean> getUnlockedDeckHolders() {
        return unlockedDeckHolders;
    }

    public void unlockDeckHolder(int i) {
        unlockedDeckHolders.set(i, true);
    }

    public void removeDeck(int i) {
        allUserDecksId.set(i, null);
    }

    public Deck getDeckOnModify() {
        return deckOnModify;
    }

    public void setDeckOnModify(Deck deckOnModify) {
        this.deckOnModify = deckOnModify;
    }

    @Override
    public Deck getActiveDeck() {
        for (Deck deck : getAllUserDecks()) {
            if (deck != null) {
                if (deck.isDeckActivated())
                    return deck;
            }
        }
        return null;
    }

    public void setActiveDeck(Deck activeDeck) {
        if (this.activeDeck != null) this.activeDeck.setDeckActivated(false);
        this.activeDeck = activeDeck;
        if (activeDeck != null) activeDeck.setDeckActivated(true);
    }
}

