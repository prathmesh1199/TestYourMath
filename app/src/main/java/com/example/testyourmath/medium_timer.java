package com.example.testyourmath;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class medium_timer extends AppCompatActivity {

    TextView tv_medium_timer_num1 , tv_medium_timer_num2  , tv_medium_timer_op , tv_medium_timer_score , tv_medium_timer_time_left , tv_medium_timer_high_score;
    TextView tv_medium_timer_opr , tv_medium_timer_score_text , tv_medium_timer_time_left_text , tv_medium_timer_high_score_text;
    Button btn_medium_timer_plus , btn_medium_timer_minus , btn_medium_timer_multiply , btn_medium_timer_divide;
    Handler handler = new Handler();

    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 60000;
    private boolean timerrunning;

    String current_score , highest_score;

    int start_time = 60000 , flag = -1 , sz = 9;
    DatabaseHelper db;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void GotoMainActivity(View view) {
        Intent intent = new Intent(medium_timer.this , MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medium_timer);

        tv_medium_timer_num1 = findViewById(R.id.tv_medium_timer_easy_num1);
        tv_medium_timer_num2 = findViewById(R.id.tv_medium_timer_easy_num2);
        tv_medium_timer_opr = findViewById(R.id.tv_medium_timer_easy_opr1);
        tv_medium_timer_op = findViewById(R.id.tv_medium_timer_easy_op);
        tv_medium_timer_score = findViewById(R.id.tv_medium_timer_score);
        tv_medium_timer_time_left = findViewById(R.id.tv_medium_timer_time_left);
        tv_medium_timer_high_score = findViewById(R.id.tv_medium_timer_high_score);
        tv_medium_timer_high_score_text = findViewById(R.id.tv_medium_timer_high_score_text);
        tv_medium_timer_time_left_text = findViewById(R.id.tv_medium_timer_time_left_text);
        tv_medium_timer_score_text = findViewById(R.id.tv_medium_timer_score_text);

        btn_medium_timer_plus = findViewById(R.id.btn_medium_timer_plus);
        btn_medium_timer_minus = findViewById(R.id.btn_medium_timer_minus);
        btn_medium_timer_multiply = findViewById(R.id.btn_medium_timer_multiply);
        btn_medium_timer_divide = findViewById(R.id.btn_medium_timer_divide);

        db = new DatabaseHelper(medium_timer.this);

        String high_score = db.getData("timer" , "medium");

        highest_score = high_score;

        tv_medium_timer_high_score.setText(high_score);


        tv_medium_timer_num1.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_medium_timer_num2.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_medium_timer_op.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_medium_timer_opr.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_medium_timer_score_text.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_medium_timer_score.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_medium_timer_time_left_text.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_medium_timer_time_left.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_medium_timer_score.setTextSize(sz * getResources().getDisplayMetrics().density);
        btn_medium_timer_divide.setTextSize(sz * getResources().getDisplayMetrics().density);
        btn_medium_timer_minus.setTextSize(sz * getResources().getDisplayMetrics().density);
        btn_medium_timer_multiply.setTextSize(sz * getResources().getDisplayMetrics().density);
        btn_medium_timer_plus.setTextSize(sz * getResources().getDisplayMetrics().density);

        
        countDownTimer = new CountDownTimer(start_time , 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv_medium_timer_time_left.setText(""+String.format("%d : %d ",
                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            @Override
            public void onFinish() {
                AlertDialog.Builder builder = new AlertDialog.Builder(medium_timer.this);
                builder.setTitle("Opps..Time Up.!").setMessage("Your Score is : " + tv_medium_timer_score.getText().toString().trim())
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(medium_timer.this , MainActivity.class);
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
                        Intent intent = new Intent(medium_timer.this , MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();
                    }
                });

                Log.d("here", "onFinish: easyyyy 1");
                db.insert("timer" , "medium" , Integer.parseInt(tv_medium_timer_score.getText().toString().trim()));
                Log.d("here", "onFinish: easyyyy 2");

            }
        };

        countDownTimer.start();
        show();

    }

    public void medium_correct_answer(Button btn , String operation) {
        int score = Integer.parseInt(tv_medium_timer_score.getText().toString().trim());

        score++;
        tv_medium_timer_score.setText(String.valueOf(score));
        tv_medium_timer_opr.setText("");

        Drawable d = getResources().getDrawable(R.drawable.green_button);
        btn.setBackground(d);
        MediumDelayButtonColor(btn , operation);
        show();
    }

    public void medium_wrong_answer(Button btn , String operation) {
        Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
        Drawable d = getResources().getDrawable(R.drawable.red_button);
        btn.setBackground(d);
        MediumDelayButtonColor(btn , operation);
        show();
    }

    public void medium_check(Button btn , String operation) {
        int n1 = Integer.parseInt(tv_medium_timer_num1.getText().toString().trim());
        int op = Integer.parseInt(tv_medium_timer_op.getText().toString().trim());
        String operator = tv_medium_timer_opr.getText().toString().trim();
        int num = Integer.parseInt(btn.getText().toString().trim());
        if(operator.equals("+")) {
            int n2 = op - n1;

            if(n2 == num) {
                medium_correct_answer(btn , operation);
            } else {
                medium_wrong_answer(btn , operation);
            }

        } else if(operator.equals("-")) {

            int n2 = n1 - op;
            if(num == n2) medium_correct_answer(btn , operation);
            else medium_wrong_answer(btn , operation);

        } else if(operator.equals("*")) {
            int n2 = op / n1;

            if(num == n2) medium_correct_answer(btn , operation);
            else medium_wrong_answer(btn , operation);

        } else {

            int n2 = n1 / op;

            if(n1%op!=0) {
                medium_wrong_answer(btn , operation);
                show();
            } else if(num == n2) medium_correct_answer(btn , operation);
            else medium_wrong_answer(btn , operation);
        }
    }

    public void OnMediumPlusClick(View view) {

        if(flag <= 5) {
            tv_medium_timer_opr.setText(String.valueOf('+'));
            Log.d("here", "OnPlusClick: aaaaaaaa");
            int n1 = Integer.parseInt(tv_medium_timer_num1.getText().toString().trim());
            Log.d("here", "OnPlusClick: aaaaaaaa n1 " + n1);
            int n2 = Integer.parseInt(tv_medium_timer_num2.getText().toString().trim());
            Log.d("here", "OnPlusClick: aaaaaaaa n2 " + n2);
            int op = Integer.parseInt(tv_medium_timer_op.getText().toString().trim());
            Log.d("here", "OnPlusClick: aaaaaaaa op " + op);

            Log.d("here " ,"OnPlusClick: " + n1 + " " + n2 + " " + op);

            if(n1 + n2 == op) {
                medium_correct_answer(btn_medium_timer_plus , "plus");
            }
            else {
                medium_wrong_answer(btn_medium_timer_plus , "plus");
            }
        } else {
            medium_check(btn_medium_timer_plus , "plus");
        }

    }

    public void OnMediumMinusClick(View view) {

        if(flag <= 5) {
            tv_medium_timer_opr.setText(String.valueOf('-'));
            int n1 = Integer.parseInt(tv_medium_timer_num1.getText().toString().trim());
            int n2 = Integer.parseInt(tv_medium_timer_num2.getText().toString().trim());
            int op = Integer.parseInt(tv_medium_timer_op.getText().toString().trim());

            Log.d("here", "OnMinusClick: " + n1 + " " + n2 + " " + op);

            if(n1 - n2 == op) {
                medium_correct_answer(btn_medium_timer_minus , "minus");
            }
            else {
                medium_wrong_answer(btn_medium_timer_minus , "minus");
            }
        } else {
            medium_check(btn_medium_timer_minus , "minus");

        }

    }

    public void OnMediumMultiplyClick(View view) {

        if(flag <= 5) {
            tv_medium_timer_opr.setText(String.valueOf('*'));

            int n1 = Integer.parseInt(tv_medium_timer_num1.getText().toString().trim());
            int n2 = Integer.parseInt(tv_medium_timer_num2.getText().toString().trim());
            int op = Integer.parseInt(tv_medium_timer_op.getText().toString().trim());

            Log.d("here", "OnMultiplyClick: " + n1 + " " + n2 + " " + op);

            if(n1 * n2 == op) {
                medium_correct_answer(btn_medium_timer_multiply , "multiply");

            }
            else {
                medium_wrong_answer(btn_medium_timer_multiply , "multiply")  ;
            }
        } else {
            medium_check(btn_medium_timer_multiply , "multiply");
        }

    }

    public void OnMediumDivideClick(View view) {

        if(flag <= 5) {
            tv_medium_timer_opr.setText(String.valueOf('/'));

            int n1 = Integer.parseInt(tv_medium_timer_num1.getText().toString().trim());
            int n2 = Integer.parseInt(tv_medium_timer_num2.getText().toString().trim());
            int op = Integer.parseInt(tv_medium_timer_op.getText().toString().trim());

            Log.d("here", "OnDivideClick: " + n1 + " " + n2 + " " + op);

            if(n1 / n2 == op && n1%n2 == 0) {
                medium_correct_answer(btn_medium_timer_divide , "divide");
            }
            else {
                medium_wrong_answer(btn_medium_timer_divide , "divide");
            }
        } else {
            medium_check(btn_medium_timer_divide , "divide");
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

    public void MediumDelayButtonColor(Button prm_Button , final String operation)
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
        //flag = 1;
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
        tv_medium_timer_opr.clearAnimation();
        tv_medium_timer_num2.clearAnimation();

        if(flag <= 5 ) {

            tv_medium_timer_num1.setText(String.valueOf(n1));
            tv_medium_timer_num2.setText(String.valueOf(n2));

            tv_medium_timer_opr.setText("?");
            tv_medium_timer_op.setText(String.valueOf(op));

            btn_medium_timer_plus.setText("+");
            btn_medium_timer_minus.setText("-");
            btn_medium_timer_multiply.setText("*");
            btn_medium_timer_divide.setText("/");

            tv_medium_timer_opr.startAnimation(anim);
        } else {

            int button_index = random.nextInt(4);

            if(button_index == 0) {
                btn_medium_timer_plus.setText(String.valueOf(n2));
                btn_medium_timer_minus.setText(String.valueOf(n2+1));
                btn_medium_timer_multiply.setText(String.valueOf(n2-1));
                btn_medium_timer_divide.setText(String.valueOf(n2+2));
            } else if(button_index == 1) {
                btn_medium_timer_plus.setText(String.valueOf(n2-1));
                btn_medium_timer_minus.setText(String.valueOf(n2));
                btn_medium_timer_multiply.setText(String.valueOf(n2)+1);
                btn_medium_timer_divide.setText(String.valueOf(n2+2));
            } else if(button_index == 2) {
                btn_medium_timer_plus.setText(String.valueOf(n2+1));
                btn_medium_timer_minus.setText(String.valueOf(n2+2));
                btn_medium_timer_multiply.setText(String.valueOf(n2));
                btn_medium_timer_divide.setText(String.valueOf(n2+4));
            } else {
                btn_medium_timer_plus.setText(String.valueOf(n2+2));
                btn_medium_timer_minus.setText(String.valueOf(n2+1));
                btn_medium_timer_multiply.setText(String.valueOf(n2-2));
                btn_medium_timer_divide.setText(String.valueOf(n2));
            }

            tv_medium_timer_num1.setText(String.valueOf(n1));
            tv_medium_timer_num2.setText("?");

            tv_medium_timer_opr.setText(String.valueOf(opr[idx]));
            //tv_opr.setText(opr[idx]);
            tv_medium_timer_op.setText(String.valueOf(op));


            tv_medium_timer_num2.startAnimation(anim);
        }

    }
}