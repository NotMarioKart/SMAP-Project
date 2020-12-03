package com.fub.fifaultimatebravery.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.fub.fifaultimatebravery.R;

import java.util.ArrayList;

public class MatchSettingsDialog extends AppCompatDialogFragment {
    private static final int maxSelectableLeagues = 10;
    private TextView selectedCountText;

    public MatchSettingsDialog(ArrayList<String> allLeagues, ArrayList<String> myLeagues, boolean isMyPlayer) {
        this.isMyPlayer = isMyPlayer;
        this.allLeagues = new String[allLeagues.size()];
        this.allLeagues = allLeagues.toArray(this.allLeagues);

        checkedLeagues = new boolean[this.allLeagues.length];

        for (String league:myLeagues) {
            checkedLeagues[allLeagues.indexOf(league)] = true;
        }
        selectedLeaguesCount = myLeagues.size();
    }

    private IMatchSettingsDialogListener listener;

    private boolean isMyPlayer;

    private String[] allLeagues;
    private boolean[] checkedLeagues;
    private int selectedLeaguesCount = 0;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.settings_dialog, null);

        builder
                .setTitle("Set Leagues")
                //.setMessage("0/"+maxSelectableLeagues)
                .setMultiChoiceItems(allLeagues, checkedLeagues, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                        if (!isChecked) {
                            selectedLeaguesCount--;
                            ((AlertDialog) dialogInterface).setMessage(selectedLeaguesCount + "/" + maxSelectableLeagues);

                            selectedCountText.setText(selectedLeaguesCount + "/" + maxSelectableLeagues);
                        } else if (selectedLeaguesCount == maxSelectableLeagues) {
                            checkedLeagues[i] = false;
                            ((AlertDialog) dialogInterface).getListView().setItemChecked(i, false);
                            Animation animation = AnimationUtils.loadAnimation(((AlertDialog) dialogInterface).getContext(), R.anim.shake_animation);
                            selectedCountText.setAnimation(animation);
                        } else {
                            selectedLeaguesCount++;
                            ((AlertDialog) dialogInterface).setMessage(selectedLeaguesCount + "/" + maxSelectableLeagues);

                            selectedCountText.setText(selectedLeaguesCount + "/" + maxSelectableLeagues);
                        }
                    }
                });

        builder.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Todo get the selected leagues
                        ArrayList<String> selectedLeagues = new ArrayList<>();
                        for (int j = 0; j < allLeagues.length; j++) {
                            if (checkedLeagues[j]) {
                                selectedLeagues.add(allLeagues[j]);
                            }
                        }
                        listener.updateSettings(selectedLeagues, isMyPlayer);
                    }
                });

        selectedCountText = view.findViewById(R.id.settings_dialog_leaguesPickedCounterText);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (MatchSettingsDialog.IMatchSettingsDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement EnterGamertagListener");
        }
    }

    public interface IMatchSettingsDialogListener {
        void updateSettings(ArrayList<String> selectedLeagues, boolean isMyPlayer);
    }
}
