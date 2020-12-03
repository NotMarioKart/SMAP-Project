package com.fub.fifaultimatebravery.ScrapingClasses;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fub.fifaultimatebravery.ScrapingClasses.ClubsResponse.FutDbClubsRequest;
import com.fub.fifaultimatebravery.ScrapingClasses.LeaguesResponse.LeagueResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class ScrapingFunctions {

    public static void pullAllTheTeams(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        //HashMap<Integer,String> leagues = new HashMap<>();

        int counter = 1;
        while(true) {
            if(counter == 4){
                counter = 1;
                break;
            }
            final int finalCounter = counter;
            String url = "https://futdb.app/api/leagues?page=" + counter + "&limit=20";
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (!response.equals(null)) {
                        Gson gson = new Gson();
                        LeagueResponse responseData = gson.fromJson(response, LeagueResponse.class);
                        responseData.getItems().forEach(league -> {
                            addLeagueToDb(league.getName());
                            //leagues.put(league.getId(), league.getName());
                        });
                        Log.e("Your Array Response", response);
                    } else {
                        Log.e("Your Array Response", "Data Null");
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("error is ", "" + error);
                }
            }) {
                //This is for Headers If You Needed
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json; charset=UTF-8");
                    params.put("X-AUTH-TOKEN", "97462f6a-a53c-4db6-8960-ecceba257d95");
                    return params;
                }

            };

            queue.add(request);
            counter++;
        }

       /* while(leagues.size() != 48){
            Log.d("leagues recieved",String.valueOf(leagues.size()));
        }

        while(true) {
            if(counter == 41){
                break;
            }
            final int finalCounter = counter;
            String url = "https://futdb.app/api/clubs?page="+ counter +"&limit=20";
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (!response.equals(null)) {
                        Gson gson = new Gson();
                        FutDbClubsRequest responseData = gson.fromJson(response, FutDbClubsRequest.class);
                        responseData.getItems().forEach(item -> addTeamToDb(leagues.get(item.getLeague()), item.getName()));
                        Log.e("Your Array Response", response);
                    } else {
                        Log.e("Your Array Response", "Data Null");
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("error is ", "" + error);
                }
            }) {

                //This is for Headers If You Needed
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json; charset=UTF-8");
                    params.put("X-AUTH-TOKEN", "97462f6a-a53c-4db6-8960-ecceba257d95");
                    return params;
                }
            };

            queue.add(request);
            counter++;
        }*/
    }

    private static void addTeamToDb(String league, String name) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> team = new HashMap<>();
        team.put("League", league);
        team.put("Name", name);
        team.put("Rating", 0);
        team.put("Reviews",0);

        // Add a new document with a generated ID
        db.collection("Teams")
                .add(team)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Adding team to db", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Adding team to db", "Error adding document", e);
                    }
                });
    }

    private static void addLeagueToDb( String name) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> league = new HashMap<>();
        league.put("Name", name);

        // Add a new document with a generated ID
        db.collection("Leagues")
                .add(league)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Adding team to db", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Adding team to db", "Error adding document", e);
                    }
                });
    }
}
