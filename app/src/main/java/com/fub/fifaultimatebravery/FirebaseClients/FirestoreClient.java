package com.fub.fifaultimatebravery.FirebaseClients;

import android.util.Log;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fub.fifaultimatebravery.Activities.MenuActivity;
import com.fub.fifaultimatebravery.Activities.ResultsActivity;
import com.fub.fifaultimatebravery.Activities.StatisticsActivity;
import com.fub.fifaultimatebravery.DataClasses.Matches;
import com.fub.fifaultimatebravery.DataClasses.Team;
import com.fub.fifaultimatebravery.DataClasses.Wagers;
import com.fub.fifaultimatebravery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirestoreClient {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static String TAG = "FirebaseClient";

    //Constants
    static final String teamsCollectionName = "Teams";
    static final String leaguesCollectionName = "Leagues";
    static final String matchesCollectionName = "Matches";
    static final String wagersCollectionsName = "Wages";

    //Livedata
    MutableLiveData<Team> myTeam;
    MutableLiveData<Team> opponentsTeam;
    MutableLiveData<Matches> match;
    MutableLiveData<Wagers> wager;
    MutableLiveData<Integer> games;
    MutableLiveData<Integer> wins;

    public MutableLiveData<Integer> getGames() {
        return games;
    }
    public MutableLiveData<Integer> getWins() {
        return wins;
    }
    public MutableLiveData<Team> getMyTeam() {
        return myTeam;
    }
    public MutableLiveData<Team> getOpponentsTeam() {
        return opponentsTeam;
    }
    public MutableLiveData<Matches> getMatches(){
        return match;
    }
    public MutableLiveData<Wagers> getWager() {
        return wager;
    }


    ArrayList<String> leagues;

    public FirestoreClient(){
        myTeam = new MutableLiveData<>();
        opponentsTeam = new MutableLiveData<>();
        wins = new MutableLiveData<>();
        games = new MutableLiveData<>();
        leagues = new ArrayList<>();
        new Thread(() -> {
            getAllLeagues();
        }).start();
    }

    public ArrayList<String> getLeagues() {
        return leagues;
    }

    //https://stackoverflow.com/questions/46798981/firestore-how-to-get-random-documents-in-a-collection
    public void getRandomTeam(final boolean setMyTeam, ArrayList<String> leagues) {
        new Thread(() -> {
            if(leagues == null){ //Sometimes leagues are unexplanably a null value;
                getRandomTeam(setMyTeam, new ArrayList<String>());
                return;
            }
            CollectionReference teamsCollection = db.collection(teamsCollectionName);
            String randomKey = teamsCollection.document().getId();
            if(leagues.isEmpty()){
                teamsCollection.whereGreaterThan(FieldPath.documentId(), randomKey).limit(1)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    QuerySnapshot result = task.getResult();
                                    List<DocumentSnapshot> documents = result.getDocuments();
                                    if(documents.size() == 0){
                                        getRandomTeam(setMyTeam, leagues); //Run the function again, an error happened
                                        return;
                                    }
                                    DocumentSnapshot documentSnapshot = documents.get(0);
                                    Team randomTeam = documentSnapshot.toObject(Team.class);
                                    if (setMyTeam) {
                                        myTeam.postValue(randomTeam);
                                    }
                                    else {
                                        opponentsTeam.setValue(randomTeam);
                                    }
                                }
                            }
                        });
            }
            else {
                teamsCollection.whereGreaterThan(FieldPath.documentId(), randomKey).limit(1)
                        .whereIn("League",leagues)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    QuerySnapshot result = task.getResult();
                                    List<DocumentSnapshot> documents = result.getDocuments();
                                    if(documents.size() == 0){
                                        getRandomTeam(setMyTeam, leagues); //Run the function again, an error happened
                                        return;
                                    }
                                    DocumentSnapshot documentSnapshot = documents.get(0);
                                    Team randomTeam = documentSnapshot.toObject(Team.class);
                                    String test = randomTeam.Name;
                                    if (setMyTeam) {
                                        myTeam.postValue(randomTeam);
                                    } else {
                                        opponentsTeam.setValue(randomTeam);
                                    }
                                }
                            }
                        });
            }
        }).start();
    }

    private void getAllLeagues(){
        db.collection(leaguesCollectionName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                leagues.add(document.get("Name").toString());
                            }
                            //https://stackoverflow.com/questions/5815423/sorting-arraylist-in-alphabetical-order-case-insensitive
                            leagues.sort(String::compareToIgnoreCase);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    public void addWager(String customWager, String userID){
        CollectionReference dbMatches = db.collection(wagersCollectionsName);
        Wagers wagers = new Wagers(
                customWager,
                userID
        );

        dbMatches.add(wagers).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    public void saveMatch(String userID, String myGoalsResult, String opponentGoalsResult, boolean resultIsWin)
    {
        CollectionReference matchesCollection = db.collection(matchesCollectionName);
        Matches matches = new Matches(
                userID,
                myGoalsResult,
                opponentGoalsResult,
                resultIsWin
        );

        matchesCollection.add(matches)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    public void getNumberMatches() {
        db.collection(matchesCollectionName)
                .whereEqualTo("userID", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int counter = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                counter++;
                            }
                            games.postValue(counter);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void getMatchesWon(){
        db.collection(matchesCollectionName)
                .whereEqualTo("userID", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .whereEqualTo("resultIsWin", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int counter = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                               counter++;
                            }
                            wins.postValue(counter);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}