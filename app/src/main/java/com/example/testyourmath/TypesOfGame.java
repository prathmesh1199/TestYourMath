package com.example.testyourmath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TypesOfGame extends AppCompatActivity {

    Button btn_classic , btn_timer,btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_types_of_game);

        getSupportActionBar().hide();

        btn_classic = findViewById(R.id.btn_classic);
        btn_timer = findViewById(R.id.btn_timer);
        btn_back=findViewById(R.id.btn_back);

        btn_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TypesOfGame.this , Easy.class);
                startActivity(intent);
            }
        });

        btn_classic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TypesOfGame.this , ClassicGame.class);
                startActivity(intent);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TypesOfGame.this ,MainActivity.class);
                startActivity(intent);
            }
        });
    }

}