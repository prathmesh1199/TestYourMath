package com.example.testyourmath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    DatabaseHelper db;
    Button btn_view;
    ImageView img_btn_main;

    // Activity lifecycle : in game if phone comes timer should stop

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().hide();
        db = new DatabaseHelper(MainActivity.this);

        String curr = db.getData(2);
        Log.d("here", "onCreate: curr : " + curr);

    }

    public void OnPlayClick(View view) {
        Intent intent = new Intent(this , TypesOfGame.class);
        startActivity(intent);
    }

    public void OnSettingClick(View view) {
    }

    public void OnCloseClick(View view) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
       // finish();
    }
}