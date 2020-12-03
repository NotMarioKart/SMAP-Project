package com.fub.fifaultimatebravery.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fub.fifaultimatebravery.DataClasses.Matches;
import com.fub.fifaultimatebravery.DataClasses.Wagers;
import com.fub.fifaultimatebravery.R;
import com.fub.fifaultimatebravery.ViewModels.MenuActivityViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MenuActivity extends AppCompatActivity {
    // Variables
    private MenuActivityViewModel viewModel;
    EditText EdtTxtWager;
    Button bntGenerate, bntStatistics, bntAdd, bntLogOut;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Resources
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

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
        finish();
    }

    private void StatisticsClicked() {
        Intent i = new Intent(this, StatisticsActivity.class);
        startActivity(i);
        finish();
    }

    private void AddClicked() {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String customWager = EdtTxtWager.getText().toString();
        CollectionReference dbMatches = db.collection("Wagers");


        Wagers wagers = new Wagers(
                customWager,
                userID
        );

        dbMatches.add(wagers).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                EdtTxtWager.setText("");
                Toast.makeText(MenuActivity.this, R.string.WagerSuccessToast, Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MenuActivity.this, R.string.WagerFailedToast, Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void LogOutClicked() {
        mAuth.signOut();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}