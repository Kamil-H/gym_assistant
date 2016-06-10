package com.gymassistant.Models;

/**
 * Created by KamilH on 2016-05-19.
 */
public class SeriesDone {
    private long id;
    private long trainingId;
    private long exerciseId;
    private int actualOrder;
    private int actualRepeat;
    private int actualWeight;
    private int plannedOrder;
    private int plannedRepeat;
    private int plannedWeight;
    private Exercise exercise;
    private TrainingDone trainingDone;

    public SeriesDone(){}

    public SeriesDone(Series series){
        this.trainingId = series.getTrainingId();
        this.exerciseId = series.getExerciseId();
        this.actualOrder = series.getOrder();
        this.plannedOrder = series.getOrder();
        this.plannedRepeat = series.getRepeat();
        this.plannedWeight = series.getWeight();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getActualOrder() {
        return actualOrder;
    }

    public void setActualOrder(int actualOrder) {
        this.actualOrder = actualOrder;
    }

    public int getActualRepeat() {
        return actualRepeat;
    }

    public void setActualRepeat(int actualRepeat) {
        this.actualRepeat = actualRepeat;
    }

    public int getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(int actualWeight) {
        this.actualWeight = actualWeight;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getPlannedOrder() {
        return plannedOrder;
    }

    public void setPlannedOrder(int plannedOrder) {
        this.plannedOrder = plannedOrder;
    }

    public int getPlannedRepeat() {
        return plannedRepeat;
    }

    public void setPlannedRepeat(int plannedRepeat) {
        this.plannedRepeat = plannedRepeat;
    }

    public int getPlannedWeight() {
        return plannedWeight;
    }

    public void setPlannedWeight(int plannedWeight) {
        this.plannedWeight = plannedWeight;
    }

    public long getTrainingDoneId() {
        return trainingId;
    }

    public void setTrainingDoneId(long trainingId) {
        this.trainingId = trainingId;
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
}
