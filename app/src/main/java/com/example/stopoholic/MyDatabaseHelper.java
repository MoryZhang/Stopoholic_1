package com.example.stopoholic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Stopoholic.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "record";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_CONTENT = "content";
    private static final String COLUMN_VOLUME = "volume";
    private static final String COLUMN_ALCOHOL_WEIGHT = "alcohol_weight";
    private static final String COLUMN_DATE = "Date";

    private static final String TABLE2_NAME = "goal";
    private static final String COLUMN2_ID = "_goalID";
    private static final String COLUMN2_GOAL = "goal_weight";



    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TYPE + " TEXT, " + COLUMN_CONTENT + " REAL, " + COLUMN_VOLUME + " INT, " + COLUMN_ALCOHOL_WEIGHT + " REAL, " + COLUMN_DATE + " TEXT default CURRENT_TIMESTAMP);";
        sqLiteDatabase.execSQL(query);
        String query2 = "CREATE TABLE " + TABLE2_NAME + " (" + COLUMN2_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN2_GOAL + " INT);";
        sqLiteDatabase.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    void addRecord(String type, double content, int volume, double alcohol_weight){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TYPE, type);
        values.put(COLUMN_CONTENT, content);
        values.put(COLUMN_VOLUME, volume);
        values.put(COLUMN_ALCOHOL_WEIGHT, alcohol_weight);

        long result = db.insert(TABLE_NAME, null, values);

        if (result == -1){
            Toast.makeText(context, "Adding Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "The record added successfully", Toast.LENGTH_SHORT).show();
        }

    }

    Cursor readAllRecords() {
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_DATE + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    Cursor readTodayRecords(){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_DATE + ">=date('now', 'start of day')";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    void deleteRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    Cursor readGoal() {
        String query = "SELECT * FROM " + TABLE2_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    void setGoal(int goal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN2_GOAL, goal);

        long result = db.insert(TABLE2_NAME, null, values);

        if (result == -1){
            Toast.makeText(context, "Adding Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "The goal set successfully", Toast.LENGTH_SHORT).show();
        }

    }

    void updateGoal(int goal){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE2_NAME + " SET " + COLUMN2_GOAL + " = " + goal);
    }

}
