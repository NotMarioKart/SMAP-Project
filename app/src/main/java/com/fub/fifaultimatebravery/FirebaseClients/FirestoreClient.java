package com.fub.fifaultimatebravery.FirebaseClients;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.fub.fifaultimatebravery.DataClasses.Team;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
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

    //Livedata
    MutableLiveData<Team> myTeam;
    MutableLiveData<Team> opponentsTeam;
    public MutableLiveData<Team> getMyTeam() {
        return myTeam;
    }
    public MutableLiveData<Team> getOpponentsTeam() {
        return opponentsTeam;
    }
    ArrayList<String> leagues;

    public FirestoreClient(){
        myTeam = new MutableLiveData<>();
        opponentsTeam = new MutableLiveData<>();
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

}