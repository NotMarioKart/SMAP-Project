package com.fub.fifaultimatebravery.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fub.fifaultimatebravery.DataClasses.Matches;
import com.fub.fifaultimatebravery.DataClasses.Team;
import com.fub.fifaultimatebravery.DataClasses.Wagers;
import com.fub.fifaultimatebravery.Repositories.Repository;

public class ResultsActivityViewModel extends AndroidViewModel {

    Repository repository;

    LiveData<Matches> match;

    public LiveData<Matches> getMatch(){
        return match;
    }

    public ResultsActivityViewModel(@NonNull Application application) {
        super(application);
        repository  = Repository.getRepository(application.getApplicationContext());
        match = repository.getMatch();
    }

    public LiveData<Team> getMyTeam(){
        return repository.getMyTeam();
    }

    public LiveData<Wagers> getWager(){
        return repository.getWager();
    }

    public LiveData<Team> getOpponentTeam(){
        return repository.getOpponentsTeam();
    }

    public void saveMatch(String userID, String myGoalsResult, String opponentGoalsResult, boolean resultIsWin){
        repository.saveMatch(userID, myGoalsResult, opponentGoalsResult, resultIsWin);
    }
}
