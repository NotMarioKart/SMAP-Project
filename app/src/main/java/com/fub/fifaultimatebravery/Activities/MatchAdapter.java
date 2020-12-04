package com.fub.fifaultimatebravery.Activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.fub.fifaultimatebravery.DataClasses.Matches;
import com.fub.fifaultimatebravery.R;

// This adapter is inspired from https://www.youtube.com/watch?v=RFFu3dP5JDk&list=PLrnPJCHvNZuAXdWxOzsN5rgG2M4uJ8bH1&index=2
public class MatchAdapter extends FirestoreRecyclerAdapter<Matches, MatchAdapter.MatchHolder> {

    Context context;

    public MatchAdapter(@NonNull FirestoreRecyclerOptions<Matches> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MatchHolder matchHolder, int i, @NonNull Matches matches) {
        matchHolder.myGoals.setText(matches.getMyGoals());
        matchHolder.opponentGoals.setText(matches.getOpponentGoals());

        Glide.with(context)
                .load(matches.getOpponentLogoUrl())
                .into(matchHolder.opponentTeamImg);

        Glide.with(context)
                .load(matches.getMyTeamLogoUrl())
                .into(matchHolder.myTeamImg);
    }

    @NonNull
    @Override
    public MatchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stat_view, parent
                , false );

        return new MatchHolder(v);
    }

    class MatchHolder extends RecyclerView.ViewHolder{
        TextView myGoals;
        TextView opponentGoals;
        ImageView myTeamImg;
        ImageView opponentTeamImg;

        public MatchHolder(@NonNull View itemView) {
            super(itemView);
            myGoals = itemView.findViewById(R.id.myGoalsStatTV);
            opponentGoals = itemView.findViewById(R.id.opponentGoalsStatTV);
            myTeamImg = itemView.findViewById(R.id.myTeamIMG);
            opponentTeamImg = itemView.findViewById(R.id.opponentTeamIMG);

        }
    }
}
