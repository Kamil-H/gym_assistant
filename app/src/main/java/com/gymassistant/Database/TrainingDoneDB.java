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
    private final String TABLE_NAME = "TrainingDone", KEY_ID = "id", STARTED_TRAINING_PLN = "startedTrainingPlan", DAY = "day", DATE = "date", TIME = "time";
    private Context context;

    public TrainingDoneDB(Context context) {
        super(context, "TrainingDone", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER)",
                        TABLE_NAME, KEY_ID, STARTED_TRAINING_PLN, DAY, DATE, TIME);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addTrainingDone(TrainingDone trainingDone, int time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(STARTED_TRAINING_PLN, trainingDone.getStartedTrainingPlanId());
        values.put(DAY, trainingDone.getDay());
        values.put(DATE, DateConverter.dateToTime(trainingDone.getDate()));
        values.put(TIME, time);

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
                trainingDone.setId(cursor.getLong(0));
                trainingDone.setStartedTrainingPlanId(cursor.getLong(1));
                trainingDone.setDay(cursor.getInt(2));
                trainingDone.setDate(DateConverter.timeToDate(cursor.getLong(3)));
                trainingDone.setTime(DateConverter.timeConversion(cursor.getInt(4)));
                trainingDone.setSeriesDoneList(seriesDoneDB.getSeriesDoneByTrainingDoneId(trainingDone.getId()));

                trainingDones.add(trainingDone);
            } while (cursor.moveToNext());
        }
        db.close();
        return trainingDones;
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
