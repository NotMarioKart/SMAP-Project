package com.fub.fifaultimatebravery.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.fub.fifaultimatebravery.R;
import com.fub.fifaultimatebravery.ViewModels.MenuActivityViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {
    // Variables
    private MenuActivityViewModel viewModel;
    EditText EdtTxtWager;
    Button bntGenerate, bntStatistics, bntAdd, bntLogOut;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Resources
        mAuth = FirebaseAuth.getInstance();

        EdtTxtWager = findViewById(R.id.EdtTxtAddWager);
        bntGenerate = findViewById(R.id.generateBtn);
        bntStatistics = findViewById(R.id.statisticsBtn);
        bntAdd = findViewById(R.id.addBtn);
        bntLogOut = findViewById(R.id.LogOutBtn2);
        
        bntLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogOutClicked();
            }
        });
        
        bntAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddClicked();
            }
        });
        
        bntStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatisticsClicked();
            }
        });
        
        bntGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GenerateClicked();
            }
        });
    }

    private void GenerateClicked() {
        Intent i = new Intent(this, MatchActivity.class);
        startActivity(i);
        //finish();
    }

    private void StatisticsClicked() {
        Intent i = new Intent(this, StatisticsActivity.class);
        startActivity(i);
        finish();
    }

    private void AddClicked() {

    }

    private void LogOutClicked() {
        mAuth.signOut();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}