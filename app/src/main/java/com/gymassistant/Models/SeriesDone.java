package com.gymassistant.Models;

/**
 * Created by KamilH on 2016-05-19.
 */
public class SeriesDone {
    private long id;
    private long trainingId;
    private long trainingDoneId;
    private long exerciseId;
    private int order;
    private int repeat;
    private double weight;
    private Exercise exercise;
    private TrainingDone trainingDone;
    private boolean isSaved;

    public SeriesDone(){}

    public SeriesDone(Series series){
        this.trainingId = series.getTrainingId();
        this.exerciseId = series.getExerciseId();
        this.exercise = series.getExercise();
        this.order = series.getOrder();
        this.repeat = series.getRepeat();
        this.isSaved = true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public long getTrainingDoneId() {
        return trainingDoneId;
    }

    public void setTrainingDoneId(long trainingDoneId) {
        this.trainingDoneId = trainingDoneId;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public TrainingDone getTrainingDone() {
        return trainingDone;
    }

    public void setTrainingDone(TrainingDone training) {
        this.trainingDone = training;
    }

    public long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(long trainingId) {
        this.trainingId = trainingId;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }
}
