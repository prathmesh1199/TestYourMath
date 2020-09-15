package com.example.testyourmath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    DatabaseHelper db;
    Button btn_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        db = new DatabaseHelper(MainActivity.this);

        String curr = db.getData();
        Log.d("here", "onCreate: curr : " + curr);

    }

    public void OnPlayClick(View view) {
        Intent intent = new Intent(this , Easy.class);
        startActivity(intent);
    }

    public void OnSettingClick(View view) {
    }

    public void OnCloseClick(View view) {
        finish();
    }
}