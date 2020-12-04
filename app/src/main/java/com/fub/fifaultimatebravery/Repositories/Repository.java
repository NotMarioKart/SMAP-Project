package com.fub.fifaultimatebravery.Repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fub.fifaultimatebravery.DataClasses.Matches;
import com.fub.fifaultimatebravery.DataClasses.Team;
import com.fub.fifaultimatebravery.DataClasses.Wagers;
import com.fub.fifaultimatebravery.FirebaseClients.FirestoreClient;
import com.fub.fifaultimatebravery.Volley.SportsDBClient;

import java.util.ArrayList;

public class Repository implements SportsDBClient.ISportsDbClientCallback {
    private static Repository instance;

    //Firebase
    FirestoreClient firestoreClient;

    //Volley
    SportsDBClient sportsDBClient;
    @Override
    public void updatedTeamInfo(Team team, boolean updateMyTeam) {
        if(team == null){
            if(updateMyTeam){
                firestoreClient.getRandomTeam(updateMyTeam,myLeagues);
            }
            else {
                firestoreClient.getRandomTeam(updateMyTeam,opponentsLeagues);
            }
            return;
        }
        if(updateMyTeam){
            myTeam.setValue(team);
        }
        else{
            opponentsTeam.setValue(team);
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
    ArrayList<String> myLeagues;
    ArrayList<String> opponentsLeagues;
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
        wager = firestoreClient.getWager();

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

    public void generateNewTeam(boolean setMyTeam, ArrayList<String> leagues){
        firestoreClient.getRandomTeam(setMyTeam, leagues);
        if(setMyTeam){
            myLeagues = leagues;
        }
        else {
            opponentsLeagues = leagues;
        }

    }

    public void generateRandomWager(){
        firestoreClient.generateRandomWager();
    }

    public ArrayList<String> getAllLeagues(){
        return firestoreClient.getLeagues();
    }
    public void saveMatch(String userID, String myGoalsResult, String opponentGoalsResult, boolean resultItWin){
        firestoreClient.saveMatch(userID, myGoalsResult,myTeam.getValue().LogoUrl, opponentGoalsResult, opponentsTeam.getValue().LogoUrl, resultItWin);
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

    public void resetState() {
        Team team = new Team();
        team.Name = "-";
        myTeam.postValue(team);
        opponentsTeam.postValue(team);
        wager.postValue(new Wagers("-",""));
    }
}
