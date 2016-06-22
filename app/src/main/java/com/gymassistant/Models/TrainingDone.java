package com.gymassistant.Models;

import java.util.List;
/**
 * Created by KamilH on 2016-05-19.
 */
public class TrainingDone {
    private long id;
    private String date;
    private int day;
    private long startedTrainingPlanId;
    private List<SeriesDone> seriesDoneList;
    private String time;
    private List<SeriesDoneGroup> seriesDoneGroupList;

    public TrainingDone(String date, int day, long startedTrainingPlanId) {
        this.date = date;
        this.day = day;
        this.startedTrainingPlanId = startedTrainingPlanId;
    }

    public TrainingDone(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getStartedTrainingPlanId() {
        return startedTrainingPlanId;
    }

    public void setStartedTrainingPlanId(long startedTrainingPlanId) {
        this.startedTrainingPlanId = startedTrainingPlanId;
    }

    public List<SeriesDone> getSeriesDoneList() {
        return seriesDoneList;
    }

    public void setSeriesDoneList(List<SeriesDone> seriesDoneList) {
        this.seriesDoneList = seriesDoneList;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<SeriesDoneGroup> getSeriesDoneGroupList() {
        return seriesDoneGroupList;
    }

    public void setSeriesDoneGroupList(List<SeriesDoneGroup> seriesDoneGroupList) {
        this.seriesDoneGroupList = seriesDoneGroupList;
    }
}