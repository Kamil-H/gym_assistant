package com.gymassistant.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gymassistant.Models.DimensionType;
import com.gymassistant.Preferences;
import com.gymassistant.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KamilH on 2016-05-03.
 */
public class DimensionTypeDB extends SQLiteOpenHelper {
    private Context context;
    private String lengthUnit, weightUnit;
    private final String TABLE_NAME = "DimensionType", KEY_ID = "id", NAME = "name", UNIT = "unit";

    public DimensionTypeDB(Context context) {
        super(context, "DimensionType", null, 1);
        this.context = context;

        Preferences preferences = new Preferences(context);
        this.lengthUnit = preferences.getLengthUnit();
        this.weightUnit = preferences.getWeightUnit();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s INTEGER)",
                TABLE_NAME, KEY_ID, NAME, UNIT);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void populateDimensionTypeDB(){
        String[] units = {weightUnit, "%", lengthUnit, lengthUnit, lengthUnit, lengthUnit, lengthUnit, lengthUnit, lengthUnit, lengthUnit, lengthUnit};
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String[] dimensionTypesArray = context.getResources().getStringArray(R.array.dimension_types);
        for (int i = 0; i < dimensionTypesArray.length; i++) {
            values.put(NAME, dimensionTypesArray[i]);
            values.put(UNIT, getIntFromUnit(units[i]));
            db.insert(TABLE_NAME, null, values);
        }
        db.close();
    }

    public void addDimensionType(DimensionType dimensionType){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME, dimensionType.getName());
        values.put(UNIT, getIntFromUnit(dimensionType.getUnit()));

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<DimensionType> getAllDimensionTypes() {
        List<DimensionType> dimensionTypes = new ArrayList<DimensionType>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DimensionType dimensionType = new DimensionType();

                dimensionType.setId(cursor.getLong(0));
                dimensionType.setName(cursor.getString(1));
                dimensionType.setUnit(getUnitFromInt(cursor.getInt(2)));

                dimensionTypes.add(dimensionType);
            } while (cursor.moveToNext());
        }
        db.close();
        return dimensionTypes;
    }

    public DimensionType getDimensionType(long ID){
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + "=" + ID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        DimensionType dimensionType = new DimensionType();
        if (cursor.moveToFirst()) {
            dimensionType.setId(cursor.getInt(0));
            dimensionType.setName(cursor.getString(1));
            dimensionType.setUnit(getUnitFromInt(cursor.getInt(2)));
        }
        db.close();
        return dimensionType;
    }

    public void deleteDimensionType(long id) {
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

    private String getUnitFromInt(int value){
        switch (value){
            case 1:
                return lengthUnit;
            case 2:
                return weightUnit;
            case 3:
                return "%";
        }
        return null;
    }

    private int getIntFromUnit(String unit){
        if(unit.matches("cm|inch")){
            return 1;
        }
        if(unit.matches("kg|lb")){
            return 2;
        }
        if(unit.matches("%")){
            return 3;
        }
        return -1;
    }
}
