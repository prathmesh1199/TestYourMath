package com.example.testyourmath;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class ClassicGame extends AppCompatActivity {

    TextView tv_classic_num1 , tv_classic_num2  , tv_classic_op , tv_classic_score , tv_classic_lives_left , tv_classic_high_score;
    TextView tv_classic_opr;

    Button btn_classic_plus , btn_classic_minus , btn_classic_divide , btn_classic_multiply;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_game);

        tv_classic_num1 = findViewById(R.id.tv_classic_easy_num1);
        tv_classic_num2 = findViewById(R.id.tv_classic_easy_num2);
        tv_classic_op = findViewById(R.id.tv_classic_easy_op);
        tv_classic_score = findViewById(R.id.tv_classic_score);
        tv_classic_high_score = findViewById(R.id.tv_classic_high_score);
        tv_classic_lives_left = findViewById(R.id.tv_classic_lives_left);
        tv_classic_opr = findViewById(R.id.tv_classic_easy_opr1);

        btn_classic_divide = findViewById(R.id.btn_classic_divide);
        btn_classic_minus = findViewById(R.id.btn_classic_minus);
        btn_classic_multiply = findViewById(R.id.btn_classic_multiply);
        btn_classic_plus = findViewById(R.id.btn_classic_plus);

        db = new DatabaseHelper(this);
        int lives = 3;
        String lives_left = String.valueOf(lives);

        String high_score = db.getData(2);
        Log.d("here", "onCreate: highhhhscoreeee " + high_score);
        tv_classic_high_score.setText(high_score);
        tv_classic_lives_left.setText(lives_left);

        show();

    }

    private void endgame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ClassicGame.this);
        builder.setTitle("Opps...All lives over!").setMessage("Your Score is : " + tv_classic_score.getText().toString().trim())
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(ClassicGame.this , MainActivity.class);
                        startActivity(intent);
                    }
                });
        AlertDialog alertDialog = builder.show();

        alertDialog.setCanceledOnTouchOutside(false);

        db.insert("easy" , "level2" , Integer.parseInt(tv_classic_score.getText().toString().trim()));

    }

    public void OnClassicPlusClick(View view) {
        tv_classic_opr.setText(String.valueOf('+'));
        int n1 = Integer.parseInt(tv_classic_num1.getText().toString().trim());
        int n2 = Integer.parseInt(tv_classic_num2.getText().toString().trim());
        int op = Integer.parseInt(tv_classic_op.getText().toString().trim());

        Log.d("here " ,"OnPlusClick: " + n1 + " " + n2 + " " + op);

        if(n1 + n2 == op) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            int score = Integer.parseInt(tv_classic_score.getText().toString().trim());

            Log.d("here", "OnClassicPlusClick: kkkkkkkkkkkk " + score);
            score++;
            Log.d("here", "OnClassicPlusClick: kkkkkkkkkkkk " + score);

            tv_classic_score.setText(String.valueOf(score));
            tv_classic_opr.setText("");

            Drawable d = getResources().getDrawable(R.drawable.green_button);
            btn_classic_plus.setBackground(d);
            DelayButtonColor(btn_classic_plus);

            show();
        }
        else {
            Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();

            String lives_left = tv_classic_lives_left.getText().toString().trim();
            Log.d("here", "OnClassicPlusClick: eeeeeeeeeeeeeee " + lives_left);
            //char c = lives_left.charAt(13);
            //int lives = Integer.parseInt(lives_left);
            //Log.d("here", "OnClassicPlusClick: sssssssssss" + c + " " + lives_left);
            int lives = Integer.parseInt(lives_left);

            Log.d("here", "OnClassicPlusClick: eeeeeeeeeeeeeee " + lives);
            lives--;
            Log.d("here", "OnClassicPlusClick: eeeeeeeeeeeeeee " + lives);
            lives_left = String.valueOf(lives);
            Log.d("here", "OnClassicPlusClick: eeeeeeeeeeeeeee " + lives_left);
            tv_classic_lives_left.setText(lives_left);

            Drawable d = getResources().getDrawable(R.drawable.red_button);
            btn_classic_plus.setBackground(d);
            DelayButtonColor(btn_classic_plus);

            if(lives == 0) {
                endgame();
                return;
            }

        }
    }


    public void OnClassicMinusClick(View view) {
        tv_classic_opr.setText(String.valueOf('-'));
        int n1 = Integer.parseInt(tv_classic_num1.getText().toString().trim());
        int n2 = Integer.parseInt(tv_classic_num2.getText().toString().trim());
        int op = Integer.parseInt(tv_classic_op.getText().toString().trim());

        Log.d("here", "OnMinusClick: " + n1 + " " + n2 + " " + op);

        if(n1 - n2 == op) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();

            int score = Integer.parseInt(tv_classic_score.getText().toString().trim());

            Log.d("here", "OnClassicMinusClick: hhhhhhhhhhh " + score);

            score++;
            Log.d("here", "OnClassicMinusClick: hhhhhhhhhhh " + score);
            tv_classic_score.setText(String.valueOf(score));
            tv_classic_opr.setText("");

            Drawable d = getResources().getDrawable(R.drawable.green_button);
            btn_classic_minus.setBackground(d);
            DelayButtonColor(btn_classic_minus);

            show();
        }
        else {
            String lives_left = tv_classic_lives_left.getText().toString().trim();
            //char c = lives_left.charAt(13);
            //int lives = Integer.parseInt(lives_left);
            //Log.d("here", "OnClassicPlusClick: sssssssssss" + c + " " + lives_left);
            Log.d("here", "OnClassicMinusClick: rrrrrrrrrrrr " + lives_left);
            int lives = Integer.parseInt(lives_left);
            Log.d("here", "OnClassicMinusClick: rrrrrrrrrrrr " + lives);
            lives--;
            Log.d("here", "OnClassicMinusClick: rrrrrrrrrrrr " + lives);
            lives_left = String.valueOf(lives);
            Log.d("here", "OnClassicMinusClick: rrrrrrrrrrrr " + lives_left);
            tv_classic_lives_left.setText(lives_left);

            Drawable d = getResources().getDrawable(R.drawable.red_button);
            btn_classic_divide.setBackground(d);
            DelayButtonColor(btn_classic_divide);

            if(lives == 0) {
                endgame();
                return;
            }
        }
    }

    public void OnClassicMultiplyClick(View view) {
        tv_classic_opr.setText(String.valueOf('*'));

        int n1 = Integer.parseInt(tv_classic_num1.getText().toString().trim());
        int n2 = Integer.parseInt(tv_classic_num2.getText().toString().trim());
        int op = Integer.parseInt(tv_classic_op.getText().toString().trim());

        Log.d("here", "OnMultiplyClick: " + n1 + " " + n2 + " " + op);

        if(n1 * n2 == op) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();

            int score = Integer.parseInt(tv_classic_score.getText().toString().trim());

            Log.d("here", "OnClassicMultiplyClick: llllllllllll " + score);
            score++;
            Log.d("here", "OnClassicMultiplyClick: llllllllllll " + score);
            tv_classic_score.setText(String.valueOf(score));
            tv_classic_opr.setText("");

            Drawable d = getResources().getDrawable(R.drawable.green_button);
            btn_classic_multiply.setBackground(d);
            DelayButtonColor(btn_classic_multiply);

            show();
        }
        else {
            String lives_left = tv_classic_lives_left.getText().toString().trim();

            Log.d("here", "OnClassicMultiplyClick: ggggggggggggggggggg " + lives_left);
            //char c = lives_left.charAt(13);
            //int lives = Integer.parseInt(lives_left);
            //Log.d("here", "OnClassicPlusClick: sssssssssss" + c + " " + lives_left);
            int lives = Integer.parseInt(lives_left);
            Log.d("here", "OnClassicMultiplyClick: ggggggggggggggggggg " + lives);
            lives--;
            Log.d("here", "OnClassicMultiplyClick: ggggggggggggggggggg " + lives);
            lives_left = String.valueOf(lives);
            tv_classic_lives_left.setText(lives_left);

            Drawable d = getResources().getDrawable(R.drawable.red_button);
            btn_classic_multiply.setBackground(d);
            DelayButtonColor(btn_classic_multiply);

            if(lives == 0) {
                endgame();
                return;
            }
        }
    }

    public void OnClassicDivideClick(View view) {
        Log.d("here", "OnDivideClick: aaaa");
        tv_classic_opr.setText(String.valueOf('/'));

        Log.d("here", "OnDivideClick: bbbb");
        int n1 = Integer.parseInt(tv_classic_num1.getText().toString().trim());
        int n2 = Integer.parseInt(tv_classic_num2.getText().toString().trim());
        int op = Integer.parseInt(tv_classic_op.getText().toString().trim());

        Log.d("here", "OnDivideClick: " + n1 + " " + n2 + " " + op);

        if(n1 / n2 == op && n1%n2 == 0) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            int score = Integer.parseInt(tv_classic_score.getText().toString().trim());

            Log.d("here", "OnClassicDivideClick: dddddddddddddddd sc : " + score);
            score++;
            Log.d("here", "OnClassicDivideClick: dddddddddddddddd " + score);
            tv_classic_score.setText(String.valueOf(score));
            tv_classic_opr.setText("");

            Drawable d = getResources().getDrawable(R.drawable.green_button);
            btn_classic_divide.setBackground(d);
            DelayButtonColor(btn_classic_divide);

            show();
        }
        else {
            String lives_left = tv_classic_lives_left.getText().toString().trim();
            Log.d("here", "OnClassicDivideClick: dddddddddddddd " + lives_left);
            //char c = lives_left.charAt(13);
            //int lives = Integer.parseInt(lives_left);
            //Log.d("here", "OnClassicPlusClick: sssssssssss" + c + " " + lives_left);
            int lives = Integer.parseInt(lives_left);
            Log.d("here", "OnClassicDivideClick: dddddddddddddddd " + lives);
            lives--;
            Log.d("here", "OnClassicDivideClick: dddddddddddddddd " + lives);
            lives_left = String.valueOf(lives);
            Log.d("here", "OnClassicDivideClick: dddddddddddddddd " + lives_left);
            tv_classic_lives_left.setText(lives_left);

            Drawable d = getResources().getDrawable(R.drawable.red_button);
            btn_classic_divide.setBackground(d);
            DelayButtonColor(btn_classic_divide);

            if(lives == 0) {
                endgame();
                return;
            }
        }
    }

    public void DelayButtonColor(Button prm_Button)
    {
        final Button btn_Color=prm_Button;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Drawable d = getResources().getDrawable(R.drawable.mybutton);
                btn_Color.setBackground(d);
            }
        }, 200);

    }

    public void show() {
        Random random = new Random();
        int n1 = random.nextInt(10) + 1 ;
        int n2 = random.nextInt(10) + 1;

        char[] opr = new char[4];
        opr[0] = '+';
        opr[1] = '-';
        opr[2] = '*';
        opr[3] = '/';

        int idx = random.nextInt(4);
        Log.d("here", "onCreate: aaa" + idx + " " + opr[idx] + " " + n1 + " " + n2);

        if(n2 > n1) {
            int temp = n1;
            n1 = n2;
            n2 = temp;
        }

        Log.d("here", "onCreate: bbb" + idx + " " + opr[idx] + " " + n1 + " " + n2);


        Log.d("here", "onCreate: ccc" + idx + " " + opr[idx] + " " + n1 + " " + n2);

        while(idx == 3 && n1 % n2 !=0) {
            idx = random.nextInt(4);
        }

        Log.d("here", "onCreate: ddd" + idx + " " + opr[idx] + " " + n1 + " " + n2);
        //Log.d("here", "onCreate: below" + n1 + " " + n2 + " " + idx + " " + opr[idx]);
        int op = 1;

        if(n2 > n1) {
            int temp = n1;
            n1 = n2;
            n2 = temp;
        }

        Log.d("here", "onCreate: eee" + idx + " " + opr[idx] + " " + n1 + " " + n2);
        switch (idx) {
            case 0 : op = n1 + n2;break;
            case 1 : op = n1 - n2;break;
            case 2 : op = n1 * n2;break;
            case 3 : op = n1 / n2;break;
        }

        Log.d("here", "onCreate: fff" + idx + " " + opr[idx] + " " + n1 + " " + n2);
        tv_classic_num1.setText(String.valueOf(n1));
        tv_classic_num2.setText(String.valueOf(n2));

        tv_classic_opr.setText("?");
        //edt_opr.setText(String.valueOf(opr[idx]));
        tv_classic_op.setText(String.valueOf(op));
    }
}