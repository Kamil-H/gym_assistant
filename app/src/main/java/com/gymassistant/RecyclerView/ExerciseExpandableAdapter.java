package com.gymassistant.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.gymassistant.Activities.ExercisesPreview;
import com.gymassistant.Models.Category;
import com.gymassistant.Models.Exercise;
import com.gymassistant.R;

import java.util.List;

/**
 * Created by KamilH on 2016-03-24.
 */
public class ExerciseExpandableAdapter extends ExpandableRecyclerAdapter<ExerciseExpandableAdapter.ExerciseParentViewHolder,
        ExerciseExpandableAdapter.ExerciseChildViewHolder> {
    private LayoutInflater mInflator;
    private Context context;

    public ExerciseExpandableAdapter(Context context, @NonNull List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        this.context = context;
        this.mInflator = LayoutInflater.from(context);
    }

    @Override
    public ExerciseParentViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View parentView = mInflator.inflate(R.layout.item_parent, parentViewGroup, false);
        return new ExerciseParentViewHolder(parentView);
    }

    @Override
    public ExerciseChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View childView = mInflator.inflate(R.layout.item_child, childViewGroup, false);
        return new ExerciseChildViewHolder(childView);
    }

    @Override
    public void onBindParentViewHolder(ExerciseParentViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        Category category = (Category) parentListItem;
        parentViewHolder.titleTextView.setText(category.getCategory());
    }

    @Override
    public void onBindChildViewHolder(ExerciseChildViewHolder childViewHolder, final int position, final Object childListItem) {
        Exercise exercise = (Exercise) childListItem;

        if(isSecondName(exercise)){
            childViewHolder.exerciseEditText.setText(String.format("%s, %s", exercise.getName(), exercise.getSecondName()));
        } else {
            childViewHolder.exerciseEditText.setText(exercise.getName());
        }


        childViewHolder.exerciseEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = ((Exercise) childListItem).getId();
                goToExercisesPreviewScreen(id);
            }
        });
    }

    private void goToExercisesPreviewScreen(int id){
        Bundle b = new Bundle();
        b.putInt("id", id);
        Intent intent = new Intent(context.getApplicationContext(), ExercisesPreview.class);
        intent.putExtras(b);
        context.startActivity(intent);
    }

    private boolean isSecondName(Exercise exercise){
        return exercise.getSecondName().length() > 2;
    }

    public class ExerciseChildViewHolder extends ChildViewHolder {

        public TextView exerciseEditText;

        public ExerciseChildViewHolder(View itemView) {
            super(itemView);

            exerciseEditText = (TextView) itemView.findViewById(R.id.exerciseEditText);
        }
    }

    public class ExerciseParentViewHolder extends ParentViewHolder {

        public TextView titleTextView;

        public ExerciseParentViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.traningNameTextView);
        }
    }
}
