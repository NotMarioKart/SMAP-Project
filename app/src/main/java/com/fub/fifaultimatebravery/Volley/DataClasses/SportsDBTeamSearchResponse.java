package com.fub.fifaultimatebravery.Volley.DataClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SportsDBTeamSearchResponse {
    @SerializedName("teams")
    @Expose
    private List<com.fub.fifaultimatebravery.Volley.DataClasses.Team> teams = null;

    public List<com.fub.fifaultimatebravery.Volley.DataClasses.Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
