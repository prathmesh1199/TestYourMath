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

    private static final int DATABASE_VERSION = 3;

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

        String timer = "CREATE TABLE IF NOT EXISTS table_timer(easy VARCHAR , medium VARCHAR , hard VARCHAR);";
        String classic = "CREATE TABLE IF NOT EXISTS table_classic(easy VARCHAR , medium VARCHAR , hard VARCHAR);";

        //sqLiteDatabase.execSQL(easy);
        //sqLiteDatabase.execSQL(medium);
        //sqLiteDatabase.execSQL(hard);

        sqLiteDatabase.execSQL(timer);
        sqLiteDatabase.execSQL(classic);

        ContentValues cv = new ContentValues();
        String s = " - ";
        cv.put("easy" , s);
        cv.put("medium" , s);
        cv.put("hard" , s);

        sqLiteDatabase.insert("table_timer" , null , cv);
        sqLiteDatabase.insert("table_classic" , null , cv);
        //sqLiteDatabase.insert("table_easy" , null , cv);
        //sqLiteDatabase.insert("table_medium" , null , cv);
        //sqLiteDatabase.insert("table_hard" , null , cv);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        Log.d("upgrade", "onUpgrade: ");

        db.execSQL("DROP TABLE IF EXISTS table_easy");
        db.execSQL("DROP TABLE IF EXISTS table_medium");
        db.execSQL("DROP TABLE IF EXISTS table_hard");

        // create new table
        onCreate(db);
    }

    public void insert(String stage , String level , int  score) {
        // stage : timer / classic
        // level : easy / medium / hard

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

    //easy med hard
    //timer classic

    public String getData(String stage , String level) {

        // stage : timer / classic
        // level : easy / medium / hard

        Log.d("here", "getData: 1");
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Log.d("here", "getData: 2");
        String table_name = "table_" + stage;


        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM " + table_name  , null );

        String ans = " - ";
        if(c.moveToFirst()) {
            Log.d("here", "getData: 4");
            int i1 =c.getColumnIndex("easy");
            int i2 = c.getColumnIndex("medium");
            int i3 = c.getColumnIndex("hard");
            Log.d("here", "getData: 5");
            String current_top_score_easy_string = c.getString(i1);
            String current_top_score_medium_string = c.getString(i2);
            String current_top_score_hard_string = c.getString(i3);
            Log.d("here", "getData: 6");

            if(level.equals("easy")) ans = current_top_score_easy_string;
            else if(level.equals("medium")) ans = current_top_score_medium_string;
            else if(level.equals("hard")) ans = current_top_score_hard_string;

            Log.d("here", "getData: 7");
        }

        Log.d("here", "getData: ans : " + ans );
        sqLiteDatabase.close();
        return ans;
    }
}
