package com.fub.fifaultimatebravery.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.fub.fifaultimatebravery.DataClasses.Matches;
import com.fub.fifaultimatebravery.R;
import com.fub.fifaultimatebravery.ViewModels.ResultsActivityViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ResultsActivity extends AppCompatActivity {
    // Variables
    EditText EdtTxtMyGoals, EdtTxtOpponentGoals;
    TextView txtMyClub, txtOpponentClub, txtMyLeague, txtOpponentLeague, txtWager;
    Button bntSubmit, bntCancel;
    CheckBox CBMyRQ, CBOpponentRQ;

    private FirebaseFirestore db;
    private ResultsActivityViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        viewModel = new ViewModelProvider(this).get(ResultsActivityViewModel.class);

        db = FirebaseFirestore.getInstance();
        // Resources
        EdtTxtMyGoals = findViewById(R.id.myGoalsET);
        EdtTxtOpponentGoals = findViewById(R.id.opponentGoalsET);
        txtMyClub = findViewById(R.id.myClubTV4);
        txtOpponentClub = findViewById(R.id.opponentClubTV4);
        txtMyLeague = findViewById(R.id.myLeagueTV4);
        txtOpponentClub = findViewById(R.id.opponentClubTV4);
        txtOpponentLeague = findViewById(R.id.opponentCountryTV4);
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


        viewModel.getMyTeam();
        viewModel.getOpponentTeam();




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
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String myGoalsResult = EdtTxtMyGoals.getText().toString();
        String opponentGoalsResult = EdtTxtOpponentGoals.getText().toString();
        Boolean resultIsWin;
        if (myGoalsResult.compareTo(opponentGoalsResult) > 0){
            resultIsWin = true;
        } else{
            resultIsWin = false;
        }
        viewModel.saveMatch(userID, myGoalsResult, opponentGoalsResult, resultIsWin);
        finish();
    }

    private void CancelClicked() {
        Intent i = new Intent(this, MatchActivity.class);
        startActivity(i);
        finish();
    }
}