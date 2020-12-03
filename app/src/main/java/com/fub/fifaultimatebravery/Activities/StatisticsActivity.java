package com.fub.fifaultimatebravery.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.fub.fifaultimatebravery.Activities.MenuActivity;
import com.fub.fifaultimatebravery.DataClasses.Matches;
import com.fub.fifaultimatebravery.R;
import com.fub.fifaultimatebravery.ViewModels.StatisticsActivityViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class StatisticsActivity extends AppCompatActivity {
    // Variables
    private StatisticsActivityViewModel viewModel;
    TextView TxtWins, TxtGamesPlayed, TxtWinPercentage;
    Button bntCancel;

    int games;
    int wins;
    Double winPercentage;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    private MatchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        firebaseFirestore = FirebaseFirestore.getInstance();

        // Resources
        TxtWins = findViewById(R.id.wins);
        TxtGamesPlayed = findViewById(R.id.gamesPlayed);
        TxtWinPercentage = findViewById(R.id.winPercentage);
        bntCancel = findViewById(R.id.cancelBnt5);
        setUpRecyclerView();

        firebaseFirestore.collection("Matches").whereEqualTo("userID", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                games++;
                                TxtGamesPlayed.setText(String.valueOf((games)));
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

        firebaseFirestore.collection("Matches").whereEqualTo("userID", FirebaseAuth.getInstance().getCurrentUser().getUid()).whereEqualTo("resultIsWin", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                wins++;
                                TxtWins.setText(String.valueOf((wins)));
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

        if(games != 0){
            winPercentage = wins/games*100.0;
        }else{
            winPercentage = 0.0;
        }

        TxtWinPercentage.setText(String.valueOf(winPercentage));







        bntCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CancelClicked();
            }
        });
    }

    private void setUpRecyclerView() {
        Query query = firebaseFirestore.collection("Matches").whereEqualTo("userID", FirebaseAuth.getInstance().getCurrentUser().getUid());
        FirestoreRecyclerOptions<Matches> options = new FirestoreRecyclerOptions.Builder<Matches>()
                .setQuery(query, Matches.class)
                .build();
        adapter = new MatchAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.StatsList);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void CancelClicked() {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        finish();
    }
}