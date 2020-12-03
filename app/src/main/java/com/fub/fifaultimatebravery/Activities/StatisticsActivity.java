package com.fub.fifaultimatebravery.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.fub.fifaultimatebravery.DataClasses.Matches;
import com.fub.fifaultimatebravery.R;
import com.fub.fifaultimatebravery.ViewModels.StatisticsActivityViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class StatisticsActivity extends AppCompatActivity {
    // Variables
    private StatisticsActivityViewModel viewModel;
    TextView TxtWins, TxtGamesPlayed, TxtWinPercentage;
    Button bntCancel;

    Double winPercentage;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    private MatchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        viewModel = new ViewModelProvider(this).get(StatisticsActivityViewModel.class);
        firebaseFirestore = FirebaseFirestore.getInstance();

        // Resources
        TxtWins = findViewById(R.id.wins);
        TxtGamesPlayed = findViewById(R.id.gamesPlayed);
        TxtWinPercentage = findViewById(R.id.winPercentage);
        bntCancel = findViewById(R.id.cancelBnt5);
        setUpRecyclerView();

        viewModel.updateGames();
        viewModel.updateWins();

        viewModel.getGames().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer games) {
                TxtGamesPlayed.setText(String.valueOf(games));
                updateWinPercentage();
            }
        });

        viewModel.getWins().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer wins) {
                TxtWins.setText(String.valueOf(wins));
                updateWinPercentage();
            }
        });


        bntCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CancelClicked();
            }
        });
    }

    private void updateWinPercentage(){
        if(viewModel.getWins().getValue() != null && viewModel.getGames().getValue() != null) {
            int wins = viewModel.getWins().getValue();
            int games = viewModel.getGames().getValue();

            if (games != 0) {
                winPercentage = wins / games * 100.0;
            } else {
                winPercentage = 0.0;
            }
            TxtWinPercentage.setText(String.valueOf(winPercentage));
        }
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