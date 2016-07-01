package com.gymassistant.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.util.LongSparseArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gymassistant.Models.Dimension;
import com.gymassistant.R;

import java.util.List;

/**
 * Created by KamilH on 2016-06-23.
 */
public class DimensionAdapter extends RecyclerView.Adapter<DimensionAdapter.DimensionRowViewHolder> {
    private Context context;
    private LongSparseArray<List<Dimension>> map;

    public DimensionAdapter(Context context, LongSparseArray<List<Dimension>> map) {
        this.context = context;
        this.map = map;
    }

    @Override
    public int getItemCount() {
        return map.size();
    }

    @Override
    public DimensionRowViewHolder onCreateViewHolder(ViewGroup viewGroup, final int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_dimension, null);

        return new DimensionRowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DimensionRowViewHolder rowViewHolder, final int position) {
        List<Dimension> dimensionList = map.valueAt(position);
        Dimension actualDimension = dimensionList.get(0);
        String unit = actualDimension.getType().getUnit();

        rowViewHolder.typeTextView.setText(actualDimension.getType().getName());
        rowViewHolder.valueTextView.setText(String.valueOf(actualDimension.getValue() + " " + unit));

        if(dimensionList.size() > 1){
            Dimension oldDimension = dimensionList.get(1);
            double comparedValue = compare(actualDimension.getValue(), oldDimension.getValue());

            if(comparedValue > 0){
                rowViewHolder.oldValueTextView.setTextColor(Color.GREEN);
                rowViewHolder.oldValueTextView.setText(String.format("(+%s)", String.valueOf(comparedValue)));
            } else {
                rowViewHolder.oldValueTextView.setTextColor(Color.RED);
                rowViewHolder.oldValueTextView.setText(String.format("(-%s)", String.valueOf(comparedValue)));
            }
        }
    }

    private double compare(double actualValue, double oldValue){
        return actualValue - oldValue;
    }

    class DimensionRowViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView typeTextView, valueTextView, oldValueTextView;

        public DimensionRowViewHolder(View view) {
            super(view);
            this.typeTextView = (TextView) view.findViewById(R.id.typeTextView);
            this.valueTextView = (TextView) view.findViewById(R.id.valueTextView);
            this.oldValueTextView = (TextView) view.findViewById(R.id.oldValueTextView);
        }
    }
}
