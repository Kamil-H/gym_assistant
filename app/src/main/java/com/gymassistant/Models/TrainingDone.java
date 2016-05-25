package com.gymassistant.Models;

import java.util.List;
/**
 * Created by KamilH on 2016-05-19.
 */
public class TrainingDone {
    private int id;
    private String date;
    private int day;
    private int startedTrainingPlan;
    private List<SeriesDone> seriesDoneList;

    public TrainingDone(String date, int day, int startedTrainingPlan) {
        this.date = date;
        this.day = day;
        this.startedTrainingPlan = startedTrainingPlan;
    }

    public TrainingDone(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getStartedTrainingPlan() {
        return startedTrainingPlan;
    }

    public void setStartedTrainingPlan(int startedTrainingPlan) {
        this.startedTrainingPlan = startedTrainingPlan;
    }

    public List<SeriesDone> getSeriesDoneList() {
        return seriesDoneList;
    }

    public void setSeriesDoneList(List<SeriesDone> seriesDoneList) {
        this.seriesDoneList = seriesDoneList;
    }
}