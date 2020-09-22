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


        sqLiteDatabase.execSQL(easy);
        sqLiteDatabase.execSQL(medium);
        sqLiteDatabase.execSQL(hard);


        ContentValues cv = new ContentValues();
        String s = " - ";
        cv.put("level1" , s);
        cv.put("level2" , s);
        cv.put("level3" , s);


        sqLiteDatabase.insert("table_easy" , null , cv);
        sqLiteDatabase.insert("table_medium" , null , cv);
        sqLiteDatabase.insert("table_hard" , null , cv);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insert(String stage , String level , int  score) {
        // stage : easy / medium / hard
        // level : level 1 / level 2 / level 3

        String table_name = "table_" + stage;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM " + table_name , null );

        if(c.moveToFirst()) {

            int idx =c.getColumnIndex(level);

            String current_top_score_string = c.getString(idx);

            if(current_top_score_string.equals(" - ")) {

                String sc = String.valueOf(score);
                sqLiteDatabase.execSQL("UPDATE " + table_name +  " SET " + level + " = " + sc);
            } else {

                int curr = Integer.valueOf(current_top_score_string);

                if(score >= curr) {

                    String sc = String.valueOf(score);

                    sqLiteDatabase.execSQL("UPDATE " + table_name +  " SET " + level + " = "  + sc );

                }
            }
        }
        sqLiteDatabase.close();
    }

    public String getData(int level) {

        Log.d("here", "getData: 1");
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Log.d("here", "getData: 2");
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM table_easy " , null );

        Log.d("here", "getData: 3");
        String ans = " - ";
        if(c.moveToFirst()) {
            Log.d("here", "getData: 4");
            int i1 =c.getColumnIndex("level1");
            int i2 = c.getColumnIndex("level2");
            Log.d("here", "getData: 5");
            String current_top_score_level1_string = c.getString(i1);
            String current_top_score_level2_string = c.getString(i2);
            Log.d("here", "getData: 6");

            if(level == 1) ans = current_top_score_level1_string;
            else if(level == 2) ans = current_top_score_level2_string;

            Log.d("here", "getData: 7");
        }

        Log.d("here", "getData: ans : " + ans );
        sqLiteDatabase.close();
        return ans;
    }
}
