package com.fub.fifaultimatebravery.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.fub.fifaultimatebravery.DataClasses.Wagers;
import com.fub.fifaultimatebravery.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class WagerActivity extends AppCompatActivity {
    private WagerAdapter adapter;
    private FirebaseFirestore db;
    TextView Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wager);
        setUpRecyclerView();

        Title = findViewById(R.id.WagerActivityTitle);
    }

    // Swipe to delete function inspired from https://www.youtube.com/watch?v=dTuhMFP-a1g
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

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}

