package com.fub.fifaultimatebravery.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.fub.fifaultimatebravery.DataClasses.Team;
import com.fub.fifaultimatebravery.R;
import com.fub.fifaultimatebravery.ViewModels.ResultsActivityViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ResultsActivity extends AppCompatActivity {
    // Variables
    EditText EdtTxtMyGoals, EdtTxtOpponentGoals;
    TextView txtMyClub, txtOpponentClub, txtMyLeague, txtOpponentLeague, txtWager, txtOpponentCountry, txtMyCountry;
    ImageView myTeamLogo, opponentLogo;
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
        txtMyCountry = findViewById(R.id.activity_result_myTeamCountry_Txt);
        txtOpponentClub = findViewById(R.id.opponentClubTV4);
        txtOpponentLeague = findViewById(R.id.activtiy_result_opponentLeague_txt);
        txtOpponentCountry = findViewById(R.id.activity_result_opponentCountry_txt);
        txtWager = findViewById(R.id.wagerTV4);
        CBMyRQ = findViewById(R.id.myRQ);
        CBOpponentRQ = findViewById(R.id.opponentRQ);
        bntSubmit = findViewById(R.id.submitBtn);
        bntCancel = findViewById(R.id.cancelBtn4);
        myTeamLogo = findViewById(R.id.myLogo4);
        opponentLogo = findViewById(R.id.opponentLogo4);

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

        loadTeamInfo();
        loadWager();
    }

    private void loadWager() {
        txtWager.setText(Objects.requireNonNull(viewModel.getWager().getValue()).getCustomWager());
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
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void CancelClicked() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void loadTeamInfo() {
        updateOpponenentsTeam(Objects.requireNonNull(viewModel.getOpponentTeam().getValue()));
        updateMyTeam(Objects.requireNonNull(viewModel.getMyTeam().getValue()));
    }

    private void updateOpponenentsTeam(Team team) {
        txtOpponentClub.setText(team.Name);
        String[] leagueSplitted = team.League.split(" ");
        txtOpponentCountry.setText(leagueSplitted[0]);
        String leagueName = "";
        for(int i  = 1; i<leagueSplitted.length;i++){
            if(!(leagueSplitted[i].charAt(0) == '(')) {
                if (i == leagueSplitted.length - 2){
                    leagueName += leagueSplitted[i];
                }
                else {
                    leagueName += leagueSplitted[i] + " ";
                }
            }
        }
        txtOpponentLeague.setText(leagueName);
        Glide.with(this)
                .load(team.LogoUrl)
                .override(200, 200)
                .into(opponentLogo);
    }

    private void updateMyTeam(Team team) {
        txtMyClub.setText(team.Name);
        String[] leagueSplitted = team.League.split(" ");
        txtMyCountry.setText(leagueSplitted[0]);
        String leagueName = "";
        for(int i  = 1; i<leagueSplitted.length;i++){
            if(!(leagueSplitted[i].charAt(0) == '(')) {
                if (i == leagueSplitted.length - 2){
                    leagueName += leagueSplitted[i];
                }
                else {
                    leagueName += leagueSplitted[i] + " ";
                }
            }
        }
        txtMyLeague.setText(leagueName);
        Glide.with(this)
                .load(team.LogoUrl)
                .override(200, 200)
                .into(myTeamLogo);
    }

}