package com.gymassistant.Models;

/**
 * Created by KamilH on 2016-04-29.
 */
public class TrainingPlanExercise {
    private String muscleGroup;
    private String exercise;
    private int series;
    private int repeats;

    public TrainingPlanExercise(String muscleGroup, String exercise, int series, int repeats) {
        this.muscleGroup = muscleGroup;
        this.exercise = exercise;
        this.series = series;
        this.repeats = repeats;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepeats() {
        return repeats;
    }

    public void setRepeats(int repeats) {
        this.repeats = repeats;
    }
}
