package com.gymassistant.Models;

import java.util.List;
/**
 * Created by KamilH on 2016-05-04.
 */
public class Training {
    private long id;
    private long trainingPlanId;
    private TrainingPlan trainingPlan;
    private int day;
    private String description;
    private List<Series> seriesList;

    public Training(){}

    public Training(long trainingPlanId, int day, String description) {
        this.trainingPlanId = trainingPlanId;
        this.day = day;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTrainingPlanId() {
        return trainingPlanId;
    }

    public void setTrainingPlanId(long trainingPlanId) {
        this.trainingPlanId = trainingPlanId;
    }

    public TrainingPlan getTrainingPlan() {
        return trainingPlan;
    }

    public void setTrainingPlan(TrainingPlan trainingPlan) {
        this.trainingPlan = trainingPlan;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Series> getSeriesList() {
        return seriesList;
    }

    public void setSeriesList(List<Series> seriesList) {
        this.seriesList = seriesList;
    }
}
