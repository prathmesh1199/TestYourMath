package com.example.testyourmath;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
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

    TextView tv_num1 , tv_num2  , tv_op , tv_score , tv_time_left , tv_high_score , tv_equ;
    TextView tv_opr,tv_score_text , tv_high_score_text , tv_time_left_text;
    Button btn_plus , btn_minus , btn_multiply , btn_divide;
    Handler handler = new Handler();



    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 60000;
    private boolean timerrunning;

    String current_score , highest_score;
    int sz = 9;

    int start_time = 6000 , flag = -1;
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
        tv_high_score_text = findViewById(R.id.tv_high_score_text);
        tv_time_left_text = findViewById(R.id.tv_time_left_text);
        tv_score_text = findViewById(R.id.tv_score_text);
        tv_equ = findViewById(R.id.tv_easy_equ);

        btn_plus = findViewById(R.id.btn_plus);
        btn_minus = findViewById(R.id.btn_minus);
        btn_multiply = findViewById(R.id.btn_multiply);
        btn_divide = findViewById(R.id.btn_divide);

        tv_num1.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_num2.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_opr.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_op.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_time_left.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_high_score.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_score.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_time_left_text.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_high_score_text.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_score_text.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_equ.setTextSize(sz * getResources().getDisplayMetrics().density);
        btn_plus.setTextSize(sz * getResources().getDisplayMetrics().density);
        btn_minus.setTextSize(sz * getResources().getDisplayMetrics().density);
        btn_multiply.setTextSize(sz * getResources().getDisplayMetrics().density);
        btn_divide.setTextSize(sz * getResources().getDisplayMetrics().density);


        db = new DatabaseHelper(Easy.this);

        String high_score = db.getData("timer" , "easy");

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
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();
                    }
                });

                AlertDialog alertDialog = builder.show();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Intent intent = new Intent(Easy.this , MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();
                    }
                });

                Log.d("here", "onFinish: easyyyy 1");
                db.insert("timer" , "easy" , Integer.parseInt(tv_score.getText().toString().trim()));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void correct_answer(Button btn , String operation) {
        int score = Integer.parseInt(tv_score.getText().toString().trim());

        score++;
        tv_score.setText(String.valueOf(score));
        tv_opr.setText("");

        Drawable d = getResources().getDrawable(R.drawable.green_button);
        btn.setBackground(d);
        DelayButtonColor(btn , operation);
        show();
    }

    public void wrong_answer(Button btn , String operation) {
        Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
        Drawable d = getResources().getDrawable(R.drawable.red_button);
        btn.setBackground(d);
        DelayButtonColor(btn , operation);
        show();
    }

    public void check(Button btn , String operation) {
        int n1 = Integer.parseInt(tv_num1.getText().toString().trim());
        int op = Integer.parseInt(tv_op.getText().toString().trim());
        String operator = tv_opr.getText().toString().trim();
        int num = Integer.parseInt(btn.getText().toString().trim());
        if(operator.equals("+")) {
            int n2 = op - n1;

            if(n2 == num) {
                correct_answer(btn , operation);
            } else {
                wrong_answer(btn , operation);
            }

        } else if(operator.equals("-")) {

            int n2 = n1 - op;
            if(num == n2) correct_answer(btn , operation);
            else wrong_answer(btn , operation);

        } else if(operator.equals("*")) {
            int n2 = op / n1;

            if(num == n2) correct_answer(btn , operation);
            else wrong_answer(btn , operation);

        } else {

            int n2 = n1 / op;

            if(n1%op!=0) {
                wrong_answer(btn , operation);
                show();
            } else if(num == n2) correct_answer(btn , operation);
            else wrong_answer(btn , operation);
        }
    }

    public void OnPlusClick(View view) {

        if(flag <= 5) {
            tv_opr.setText(String.valueOf('+'));
            Log.d("here", "OnPlusClick: aaaaaaaa");
            int n1 = Integer.parseInt(tv_num1.getText().toString().trim());
            Log.d("here", "OnPlusClick: aaaaaaaa n1 " + n1);
            int n2 = Integer.parseInt(tv_num2.getText().toString().trim());
            Log.d("here", "OnPlusClick: aaaaaaaa n2 " + n2);
            int op = Integer.parseInt(tv_op.getText().toString().trim());
            Log.d("here", "OnPlusClick: aaaaaaaa op " + op);

            Log.d("here " ,"OnPlusClick: " + n1 + " " + n2 + " " + op);

            if(n1 + n2 == op) {
                correct_answer(btn_plus , "plus");
            }
            else {
                wrong_answer(btn_plus , "plus");
            }
        } else {
            check(btn_plus , "plus");
        }

    }

    public void OnMinusClick(View view) {

        if(flag <= 5) {
            tv_opr.setText(String.valueOf('-'));
            int n1 = Integer.parseInt(tv_num1.getText().toString().trim());
            int n2 = Integer.parseInt(tv_num2.getText().toString().trim());
            int op = Integer.parseInt(tv_op.getText().toString().trim());

            Log.d("here", "OnMinusClick: " + n1 + " " + n2 + " " + op);

            if(n1 - n2 == op) {
                correct_answer(btn_minus , "minus");
            }
            else {
                wrong_answer(btn_minus , "minus");
            }
        } else {
            check(btn_minus , "minus");

        }

    }

    public void OnMultiplyClick(View view) {

        if(flag <= 5) {
            tv_opr.setText(String.valueOf('*'));

            int n1 = Integer.parseInt(tv_num1.getText().toString().trim());
            int n2 = Integer.parseInt(tv_num2.getText().toString().trim());
            int op = Integer.parseInt(tv_op.getText().toString().trim());

            Log.d("here", "OnMultiplyClick: " + n1 + " " + n2 + " " + op);

            if(n1 * n2 == op) {
                correct_answer(btn_multiply , "multiply");

            }
            else {
              wrong_answer(btn_multiply , "multiply")  ;
            }
        } else {
            check(btn_multiply , "multiply");
        }

    }

    public void OnDivideClick(View view) {

        if(flag <= 5) {
            tv_opr.setText(String.valueOf('/'));

            int n1 = Integer.parseInt(tv_num1.getText().toString().trim());
            int n2 = Integer.parseInt(tv_num2.getText().toString().trim());
            int op = Integer.parseInt(tv_op.getText().toString().trim());

            Log.d("here", "OnDivideClick: " + n1 + " " + n2 + " " + op);

            if(n1 / n2 == op && n1%n2 == 0) {
                correct_answer(btn_divide , "divide");
            }
            else {
                wrong_answer(btn_divide , "divide");
            }
        } else {
            check(btn_divide , "divide");
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

    public void DelayButtonColor(Button prm_Button , final String operation)
    {
        final Button btn_Color=prm_Button;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(operation.equals("plus")) {
                    Drawable d = getResources().getDrawable(R.drawable.background_plus);
                    btn_Color.setBackground(d);
                } else if(operation.equals("minus")) {
                    Drawable d = getResources().getDrawable(R.drawable.background_minus);
                    btn_Color.setBackground(d);
                } else if(operation.equals("multiply")) {
                    Drawable d = getResources().getDrawable(R.drawable.background_multiply);
                    btn_Color.setBackground(d);
                } else {
                    Drawable d = getResources().getDrawable(R.drawable.background_divide);
                    btn_Color.setBackground(d);
                }


            }
        }, 200);

    }


    public void show() {
        Random random = new Random();
        int n1 = random.nextInt(10) + 1 ;
        int n2 = random.nextInt(10) + 1;

        Drawable d = getResources().getDrawable(R.drawable.mybutton);


        char[] opr = new char[4];
        opr[0] = '+';
        opr[1] = '-';
        opr[2] = '*';
        opr[3] = '/';

        flag = random.nextInt(10);
        flag = 1;
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

        Animation anim = new AlphaAnimation(0.0f , 1.0f);
        anim.setDuration(1000); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);

        tv_opr.clearAnimation();
        tv_num2.clearAnimation();

        //if(flag <= 5 ) {

            tv_num1.setText(String.valueOf(n1));
            tv_num2.setText(String.valueOf(n2));

            tv_opr.setText("?");
            tv_op.setText(String.valueOf(op));

            btn_plus.setText("+");
            btn_minus.setText("-");
            btn_multiply.setText("*");
            btn_divide.setText("/");

            tv_opr.startAnimation(anim);
        //} else {

           /* int button_index = random.nextInt(4);

            if(button_index == 0) {
                btn_plus.setText(String.valueOf(n2));
                btn_minus.setText(String.valueOf(n2+1));
                btn_multiply.setText(String.valueOf(n2-1));
                btn_divide.setText(String.valueOf(n2+2));
            } else if(button_index == 1) {
                btn_plus.setText(String.valueOf(n2-1));
                btn_minus.setText(String.valueOf(n2));
                btn_multiply.setText(String.valueOf(n2)+1);
                btn_divide.setText(String.valueOf(n2+2));
            } else if(button_index == 2) {
                btn_plus.setText(String.valueOf(n2+1));
                btn_minus.setText(String.valueOf(n2+2));
                btn_multiply.setText(String.valueOf(n2));
                btn_divide.setText(String.valueOf(n2+4));
            } else {
                btn_plus.setText(String.valueOf(n2+2));
                btn_minus.setText(String.valueOf(n2+1));
                btn_multiply.setText(String.valueOf(n2-2));
                btn_divide.setText(String.valueOf(n2));
            }

            tv_num1.setText(String.valueOf(n1));
            tv_num2.setText("?");

            tv_opr.setText(String.valueOf(opr[idx]));
            //tv_opr.setText(opr[idx]);
            tv_op.setText(String.valueOf(op));


            tv_num2.startAnimation(anim);
        }*/

    }

    public void GotoMainActivity(View view) {
        Intent intent = new Intent(Easy.this , MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();

    }
}