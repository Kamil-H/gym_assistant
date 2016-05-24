package com.gymassistant.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gymassistant.Models.Training;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KamilH on 2016-05-04.
 */
public class TrainingDB extends SQLiteOpenHelper {
    private final String TABLE_NAME = "Training", KEY_ID = "id", TRAINING_PLAN_ID = "trainingPlanId", DAY = "day", DESCRIPTION = "description";
    private Context context;

    public TrainingDB(Context context) {
        super(context, "Training", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s INTEGER, %s TEXT)",
                        TABLE_NAME, KEY_ID, TRAINING_PLAN_ID, DAY, DESCRIPTION);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addTraining(Training training){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TRAINING_PLAN_ID, training.getTrainingPlanId());
        values.put(DAY, training.getDay());
        values.put(DESCRIPTION, training.getDescription());

        long rowid = db.insert(TABLE_NAME, null, values);
        db.close();
        return rowid;
    }

    public void addTrainingList(List<Training> trainings){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(Training training : trainings){
            values.put(TRAINING_PLAN_ID, training.getTrainingPlanId());
            values.put(DAY, training.getDay());
            values.put(DESCRIPTION, training.getDescription());

            db.insert(TABLE_NAME, null, values);
        }
        db.close();
    }

    public List<Training> getAllTrainings() {
        TrainingPlanDB trainingPlanDB = new TrainingPlanDB(context);
        SeriesDB seriesDB = new SeriesDB(context);
        List<Training> trainings = new ArrayList<Training>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Training training = new Training();
                training.setId(cursor.getInt(0));
                training.setTrainingPlanId(cursor.getInt(1));
                training.setDay(cursor.getInt(2));
                training.setDescription(cursor.getString(3));
                training.setTrainingPlan(trainingPlanDB.getTrainingPlan(training.getTrainingPlanId()));
                training.setSeriesList(seriesDB.getSeriesByTrainingId(training.getId()));

                trainings.add(training);
            } while (cursor.moveToNext());
        }
        db.close();
        return trainings;
    }

    public List<Training> getTrainingsByTraningPlanId(int ID) {
        TrainingPlanDB trainingPlanDB = new TrainingPlanDB(context);
        SeriesDB seriesDB = new SeriesDB(context);
        List<Training> trainings = new ArrayList<Training>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + TRAINING_PLAN_ID + "=" + ID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Training training = new Training();
                training.setId(cursor.getInt(0));
                training.setTrainingPlanId(cursor.getInt(1));
                training.setDay(cursor.getInt(2));
                training.setDescription(cursor.getString(3));
                training.setTrainingPlan(trainingPlanDB.getTrainingPlan(training.getTrainingPlanId()));
                training.setSeriesList(seriesDB.getSeriesByTrainingId(training.getId()));

                trainings.add(training);
            } while (cursor.moveToNext());
        }
        db.close();
        return trainings;
    }

    public Training getTraining(int ID){
        TrainingPlanDB trainingPlanDB = new TrainingPlanDB(context);
        SeriesDB seriesDB = new SeriesDB(context);
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + "=" + ID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Training training = new Training();
        if (cursor.moveToFirst()) {
            training.setId(cursor.getInt(0));
            training.setTrainingPlanId(cursor.getInt(1));
            training.setDay(cursor.getInt(2));
            training.setDescription(cursor.getString(3));
            training.setTrainingPlan(trainingPlanDB.getTrainingPlan(training.getTrainingPlanId()));
            training.setSeriesList(seriesDB.getSeriesByTrainingId(training.getId()));
        }
        db.close();
        return training;
    }

    public void deleteTraining(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public List<Integer> getTrainingIdsByTraningPlanId(long ID){
        List<Integer> traningIds = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + TRAINING_PLAN_ID + "=" + ID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                traningIds.add(cursor.getInt(1));
            } while (cursor.moveToNext());
        }
        db.close();
        return traningIds;
    }

    public int deleteTrainingByTrainingPlanId(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int count = db.delete(TABLE_NAME, TRAINING_PLAN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();

        return count;
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
