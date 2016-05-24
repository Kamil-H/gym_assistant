package com.gymassistant.Models;


import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

/**
 * Created by KamilH on 2016-05-04.
 */
public class StartedTrainingPlan implements ParentListItem {
    private int id;
    private String name;
    private String description;
    private int owner;
    private int trainingPlanId;
    private TrainingPlan trainingPlan;
    private String startDate;
    private String expectedEndDate;
    private String endDate;
    private int firstDay;
    private boolean isPublic;
    private List<StartedTrainingPlan> startedTrainingPlanList;

    public StartedTrainingPlan(){}

    public StartedTrainingPlan(String name, String description, int trainingPlanId, String startDate, String expectedEndDate) {
        this.name = name;
        this.description = description;
        this.trainingPlanId = trainingPlanId;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getTrainingPlanId() {
        return trainingPlanId;
    }

    public void setTrainingPlanId(int trainingPlanId) {
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

    public int getFirstDay() {
        return firstDay;
    }

    public void setFirstDay(int firstDay) {
        this.firstDay = firstDay;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public TrainingPlan getTrainingPlan() {
        return trainingPlan;
    }

    public void setTrainingPlan(TrainingPlan trainingPlan) {
        this.trainingPlan = trainingPlan;
    }

    @Override
    public List<?> getChildItemList() {
        return startedTrainingPlanList;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public void setStartedTrainingPlanList(List<StartedTrainingPlan> startedTrainingPlanList) {
        this.startedTrainingPlanList = startedTrainingPlanList;
    }
}
