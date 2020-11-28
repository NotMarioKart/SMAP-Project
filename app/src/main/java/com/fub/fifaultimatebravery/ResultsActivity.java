package com.fub.fifaultimatebravery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {
    // Variables
    private ResultsActivity viewModel;
    EditText EdtTxtMyGoals, EdtTxtOpponentGoals;
    TextView txtMyClub, txtOpponentClub, txtMyLeague, txtOpponentLeague, txtWager;
    Button bntSubmit, bntCancel;
    CheckBox CBMyRQ, CBOpponentRQ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Resources
        EdtTxtMyGoals = findViewById(R.id.myGoalsET);
        EdtTxtOpponentGoals = findViewById(R.id.opponentGoalsET);
        txtMyClub = findViewById(R.id.myClubTV4);
        txtOpponentClub = findViewById(R.id.opponentClubTV4);
        txtMyLeague = findViewById(R.id.myLeagueTV4);
        txtOpponentClub = findViewById(R.id.opponentClubTV4);
        txtOpponentLeague = findViewById(R.id.opponentLeagueTV4);
        txtWager = findViewById(R.id.wagerTV4);
        CBMyRQ = findViewById(R.id.myRQ);
        CBOpponentRQ = findViewById(R.id.opponentRQ);
        bntSubmit = findViewById(R.id.submitBtn);
        bntCancel = findViewById(R.id.cancelBtn4);

        CBOpponentRQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EdtTxtMyGoals.setEnabled(!CBOpponentRQ.isChecked());
                EdtTxtMyGoals.setText("3");
                CBMyRQ.setEnabled(!CBOpponentRQ.isChecked());
                EdtTxtOpponentGoals.setEnabled(!CBOpponentRQ.isChecked());
                EdtTxtOpponentGoals.setText("0");
            }
        });
        CBMyRQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EdtTxtMyGoals.setEnabled(!CBMyRQ.isChecked());
                EdtTxtMyGoals.setText("0");
                CBOpponentRQ.setEnabled(!CBMyRQ.isChecked());
                EdtTxtOpponentGoals.setEnabled(!CBMyRQ.isChecked());
                EdtTxtOpponentGoals.setText("3");
            }
        });




        bntCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CancelClicked();
            }
        });

        bntSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmitClicked();
            }
        });
    }


    private void SubmitClicked() {

    }

    private void CancelClicked() {
        Intent i = new Intent(this, MatchActivity.class);
        startActivity(i);
        finish();
    }
}