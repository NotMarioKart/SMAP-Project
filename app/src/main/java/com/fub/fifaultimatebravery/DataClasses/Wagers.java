package com.fub.fifaultimatebravery.DataClasses;

public class Wagers {
    String customWager;
    String userID;

    public Wagers() {}

    public Wagers(String customWager, String userID) {
        this.customWager = customWager;
        this.userID = userID;
    }

    public String getCustomWager() {
        return customWager;
    }

    public void setCustomWager(String customWager) {
        this.customWager = customWager;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
