package com.gymassistant.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KamilH on 2016-03-22.
 */
public class Exercise {
    @SerializedName("category")
    private String category;
    @SerializedName("name")
    private String name;
    @SerializedName("img_1")
    private String img1;
    @SerializedName("img_2")
    private String img2;
    @SerializedName("img_3")
    private String img3;
    @SerializedName("img_muscle")
    private String imgMuscle;
    @SerializedName("video")
    private String video;
    @SerializedName("main_muscles")
    private String mainMuscles;
    @SerializedName("aux_muscles")
    private String auxMuscles;
    @SerializedName("stabilizers")
    private String stabilizers;
    @SerializedName("how_to")
    private String howTo;
    @SerializedName("attentions")
    private String attentions;
    @SerializedName("second_name")
    private String secondName;
    private int id;

    @SerializedName("Exercises")
    private List<Exercise> exercises = new ArrayList<Exercise>();

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getImgMuscle() {
        return imgMuscle;
    }

    public void setImgMuscle(String imgMuscle) {
        this.imgMuscle = imgMuscle;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getMainMuscles() {
        return mainMuscles;
    }

    public void setMainMuscles(String mainMuscles) {
        this.mainMuscles = mainMuscles;
    }

    public String getAuxMuscles() {
        return auxMuscles;
    }

    public void setAuxMuscles(String auxMuscles) {
        this.auxMuscles = auxMuscles;
    }

    public String getStabilizers() {
        return stabilizers;
    }

    public void setStabilizers(String stabilizers) {
        this.stabilizers = stabilizers;
    }

    public String getHowTo() {
        return howTo;
    }

    public void setHowTo(String howTo) {
        this.howTo = howTo;
    }

    public String getAttentions() {
        return attentions;
    }

    public void setAttentions(String attentions) {
        this.attentions = attentions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.name  + " " + secondName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
}
