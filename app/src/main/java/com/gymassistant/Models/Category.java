package com.gymassistant.Models;

import com.google.gson.annotations.SerializedName;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

/**
 * Created by KamilH on 2016-03-22.
 */
public class Category implements ParentListItem{
    @SerializedName("category")
    private String category;
    @SerializedName("Icon")
    private String Icon;

    @Override
    public String toString() {
        return "Category{" +
                "category='" + category + '\'' +
                ", Icon='" + Icon + '\'' +
                ", exercise=" + exercise +
                '}';
    }

    private List<Exercise> exercise;

    public Category(String category, List<Exercise> exercise){
        this.category = category;
        this.exercise = exercise;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }


    @Override
    public List<?> getChildItemList() {
        return exercise;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
