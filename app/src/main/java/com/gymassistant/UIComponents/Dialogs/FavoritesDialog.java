package com.gymassistant.UIComponents.Dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gymassistant.Database.ExerciseDB;
import com.gymassistant.Fragments.WizardFragments.SecondPage;
import com.gymassistant.Models.Exercise;
import com.gymassistant.R;
import com.gymassistant.RecyclerView.FavoritesAdapter;

import java.util.List;

/**
 * Created by KamilH on 2016-07-06.
 */
public class FavoritesDialog extends DialogFragment {
    private View view;

    public static FavoritesDialog newInstance() {
        FavoritesDialog favoritesDialog = new FavoritesDialog();
        favoritesDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);

        return favoritesDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_favorites, container, false);
        setUpRecyclerView();

        return view;
    }

    private void setUpRecyclerView(){
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FavoritesAdapter favoritesAdapter = new FavoritesAdapter(getActivity(), getFavoritesList(), FavoritesDialog.this);
        recyclerView.setAdapter(favoritesAdapter);
    }

    private List<Exercise> getFavoritesList(){
        ExerciseDB exerciseDB = new ExerciseDB(getActivity());
        return exerciseDB.getFavoritesExercises();
    }

    private void selectSpinnerItems(Exercise exercise){
        SecondPage secondPage = (SecondPage) getTargetFragment();
        secondPage.selectExerciseSpinnerItem(exercise.getName());
        secondPage.selectMuscleGroupSpinnerItem(exercise.getCategory());
    }

    public void closeDialogAndSelectSpinnerItem(Exercise exercise){
        selectSpinnerItems(exercise);
        this.dismiss();
    }
}
