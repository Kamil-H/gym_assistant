package com.gymassistant.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.gymassistant.DateConverter;
import com.gymassistant.Models.Series;
import com.gymassistant.Models.User;

/**
 * Created by KamilH on 2016-05-10.
 */
public class UserDB extends SQLiteOpenHelper {
    private final String TABLE_NAME = "User", KEY_ID = "id", USER_ID = "userId", ADDED_DATE = "addedDate", BIRTHDAY = "birthday", EMAIL = "EMAIL", FIRSTNAME = "firstName", GENDER = "gender", PASSWORD = "password", SURNAME = "surname", USERNAME = "userName", HEIGHT = "height", WEIGHT = "weight";
    private Context context;

    public UserDB(Context context) {
        super(context, "User", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("ExerciseDB", "onCreate");
        String CREATE_TABLE =
                String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s INTEGER, %s INTEGER, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s REAL, %s REAL)", TABLE_NAME, KEY_ID, USER_ID, ADDED_DATE, BIRTHDAY, EMAIL, FIRSTNAME, GENDER, PASSWORD, SURNAME, USERNAME, HEIGHT, WEIGHT);
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

        values.put(USER_ID, user.getUserId());
        if(user.getAddedDate() != null){
            values.put(ADDED_DATE, DateConverter.dateToTime(user.getAddedDate()));
        }
        if(user.getBirthday() != null){
            values.put(BIRTHDAY, DateConverter.dateToTime(user.getBirthday()));
        }
        values.put(EMAIL, user.getEmail());
        values.put(FIRSTNAME, user.getFirstName());
        values.put(GENDER, user.getGender());
        values.put(PASSWORD, user.getPassword());
        values.put(SURNAME, user.getSurname());
        values.put(USERNAME, user.getUserName());
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
            user.setId(cursor.getInt(0));
            user.setUserId(cursor.getInt(1));
            user.setAddedDate(DateConverter.timeToDate(cursor.getLong(2)));
            user.setBirthday(DateConverter.timeToDate(cursor.getLong(3)));
            user.setEmail(cursor.getString(4));
            user.setFirstName(cursor.getString(5));
            user.setGender(cursor.getString(6));
            user.setPassword(cursor.getString(7));
            user.setSurname(cursor.getString(8));
            user.setUserName(cursor.getString(9));
            user.setHeight(cursor.getDouble(10));
            user.setWeight(cursor.getDouble(11));
        }
        db.close();
        return user;
    }

    public void updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_ID, user.getUserId());
        if(user.getAddedDate() != null){
            values.put(ADDED_DATE, DateConverter.dateToTime(user.getAddedDate()));
        }
        if(user.getBirthday() != null){
            values.put(BIRTHDAY, DateConverter.dateToTime(user.getBirthday()));
        }
        values.put(EMAIL, user.getEmail());
        values.put(FIRSTNAME, user.getFirstName());
        values.put(GENDER, user.getGender());
        values.put(PASSWORD, user.getPassword());
        values.put(SURNAME, user.getSurname());
        values.put(USERNAME, user.getUserName());
        values.put(HEIGHT, user.getHeight());
        values.put(WEIGHT, user.getWeight());

        db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[]{"1"});
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

