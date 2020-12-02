package com.fub.fifaultimatebravery.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fub.fifaultimatebravery.DataClasses.Team;
import com.fub.fifaultimatebravery.Repositories.Repository;

public class MatchActivityViewModel extends AndroidViewModel {

    Repository repository;

    LiveData<Team> myTeam;
    LiveData<Team> opponenetsTeam;
    public LiveData<Team> getMyTeam() {
        return myTeam;
    }
    public LiveData<Team> getOpponenetsTeam() {
        return opponenetsTeam;
    }

    public MatchActivityViewModel(@NonNull Application application) {
        super(application);
        repository  = Repository.getRepository(application.getApplicationContext());
        myTeam = repository.getMyTeam();
        opponenetsTeam = repository.getOpponentsTeam();
    }

    public void generateNewTeam(boolean setMyTeam) {
        repository.generateNewTeam(setMyTeam);
    }

    public void generateTwoNewTeam() {
        repository.generateNewTeam(true);
        repository.generateNewTeam(false);
    }
}
