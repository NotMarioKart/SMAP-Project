package com.fub.fifaultimatebravery.ViewModels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.fub.fifaultimatebravery.Spotify.SpotifyController;

public class LoginActivityViewModel extends AndroidViewModel {
    SpotifyController spotifyController;
    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        spotifyController = SpotifyController.getSpotifyController();
    }

    public void startSpotify(Context context) {
        spotifyController.setupAndPlay(context);
    }
}
