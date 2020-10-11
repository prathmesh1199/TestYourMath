package com.example.testyourmath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TypesOfGame extends AppCompatActivity {

    Button btn_classic , btn_timer,btn_back;
    int sz = 9;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_types_of_game);

        getSupportActionBar().hide();

        btn_classic = findViewById(R.id.btn_classic);
        btn_timer = findViewById(R.id.btn_timer);
        btn_back=findViewById(R.id.btn_back);

        btn_classic.setTextSize(sz * getResources().getDisplayMetrics().density);
        btn_timer.setTextSize(sz * getResources().getDisplayMetrics().density);
        btn_back.setTextSize(sz * getResources().getDisplayMetrics().density);

        btn_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TypesOfGame.this , levels_of_game.class);
                intent.putExtra("type" , "timer");
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        btn_classic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TypesOfGame.this , levels_of_game.class);
                intent.putExtra("type" , "classic");
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TypesOfGame.this ,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

}