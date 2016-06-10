package com.gymassistant.Models;

import java.util.List;
/**
 * Created by KamilH on 2016-04-21.
 */
public class TrainingPlan {
    private long id;
    private int days;
    private boolean exist;
    private String name;
    private String description;
    private List<Training> trainingList;

    public TrainingPlan(){}

    public TrainingPlan(int days, boolean exist, String name, String description) {
        this.days = days;
        this.exist = exist;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
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

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }
}
