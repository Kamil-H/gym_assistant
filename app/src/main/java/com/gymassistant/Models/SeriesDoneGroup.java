package com.gymassistant.Models;
import java.util.List;
/**
 * Created by KamilH on 2016-05-26.
 */
public class SeriesDoneGroup {
    private List<SeriesDone> seriesDoneList;
    private Exercise exercise;

    public SeriesDoneGroup(List<SeriesDone> seriesDoneList, Exercise exercise) {
        this.seriesDoneList = seriesDoneList;
        this.exercise = exercise;
    }

    public List<SeriesDone> getSeriesDoneList() {
        return seriesDoneList;
    }

    public void setSeriesDoneList(List<SeriesDone> seriesDoneList) {
        this.seriesDoneList = seriesDoneList;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}
