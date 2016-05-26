package com.gymassistant.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gymassistant.Models.SeriesDone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KamilH on 2016-05-19.
 */
public class SeriesDoneDB extends SQLiteOpenHelper {
    private final String TABLE_NAME = "SeriesDone", KEY_ID = "id", TRAINING_DONE_ID = "trainingDoneId", EXERCISE_ID = "exerciseId", ACTUAL_ORDER = "actualOrder", ACTUAL_REPEAT = "actualRepeat",
            ACTUAL_WEIGHT = "actualWeight", PLANNED_ORDER = "plannedOrder", PLANNED_REPEAT = "plannedRepeat", PLANED_WEIGHT = "plannedWeight";
    private Context context;

    public SeriesDoneDB(Context context) {
        super(context, "SeriesDone", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER)",
                        TABLE_NAME, KEY_ID, TRAINING_DONE_ID, EXERCISE_ID, ACTUAL_ORDER, ACTUAL_REPEAT, ACTUAL_WEIGHT, PLANNED_ORDER, PLANNED_REPEAT, PLANED_WEIGHT);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addSeriesDone(SeriesDone seriesDone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TRAINING_DONE_ID, seriesDone.getTrainingDoneId());
        values.put(EXERCISE_ID, seriesDone.getExerciseId());
        values.put(ACTUAL_ORDER, seriesDone.getActualOrder());
        values.put(ACTUAL_REPEAT, seriesDone.getActualRepeat());
        values.put(ACTUAL_WEIGHT, seriesDone.getActualWeight());
        values.put(PLANNED_ORDER, seriesDone.getPlannedOrder());
        values.put(PLANNED_REPEAT, seriesDone.getPlannedRepeat());
        values.put(PLANED_WEIGHT, seriesDone.getPlannedWeight());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void addSeriesDoneList(List<SeriesDone> seriesDoneList, long traningDoneId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(SeriesDone seriesDone : seriesDoneList){
            values.put(TRAINING_DONE_ID, traningDoneId);
            values.put(EXERCISE_ID, seriesDone.getExerciseId());
            values.put(ACTUAL_ORDER, seriesDone.getActualOrder());
            values.put(ACTUAL_REPEAT, seriesDone.getActualRepeat());
            values.put(ACTUAL_WEIGHT, seriesDone.getActualWeight());
            values.put(PLANNED_ORDER, seriesDone.getPlannedOrder());
            values.put(PLANNED_REPEAT, seriesDone.getPlannedRepeat());
            values.put(PLANED_WEIGHT, seriesDone.getPlannedWeight());

            db.insert(TABLE_NAME, null, values);
        }
        db.close();
    }

    public List<SeriesDone> getSeriesDoneByTrainingDoneId(int trainingDoneId) {
        ExerciseDB exerciseDb = new ExerciseDB(context);
        List<SeriesDone> seriesDoneList = new ArrayList<SeriesDone>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + TRAINING_DONE_ID + " = " + trainingDoneId  + " ORDER BY " + EXERCISE_ID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                SeriesDone seriesDone = new SeriesDone();
                seriesDone.setId(cursor.getInt(0));
                seriesDone.setTrainingDoneId(cursor.getInt(1));
                seriesDone.setExerciseId(cursor.getInt(2));
                seriesDone.setActualOrder(cursor.getInt(3));
                seriesDone.setActualRepeat(cursor.getInt(4));
                seriesDone.setActualWeight(cursor.getInt(5));
                seriesDone.setPlannedOrder(cursor.getInt(6));
                seriesDone.setPlannedRepeat(cursor.getInt(7));
                seriesDone.setPlannedWeight(cursor.getInt(8));
                seriesDone.setExercise(exerciseDb.getExercise(seriesDone.getExerciseId()));

                seriesDoneList.add(seriesDone);
            } while (cursor.moveToNext());
        }
        db.close();
        return seriesDoneList;
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
