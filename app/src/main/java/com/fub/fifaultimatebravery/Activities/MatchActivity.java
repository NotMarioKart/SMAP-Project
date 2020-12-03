package com.fub.fifaultimatebravery.Activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.fub.fifaultimatebravery.DataClasses.Team;
import com.fub.fifaultimatebravery.Dialogs.MatchSettingsDialog;
import com.fub.fifaultimatebravery.R;
import com.fub.fifaultimatebravery.ViewModels.MatchActivityViewModel;

import java.util.ArrayList;

public class MatchActivity extends AppCompatActivity implements MatchSettingsDialog.IMatchSettingsDialogListener {
    // Variables
    private MatchActivityViewModel viewModel;
    TextView txtMyClub, txtOpponentClub, txtMyLeague, txtMyCountry, txtOpponentCountry, txtOpponentLeague, txtWager, txtLeagues, txtOpponentLeagues, txtMyTeamStadium, txtOpponentStadium;
    Button bntMatchResults, bntMyRegenerate, bntOpponentRegenerate, bntMySettings, bntOpponentSettings;
    ImageView ImgMyLogo, ImgOpponentLogo, ImgMyTeamFacebook, ImgMyTeamInstagram, ImgOpponentFacebook, ImgOpponentInstagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        viewModel = new ViewModelProvider(this).get(MatchActivityViewModel.class);

        //Resources
        txtMyClub = findViewById(R.id.myClubTV4);
        txtOpponentClub = findViewById(R.id.opponentClubTV4);
        txtMyLeague = findViewById(R.id.myLeagueTV4);
        txtMyCountry = findViewById(R.id.myCountry);
        txtOpponentLeague = findViewById(R.id.opponentLeagueTV4);
        txtWager = findViewById(R.id.wagerTV3);
        txtLeagues = findViewById(R.id.LeaguesTv);
        txtOpponentLeagues = findViewById(R.id.OpponentLeaguesTv);
        txtOpponentCountry = findViewById(R.id.opponentCountryTV4);
        bntMatchResults = findViewById(R.id.enterResultsBtn);
        bntMyRegenerate = findViewById(R.id.myRegenerateBtn);
        bntOpponentRegenerate = findViewById(R.id.opponentRegenerateBtn);
        bntMySettings = findViewById(R.id.mySettingsBtn);
        bntOpponentSettings = findViewById(R.id.opponentSettingsBtn);
        ImgMyLogo = findViewById(R.id.myLogo4);
        ImgOpponentLogo = findViewById(R.id.opponentLogo4);
        ImgMyTeamFacebook = findViewById(R.id.activity_match_myTeamFacebook_Img);
        ImgMyTeamInstagram = findViewById(R.id.activity_match_myTeamInstagram_Img);
        ImgOpponentFacebook = findViewById(R.id.activity_match_opponentFacebook_Img);
        ImgOpponentInstagram = findViewById(R.id.activity_match_opponentInstagram_Img);
        txtMyTeamStadium = findViewById(R.id.activity_match_myTeamStadium_txt);
        txtOpponentStadium = findViewById(R.id.activity_match_opponentStadium_txt);

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

        viewModel.getMyTeam().observe(this, team -> {
            updateMyTeam(team);
        });

        viewModel.getOpponenetsTeam().observe(this, team -> {
            updateOpponenentsTeam(team);
        });

        ImgMyTeamFacebook.setOnClickListener(view -> openFacebook(viewModel.getMyTeam().getValue()));
        ImgMyTeamInstagram.setOnClickListener(view -> openInstagram(viewModel.getMyTeam().getValue()));
        ImgOpponentFacebook.setOnClickListener(view -> openFacebook(viewModel.getOpponenetsTeam().getValue()));
        ImgOpponentInstagram.setOnClickListener(view -> openInstagram(viewModel.getOpponenetsTeam().getValue()));

