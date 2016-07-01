package com.gymassistant.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gymassistant.Models.Category;
import com.gymassistant.Models.Exercise;
import com.gymassistant.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KamilH on 2016-05-03.
 */
public class ExerciseDB extends SQLiteOpenHelper {
    private final String TABLE_NAME = "Exercise", KEY_ID = "id", CATEGORY = "category", NAME = "name", IMG_1 = "img1", IMG_2 = "img2", IMG_3 = "img3",
    VIDEO = "video", MAIN_MUSCLES = "main_muscles", AUX_MUSCLES = "aux_muscles", STABILIZERS = "stabilizers", HOW_TO = "how_to",
            SECOND_NAME = "second_name", ATTENTIONS = "attentions", IS_FAVORITE = "is_favorite";
    private Context context;

    public ExerciseDB(Context context) {
        super(context, "Exercise", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER)",
                        TABLE_NAME, KEY_ID, CATEGORY, NAME, SECOND_NAME, IMG_1, IMG_2, IMG_3, VIDEO, MAIN_MUSCLES, AUX_MUSCLES, STABILIZERS, HOW_TO, ATTENTIONS, IS_FAVORITE);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void populateExerciseDB(){
        List<Exercise> exercises = getExercises();
        addExercisesList(exercises);
    }

    private List<Exercise> getExercises(){
        BufferedReader in = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.exercises)));
        Gson gson = new GsonBuilder().create();
        Exercise exer = gson.fromJson(in, Exercise.class);
        return exer.getExercises();
    }

    public void addExercisesList(List<Exercise> exercises){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(Exercise exercise : exercises){
            values.put(CATEGORY, exercise.getCategory());
            values.put(NAME, exercise.getName());
            values.put(IMG_1, exercise.getImg1());
            values.put(IMG_2, exercise.getImg2());
            values.put(IMG_3, exercise.getImg3());
            values.put(VIDEO, exercise.getVideo());
            values.put(MAIN_MUSCLES, exercise.getMainMuscles());
            values.put(AUX_MUSCLES, exercise.getAuxMuscles());
            values.put(STABILIZERS, exercise.getStabilizers());
            values.put(HOW_TO, exercise.getHowTo());
            values.put(SECOND_NAME, exercise.getSecondName());
            values.put(ATTENTIONS, exercise.getAttentions());
            values.put(IS_FAVORITE, getIntFromBoolean(false));

            db.insert(TABLE_NAME, null, values);
        }
        db.close();
    }

    public List<Exercise> getAllExercises() {
        List<Exercise> exercises = new ArrayList<Exercise>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Exercise exercise = new Exercise();
                exercise.setId(cursor.getLong(0));
                exercise.setCategory(cursor.getString(1));
                exercise.setName(cursor.getString(2));
                exercise.setSecondName(cursor.getString(3));
                exercise.setImg1(cursor.getString(4));
                exercise.setImg2(cursor.getString(5));
                exercise.setImg3(cursor.getString(6));
                exercise.setVideo(cursor.getString(7));
                exercise.setMainMuscles(cursor.getString(8));
                exercise.setAuxMuscles(cursor.getString(9));
                exercise.setStabilizers(cursor.getString(10));
                exercise.setHowTo(cursor.getString(11));
                exercise.setAttentions(cursor.getString(12));
                exercise.setFavorite(getBooleanFromInt(cursor.getInt(13)));

                exercises.add(exercise);
            } while (cursor.moveToNext());
        }
        db.close();
        return exercises;
    }

    public Exercise getExercise(long ID){
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + "=" + ID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Exercise exercise = new Exercise();
        if (cursor.moveToFirst()) {
            exercise.setId(cursor.getLong(0));
            exercise.setCategory(cursor.getString(1));
            exercise.setName(cursor.getString(2));
            exercise.setSecondName(cursor.getString(3));
            exercise.setImg1(cursor.getString(4));
            exercise.setImg2(cursor.getString(5));
            exercise.setImg3(cursor.getString(6));
            exercise.setVideo(cursor.getString(7));
            exercise.setMainMuscles(cursor.getString(8));
            exercise.setAuxMuscles(cursor.getString(9));
            exercise.setStabilizers(cursor.getString(10));
            exercise.setHowTo(cursor.getString(11));
            exercise.setAttentions(cursor.getString(12));
            exercise.setFavorite(getBooleanFromInt(cursor.getInt(13)));
        }
        db.close();
        return exercise;
    }

    public void setFavorite(long id, boolean isFavorite){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IS_FAVORITE, isFavorite);
        db.update(TABLE_NAME, values, KEY_ID + "= ?", new String[] {String.valueOf(id)});
        db.close();
    }

    public List<Category> getCategories(){
        List<Category> categories = new ArrayList<Category>();
        String selectQuery = "SELECT DISTINCT " + CATEGORY + " FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category(cursor.getString(0), null);
                categories.add(category);
            } while (cursor.moveToNext());
        }
        db.close();
        return categories;
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
