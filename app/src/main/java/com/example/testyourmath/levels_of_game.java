package com.example.testyourmath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class levels_of_game extends AppCompatActivity {

    Button btn_level_easy , btn_level_medium , btn_level_hard;
    String type = "";

    int sz = 7;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels_of_game);

        type = getIntent().getExtras().getString("type");

        getSupportActionBar().hide();

        Log.d("level", "onCreate: ffff " + type);

        btn_level_easy = findViewById(R.id.btn_levels_easy);
        btn_level_medium = findViewById(R.id.btn_level_medium);
        btn_level_hard = findViewById(R.id.btn_level_hard);

        btn_level_easy.setTextSize(sz * getResources().getDisplayMetrics().density);
        btn_level_medium.setTextSize(sz * getResources().getDisplayMetrics().density);
        btn_level_hard.setTextSize(sz * getResources().getDisplayMetrics().density);

        btn_level_easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPlay("easy" , type);
            }
        });

        btn_level_medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPlay("medium" , type);
            }
        });

        btn_level_hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPlay("hard" , type);
            }
        });
    }

    public void startPlay(String level , String type) {
        if(type.equals("timer")) {

            if(level.equals("easy")) {
                Intent intent = new Intent(levels_of_game.this , Easy.class);
                intent.putExtra("level" , "easy");
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            } else if(level.equals("medium")) {
                Intent intent = new Intent(levels_of_game.this , medium_timer.class);
                intent.putExtra("level","medium");
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            } else {
                Intent intent = new Intent(levels_of_game.this , hard_timer.class);
                intent.putExtra("level" , "hard");
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }

        } else {
            if(level.equals("easy")) {
                Intent intent = new Intent(levels_of_game.this , ClassicGame.class);
                intent.putExtra("level" , "easy");
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            } else if(level.equals("medium")) {
                Intent intent = new Intent(levels_of_game.this , medium_classic.class);
                intent.putExtra("level","medium");
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }  else {
                Intent intent = new Intent(levels_of_game.this , Hard_classic.class);
                intent.putExtra("level" , "hard");
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }

        }

    }

    public void GotoMainActivity(View view) {
        Intent intent = new Intent(levels_of_game.this , MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}