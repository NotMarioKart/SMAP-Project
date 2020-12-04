package com.fub.fifaultimatebravery.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fub.fifaultimatebravery.DataClasses.Team;
import com.fub.fifaultimatebravery.DataClasses.Wagers;
import com.fub.fifaultimatebravery.Repositories.Repository;

import java.util.ArrayList;

public class MatchActivityViewModel extends AndroidViewModel {

    Repository repository;

    LiveData<Team> myTeam;
    LiveData<Team> opponenetsTeam;
    LiveData<Wagers> wager;
    public LiveData<Team> getMyTeam() {
        return myTeam;
    }
    public LiveData<Team> getOpponenetsTeam() {
        return opponenetsTeam;
    }
    public  LiveData<Wagers> getWager(){return wager;}

    ArrayList<String> opponentLeagues = new ArrayList<>();
    ArrayList<String> myLeagues = new ArrayList<>();
    public ArrayList<String> getMyLeagues() {
        return myLeagues;
    }
    public ArrayList<String> getOpponentLeagues(){
        return opponentLeagues;
    }
    public void setMyLeagues(ArrayList<String> myLeagues) {
        this.myLeagues = myLeagues;
    }
    public void setOpponentLeagues(ArrayList<String> opponentLeagues) { this.opponentLeagues = opponentLeagues;}

    public MatchActivityViewModel(@NonNull Application application) {
        super(application);
        repository  = Repository.getRepository(application.getApplicationContext());
        myTeam = repository.getMyTeam();
        opponenetsTeam = repository.getOpponentsTeam();
        wager = repository.getWager();
        generateTwoNewTeam(); //Called here so teams are not regenerated when app is rotated
        repository.generateRandomWager();
    }

    public void generateNewTeam(boolean setMyTeam) {
        if(setMyTeam){
            repository.generateNewTeam(setMyTeam,myLeagues);
        }
        else{
            repository.generateNewTeam(setMyTeam,opponentLeagues);
        }
    }

    public void generateTwoNewTeam() {
        repository.generateNewTeam(true,myLeagues);
        repository.generateNewTeam(false,opponentLeagues);
    }

    public ArrayList<String> getAllLeagues(){
        return repository.getAllLeagues();
    }
}
