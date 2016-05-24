package com.gymassistant.Models;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;
/**
 * Created by KamilH on 2016-04-21.
 */
public class TrainingPlan implements ParentListItem {
    private int id;
    private int days;
    private int owner;
    private boolean isPublic;
    private String name;
    private String description;
    private List<Training> trainingList;
    private List<TrainingPlan> trainingPlanList;

    public TrainingPlan(List<TrainingPlan> trainingPlanList){
        this.trainingPlanList = trainingPlanList;
    }

    public TrainingPlan(){}

    public TrainingPlan(int days, int owner, boolean isPublic, String name, String description) {
        this.days = days;
        this.owner = owner;
        this.isPublic = isPublic;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
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

    @Override
    public String toString() {
        return this.name;
    }

    public List<Training> getTrainingList() {
        return trainingList;
    }

    public void setTrainingList(List<Training> trainingList) {
        this.trainingList = trainingList;
    }

    @Override
    public List<?> getChildItemList() {
        return trainingPlanList;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public List<TrainingPlan> getTrainingPlanList() {
        return trainingPlanList;
    }

    public void setTrainingPlanList(List<TrainingPlan> trainingPlanList) {
        this.trainingPlanList = trainingPlanList;
    }
}
