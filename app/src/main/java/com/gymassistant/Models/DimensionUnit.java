package com.gymassistant.Models;

/**
 * Created by KamilH on 2016-05-02.
 */
public class DimensionUnit {
    private int id;
    private String shortName;
    private String name;

    public DimensionUnit(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
