package com.fub.fifaultimatebravery.DataClasses;

public class Matches {
    String userID;
    String myGoals;
    String opponentGoals;
    Boolean resultIsWin;

    public Matches () {}

    public Matches(String userID, String myGoals, String opponentGoals, Boolean result) {
        this.userID = userID;
        this.myGoals = myGoals;
        this.opponentGoals = opponentGoals;
        this.resultIsWin = result;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getMyGoals() {
        return myGoals;
    }

    public void setMyGoals(String myGoals) {
        this.myGoals = myGoals;
    }

    public String getOpponentGoals() {
        return opponentGoals;
    }

    public void setOpponentGoals(String opponentGoals) {
        this.opponentGoals = opponentGoals;
    }

    public Boolean getResultIsWin() {
        return resultIsWin;
    }

    public void setResult(Boolean resultIsWin) {
        this.resultIsWin = resultIsWin;
    }
}
