package com.fub.fifaultimatebravery.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.fub.fifaultimatebravery.Repositories.Repository;

public class MatchActivityViewModel extends AndroidViewModel {

    Repository repository;

    public MatchActivityViewModel(@NonNull Application application) {
        super(application);

        repository  = Repository.getRepository(application.getApplicationContext());
    }

}
