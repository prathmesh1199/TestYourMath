package com.example.testyourmath;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "my_database";

    // Table Names
    private static final String DB_EASY = "easy";
    private static final String DB_MEDIUM = "medium";
    private static final String DB_HARD = "hard";

    private static final String LEVEL1 = "level1";
    private static final String LEVEL2 = "level2";
    private static final String LEVEL3 = "level3";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String easy = "CREATE TABLE table_easy(level1 VARCHAR , level2 VARCHAR , level3 VARCHAR);";
        String medium = "CREATE TABLE table_medium(level1 VARCHAR , level2 VARCHAR , level3 VARCHAR);";
        String hard = "CREATE TABLE table_hard(level1 VARCHAR , level2 VARCHAR , level3 VARCHAR);";

        Log.d("here", "onCreate: 1");

        sqLiteDatabase.execSQL(easy);
        sqLiteDatabase.execSQL(medium);
        sqLiteDatabase.execSQL(hard);

        Log.d("here", "onCreate: 2");

        ContentValues cv = new ContentValues();
        String s = " - ";
        cv.put("level1" , s);
        cv.put("level2" , s);
        cv.put("level3" , s);

        Log.d("here", "onCreate: 3");
        sqLiteDatabase.insert("table_easy" , null , cv);
        sqLiteDatabase.insert("table_medium" , null , cv);
        sqLiteDatabase.insert("table_hard" , null , cv);

        Log.d("here", "onCreate: 4");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insert(String stage , String level , int  score) {
        // stage : easy / medium / hard
        // level : level 1 / level 2 / level 3

        Log.d("here", "insert: 1");
        String table_name = "table_" + stage;
        Log.d("here", "insert: 2 " + table_name);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Log.d("here", "insert: 3");
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM " + table_name , null );

        Log.d("here", "insert: 4");
        if(c.moveToFirst()) {
            Log.d("here", "insert: 5");
            int idx =c.getColumnIndex(level);
            Log.d("here", "insert: 6");
            String current_top_score_string = c.getString(idx);

            Log.d("here", "insert: 7");
            if(current_top_score_string.equals(" - ")) {
                Log.d("here", "insert: 8");
                String sc = String.valueOf(score);
                sqLiteDatabase.execSQL("UPDATE " + table_name +  " SET " + level + " = " + sc);
            } else {
                Log.d("here", "insert:  9 "  + current_top_score_string);
                int curr = Integer.valueOf(current_top_score_string);
                Log.d("here", "insert: 10");
                if(score >= curr) {
                    Log.d("here", "insert: 11");
                    String sc = String.valueOf(score);
                    Log.d("here", "insert: 12");
                    sqLiteDatabase.execSQL("UPDATE " + table_name +  " SET " + level + " = "  + sc );
                    Log.d("here", "insert: 12");
                }
            }
        }
    }

    public String getData() {

        Log.d("here", "getData: 1");
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Log.d("here", "getData: 2");
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM table_easy " , null );

        Log.d("here", "getData: 3");
        String ans = " - ";
        if(c.moveToFirst()) {
            Log.d("here", "getData: 4");
            int idx =c.getColumnIndex("level1");
            Log.d("here", "getData: 5");
            String current_top_score_string = c.getString(idx);
            Log.d("here", "getData: 6");
            ans = current_top_score_string;
            Log.d("here", "getData: 7");
        }

        Log.d("here", "getData: ans : " + ans );
        return ans;
    }
}
