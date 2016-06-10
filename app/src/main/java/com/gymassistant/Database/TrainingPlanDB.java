package com.gymassistant.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gymassistant.Models.TrainingPlan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KamilH on 2016-05-04.
 */
public class TrainingPlanDB extends SQLiteOpenHelper{

    private final String TABLE_NAME = "TrainingPlan", KEY_ID = "id", DAYS = "days", NAME = "name", DESCRIPTION = "description", EXIST = "exist";
    private Context context;

    public TrainingPlanDB(Context context) {
        super(context, "TrainingPlan", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s TEXT, %s TEXT, %s INTEGER)",
                        TABLE_NAME, KEY_ID, DAYS, NAME, DESCRIPTION, EXIST);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addTrainingPlan(TrainingPlan trainingPlan){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DAYS, trainingPlan.getDays());
        values.put(NAME, trainingPlan.getName());
        values.put(DESCRIPTION, trainingPlan.getDescription());
        values.put(EXIST, getIntFromBoolean(trainingPlan.isExist()));

        long rowid = db.insert(TABLE_NAME, null, values);
        db.close();
        return rowid;
    }

    public List<TrainingPlan> getAllTrainingPlans() {
        List<TrainingPlan> trainingPlans = new ArrayList<TrainingPlan>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                TrainingPlan trainingPlan = new TrainingPlan();
                trainingPlan.setId(cursor.getLong(0));
                trainingPlan.setDays(cursor.getInt(1));
                trainingPlan.setName(cursor.getString(2));
                trainingPlan.setDescription(cursor.getString(3));
                trainingPlan.setExist(getBooleanFromInt(cursor.getInt(4)));

                trainingPlans.add(trainingPlan);
            } while (cursor.moveToNext());
        }
        db.close();
        return trainingPlans;
    }

    public List<TrainingPlan> getExistingTrainingPlans() {
        List<TrainingPlan> trainingPlans = new ArrayList<TrainingPlan>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + EXIST + "=" + 1;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                TrainingPlan trainingPlan = new TrainingPlan();
                trainingPlan.setId(cursor.getLong(0));
                trainingPlan.setDays(cursor.getInt(1));
                trainingPlan.setName(cursor.getString(2));
                trainingPlan.setDescription(cursor.getString(3));
                trainingPlan.setExist(getBooleanFromInt(cursor.getInt(4)));

                trainingPlans.add(trainingPlan);
            } while (cursor.moveToNext());
        }
        db.close();
        return trainingPlans;
    }

    public TrainingPlan getTrainingPlan(long ID){
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + "=" + ID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        TrainingPlan trainingPlan = new TrainingPlan();
        if (cursor.moveToFirst()) {
            trainingPlan.setId(cursor.getLong(0));
            trainingPlan.setDays(cursor.getInt(1));
            trainingPlan.setName(cursor.getString(2));
            trainingPlan.setDescription(cursor.getString(3));
            trainingPlan.setExist(getBooleanFromInt(cursor.getInt(4)));
        }
        db.close();
        return trainingPlan;
    }

    private int getIntFromBoolean(boolean value){
        return value ? 1 : 0;
    }

    private boolean getBooleanFromInt(int value){
        return value == 1;
    }

    public void deleteTrainingPlan(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(EXIST, getIntFromBoolean(false));
        db.update(TABLE_NAME, cv, KEY_ID + "= ?", new String[] {String.valueOf(id)});
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
