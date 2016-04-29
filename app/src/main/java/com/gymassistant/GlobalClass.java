package com.gymassistant;

import android.app.Application;

import com.gymassistant.Models.Category;
import com.gymassistant.Models.Exercise;
import java.util.List;

/**
 * Created by KamilH on 2016-03-25.
 */
public class GlobalClass extends Application {
    public List<Exercise> exercises;
    public List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
