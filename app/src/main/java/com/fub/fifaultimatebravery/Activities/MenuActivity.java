package com.fub.fifaultimatebravery.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.fub.fifaultimatebravery.DataClasses.Matches;
import com.fub.fifaultimatebravery.DataClasses.Wagers;
import com.fub.fifaultimatebravery.R;
import com.fub.fifaultimatebravery.ScrapingClasses.ClubsResponse.Item;
import com.fub.fifaultimatebravery.ViewModels.MenuActivityViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
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

    private void WagerListClicked() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.wager_list_viw, null);
        mBuilder.setTitle("List of wagers");
        setUpRecyclerView();

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

    }

    private void setUpRecyclerView() {
        Query query = db.collection("Wagers").whereEqualTo("userID", FirebaseAuth.getInstance().getCurrentUser().getUid());
        FirestoreRecyclerOptions<Wagers> options = new FirestoreRecyclerOptions.Builder<Wagers>()
                .setQuery(query, Wagers.class)
                .build();
        adapter = new WagerAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.rcvWager);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteWager(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);
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
    }

    private void LogOutClicked() {
        mAuth.signOut();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}