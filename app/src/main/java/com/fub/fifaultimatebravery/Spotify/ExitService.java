package com.fub.fifaultimatebravery.Spotify;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class ExitService extends Service {

    SpotifyController spotifyController = SpotifyController.getSpotifyController();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        System.out.println("onTaskRemoved called!");
        spotifyController.turnOffMusic();
        spotifyController.disconnectSpotify();
        this.stopSelf();
    }
}
