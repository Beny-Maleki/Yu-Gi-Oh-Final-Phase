package client.model.userProp;

public abstract class FatherUser {
    protected int score;
    protected Deck activeDeck;
    protected String nickname;
    protected int balance;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void increaseScore(int score) {
        this.score = score;
    }

    public abstract Deck getActiveDeck();

    public int getScore() {
        return score;
    }

    public void changeBalance(int cost) {
        this.balance += cost;
    }

    public int getBalance() {
        return balance;
    }
}
