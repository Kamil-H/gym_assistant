package com.gymassistant.Models;


/**
 * Created by KamilH on 2016-05-04.
 */
public class StartedTrainingPlan {
    private long id;
    private String name;
    private String description;
    private long trainingPlanId;
    private TrainingPlan trainingPlan;
    private String startDate;
    private String expectedEndDate;
    private String endDate;
    private boolean exist;

    public StartedTrainingPlan(){}

    public StartedTrainingPlan(String name, String description, long trainingPlanId, String startDate, String expectedEndDate) {
        this.name = name;
        this.description = description;
        this.trainingPlanId = trainingPlanId;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTrainingPlanId() {
        return trainingPlanId;
    }

    public void setTrainingPlanId(long trainingPlanId) {
        this.trainingPlanId = trainingPlanId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(String expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public TrainingPlan getTrainingPlan() {
        return trainingPlan;
    }

    public void setTrainingPlan(TrainingPlan trainingPlan) {
        this.trainingPlan = trainingPlan;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }
}
