package com.fub.fifaultimatebravery.DataClasses;

public class Matches {
    String userID;
    String myGoals;
    String myTeamLogoUrl;
    String opponentGoals;
    String opponentLogoUrl;
    Boolean resultIsWin;
    Long timestamp;

    public Matches () {}

    public Matches(String userID, String myGoals, String myTeamLogoUrl, String opponentGoals, String opponentLogoUrl, Boolean result, long timestamp) {
        this.userID = userID;
        this.myGoals = myGoals;
        this.opponentGoals = opponentGoals;
        this.resultIsWin = result;
        this.myTeamLogoUrl = myTeamLogoUrl;
        this.opponentLogoUrl = opponentLogoUrl;
        this.timestamp = timestamp;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
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

    public String getMyTeamLogoUrl() {
        return myTeamLogoUrl;
    }

    public void setMyTeamLogoUrl(String myTeamLogoUrl) {
        this.myTeamLogoUrl = myTeamLogoUrl;
    }

    public String getOpponentGoals() {
        return opponentGoals;
    }

    public void setOpponentGoals(String opponentGoals) {
        this.opponentGoals = opponentGoals;
    }

    public String getOpponentLogoUrl() {
        return opponentLogoUrl;
    }

    public void setOpponentLogoUrl(String opponentLogoUrl) {
        this.opponentLogoUrl = opponentLogoUrl;
    }

    public Boolean getResultIsWin() {
        return resultIsWin;
    }

    public void setResult(Boolean resultIsWin) {
        this.resultIsWin = resultIsWin;
    }
}
