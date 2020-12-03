package com.fub.fifaultimatebravery.Activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.fub.fifaultimatebravery.DataClasses.Wagers;
import com.fub.fifaultimatebravery.R;

public class WagerAdapter extends FirestoreRecyclerAdapter<Wagers, WagerAdapter.Wagerholder> {


    public WagerAdapter(@NonNull FirestoreRecyclerOptions<Wagers> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Wagerholder wagerHolder, int position, @NonNull Wagers wagers) {
        wagerHolder.customWagers.setText(wagers.getCustomWager());

    }

    @NonNull
    @Override
    public Wagerholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wager_view, parent, false);
        return new Wagerholder(v);
    }

    public void deleteWager(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class Wagerholder extends RecyclerView.ViewHolder{
        TextView customWagers;

        public Wagerholder(@NonNull View itemView) {
            super(itemView);
            customWagers = itemView.findViewById(R.id.wagerTextView);

        }
    }

}
