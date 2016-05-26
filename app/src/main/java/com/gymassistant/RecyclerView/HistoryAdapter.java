package com.gymassistant.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gymassistant.Models.SeriesDone;
import com.gymassistant.R;

import java.util.List;

/**
 * Created by KamilH on 2016-05-26.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryRowViewHolder> {
    private Context context;
    private List<SeriesDone> seriesDoneList;

    public HistoryAdapter(Context context, List<SeriesDone> seriesDoneList) {
        this.context = context;
        this.seriesDoneList = seriesDoneList;
    }

    @Override
    public int getItemCount() {
        return seriesDoneList.size();
    }

    @Override
    public HistoryRowViewHolder onCreateViewHolder(ViewGroup viewGroup, final int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_series_history, null);
        HistoryRowViewHolder viewHolder = new HistoryRowViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HistoryRowViewHolder rowViewHolder, final int position) {
        rowViewHolder.seriesNumberTextView.setText(String.valueOf(position + 1));
        rowViewHolder.repeatsNumberTextView.setText(String.valueOf(seriesDoneList.get(position).getActualRepeat()));
        rowViewHolder.loadTextView.setText(String.format("%d kg", seriesDoneList.get(position).getActualWeight()));
    }

    class HistoryRowViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView seriesNumberTextView, repeatsNumberTextView, loadTextView;

        public HistoryRowViewHolder(View view) {
            super(view);
            this.seriesNumberTextView = (TextView) view.findViewById(R.id.seriesNumberTextView);
            this.repeatsNumberTextView = (TextView) view.findViewById(R.id.repeatsNumberTextView);
            this.loadTextView = (TextView) view.findViewById(R.id.loadTextView);
        }
    }
}