package com.example.testyourmath;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Easy extends AppCompatActivity {

    TextView tv_num1 , tv_num2  , tv_op , tv_score , tv_time_left , tv_high_score;
    TextView tv_opr;
    Button btn_plus , btn_minus , btn_multiply , btn_divide;
    Handler handler = new Handler();

    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 60000;
    private boolean timerrunning;

    String current_score , highest_score;

    int start_time = 60000;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);

        getSupportActionBar().hide();

        tv_num1 = findViewById(R.id.tv_easy_num1);
        tv_num2 = findViewById(R.id.tv_easy_num2);
        tv_opr = findViewById(R.id.tv_easy_opr1);
        tv_op = findViewById(R.id.tv_easy_op);
        tv_score = findViewById(R.id.tv_score);
        tv_time_left = findViewById(R.id.tv_time_left);
        tv_high_score = findViewById(R.id.tv_high_score);

        btn_plus = findViewById(R.id.btn_plus);
        btn_minus = findViewById(R.id.btn_minus);
        btn_multiply = findViewById(R.id.btn_multiply);
        btn_divide = findViewById(R.id.btn_divide);

        db = new DatabaseHelper(Easy.this);

        String high_score = db.getData();

        highest_score = high_score;

        tv_high_score.setText(high_score);


        countDownTimer = new CountDownTimer(start_time , 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv_time_left.setText(""+String.format("%d : %d ",
                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            @Override
            public void onFinish() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Easy.this);
                builder.setTitle("Opps..Time Up.!").setMessage("Your Score is : " + tv_score.getText().toString().trim())
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Easy.this , MainActivity.class);
                        startActivity(intent);
                    }
                });

                AlertDialog alertDialog = builder.show();
                alertDialog.setCanceledOnTouchOutside(false);

                Log.d("here", "onFinish: easyyyy 1");
                db.insert("easy" , "level1" , Integer.parseInt(tv_score.getText().toString().trim()));
                Log.d("here", "onFinish: easyyyy 2");

            }
        };

        countDownTimer.start();
        show();

        /*if(savedInstanceState != null) {
            String score = savedInstanceState.getString("score");
            highest_score = savedInstanceState.getString("high_score");
            String time = savedInstanceState.getString("time");

            start_time = Integer.parseInt(time);

            tv_score.setText(score);
            tv_high_score.setText(highest_score);
            tv_time_left.setText(time);
        }*/

    }

    public void OnPlusClick(View view) {
        tv_opr.setText(String.valueOf('+'));
        int n1 = Integer.parseInt(tv_num1.getText().toString().trim());
        int n2 = Integer.parseInt(tv_num2.getText().toString().trim());
        int op = Integer.parseInt(tv_op.getText().toString().trim());

        Log.d("here " ,"OnPlusClick: " + n1 + " " + n2 + " " + op);

        if(n1 + n2 == op) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            int score = Integer.parseInt(tv_score.getText().toString().trim());

            score++;
            tv_score.setText(String.valueOf(score));
            tv_opr.setText("");

            Drawable d = getResources().getDrawable(R.drawable.green_button);
            btn_plus.setBackground(d);
            DelayButtonColor(btn_plus);
            show();
        }
        else {
            Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
            Drawable d = getResources().getDrawable(R.drawable.red_button);
            btn_plus.setBackground(d);
            DelayButtonColor(btn_plus);
            show();
        }
    }

    public void OnMinusClick(View view) {
        tv_opr.setText(String.valueOf('-'));
        int n1 = Integer.parseInt(tv_num1.getText().toString().trim());
        int n2 = Integer.parseInt(tv_num2.getText().toString().trim());
        int op = Integer.parseInt(tv_op.getText().toString().trim());

        Log.d("here", "OnMinusClick: " + n1 + " " + n2 + " " + op);

        if(n1 - n2 == op) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();

            int score = Integer.parseInt(tv_score.getText().toString().trim());

            score++;
            tv_score.setText(String.valueOf(score));
            tv_opr.setText("");

            Drawable d = getResources().getDrawable(R.drawable.green_button);
            btn_minus.setBackground(d);
            DelayButtonColor(btn_minus);
            show();
        }
        else {
            Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
            Drawable d = getResources().getDrawable(R.drawable.red_button);
            btn_minus.setBackground(d);
            DelayButtonColor(btn_minus);
            show();
        }

    }

    public void OnMultiplyClick(View view) {
        tv_opr.setText(String.valueOf('*'));

        int n1 = Integer.parseInt(tv_num1.getText().toString().trim());
        int n2 = Integer.parseInt(tv_num2.getText().toString().trim());
        int op = Integer.parseInt(tv_op.getText().toString().trim());

        Log.d("here", "OnMultiplyClick: " + n1 + " " + n2 + " " + op);

        if(n1 * n2 == op) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();

            int score = Integer.parseInt(tv_score.getText().toString().trim());

            score++;
            tv_score.setText(String.valueOf(score));
            tv_opr.setText("");

             Drawable d = getResources().getDrawable(R.drawable.green_button);
            btn_multiply.setBackground(d);
            DelayButtonColor(btn_multiply);
            show();
        }
        else {
            Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
            Drawable d = getResources().getDrawable(R.drawable.red_button);
            btn_multiply.setBackground(d);
            DelayButtonColor(btn_multiply);
            show();
        }

    }

    public void OnDivideClick(View view) {
        tv_opr.setText(String.valueOf('/'));

        int n1 = Integer.parseInt(tv_num1.getText().toString().trim());
        int n2 = Integer.parseInt(tv_num2.getText().toString().trim());
        int op = Integer.parseInt(tv_op.getText().toString().trim());

        Log.d("here", "OnDivideClick: " + n1 + " " + n2 + " " + op);

        if(n1 / n2 == op) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            int score = Integer.parseInt(tv_score.getText().toString().trim());

            score++;
            tv_score.setText(String.valueOf(score));
            tv_opr.setText("");

          //added for
            Drawable d = getResources().getDrawable(R.drawable.green_button);
            btn_divide.setBackground(d);
             DelayButtonColor(btn_divide);
            show();
        }
        else {
            Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
            Drawable d = getResources().getDrawable(R.drawable.red_button);
            btn_divide.setBackground(d);
            DelayButtonColor(btn_divide);
            show();
        }

    }



    /*@Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        String score = tv_score.getText().toString().trim();
        String time = tv_time_left.toString().trim();

        outState.putString("score" , score);
        outState.putString("high_score" , highest_score);
        outState.putString("time" , time);
    }*/

    public void DelayButtonColor(Button prm_Button)
    {
        final Button btn_Color=prm_Button;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Drawable d = getResources().getDrawable(R.drawable.mybutton);
                btn_Color.setBackground(d);
            }
        }, 2000);

    }


    public void show() {
        Random random = new Random();
        int n1 = random.nextInt(10) + 1 ;
        int n2 = random.nextInt(10) + 1;

        Drawable d = getResources().getDrawable(R.drawable.mybutton);

        //Remove this comments to restore original colors to button

        //btn_divide.setBackground(d);
       // (new Handler()).postDelayed( 5000);

        //btn_multiply.setBackground(d);

        //btn_minus.setBackground(d);
        //btn_plus.setBackground(d);

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
        tv_num1.setText(String.valueOf(n1));
        tv_num2.setText(String.valueOf(n2));

        tv_opr.setText("?");
        tv_op.setText(String.valueOf(op));

        Animation anim = new AlphaAnimation(0.0f , 1.0f);
        anim.setDuration(1000); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        tv_opr.startAnimation(anim);

    }
}