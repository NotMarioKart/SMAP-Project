package com.fub.fifaultimatebravery.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fub.fifaultimatebravery.DataClasses.Wagers;
import com.fub.fifaultimatebravery.Repositories.Repository;

public class MenuActivityViewModel extends AndroidViewModel {
    Repository repository;

    LiveData<Wagers> wager;

    public MenuActivityViewModel(@NonNull Application application) {
        super(application);
        repository  = Repository.getRepository(application.getApplicationContext());
        wager = repository.getWager();
    }

    public void addWager(String customWager, String userID)
    {
        repository.addWager(customWager, userID);
    }
}
