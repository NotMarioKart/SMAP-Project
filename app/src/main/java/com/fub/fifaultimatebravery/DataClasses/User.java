package com.fub.fifaultimatebravery;

public class User {

    String customWagers;
    int matchesPlayed;
    int wins;
    Double winPercentage;

    public User(String customWagers, int matchesPlayed, int wins, Double winPercentage) {
        this.customWagers = customWagers;
        this.matchesPlayed = matchesPlayed;
        this.wins = wins;
        this.winPercentage = winPercentage;
    }

    public String getCustomWagers() {
        return customWagers;
    }

    public void setCustomWagers(String customWagers) {
        this.customWagers = customWagers;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public Double getWinPercentage() {
        return winPercentage;
    }

    public void setWinPercentage(Double winPercentage) {
        this.winPercentage = winPercentage;
    }
}
