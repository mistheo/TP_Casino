package org.example;

public class Stats {
    private int coins;
    private int gamesPlayed;
    private int gamesWon;
    private int coinsSpent;

    // Constructeur
    public Stats() {
    }
    public int getCoins() {
        return coins;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getCoinsSpent() {
        return coinsSpent;
    }

    // Méthodes de modification (setters)
    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public void setCoinsSpent(int coinsSpent) {
        this.coinsSpent = coinsSpent;
    }
}

