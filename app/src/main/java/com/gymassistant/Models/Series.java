package com.gymassistant.Models;

/**
 * Created by KamilH on 2016-05-02.
 */
public class Series {
    private int id;
    private Training training;
    private int trainingId;
    private Exercise exercise;
    private int exerciseId;
    private int order;
    private int repeat;
    private int weight;

    public Series(){}

    public Series(int trainingId, Exercise exercise, int exerciseId, int order, int repeat, int weight) {
        this.trainingId = trainingId;
        this.exercise = exercise;
        this.exerciseId = exerciseId;
        this.order = order;
        this.repeat = repeat;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public int getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(int trainingId) {
        this.trainingId = trainingId;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return exercise.getName();
    }
}
