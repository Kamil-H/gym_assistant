package com.gymassistant.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.gymassistant.Models.Series;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KamilH on 2016-05-04.
 */
public class SeriesDB extends SQLiteOpenHelper{
    private final String TABLE_NAME = "Series", KEY_ID = "id", TRAINING_ID = "trainingId", EXERCISE_ID = "exerciseId", ORDER = "order_", REPEAT = "repeat", WEIGHT = "weight";
    private Context context;

    public SeriesDB(Context context) {
        super(context, "Series", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("ExerciseDB", "onCreate");
        String CREATE_TABLE =
                String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER)", TABLE_NAME, KEY_ID, TRAINING_ID, EXERCISE_ID, ORDER, REPEAT, WEIGHT);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addSeries(Series series){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TRAINING_ID, series.getTrainingId());
        values.put(EXERCISE_ID, series.getExerciseId());
        values.put(ORDER, series.getOrder());
        values.put(REPEAT, series.getRepeat());
        values.put(WEIGHT, series.getWeight());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void addSeriesList(List<Series> seriesList, int trainingId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(Series series : seriesList){
            values.put(TRAINING_ID, trainingId);
            values.put(EXERCISE_ID, series.getExerciseId());
            values.put(ORDER, series.getOrder());
            values.put(REPEAT, series.getRepeat());
            values.put(WEIGHT, series.getWeight());

            db.insert(TABLE_NAME, null, values);
        }
        db.close();
    }

    public List<Series> getAllSeries() {
        ExerciseDB exerciseDb = new ExerciseDB(context);
        List<Series> seriesList = new ArrayList<Series>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Series series = new Series();
                series.setId(cursor.getInt(0));
                series.setTrainingId(cursor.getInt(1));
                series.setExerciseId(cursor.getInt(2));
                series.setOrder(cursor.getInt(3));
                series.setRepeat(cursor.getInt(4));
                series.setWeight(cursor.getInt(5));
                series.setExercise(exerciseDb.getExercise(series.getExerciseId()));

                seriesList.add(series);
            } while (cursor.moveToNext());
        }
        db.close();
        return seriesList;
    }

    public List<Series> getSeriesByTrainingId(int trainingId) {
        ExerciseDB exerciseDb = new ExerciseDB(context);
        List<Series> seriesList = new ArrayList<Series>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + TRAINING_ID + " = " + trainingId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Series series = new Series();
                series.setId(cursor.getInt(0));
                series.setTrainingId(cursor.getInt(1));
                series.setExerciseId(cursor.getInt(2));
                series.setOrder(cursor.getInt(3));
                series.setRepeat(cursor.getInt(4));
                series.setWeight(cursor.getInt(5));
                series.setExercise(exerciseDb.getExercise(series.getExerciseId()));

                seriesList.add(series);
            } while (cursor.moveToNext());
        }
        db.close();
        return seriesList;
    }

    public Series getSeries(int ID){
        ExerciseDB exerciseDb = new ExerciseDB(context);
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + "=" + ID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Series series = new Series();
        if (cursor.moveToFirst()) {
            series.setId(cursor.getInt(0));
            series.setTrainingId(cursor.getInt(1));
            series.setExerciseId(cursor.getInt(2));
            series.setOrder(cursor.getInt(3));
            series.setRepeat(cursor.getInt(4));
            series.setWeight(cursor.getInt(5));
            series.setExercise(exerciseDb.getExercise(series.getExerciseId()));
        }
        db.close();
        return series;
    }

    public void deleteSeries(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void removeAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }

    public long getRowCount() {
        return DatabaseUtils.queryNumEntries(getReadableDatabase(), TABLE_NAME);
    }
}
