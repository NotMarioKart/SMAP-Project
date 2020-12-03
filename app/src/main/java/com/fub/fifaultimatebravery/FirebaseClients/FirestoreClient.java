package com.fub.fifaultimatebravery.FirebaseClients;

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

public class FirestoreClient {
FirebaseFirestore db = FirebaseFirestore.getInstance();

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

    public FirestoreClient(){
        myTeam = new MutableLiveData<>();
        opponentsTeam = new MutableLiveData<>();
    }

    //https://stackoverflow.com/questions/46798981/firestore-how-to-get-random-documents-in-a-collection
    public void getRandomTeam(final boolean setMyTeam) {
        CollectionReference teamsCollection = db.collection(teamsCollectionName);
        String randomKey = teamsCollection.document().getId();
        Team returnTeam = null;

        teamsCollection.whereGreaterThanOrEqualTo(FieldPath.documentId(), randomKey).limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
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

}