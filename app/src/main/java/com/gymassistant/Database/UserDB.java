package com.gymassistant.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gymassistant.DateConverter;
import com.gymassistant.Models.User;

/**
 * Created by KamilH on 2016-05-10.
 */
public class UserDB extends SQLiteOpenHelper {
    private final String TABLE_NAME = "User", KEY_ID = "id", BIRTHDAY = "birthday", GENDER = "gender", HEIGHT = "height", WEIGHT = "weight";

    public UserDB(Context context) {
        super(context, "User", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s TEXT, %s REAL, %s REAL)",
                        TABLE_NAME, KEY_ID, BIRTHDAY, GENDER, HEIGHT, WEIGHT);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BIRTHDAY, DateConverter.dateToTime(user.getBirthday()));
        values.put(GENDER, user.getGender());
        values.put(HEIGHT, user.getHeight());
        values.put(WEIGHT, user.getWeight());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public User getUser(){
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        User user = new User();
        if (cursor.moveToFirst()) {
            user.setId(cursor.getLong(0));
            user.setBirthday(DateConverter.timeToDate(cursor.getLong(1)));
            user.setGender(cursor.getString(2));
            user.setHeight(cursor.getDouble(3));
            user.setWeight(cursor.getDouble(4));
        }
        db.close();
        return user;
    }

    public void updateWeight(double weight){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WEIGHT, weight);
        db.update(TABLE_NAME, values, KEY_ID + "= ?", new String[] {String.valueOf(1)});
        db.close();
    }

    public void updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BIRTHDAY, DateConverter.dateToTime(user.getBirthday()));
        values.put(GENDER, user.getGender());
        values.put(HEIGHT, user.getHeight());
        values.put(WEIGHT, user.getWeight());
        db.update(TABLE_NAME, values, KEY_ID + "= ?", new String[] {String.valueOf(1)});
        db.close();
    }

    public void deleteUser(int id) {
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

