package com.fub.fifaultimatebravery.ScrapingClasses.ClubsResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("league")
    @Expose
    private Integer league;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLeague() {
        return league;
    }

    public void setLeague(Integer league) {
        this.league = league;
    }

}
