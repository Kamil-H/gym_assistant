package com.gymassistant.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.gymassistant.Activities.ExercisesPreview;
import com.gymassistant.Activities.RegisterActivity;
import com.gymassistant.Models.Category;
import com.gymassistant.Models.Exercise;
import com.gymassistant.R;

import java.util.List;

/**
 * Created by KamilH on 2016-03-24.
 */
public class ExerciseExpandableAdapter extends ExpandableRecyclerAdapter<ExerciseParentViewHolder, ExerciseChildViewHolder> {

    private LayoutInflater mInflator;
    private Context context;

    public ExerciseExpandableAdapter(Context context, @NonNull List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        this.context = context;
        mInflator = LayoutInflater.from(context);
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
            childViewHolder.exerciseEditText.setText(exercise.getName() + ", " +exercise.getSecondName());
        } else {
            childViewHolder.exerciseEditText.setText(exercise.getName());
        }


        childViewHolder.exerciseEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = ((Exercise) childListItem).getId();
                goToExercisesPreviewScreen(id);
            }
        });
    }

    private void goToExercisesPreviewScreen(String id){
        Bundle b = new Bundle();
        b.putString("id", id);
        Intent intent = new Intent(context.getApplicationContext(), ExercisesPreview.class);
        intent.putExtras(b);
        context.startActivity(intent);
    }

    private boolean isSecondName(Exercise exercise){
        return exercise.getSecondName().length() > 2;
    }
}
