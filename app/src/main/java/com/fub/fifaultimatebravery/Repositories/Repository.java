package com.fub.fifaultimatebravery.Repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fub.fifaultimatebravery.DataClasses.Matches;
import com.fub.fifaultimatebravery.DataClasses.Team;
import com.fub.fifaultimatebravery.DataClasses.Wagers;
import com.fub.fifaultimatebravery.FirebaseClients.FirestoreClient;
import com.fub.fifaultimatebravery.Volley.SportsDBClient;
import com.google.android.material.internal.ManufacturerUtils;

public class Repository implements SportsDBClient.ISportsDbClientCallback {
    private static Repository instance;

    //Firebase
    FirestoreClient firestoreClient;

    //Volley
    SportsDBClient sportsDBClient;
    @Override
    public void updatedTeamInfo(Team team, boolean updateMyTeam) {
        if(team == null){
            firestoreClient.getRandomTeam(updateMyTeam);
            return;
        }
        if(updateMyTeam){
            myTeam.postValue(team);
        }
        else{
            opponentsTeam.postValue(team);
        }
    }

    MutableLiveData<Team> myTeam;
    MutableLiveData<Team> opponentsTeam;
    MutableLiveData<Matches> match;
    MutableLiveData<Wagers> wager;
    LiveData<Integer> games;
    LiveData<Integer> wins;

    public LiveData<Integer> getGames() {
        return games;
    }

    public LiveData<Integer> getWins() {
        return wins;
    }

    public MutableLiveData<Team> getMyTeam() {
        return myTeam;
    }
    public MutableLiveData<Team> getOpponentsTeam() {
        return opponentsTeam;
    }
    public MutableLiveData<Matches> getMatch() {
        return match;
    }
    public MutableLiveData<Wagers> getWager(){
        return wager;
    }

    //Making the repo a singleton
    public static Repository getRepository(final Context context){
        if (instance == null) {
            synchronized (Repository.class) {
                if (instance == null) {
                    instance = new Repository(context);
                }
            }
        }
        return instance;
    }

    private Repository(Context context){
        //Firebase setup
        firestoreClient = new FirestoreClient();
        firestoreClient.getMyTeam().observeForever(team -> updateTeamWithTeamInfo(team,true));
        firestoreClient.getOpponentsTeam().observeForever(team -> updateTeamWithTeamInfo(team,false));
        games = firestoreClient.getGames();
        wins = firestoreClient.getWins();


        //Volley setup
        sportsDBClient = new SportsDBClient(context,this);

        //Repository setup
        myTeam = new MutableLiveData<>();
        opponentsTeam = new MutableLiveData<>();
        match = new MutableLiveData<>();
    }

    private void updateTeamWithTeamInfo(Team team, boolean updatedTeamIsMyTeam) {
        sportsDBClient.updateTeamInfo(team, updatedTeamIsMyTeam);
    }

    public void generateNewTeam(boolean setMyTeam){
        firestoreClient.getRandomTeam(setMyTeam);
    }

    public void saveMatch(String userID, String myGoalsResult, String opponentGoalsResult, boolean resultItWin){
        firestoreClient.saveMatch(userID, myGoalsResult, opponentGoalsResult, resultItWin);
    }

    public void addWager(String customWager, String userID){
        firestoreClient.addWager(customWager, userID);
    }

    public void updateNumberMatches(){
        firestoreClient.getNumberMatches();
    }

    public void updateMatchesWon(){
        firestoreClient.getMatchesWon();
    }
}
