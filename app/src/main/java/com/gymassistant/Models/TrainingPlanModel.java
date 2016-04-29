package com.gymassistant.Models;

/**
 * Created by KamilH on 2016-04-21.
 */
public class TrainingPlanModel {
    private String dayName;

    public TrainingPlanModel(String dayName) {
        this.dayName = dayName;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }
}
