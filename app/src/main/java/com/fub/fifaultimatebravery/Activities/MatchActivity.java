package com.fub.fifaultimatebravery.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fub.fifaultimatebravery.R;
import com.fub.fifaultimatebravery.ViewModels.MatchActivityViewModel;

import java.util.ArrayList;

public class MatchActivity extends AppCompatActivity {
    // Variables
    private MatchActivityViewModel viewModel;
    TextView txtMyClub, txtOpponentClub, txtMyLeague, txtOpponentLeague, txtWager, txtLeagues, txtOpponentLeagues;
    Button bntCancel, bntMatchResults, bntMyRegenerate, bntOpponentRegenerate, bntMySettings, bntOpponentSettings;
    ImageView ImgMyLogo, ImgOpponentLogo;

    String[] Leagues;
    boolean[] MyCheckedLeagues;
    boolean[] OpponentCheckedLeague;
    ArrayList<Integer> OpponentLeagues = new ArrayList<>();
    ArrayList<Integer> MyLeagues = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        Leagues = getResources().getStringArray(R.array.Leagues);
        MyCheckedLeagues = new boolean[Leagues.length];
        OpponentCheckedLeague = new boolean[Leagues.length];

        //Resources
        txtMyClub = findViewById(R.id.myClubTV4);
        txtOpponentClub = findViewById(R.id.opponentClubTV4);
        txtMyLeague = findViewById(R.id.myLeagueTV4);
        txtOpponentClub = findViewById(R.id.opponentClubTV4);
        txtOpponentLeague = findViewById(R.id.opponentLeagueTV4);
        txtWager = findViewById(R.id.wagerTV3);
        txtLeagues = findViewById(R.id.LeaguesTv);
        txtOpponentLeagues = findViewById(R.id.OpponentLeaguesTv);
        bntCancel = findViewById(R.id.cancelBtn3);
        bntMatchResults = findViewById(R.id.enterResultsBtn);
        bntMyRegenerate = findViewById(R.id.myRegenerateBtn);
        bntOpponentRegenerate = findViewById(R.id.opponentRegenerateBtn);
        bntMySettings = findViewById(R.id.mySettingsBtn);
        bntOpponentSettings = findViewById(R.id.opponentSettingsBtn);
        ImgMyLogo = findViewById(R.id.myLogo4);
        ImgOpponentLogo = findViewById(R.id.opponentLogo4);
        
        bntCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CancelClicked();
            }
        });
        
        bntMatchResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MatchResultsClicked();
            }
        });
        
        bntMyRegenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyRegenerateClicked();
            }
        });
        
        bntOpponentRegenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpponentRegenerateClicked();
            }
        });
        
        bntMySettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySettingsClicked();
            }
        });
        
        bntOpponentSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpponentSettingsClicked();
            }
        });
    }

    private void OpponentSettingsClicked() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.settings_dialog, null);
        mBuilder.setTitle("Set your prefered league");
        mBuilder.setMultiChoiceItems(Leagues, OpponentCheckedLeague, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                if(isChecked){
                    if(! OpponentLeagues.contains(i)){
                        OpponentLeagues.add(i);
                    } else{
                        OpponentLeagues.remove(i);
                    }
                }
            }
        });
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                txtOpponentLeagues.setText(null);
                String League = "";
                for (int i = 0; i < OpponentLeagues.size(); i++){
                    League = League + Leagues[OpponentLeagues.get(i)];
                    if (i != OpponentLeagues.size() -1){
                        League = League + ", ";
                    }
                }
                txtOpponentLeagues.setText(getResources().getString(R.string.SpecificLeagues) + League);
            }
        });
        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

    }

    private void MySettingsClicked() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.settings_dialog, null);
        mBuilder.setTitle("Set your prefered leagues");
        mBuilder.setMultiChoiceItems(Leagues, MyCheckedLeagues, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                if(isChecked){
                    if(! MyLeagues.contains(i)){
                        MyLeagues.add(i);
                    } else{
                        MyLeagues.remove(i);
                    }
                }
            }
        });
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                txtLeagues.setText(null);
                String League = "";
                for (int i = 0; i < MyLeagues.size(); i++){
                    League = League + Leagues[MyLeagues.get(i)];
                    if (i != MyLeagues.size() -1){
                        League = League + ", ";
                    }
                }
                txtLeagues.setText(getResources().getString(R.string.SpecificLeagues) + League);
            }
        });
        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

    }

    private void OpponentRegenerateClicked() {
    }

    private void MyRegenerateClicked() {
    }

    private void MatchResultsClicked() {
        Intent i = new Intent(this, ResultsActivity.class);
        startActivity(i);
        finish();
    }

    private void CancelClicked() {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        finish();
    }


}