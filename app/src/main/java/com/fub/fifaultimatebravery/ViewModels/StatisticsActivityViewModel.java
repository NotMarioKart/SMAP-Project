package com.fub.fifaultimatebravery.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fub.fifaultimatebravery.Repositories.Repository;

public class StatisticsActivityViewModel extends AndroidViewModel {
    Repository repository;
    LiveData<Integer> games;
    LiveData<Integer> wins;
    public LiveData<Integer> getGames() {
        return games;
    }
    public LiveData<Integer> getWins() {
        return wins;
    }

    public StatisticsActivityViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getRepository(application.getApplicationContext());
        games = repository.getGames();
        wins = repository.getWins();
    }

    public void updateGames() {
        repository.updateNumberMatches();
    }
    public void updateWins(){
        repository.updateMatchesWon();
    }
}
