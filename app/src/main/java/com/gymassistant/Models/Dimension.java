package com.gymassistant.Models;

/**
 * Created by KamilH on 2016-05-02.
 */
public class Dimension {
    private long id;
    private double value;
    private String addedDate;
    private long typeKey;
    private DimensionType type;

    public Dimension(){}

    public Dimension(double value, String addedDate, long typeKey) {
        this.value = value;
        this.addedDate = addedDate;
        this.typeKey = typeKey;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public long getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(long typeKey) {
        this.typeKey = typeKey;
    }

    public DimensionType getType() {
        return type;
    }

    public void setType(DimensionType type) {
        this.type = type;
    }
}
