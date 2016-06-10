package com.gymassistant.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gymassistant.DateConverter;
import com.gymassistant.Models.StartedTrainingPlan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KamilH on 2016-05-19.
 */
public class StartedTrainingPlanDB extends SQLiteOpenHelper {

    private final String TABLE_NAME = "StartedTrainingPlan", KEY_ID = "id", NAME = "name", DESCRIPTION = "description", TRAINING_PLAN_ID = "trainingPlanId",
            START_DATE = "startDate", EXPECTED_END_DATE = "expectedEndDate", END_DATE = "endDate";
    private Context context;

    public StartedTrainingPlanDB(Context context) {
        super(context, "StartedTrainingPlan", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, %s TEXT, %s TEXT)",
                        TABLE_NAME, KEY_ID, TRAINING_PLAN_ID, START_DATE, EXPECTED_END_DATE, END_DATE, NAME, DESCRIPTION);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addStartedTrainingPlan(StartedTrainingPlan startedTrainingPlan){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TRAINING_PLAN_ID, startedTrainingPlan.getTrainingPlanId());
        values.put(START_DATE, DateConverter.dateToTime(startedTrainingPlan.getStartDate()));
        values.put(EXPECTED_END_DATE, DateConverter.dateToTime(startedTrainingPlan.getExpectedEndDate()));
        values.put(END_DATE, DateConverter.dateToTime(startedTrainingPlan.getEndDate()));
        values.put(NAME, startedTrainingPlan.getName());
        values.put(DESCRIPTION, startedTrainingPlan.getDescription());

        long rowid = db.insert(TABLE_NAME, null, values);
        db.close();
        return rowid;
    }

    public List<StartedTrainingPlan> getAllStartedTrainingPlans() {
        TrainingPlanDB trainingPlanDB = new TrainingPlanDB(context);
        List<StartedTrainingPlan> startedTrainingPlanList = new ArrayList<StartedTrainingPlan>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                StartedTrainingPlan startedTrainingPlan = new StartedTrainingPlan();
                startedTrainingPlan.setId(cursor.getLong(0));
                startedTrainingPlan.setTrainingPlanId(cursor.getLong(1));
                startedTrainingPlan.setStartDate(DateConverter.timeToDate(cursor.getLong(2)));
                startedTrainingPlan.setExpectedEndDate(DateConverter.timeToDate(cursor.getLong(3)));
                startedTrainingPlan.setEndDate(DateConverter.timeToDate(cursor.getLong(4)));
                startedTrainingPlan.setName(cursor.getString(5));
                startedTrainingPlan.setDescription(cursor.getString(6));
                startedTrainingPlan.setTrainingPlan(trainingPlanDB.getTrainingPlan(startedTrainingPlan.getTrainingPlanId()));

                startedTrainingPlanList.add(startedTrainingPlan);
            } while (cursor.moveToNext());
        }
        db.close();
        return startedTrainingPlanList;
    }

    public List<StartedTrainingPlan> getActiveStartedTrainingPlans() {
        TrainingPlanDB trainingPlanDB = new TrainingPlanDB(context);
        List<StartedTrainingPlan> startedTrainingPlanList = new ArrayList<StartedTrainingPlan>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + END_DATE + "=" + -1;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                StartedTrainingPlan startedTrainingPlan = new StartedTrainingPlan();
                startedTrainingPlan.setId(cursor.getLong(0));
                startedTrainingPlan.setTrainingPlanId(cursor.getLong(1));
                startedTrainingPlan.setStartDate(DateConverter.timeToDate(cursor.getLong(2)));
                startedTrainingPlan.setExpectedEndDate(DateConverter.timeToDate(cursor.getLong(3)));
                startedTrainingPlan.setEndDate(DateConverter.timeToDate(cursor.getLong(4)));
                startedTrainingPlan.setName(cursor.getString(5));
                startedTrainingPlan.setDescription(cursor.getString(6));
                startedTrainingPlan.setTrainingPlan(trainingPlanDB.getTrainingPlan(startedTrainingPlan.getTrainingPlanId()));

                startedTrainingPlanList.add(startedTrainingPlan);
            } while (cursor.moveToNext());
        }
        db.close();
        return startedTrainingPlanList;
    }

    public StartedTrainingPlan getStartedTrainingPlan(long ID){
        TrainingPlanDB trainingPlanDB = new TrainingPlanDB(context);
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + "=" + ID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        StartedTrainingPlan startedTrainingPlan = new StartedTrainingPlan();
        if (cursor.moveToFirst()) {
            startedTrainingPlan.setId(cursor.getLong(0));
            startedTrainingPlan.setTrainingPlanId(cursor.getLong(1));
            startedTrainingPlan.setStartDate(DateConverter.timeToDate(cursor.getLong(2)));
            startedTrainingPlan.setExpectedEndDate(DateConverter.timeToDate(cursor.getLong(3)));
            startedTrainingPlan.setEndDate(DateConverter.timeToDate(cursor.getLong(4)));
            startedTrainingPlan.setName(cursor.getString(5));
            startedTrainingPlan.setDescription(cursor.getString(6));
            startedTrainingPlan.setTrainingPlan(trainingPlanDB.getTrainingPlan(startedTrainingPlan.getTrainingPlanId()));
        }
        db.close();
        return startedTrainingPlan;
    }

    public void updateStartedTrainingPlan(StartedTrainingPlan startedTrainingPlan){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ID, startedTrainingPlan.getId());
        values.put(TRAINING_PLAN_ID, startedTrainingPlan.getTrainingPlanId());
        values.put(START_DATE, DateConverter.dateToTime(startedTrainingPlan.getStartDate()));
        values.put(EXPECTED_END_DATE, DateConverter.dateToTime(startedTrainingPlan.getExpectedEndDate()));
        values.put(END_DATE, DateConverter.dateToTime(startedTrainingPlan.getEndDate()));
        values.put(NAME, startedTrainingPlan.getName());
        values.put(DESCRIPTION, startedTrainingPlan.getDescription());

        db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[]{String.valueOf(startedTrainingPlan.getId())});
    }

    public void deleteStartedTrainingPlan(long id) {
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