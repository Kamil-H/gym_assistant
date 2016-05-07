package com.gymassistant.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.gymassistant.Models.TrainingPlan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KamilH on 2016-05-04.
 */
public class TrainingPlanDB extends SQLiteOpenHelper{

    private final String TABLE_NAME = "TrainingPlan", KEY_ID = "id", DAYS = "days", OWNER = "owner", IS_PUBLIC = "isPublic", NAME = "name", DESCRIPTION = "description";
    private Context context;

    public TrainingPlanDB(Context context) {
        super(context, "TrainingPlan", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("ExerciseDB", "onCreate");
        String CREATE_TABLE =
                String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s INTEGER, %s INTEGER, %s TEXT, %s TEXT)", TABLE_NAME, KEY_ID, DAYS, OWNER, IS_PUBLIC, NAME, DESCRIPTION);
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
        values.put(OWNER, trainingPlan.getOwner());
        values.put(NAME, trainingPlan.getName());
        values.put(DESCRIPTION, trainingPlan.getDescription());

        if(trainingPlan.isPublic())
            values.put(IS_PUBLIC, 1);
        else values.put(IS_PUBLIC, 0);

        long rowid = db.insert(TABLE_NAME, null, values);
        db.close();
        return rowid;
    }

    public void addTrainingPlanList(List<TrainingPlan> trainingPlans){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(TrainingPlan trainingPlan : trainingPlans){
            values.put(DAYS, trainingPlan.getDays());
            values.put(OWNER, trainingPlan.getOwner());
            values.put(NAME, trainingPlan.getName());
            values.put(DESCRIPTION, trainingPlan.getDescription());

            if(trainingPlan.isPublic())
                values.put(IS_PUBLIC, 1);
            else values.put(IS_PUBLIC, 0);

            db.insert(TABLE_NAME, null, values);
        }
        db.close();
    }

    public List<TrainingPlan> getAllTrainingPlans() {
        List<TrainingPlan> trainingPlans = new ArrayList<TrainingPlan>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                TrainingPlan trainingPlan = new TrainingPlan();
                trainingPlan.setId(cursor.getInt(0));
                trainingPlan.setDays(cursor.getInt(1));
                trainingPlan.setOwner(cursor.getInt(2));
                trainingPlan.setName(cursor.getString(4));
                trainingPlan.setDescription(cursor.getString(5));
                if(cursor.getInt(3) == 0)
                    trainingPlan.setPublic(false);
                else trainingPlan.setPublic(true);

                trainingPlans.add(trainingPlan);
            } while (cursor.moveToNext());
        }
        db.close();
        return trainingPlans;
    }

    public TrainingPlan getTrainingPlan(int ID){
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + "=" + ID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        TrainingPlan trainingPlan = new TrainingPlan();
        if (cursor.moveToFirst()) {
            trainingPlan.setId(cursor.getInt(0));
            trainingPlan.setDays(cursor.getInt(1));
            trainingPlan.setOwner(cursor.getInt(2));
            trainingPlan.setName(cursor.getString(4));
            trainingPlan.setDescription(cursor.getString(5));
            if(cursor.getInt(3) == 0)
                trainingPlan.setPublic(false);
            else trainingPlan.setPublic(true);
        }
        db.close();
        return trainingPlan;
    }

    public void deleteTrainingPlan(long id) {
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
