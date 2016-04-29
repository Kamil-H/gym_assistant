package com.gymassistant.RecyclerView;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.gymassistant.R;

/**
 * Created by KamilH on 2016-03-24.
 */
public class ExerciseChildViewHolder extends ChildViewHolder {

    public TextView exerciseEditText;

    public ExerciseChildViewHolder(View itemView) {
        super(itemView);

        exerciseEditText = (TextView) itemView.findViewById(R.id.exerciseEditText);
    }
}