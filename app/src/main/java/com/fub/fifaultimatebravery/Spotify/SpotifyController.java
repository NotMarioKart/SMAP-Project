package com.fub.fifaultimatebravery.Spotify;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

public class SpotifyController {
    private static final String CLIENT_ID = "daea4856a02145b4900ca38523639383";
    private static final String REDIRECT_URI = "spotifymusic://callback";
    private static final String PLAYLIST = "spotify:playlist:2UDdU0EyUmXcTf2OGPpSel";
    private SpotifyAppRemote mSpotifyAppRemote;
    public static SpotifyController instance;

    private SpotifyController() {}   //Default constructor

    public static SpotifyController getSpotifyController() {
        if (instance == null) {
            synchronized (SpotifyController.class) {
                if (instance == null) {
                    instance = new SpotifyController();
                }
            }
        }
        return instance;
    }

    public void setupAndPlay(Context context) {
        Boolean isInstalled;
        isInstalled = SpotifyAppRemote.isSpotifyInstalled(context);

        if(!isInstalled) {
            Toast.makeText(context,"For full functionality, install and log in to Spotify", Toast.LENGTH_LONG).show();
        }
        else {
            if(mSpotifyAppRemote == null) {
                connect(context);
            }
            else if(!mSpotifyAppRemote.isConnected()){
                connect(context);
            }
            else {
                //playMusic();
            }
        }
    }

    private void connect(Context context) {
        ConnectionParams connectionParams = new ConnectionParams.Builder(CLIENT_ID)
                .setRedirectUri(REDIRECT_URI)
                .showAuthView(true)
                .build();

        SpotifyAppRemote.connect(context, connectionParams, new Connector.ConnectionListener() {
            @Override
            public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                mSpotifyAppRemote = spotifyAppRemote;
                Log.d("SpotifyController", "Connected to Spotify");
                //playMusic();
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d("SpotifyController", throwable.getMessage(), throwable);
            }
        });
    }

    public void turnOffMusic() {
        Log.d("SpotifyController", "Pausing music");
        mSpotifyAppRemote.getPlayerApi().pause();
    }

    public void disconnectSpotify() {
        Log.d("SpotifyController", "Disconnecting from Spotify");
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }

    private void playMusic() {
        mSpotifyAppRemote.getPlayerApi().play(PLAYLIST);
    }
}
