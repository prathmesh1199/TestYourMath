package com.example.testyourmath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    DatabaseHelper db;
    Button btn_view;
    ImageView img_btn_main , img_calculator;

    LinearLayout linear_play , linear_close , linear_settings;

    int sz = 9;
    TextView tv_heading_text , tv_play_text , tv_settings_text , tv_close_text;

    Animation top_anim , play_anim , close_anim , settings_anim;

    // Activity lifecycle : in game if phone comes timer should stop

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_heading_text = findViewById(R.id.tv_heading_text);
        tv_play_text = findViewById(R.id.tv_play_text);
        tv_settings_text = findViewById(R.id.tv_settings_text);
        tv_close_text = findViewById(R.id.tv_close_text);

        /*tv_heading_text.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_play_text.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_settings_text.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_close_text.setTextSize(sz * getResources().getDisplayMetrics().density);*/

        tv_heading_text.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_play_text.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_settings_text.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_close_text.setTextSize(sz * getResources().getDisplayMetrics().density);

        getSupportActionBar().hide();
        db = new DatabaseHelper(MainActivity.this);

        top_anim = AnimationUtils.loadAnimation(this , R.anim.top_animation);
        play_anim = AnimationUtils.loadAnimation(this , R.anim.play_animation);
        close_anim = AnimationUtils.loadAnimation(this , R.anim.close_animation);
        settings_anim = AnimationUtils.loadAnimation(this , R.anim.settings_animation);

        img_calculator = findViewById(R.id.img_calculator);
        linear_close = findViewById(R.id.linear_close);
        linear_play = findViewById(R.id.linear_play);
        linear_settings = findViewById(R.id.linear_settings);

        img_calculator.setAnimation(top_anim);
        linear_play.setAnimation(play_anim);
        linear_close.setAnimation(close_anim);
        linear_settings.setAnimation(settings_anim);
    }

    public void OnPlayClick(View view) {
        Intent intent = new Intent(this , TypesOfGame.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        //finish();
    }

    public void OnSettingClick(View view) {
    }

    public void OnCloseClick(View view) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
       // finish();
    }


}