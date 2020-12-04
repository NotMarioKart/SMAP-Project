package com.fub.fifaultimatebravery.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.fub.fifaultimatebravery.Repositories.Repository;

public class MenuActivityViewModel extends AndroidViewModel {
    Repository repository;

    public MenuActivityViewModel(@NonNull Application application) {
        super(application);
        repository  = Repository.getRepository(application.getApplicationContext());
    }

    public void addWager(String customWager, String userID)
    {
        repository.addWager(customWager, userID);
    }

    public void resetTeams() {
        repository.resetState();
    }
}
