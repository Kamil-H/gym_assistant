package com.gymassistant.RecyclerView;

/**
 * Created by KamilH on 2015-10-23.
 */

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gymassistant.Models.Series;
import com.gymassistant.R;

import java.util.List;
import java.util.Map;

public class TrainingPlanAdapter extends RecyclerView.Adapter<TrainingPlanAdapter.TrainingPlanRowViewHolder> {
    private Context context;
    private int itemCount;
    private SparseArrayCompat<List<Series>> map;

    public TrainingPlanAdapter(Context context, int itemCount, SparseArrayCompat<List<Series>> map) {
        this.context = context;
        this.itemCount = itemCount;
        this.map = map;
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    @Override
    public TrainingPlanRowViewHolder onCreateViewHolder(ViewGroup viewGroup, final int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_training_plan, null);

        TrainingPlanRowViewHolder viewHolder = new TrainingPlanRowViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrainingPlanRowViewHolder rowViewHolder, final int position) {
        rowViewHolder.dayNameTextView.setText(context.getString(R.string.day, position + 1));
        if(map != null && map.size() > 0 && map.valueAt(position) != null) {
            TrainingPlanExerciseAdapter trainingPlanExerciseAdapter = new TrainingPlanExerciseAdapter(context, getSeriesList(position));
            rowViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            rowViewHolder.recyclerView.setAdapter(trainingPlanExerciseAdapter);
            ItemTouchHelper.Callback callback = new SeriesTouchHelper(trainingPlanExerciseAdapter);
            ItemTouchHelper helper = new ItemTouchHelper(callback);
            helper.attachToRecyclerView(rowViewHolder.recyclerView);
        }
    }

    private List<Series> getSeriesList(int position){
        return map.get(position);
    }

    class TrainingPlanRowViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView dayNameTextView;
        public RecyclerView recyclerView;

        public TrainingPlanRowViewHolder(View view) {
            super(view);
            this.dayNameTextView = (TextView) view.findViewById(R.id.dayNameTextView);
            this.recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        }
    }
}