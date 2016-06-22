package com.gymassistant.RecyclerView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gymassistant.Models.SeriesDoneGroup;
import com.gymassistant.R;

import java.util.List;

/**
 * Created by KamilH on 2016-06-17.
 */
public class HistoryChildAdapter extends RecyclerView.Adapter<HistoryChildAdapter.ViewHolder> {
    private List<SeriesDoneGroup> seriesDoneGroupList;
    private Context context;

    public HistoryChildAdapter(Context context, List<SeriesDoneGroup> seriesDoneGroupList){
        this.seriesDoneGroupList = seriesDoneGroupList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return seriesDoneGroupList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_child_history, null);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder rowViewHolder, int position) {
        final SeriesDoneGroup item = seriesDoneGroupList.get(position);

        rowViewHolder.muscleGroupTextView.setText(item.getExercise().getCategory());
        rowViewHolder.exerciseNameTextView.setText(String.format("%s %s", item.getExercise().getName(), item.getExercise().getSecondName()));

        HistoryAdapter historyAdapter = new HistoryAdapter(context, item.getSeriesDoneList());
        rowViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        rowViewHolder.recyclerView.setAdapter(historyAdapter);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView muscleGroupTextView, exerciseNameTextView;
        public RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            muscleGroupTextView = (TextView) itemView.findViewById(R.id.muscleGroupTextView);
            exerciseNameTextView = (TextView) itemView.findViewById(R.id.exerciseNameTextView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerview);
        }
    }
}
