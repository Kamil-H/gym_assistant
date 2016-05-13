package com.gymassistant.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gymassistant.Database.ExerciseDB;
import com.gymassistant.Models.Category;
import com.gymassistant.Models.Exercise;
import com.gymassistant.R;
import com.gymassistant.RecyclerView.ExerciseExpandableAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KamilH on 2016-03-21.
 */
public class ExercisesFragment extends Fragment {
    private View view;
    private List<Category> categories;
    private List<Exercise> exercises;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exercises,container,false);

        getExerciseLists();
        setUpExpandableRecyclerView();

        return view;
    }

    private void getExerciseLists(){
        ExerciseDB exerciseDB = new ExerciseDB(getActivity());
        exercises = exerciseDB.getAllExercises();
        categories = exerciseDB.getCategories();
    }

    private void setUpExpandableRecyclerView(){
        ExerciseExpandableAdapter exerciseExpandableAdapter = new ExerciseExpandableAdapter(getActivity(), generateList());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setAdapter(exerciseExpandableAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private ArrayList<Category> generateList() {
        ArrayList<Category> parentList = new ArrayList<>();
        for (Category category : categories) {
            ArrayList<Exercise> childList = new ArrayList<>();
            for (Exercise exercise : exercises) {
                if(exercise.getCategory().matches(category.getCategory())){
                    childList.add(exercise);
                }
            }
            Category parentItem = new Category(category.getCategory(), childList);
            parentList.add(parentItem);
        }
        return parentList;
    }
}