        Glide.with(this)
                .asGif()
                .load("https://i.pinimg.com/originals/18/79/17/187917b0606c80a6c295da1f19ff3e40.gif")
                .into(ImgMyLogo);

        Glide.with(this)
                .asGif()
                .load("https://i.pinimg.com/originals/18/79/17/187917b0606c80a6c295da1f19ff3e40.gif")
                .into(ImgOpponentLogo);
    }

    private void openFacebook(Team team) {
        if(team.Facebook.equals("")){
            Toast.makeText(this, getString(R.string.FacebookErrorMessage) + team.Name, Toast.LENGTH_SHORT).show();
        }
        else{
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
            String facebookUrl = getFacebookAppPageURL(this,team.Facebook);
            facebookIntent.setData(Uri.parse(facebookUrl));
            startActivity(facebookIntent);
        }
    }
    //https://stackoverflow.com/questions/34564211/open-facebook-page-in-facebook-app-if-installed-on-android
    public String getFacebookAppPageURL(Context context, String facebookUrl) {
        String[] pageUrl = facebookUrl.split("/");
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + "https://" + facebookUrl;
            } else { //older versions of fb app
                return "fb://page/" + pageUrl[1];
            }
        } catch (PackageManager.NameNotFoundException e) {
            return facebookUrl; //normal web url
        }
    }

    private void openInstagram(Team team) {
        if(team.Instagram.equals("")){
            Toast.makeText(this, getString(R.string.InstagramErrorMessage) + team.Name, Toast.LENGTH_SHORT).show();
        }
        else{
            Uri uri = Uri.parse("http://" + team.Instagram);
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
            likeIng.setPackage("com.instagram.android");

            try {
                startActivity(likeIng);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://" + team.Instagram)));
            }
        }
    }

    public String getInstagramAppPageURL(Context context, String facebookUrl) {
        String[] pageUrl = facebookUrl.split("/");
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + "https://" + facebookUrl;
            } else { //older versions of fb app
                return "fb://page/" + pageUrl[1];
            }
        } catch (PackageManager.NameNotFoundException e) {
            return facebookUrl; //normal web url
        }
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
        txtOpponentStadium.setText(team.StadiumName);
        Glide.with(this)
                .load(team.LogoUrl)
                .override(200, 200)
                .into(ImgOpponentLogo);
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
        txtMyTeamStadium.setText(team.StadiumName);
        Glide.with(this)
                .load(team.LogoUrl)
                .override(200, 200)
                .into(ImgMyLogo);
    }

    private void OpponentSettingsClicked() {
        MatchSettingsDialog matchSettingsDialog = new MatchSettingsDialog(viewModel.getAllLeagues(),viewModel.getOpponentLeagues(),false);
        matchSettingsDialog.show(getSupportFragmentManager(), "enterGamertagDialog");

    }

    private void MySettingsClicked() {
        MatchSettingsDialog matchSettingsDialog = new MatchSettingsDialog(viewModel.getAllLeagues(),viewModel.getMyLeagues(),true);
        matchSettingsDialog.show(getSupportFragmentManager(), "enterGamertagDialog");

    }

    @Override
    public void updateSettings(ArrayList<String> selectedLeagues, boolean isMyPlayer) {
        if(isMyPlayer){
            viewModel.setMyLeagues(selectedLeagues);
        }
        else{
            viewModel.setOpponentLeagues(selectedLeagues);
        }
    }

    private void OpponentRegenerateClicked() {

        Glide.with(this)
                .asGif()
                .load("https://i.pinimg.com/originals/18/79/17/187917b0606c80a6c295da1f19ff3e40.gif")
                .into(ImgOpponentLogo);
        viewModel.generateNewTeam(false);
    }

    private void MyRegenerateClicked() {
        Glide.with(this)
                .asGif()
                .load("https://i.pinimg.com/originals/18/79/17/187917b0606c80a6c295da1f19ff3e40.gif")
                .into(ImgMyLogo);
        viewModel.generateNewTeam(true);
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