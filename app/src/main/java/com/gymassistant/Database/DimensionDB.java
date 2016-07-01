package com.gymassistant.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gymassistant.DateConverter;
import com.gymassistant.Models.Dimension;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KamilH on 2016-05-03.
 */
public class DimensionDB extends SQLiteOpenHelper {
    private Context context;
    private final String TABLE_NAME = "Dimension", KEY_ID = "id", VALUE = "value", ADD_DATE = "addDate", DIMENSION_TYPE_KEY = "unitKey";

    public DimensionDB(Context context) {
        super(context, "Dimension", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s REAL, %s INTEGER, %s INTEGER)",
                TABLE_NAME, KEY_ID, VALUE, ADD_DATE, DIMENSION_TYPE_KEY);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addDimension(Dimension dimension){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(VALUE, dimension.getValue());
        values.put(ADD_DATE, DateConverter.dateToTime(dimension.getAddedDate()));
        values.put(DIMENSION_TYPE_KEY, dimension.getTypeKey());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<Dimension> getAllDimension() {
        DimensionTypeDB dimensionTypeDB = new DimensionTypeDB(context);
        List<Dimension> dimensions = new ArrayList<Dimension>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Dimension dimension = new Dimension();

                dimension.setId(cursor.getLong(0));
                dimension.setValue(cursor.getDouble(1));
                dimension.setAddedDate(DateConverter.timeToDate(cursor.getLong(2)));
                dimension.setTypeKey(cursor.getLong(3));
                dimension.setType(dimensionTypeDB.getDimensionType(dimension.getTypeKey()));

                dimensions.add(dimension);
            } while (cursor.moveToNext());
        }
        db.close();
        return dimensions;
    }

    public Dimension getDimension(long ID){
        DimensionTypeDB dimensionTypeDB = new DimensionTypeDB(context);
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + "=" + ID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Dimension dimension = new Dimension();
        if (cursor.moveToFirst()) {
            dimension.setId(cursor.getLong(0));
            dimension.setValue(cursor.getDouble(1));
            dimension.setAddedDate(DateConverter.timeToDate(cursor.getLong(2)));
            dimension.setTypeKey(cursor.getLong(3));
            dimension.setType(dimensionTypeDB.getDimensionType(dimension.getTypeKey()));
        }
        db.close();
        return dimension;
    }

    public void deleteDimension(long id) {
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
