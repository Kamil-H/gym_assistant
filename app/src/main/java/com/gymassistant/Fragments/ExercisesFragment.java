package com.gymassistant.Fragments;

import android.os.AsyncTask;
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
    private ExerciseDB exerciseDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exercises,container,false);

        exerciseDB = new ExerciseDB(getActivity());

        new AsyncUI().execute();

        return view;
    }

    private List<Exercise> getExerciseLists(){
        return exerciseDB.getAllExercises();
    }

    private List<Category> getCategoryLists(){
        return exerciseDB.getCategories();
    }

    private void setUpExpandableRecyclerView(ArrayList<Category> categories){
        ExerciseExpandableAdapter exerciseExpandableAdapter = new ExerciseExpandableAdapter(getActivity(), categories);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setAdapter(exerciseExpandableAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private ArrayList<Category> generateList(List<Category> categories, List<Exercise> exercises) {
        ArrayList<Category> parentList = new ArrayList<>();
        ArrayList<Exercise> favoritesList = new ArrayList<>();

        for (Category category : categories) {
            ArrayList<Exercise> childList = new ArrayList<>();
            for (Exercise exercise : exercises) {
                if(exercise.getCategory().matches(category.getCategory())){
                    childList.add(exercise);
                }
                if(exercise.isFavorite() && !favoritesList.contains(exercise)){
                    favoritesList.add(exercise);
                }
            }
            Category parentItem = new Category(category.getCategory(), childList);
            parentList.add(parentItem);
        }

        if(favoritesList.size() > 0){
            Category parentItem = new Category(getString(R.string.favorites), favoritesList);
            parentList.add(0, parentItem);
        }

        return parentList;
    }

    class AsyncUI extends AsyncTask<Void, Void, ArrayList<Category>> {

        @Override
        protected ArrayList<Category> doInBackground(Void... params) {
            return generateList(getCategoryLists(), getExerciseLists());
        }

        @Override
        protected void onPostExecute(ArrayList<Category> categories) {
            super.onPostExecute(categories);
            setUpExpandableRecyclerView(categories);
        }
    }
}


