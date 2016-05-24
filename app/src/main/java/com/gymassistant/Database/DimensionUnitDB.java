package com.gymassistant.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gymassistant.Models.DimensionUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KamilH on 2016-05-03.
 */
public class DimensionUnitDB extends SQLiteOpenHelper {
    private final String TABLE_NAME = "DimensionUnit", KEY_ID = "id", SHORT_NAME = "shortName", NAME = "name";


    public DimensionUnitDB(Context context) {
        super(context, "Dimension", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT)",
                TABLE_NAME, KEY_ID, SHORT_NAME, NAME);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addDimensionUnit(DimensionUnit dimensionUnit){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SHORT_NAME, dimensionUnit.getShortName());
        values.put(NAME, dimensionUnit.getName());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<DimensionUnit> getAllDimensionUnits() {
        List<DimensionUnit> dimensionUnits = new ArrayList<DimensionUnit>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DimensionUnit dimensionUnit = new DimensionUnit();

                dimensionUnit.setId(cursor.getInt(0));
                dimensionUnit.setShortName(cursor.getString(1));
                dimensionUnit.setName(cursor.getString(2));

                dimensionUnits.add(dimensionUnit);
            } while (cursor.moveToNext());
        }
        db.close();
        return dimensionUnits;
    }

    public DimensionUnit getDimensionUnit(int ID){
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + "=" + ID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        DimensionUnit dimensionUnit = new DimensionUnit();
        if (cursor.moveToFirst()) {
            dimensionUnit.setId(cursor.getInt(0));
            dimensionUnit.setShortName(cursor.getString(1));
            dimensionUnit.setName(cursor.getString(2));
        }
        db.close();
        return dimensionUnit;
    }

    public void deleteDimensionUnit(int id) {
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
