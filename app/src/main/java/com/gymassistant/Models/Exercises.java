package com.gymassistant.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KamilH on 2016-03-22.
 */
public class Exercises {
    private List<Exercise> Exercises = new ArrayList<Exercise>();

    public List<Exercise> getExercises() {
        return Exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        Exercises = exercises;
    }
}
