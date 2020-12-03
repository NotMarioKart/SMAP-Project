package com.fub.fifaultimatebravery.Volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fub.fifaultimatebravery.DataClasses.Team;
import com.fub.fifaultimatebravery.Volley.DataClasses.SportsDBTeamSearchResponse;
import com.google.gson.Gson;

public class SportsDBClient {
    //Constants:
    static final String SportsDBAPIAddress = "https://www.thesportsdb.com/api/v1/json/1/searchteams.php?t=";
    private static final String TAG = "SportsDBClient";

    //Volley request queue
    static RequestQueue queue;

    //Callback interface for giving back recieved data
    public interface ISportsDbClientCallback {
        void updatedTeamInfo(Team team, boolean myTeam);
    }

    private ISportsDbClientCallback listener;

    public SportsDBClient(Context context, ISportsDbClientCallback listener) {
        queue = Volley.newRequestQueue(context);
        this.listener = listener;
    }

    public void updateTeamInfo(Team teamToUpdate, boolean myTeam) {
        String url = SportsDBAPIAddress + teamToUpdate.Name;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                SportsDBTeamSearchResponse responseData = gson.fromJson(response, SportsDBTeamSearchResponse.class);
                if (responseData.getTeams() == null) {
                    listener.updatedTeamInfo(null,myTeam);
                    return;
                }
                com.fub.fifaultimatebravery.Volley.DataClasses.Team foundTeam = responseData.getTeams().get(0);

                teamToUpdate.LogoUrl = String.valueOf(foundTeam.getStrTeamBadge());
                teamToUpdate.Instagram = foundTeam.getStrInstagram();
                teamToUpdate.StadiumName = foundTeam.getStrStadium();
                teamToUpdate.Facebook  = foundTeam.getStrFacebook();
                listener.updatedTeamInfo(teamToUpdate, myTeam);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error :" + error.toString());
            }
        });
        queue.add(request);
    }

}


