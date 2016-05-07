package com.gymassistant.Models;

import com.gymassistant.DateConverter;

import org.joda.time.DateTime;

/**
 * Created by KamilH on 2016-05-02.
 */
public class Dimension {
    private int id;
    private int user;
    private DimensionUnit unit;
    private int unitKey;
    private int type;
    private double value;
    private DateTime addedDate;

    public Dimension(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public DimensionUnit getUnit() {
        return unit;
    }

    public void setUnit(DimensionUnit unit) {
        this.unit = unit;
    }

    public int getUnitKey() {
        return unitKey;
    }

    public void setUnitKey(int unitKey) {
        this.unitKey = unitKey;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public DateTime getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(DateTime addedDate) {
        this.addedDate = addedDate;
    }

    public void setAddedDate(long time) {
        this.addedDate = DateConverter.timeToDate(time);
    }

    @Override
    public String toString() {
        return "Dimension{" +
                "id=" + id +
                ", user=" + user +
                ", unit=" + unit +
                ", unitKey=" + unitKey +
                ", type=" + type +
                ", value=" + value +
                ", addedDate=" + addedDate +
                '}';
    }
}