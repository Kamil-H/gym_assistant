package com.gymassistant.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gymassistant.DateConverter;
import com.gymassistant.Models.TrainingDone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KamilH on 2016-05-19.
 */
public class TrainingDoneDB extends SQLiteOpenHelper {
    private final String TABLE_NAME = "TrainingDone", KEY_ID = "id", STARTED_TRAINING_PLN = "startedTrainingPlan", DAY = "day", DATE = "date";
    private Context context;

    public TrainingDoneDB(Context context) {
        super(context, "TrainingDone", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s INTEGER, %s INTEGER)",
                        TABLE_NAME, KEY_ID, STARTED_TRAINING_PLN, DAY, DATE);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addTrainingDone(TrainingDone trainingDone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(STARTED_TRAINING_PLN, trainingDone.getStartedTrainingPlan());
        values.put(DAY, trainingDone.getDay());

        values.put(DATE, DateConverter.dateToTime(trainingDone.getDate()));

        long rowid = db.insert(TABLE_NAME, null, values);
        db.close();
        return rowid;
    }

    public List<TrainingDone> getAllTrainingsDone() {
        SeriesDoneDB seriesDoneDB = new SeriesDoneDB(context);
        List<TrainingDone> trainingDones = new ArrayList<TrainingDone>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                TrainingDone trainingDone = new TrainingDone();
                trainingDone.setId(cursor.getInt(0));
                trainingDone.setStartedTrainingPlan(cursor.getInt(1));
                trainingDone.setDay(cursor.getInt(2));
                trainingDone.setDate(DateConverter.timeToDate(cursor.getLong(3)));
                trainingDone.setSeriesDoneList(seriesDoneDB.getSeriesDoneByTrainingDoneId(trainingDone.getId()));

                trainingDones.add(trainingDone);
            } while (cursor.moveToNext());
        }
        db.close();
        return trainingDones;
    }

    public TrainingDone getTrainingDone(int ID){
        SeriesDoneDB seriesDoneDB = new SeriesDoneDB(context);
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + "=" + ID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        TrainingDone trainingDone = new TrainingDone();
        if (cursor.moveToFirst()) {
            trainingDone.setId(cursor.getInt(0));
            trainingDone.setStartedTrainingPlan(cursor.getInt(1));
            trainingDone.setDay(cursor.getInt(2));
            trainingDone.setDate(DateConverter.timeToDate(cursor.getLong(3)));
            trainingDone.setSeriesDoneList(seriesDoneDB.getSeriesDoneByTrainingDoneId(trainingDone.getId()));
        }
        db.close();
        return trainingDone;
    }

    public void deleteTrainingDone(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void removeAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }

    public long getRowCount() {
        return DatabaseUtils.queryNumEntries(getReadableDatabase(), TABLE_NAME);
    }

}
