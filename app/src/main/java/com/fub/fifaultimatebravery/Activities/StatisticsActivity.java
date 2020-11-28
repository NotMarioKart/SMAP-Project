package com.fub.fifaultimatebravery.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fub.fifaultimatebravery.Activities.MenuActivity;
import com.fub.fifaultimatebravery.R;
import com.fub.fifaultimatebravery.ViewModels.StatisticsActivityViewModel;

public class StatisticsActivity extends AppCompatActivity {
    // Variables
    private StatisticsActivityViewModel viewModel;
    TextView TxtWins, TxtGamesPlayed, TxtWinPercentage;
    Button bntCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        // Resources
        TxtWins = findViewById(R.id.wins);
        TxtGamesPlayed = findViewById(R.id.gamesPlayed);
        TxtWinPercentage = findViewById(R.id.winPercentage);
        bntCancel = findViewById(R.id.cancelBnt5);

        bntCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CancelClicked();
            }
        });
    }

    private void CancelClicked() {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        finish();
    }
}