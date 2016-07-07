package com.gymassistant.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gymassistant.Models.Exercise;
import com.gymassistant.R;
import com.gymassistant.UIComponents.Dialogs.FavoritesDialog;

import java.util.List;

/**
 * Created by KamilH on 2016-07-06.
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesRowViewHolder> {
    private Context context;
    private List<Exercise> exerciseList;
    private FavoritesDialog favoritesDialog;

    public FavoritesAdapter(Context context, List<Exercise> exerciseList, FavoritesDialog favoritesDialog) {
        this.context = context;
        this.exerciseList = exerciseList;
        this.favoritesDialog = favoritesDialog;
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    @Override
    public FavoritesRowViewHolder onCreateViewHolder(ViewGroup viewGroup, final int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_favorite, null);
        final FavoritesRowViewHolder rowViewHolder = new FavoritesRowViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoritesDialog.closeDialogAndSelectSpinnerItem(exerciseList.get(rowViewHolder.getAdapterPosition()));
            }
        });

        return rowViewHolder;
    }

    @Override
    public void onBindViewHolder(FavoritesRowViewHolder rowViewHolder, final int position) {
        Exercise exercise = exerciseList.get(position);
        rowViewHolder.muscleGroupTextView.setText(exercise.getCategory());
        rowViewHolder.exerciseNameTextView.setText(String.format("%s %s", exercise.getName(), exercise.getSecondName()));
    }

    class FavoritesRowViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView muscleGroupTextView, exerciseNameTextView;

        public FavoritesRowViewHolder(View view) {
            super(view);
            this.muscleGroupTextView = (TextView) view.findViewById(R.id.muscleGroupTextView);
            this.exerciseNameTextView = (TextView) view.findViewById(R.id.exerciseNameTextView);
        }
    }
}