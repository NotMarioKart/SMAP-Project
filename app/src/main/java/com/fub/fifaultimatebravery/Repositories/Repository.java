package com.fub.fifaultimatebravery.Repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.fub.fifaultimatebravery.DataClasses.Team;
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
    public MutableLiveData<Team> getMyTeam() {
        return myTeam;
    }
    public MutableLiveData<Team> getOpponentsTeam() {
        return opponentsTeam;
    }
    ArrayList<String> myLeagues;
    ArrayList<String> opponentsLeagues;

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

        //Volley setup
        sportsDBClient = new SportsDBClient(context,this);

        //Repository setup
        myTeam = new MutableLiveData<>();
        opponentsTeam = new MutableLiveData<>();
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

    public ArrayList<String> getAllLeagues(){
        return firestoreClient.getLeagues();
    }

}
