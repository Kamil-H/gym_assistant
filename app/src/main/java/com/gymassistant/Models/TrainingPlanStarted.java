package com.gymassistant.Models;

import org.joda.time.DateTime;

/**
 * Created by KamilH on 2016-05-04.
 */
public class TrainingPlanStarted {
    private int id;
    private String name;
    private String description;
    private int owner;
    private int source;
    private DateTime startDate;
    private DateTime expectedEndDate;
    private DateTime endDate;
    private int firstDay;
    private boolean isPublic;

    TrainingPlanStarted(){}

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

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(DateTime expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
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
}
