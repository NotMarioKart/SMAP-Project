package com.fub.fifaultimatebravery.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.fub.fifaultimatebravery.DataClasses.Wagers;
import com.fub.fifaultimatebravery.R;
import com.fub.fifaultimatebravery.ViewModels.MenuActivityViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MenuActivity extends AppCompatActivity {
    // Variables
    private MenuActivityViewModel viewModel;
    EditText EdtTxtWager;
    Button bntGenerate, bntStatistics, bntAdd, bntLogOut, bntWagerList;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private WagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        viewModel = new ViewModelProvider(this).get(MenuActivityViewModel.class);
        // Resources
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        EdtTxtWager = findViewById(R.id.EdtTxtAddWager);
        bntGenerate = findViewById(R.id.generateBtn);
        bntStatistics = findViewById(R.id.statisticsBtn);
        bntAdd = findViewById(R.id.addBtn);
        bntLogOut = findViewById(R.id.LogOutBtn2);
        bntWagerList = findViewById(R.id.wagerViewBnt);


        bntWagerList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WagerListClicked();
            }
        });

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

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(() -> {
            viewModel.resetTeams();
        }).start();
    }

    private void WagerListClicked() {
       Intent i = new Intent(this, WagerActivity.class);
       startActivity(i);
    }

    private void GenerateClicked() {
        Intent i = new Intent(this, MatchActivity.class);
        startActivity(i);

    }

    private void StatisticsClicked() {
        Intent i = new Intent(this, StatisticsActivity.class);
        startActivity(i);
    }

    private void AddClicked() {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String customWager = EdtTxtWager.getText().toString();
        viewModel.addWager(customWager, userID);
        EdtTxtWager.setText("");

    }

    private void LogOutClicked() {
        mAuth.signOut();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}