package com.gymassistant.RecyclerView;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.gymassistant.R;

/**
 * Created by KamilH on 2016-03-24.
 */
public class ExerciseParentViewHolder extends ParentViewHolder {

    public TextView titleTextView;

    public ExerciseParentViewHolder(View itemView) {
        super(itemView);

        titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
    }
}