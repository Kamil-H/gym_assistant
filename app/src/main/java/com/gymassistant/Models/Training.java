package com.gymassistant.Models;

import java.util.List;
/**
 * Created by KamilH on 2016-05-04.
 */
public class Training {
    private int id;
    private int trainingPlanId;
    private TrainingPlan trainingPlan;
    private int day;
    private String description;
    private List<Series> seriesList;

    public Training(){}

    public Training(int trainingPlanId, int day, String description) {
        this.trainingPlanId = trainingPlanId;
        this.day = day;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrainingPlanId() {
        return trainingPlanId;
    }

    public void setTrainingPlanId(int trainingPlanId) {
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
