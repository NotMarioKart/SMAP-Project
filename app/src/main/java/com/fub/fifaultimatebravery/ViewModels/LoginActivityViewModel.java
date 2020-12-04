package com.fub.fifaultimatebravery.ViewModels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.fub.fifaultimatebravery.Repositories.Repository;
import com.fub.fifaultimatebravery.Spotify.SpotifyController;

public class LoginActivityViewModel extends AndroidViewModel {
    SpotifyController spotifyController;
    Repository repository;
    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getRepository(application.getApplicationContext());
        spotifyController = SpotifyController.getSpotifyController();
    }

    public void startSpotify(Context context) {
        spotifyController.setupAndPlay(context);
    }

    public void addWager(String customWager, String userID)
    {
        repository.addWager(customWager, userID);
    }
}
