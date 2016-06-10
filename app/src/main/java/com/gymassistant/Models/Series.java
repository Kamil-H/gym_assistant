package com.gymassistant.Models;

/**
 * Created by KamilH on 2016-05-02.
 */
public class Series {
    private long id;
    private Training training;
    private long trainingId;
    private Exercise exercise;
    private long exerciseId;
    private int order;
    private int repeat;
    private int weight;

    public Series(){}

    public Series(Training training, long trainingId, Exercise exercise, long exerciseId, int order, int repeat, int weight) {
        this.training = training;
        this.trainingId = trainingId;
        this.exercise = exercise;
        this.exerciseId = exerciseId;
        this.order = order;
        this.repeat = repeat;
        this.weight = weight;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(long trainingId) {
        this.trainingId = trainingId;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
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
