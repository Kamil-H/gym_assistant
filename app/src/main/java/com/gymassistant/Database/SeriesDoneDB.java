package com.gymassistant.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.gymassistant.Models.SeriesDone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KamilH on 2016-05-19.
 */
public class SeriesDoneDB extends SQLiteOpenHelper {
    private final String TABLE_NAME = "SeriesDone", KEY_ID = "id", TRAINING_DONE_ID = "trainingDoneId", EXERCISE_ID = "exerciseId", ORDER = "order_", REPEAT = "repeat",
            WEIGHT = "weight", TRAINING_ID = "trainingId", IS_SAVED = "isSaved";
    private Context context;

    public SeriesDoneDB(Context context) {
        super(context, "SeriesDone", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, %s REAL, %s INTEGER, %s INTEGER)",
                        TABLE_NAME, KEY_ID, TRAINING_DONE_ID, EXERCISE_ID, ORDER, REPEAT, WEIGHT, TRAINING_ID, IS_SAVED);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addSeriesDoneList(List<SeriesDone> seriesDoneList, long trainingDoneId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(SeriesDone seriesDone : seriesDoneList){
            values.put(TRAINING_DONE_ID, trainingDoneId);
            values.put(EXERCISE_ID, seriesDone.getExerciseId());
            values.put(ORDER, seriesDone.getOrder());
            values.put(REPEAT, seriesDone.getRepeat());
            values.put(WEIGHT, seriesDone.getWeight());
            values.put(TRAINING_ID, seriesDone.getTrainingId());
            values.put(IS_SAVED, getIntFromBoolean(seriesDone.isSaved()));

            db.insert(TABLE_NAME, null, values);
        }
        db.close();
    }

    public List<SeriesDone> getSeriesDoneByTrainingDoneId(long trainingDoneId) {
        ExerciseDB exerciseDb = new ExerciseDB(context);
        List<SeriesDone> seriesDoneList = new ArrayList<SeriesDone>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + TRAINING_DONE_ID + " = " + trainingDoneId + " AND " + IS_SAVED + " = " + 1 + " ORDER BY " + EXERCISE_ID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                SeriesDone seriesDone = new SeriesDone();
                seriesDone.setId(cursor.getLong(0));
                seriesDone.setTrainingDoneId(cursor.getLong(1));
                seriesDone.setExerciseId(cursor.getLong(2));
                seriesDone.setOrder(cursor.getInt(3));
                seriesDone.setRepeat(cursor.getInt(4));
                seriesDone.setWeight(cursor.getDouble(5));
                seriesDone.setTrainingId(cursor.getLong(6));
                seriesDone.setSaved(getBooleanFromInt(cursor.getInt(7)));
                seriesDone.setExercise(exerciseDb.getExercise(seriesDone.getExerciseId()));

                seriesDoneList.add(seriesDone);
            } while (cursor.moveToNext());
        }
        db.close();
        return seriesDoneList;
    }

    public List<SeriesDone> getLastSeriesDoneByTrainingId(long trainingId) {
        ExerciseDB exerciseDb = new ExerciseDB(context);
        List<SeriesDone> seriesDoneList = new ArrayList<SeriesDone>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + TRAINING_ID + " = " + trainingId + " AND " + TRAINING_DONE_ID + " IN " + "(SELECT MAX(" + TRAINING_DONE_ID + ") FROM " + TABLE_NAME + " WHERE " + TRAINING_ID + " = " + trainingId + ")";
        Log.i("QUERY:", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                SeriesDone seriesDone = new SeriesDone();
                seriesDone.setId(cursor.getLong(0));
                seriesDone.setTrainingDoneId(cursor.getLong(1));
                seriesDone.setExerciseId(cursor.getLong(2));
                seriesDone.setOrder(cursor.getInt(3));
                seriesDone.setRepeat(cursor.getInt(4));
                seriesDone.setWeight(cursor.getDouble(5));
                seriesDone.setTrainingId(cursor.getLong(6));
                seriesDone.setSaved(getBooleanFromInt(cursor.getInt(7)));
                seriesDone.setExercise(exerciseDb.getExercise(seriesDone.getExerciseId()));

                seriesDoneList.add(seriesDone);
            } while (cursor.moveToNext());
        }
        db.close();
        return seriesDoneList;
    }

    public void removeAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }

    public long getRowCount() {
        return DatabaseUtils.queryNumEntries(getReadableDatabase(), TABLE_NAME);
    }

    private int getIntFromBoolean(boolean value){
        return value ? 1 : 0;
    }

    private boolean getBooleanFromInt(int value){
        return value == 1;
    }
}
