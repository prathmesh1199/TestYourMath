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

public class hard_timer extends AppCompatActivity {

    TextView tv_hard_num1 , tv_hard_num2  , tv_hard_op , tv_hard_score , tv_hard_time_left , tv_hard_high_score , tv_hard_num3 , tv_hard_opr2;
    TextView tv_hard_opr1 , tv_hard_high_score_text , tv_hard_time_left_text , tv_score_text;
    Button btn_hard_plus , btn_hard_minus , btn_hard_multiply , btn_hard_divide;
    Handler handler = new Handler();

    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 60000;
    private boolean timerrunning;

    String current_score , highest_score;
    int sz = 9;

    int start_time = 60000 , flag = -1;
    DatabaseHelper db;


    // current_guess = 1 : guess 1st operator.
    // current_guess = 2 : guess 2nd operator
    int current_guess = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard_timer);

        tv_hard_num1 = findViewById(R.id.tv_hard_num1);
        tv_hard_num2 = findViewById(R.id.tv_hard_num2);
        tv_hard_num3 = findViewById(R.id.tv_hard_num3);
        tv_hard_opr1 = findViewById(R.id.tv_hard_opr1);
        tv_hard_opr2 = findViewById(R.id.tv_hard_opr2);
        tv_hard_op = findViewById(R.id.tv_hard_op);
        tv_hard_score = findViewById(R.id.tv_hard_score);
        tv_hard_time_left = findViewById(R.id.tv_hard_time_left);
        tv_hard_high_score = findViewById(R.id.tv_hard_high_score);
        tv_hard_high_score_text = findViewById(R.id.tv_hard_high_score_text);
        tv_hard_time_left_text = findViewById(R.id.tv_hard_time_left_text);
        tv_score_text = findViewById(R.id.tv_hard_score_text);

        btn_hard_plus = findViewById(R.id.btn_hard_plus);
        btn_hard_minus = findViewById(R.id.btn_hard_minus);
        btn_hard_multiply = findViewById(R.id.btn_hard_multiply);
        btn_hard_divide = findViewById(R.id.btn_hard_divide);

        tv_hard_num1.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_hard_num2.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_hard_num2.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_hard_opr1.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_hard_opr2.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_hard_op.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_hard_score.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_hard_time_left.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_hard_high_score.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_hard_high_score_text.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_hard_time_left_text.setTextSize(sz * getResources().getDisplayMetrics().density);
        btn_hard_plus.setTextSize(sz * getResources().getDisplayMetrics().density);
        btn_hard_minus.setTextSize(sz * getResources().getDisplayMetrics().density);
        btn_hard_multiply.setTextSize(sz * getResources().getDisplayMetrics().density);
        btn_hard_divide.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_score_text.setTextSize(sz * getResources().getDisplayMetrics().density);

        db = new DatabaseHelper(hard_timer.this);

        String high_score = db.getData("timer" , "hard");

        highest_score = high_score;

        tv_hard_high_score.setText(high_score);



        countDownTimer = new CountDownTimer(start_time , 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv_hard_time_left.setText(""+String.format("%d : %d ",
                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            @Override
            public void onFinish() {
                AlertDialog.Builder builder = new AlertDialog.Builder(hard_timer.this);
                builder.setTitle("Opps..Time Up.!").setMessage("Your Score is : " + tv_hard_score.getText().toString().trim())
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(hard_timer.this , MainActivity.class);
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
                        Intent intent = new Intent(hard_timer.this , MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();
                    }
                });

                Log.d("here", "onFinish: easyyyy 1");
                db.insert("timer" , "hard" , Integer.parseInt(tv_hard_score.getText().toString().trim()));
                Log.d("here", "onFinish: easyyyy 2");

            }
        };

        countDownTimer.start();
        show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void correct_answer(Button btn , String operation) {
        int score = Integer.parseInt(tv_hard_score.getText().toString().trim());

        score++;
        tv_hard_score.setText(String.valueOf(score));
        tv_hard_opr1.setText("");

        Drawable d = getResources().getDrawable(R.drawable.green_button);
        btn.setBackground(d);
        DelayButtonColor(btn , operation);
        show();
    }

    public void wrong_answer(Button btn , String operation) {
        //Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
        Drawable d = getResources().getDrawable(R.drawable.red_button);
        btn.setBackground(d);
        DelayButtonColor(btn , operation);
        show();
    }


    public void OnHardPlusClick(View view) {

        Thread t = new Thread(){
            @Override
            public void run() {

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        Animation animation = tv_hard_opr1.getAnimation();
                        if(animation != null && animation.hasStarted() && !animation.hasEnded()){
                            tv_hard_opr1.setText(String.valueOf('+'));
                            tv_hard_opr1.clearAnimation();
                            startAnimation(tv_hard_opr2);
                        } else {
                            tv_hard_opr2.setText(String.valueOf('+'));
                            int n1 = Integer.parseInt(tv_hard_num1.getText().toString().trim());
                            int n2 = Integer.parseInt(tv_hard_num2.getText().toString().trim());
                            int n3 = Integer.parseInt(tv_hard_num3.getText().toString().trim());
                            int op = Integer.parseInt(tv_hard_op.getText().toString().trim());

                            String opr1 = tv_hard_opr1.getText().toString().trim();

                            Log.d("calculate", "OnHardPlusClick: " + n1 + " " + opr1 + " " + n2 + " + " + n3 + " = " + op);

                            if(opr1.equals("+")) {
                                Log.d("calculate", "OnHardPlusClick: Plus" + (n1 + n2 + n3)  + " " + op + " " + (n1+n2+n3 == op));
                                if(n1 + n2 + n3 == op) correct_answer(btn_hard_plus , "plus");
                                else wrong_answer(btn_hard_plus , "plus");
                            } else if(opr1.equals("-")) {
                                Log.d("calculate", "OnHardPlusClick: Minus" + (n1 - n2 + n3)  + " " + op + " " + (n1-n2+n3 == op));
                                if(n1 - n2 + n3 == op) correct_answer(btn_hard_plus , "plus");
                                else wrong_answer(btn_hard_plus , "plus");
                            } else if(opr1.equals("*")) {
                                Log.d("calculate", "OnHardPlusClick: Multiply" + ((n1 * n2) + n3)  + " " + op + " " + ((n1*n2)+n3 == op));
                                if((n1*n2) + n3 == op)  correct_answer(btn_hard_plus , "plus");
                                else wrong_answer(btn_hard_plus , "plus");
                            } else {
                                Log.d("calculate", "OnHardPlusClick: Divide" + ((n1 / n2) + n3)  + " " + op + " " + ((n1/n2)+n3 == op) + " " + (n1%n2)) ;
                                if(n1%n2!=0 || (n1/n2) + n3 != op) wrong_answer(btn_hard_plus , "plus");
                                else correct_answer(btn_hard_plus,"plus");
                            }

                        }

                    }
                });

            }
        };
        t.start();
    }

    public void OnHardMinusClick(View view) {

        Thread t = new Thread(){
            @Override

            public void run() {


                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Animation animation = tv_hard_opr1.getAnimation();
                        if(animation != null && animation.hasStarted() && !animation.hasEnded()){
                            tv_hard_opr1.setText(String.valueOf('-'));
                            tv_hard_opr1.clearAnimation();
                            startAnimation(tv_hard_opr2);
                        } else {
                            tv_hard_opr2.setText(String.valueOf('-'));
                            int n1 = Integer.parseInt(tv_hard_num1.getText().toString().trim());
                            int n2 = Integer.parseInt(tv_hard_num2.getText().toString().trim());
                            int n3 = Integer.parseInt(tv_hard_num3.getText().toString().trim());
                            int op = Integer.parseInt(tv_hard_op.getText().toString().trim());

                            String opr1 = tv_hard_opr1.getText().toString().trim();

                            Log.d("calculate", "OnHardMinusClick: " + n1 + " " + opr1 + " " + n2 + " " + " - " + n3 + " = " + op);

                            if(opr1.equals("+")) {
                                Log.d("calculate", "OnHardMinusClick: Plus : " + (n1+n2-n3) + " " + op + " " + (n1+n2-n3 == op) );
                                if(n1 + n2 - n3 == op) correct_answer(btn_hard_minus , "minus");
                                else wrong_answer(btn_hard_minus , "minus");
                            } else if(opr1.equals("-")) {
                                Log.d("calculate", "OnHardMinusClick: Minus : " + (n1-n2-n3) + " " + op + " " + (n1-n2-n3 == op) );
                                if(n1 - n2 - n3 == op) correct_answer(btn_hard_minus , "minus");
                                else wrong_answer(btn_hard_minus , "minus");
                            } else if(opr1.equals("*")) {
                                Log.d("calculate", "OnHardMinusClick: Multiply : " + ((n1*n2)-n3) + " " + op + " " + ((n1*n2)-n3 == op) );
                                if((n1*n2) - n3 == op)  correct_answer(btn_hard_minus , "minus");
                                else wrong_answer(btn_hard_minus , "minus");
                            } else {
                                Log.d("calculate", "OnHardMinusClick: Divide : " + ((n1/n2)-n3) + " " + op + " " + ((n1/n2)-n3 == op) + " " + (n1%n2));
                                if(n1%n2!=0 || (n1/n2) - n3 != op) wrong_answer(btn_hard_minus , "minus");
                                else correct_answer(btn_hard_minus,"minus");
                            }
                        }
                    }
                });

            }
        };
        t.start();;


    }

    public void OnHardMultiplyClick(View view) {

        Thread t = new Thread(){
            @Override
            public void run() {

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Animation animation = tv_hard_opr1.getAnimation();
                        if(animation != null && animation.hasStarted() && !animation.hasEnded()){
                            tv_hard_opr1.setText(String.valueOf('*'));
                            tv_hard_opr1.clearAnimation();
                            startAnimation(tv_hard_opr2);
                        } else {
                            tv_hard_opr2.setText(String.valueOf('*'));
                            int n1 = Integer.parseInt(tv_hard_num1.getText().toString().trim());
                            int n2 = Integer.parseInt(tv_hard_num2.getText().toString().trim());
                            int n3 = Integer.parseInt(tv_hard_num3.getText().toString().trim());
                            int op = Integer.parseInt(tv_hard_op.getText().toString().trim());

                            String opr1 = tv_hard_opr1.getText().toString().trim();

                            Log.d("calculate", "OnHardMultiplyClick: " + n1 + " " + opr1 + " " + n2 + " * " + n3 + " = " + op);

                            if(opr1.equals("+")) {
                                Log.d("calculate", "OnHardMultiplyClick: Plus" + ((n2*n3)+n1) + " " + op + " " + ((n2*n3)+n1 == op));
                                if((n2 * n3) + n1 == op) correct_answer(btn_hard_multiply , "multiply");
                                else wrong_answer(btn_hard_multiply , "multiply");
                            } else if(opr1.equals("-")) {
                                Log.d("calculate", "OnHardMultiplyClick: Minus" + (n1-(n2*n3)) + " " + op + " " + (n1-(n2*n3) == op));
                                if(n1 - (n2 * n3) == op) correct_answer(btn_hard_multiply , "multiply");
                                else wrong_answer(btn_hard_multiply , "multiply");
                            } else if(opr1.equals("*")) {
                                Log.d("calculate", "OnHardMultiplyClick: Multiply" + ((n2*n3)*n1) + " " + op + " " + ((n2*n3)*n1 == op));
                                if((n1*n2) * n3 == op)  correct_answer(btn_hard_multiply , "multiply");
                                else wrong_answer(btn_hard_multiply , "multiply");
                            } else {
                                Log.d("calculate", "OnHardMultiplyClick: Divide" + ((n1/n2)*n3) + " " + op + " " + ((n1/n2)*n3 == op));
                                if(n1%n2!=0 || (n1/n2) * n3 != op) wrong_answer(btn_hard_multiply , "multiply");
                                else correct_answer(btn_hard_multiply,"multiply");
                            }

                        }
                    }
                });
            }
        };
        t.start();
    }

    public void OnHardDivideClick(View view) {

        Thread t = new Thread(){
            @Override
            public void run() {

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Animation animation = tv_hard_opr1.getAnimation();
                        if(animation != null && animation.hasStarted() && !animation.hasEnded()){
                            tv_hard_opr1.setText(String.valueOf('/'));
                            tv_hard_opr1.clearAnimation();
                            startAnimation(tv_hard_opr2);
                        } else {
                            tv_hard_opr2.setText(String.valueOf('/'));
                            int n1 = Integer.parseInt(tv_hard_num1.getText().toString().trim());
                            int n2 = Integer.parseInt(tv_hard_num2.getText().toString().trim());
                            int n3 = Integer.parseInt(tv_hard_num3.getText().toString().trim());
                            int op = Integer.parseInt(tv_hard_op.getText().toString().trim());

                            String opr1 = tv_hard_opr1.getText().toString().trim();

                            Log.d("calculate", "OnHardDivideClick: " + n1 + " " + opr1 + " " + n2 + " / " + n3 + " = " + op);

                            if(opr1.equals("+")) {
                                Log.d("calculate", "OnHardDivideClick: Plus" + ((n2/n3)+n1) + " " + op + " " + ((n2/n3)+n1 == op));
                                if(n2%n3!=0 || (n2/n3) + n1 != op) wrong_answer(btn_hard_divide , "divide");
                                else correct_answer(btn_hard_divide,"divide");
                            } else if(opr1.equals("-")) {
                                Log.d("calculate", "OnHardDivideClick: Minus" + (n1-(n2/n3)) + " " + op + " " + (n1-(n2/n3) == op));
                                if(n2%n3!=0 || n1 - (n2/n3) != op) wrong_answer(btn_hard_divide , "divide");
                                else correct_answer(btn_hard_divide,"divide");
                            } else if(opr1.equals("*")) {
                                int temp = n1 * n2;
                                Log.d("calculate", "OnHardDivideClick: Multiply" + (temp + " " + op + " " + (temp/n3) + " " +  ((n2/n3)+n1 == op)));
                                if(temp%n3!=0 || temp / n3 != op) wrong_answer(btn_hard_divide , "divide");
                                else correct_answer(btn_hard_divide,"divide");
                            } else {
                                int temp = n2 * n3;
                                Log.d("calculate", "OnHardDivideClick: Multiply" + (temp + " " + op + " " + (n1/temp) + " " +  ((n1/temp) == op)));
                                if(n1%temp!=0 || (n1 / temp) != op) wrong_answer(btn_hard_divide , "divide");
                                else correct_answer(btn_hard_divide,"divide");
                            }

                        }
                    }
                });
            }
        };
        t.start();
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
        int n1 = random.nextInt(10) + 6 ;
        int n2 = random.nextInt(7) + 3;
        int n3 = random.nextInt(3) + 1;

        Drawable d = getResources().getDrawable(R.drawable.mybutton);

        char[] opr = new char[4];
        opr[0] = '+';
        opr[1] = '-';
        opr[2] = '*';
        opr[3] = '/';

        int op = 0;
        int idx1 = random.nextInt(4);
        int idx2 = random.nextInt(4);

        if(idx1 == 0 && idx2 == 0) {
            op = n1 + n2 + n3;
        } else if(idx1 == 0 && idx2 == 1) {
            op = (n1 + n2) - n3;
        } else if(idx1 == 0 && idx2 == 2) {
            op = (n2 * n3) + n1;
        } else if(idx1 == 0 && idx2 == 3) {
            while(idx2 == 3 && n2%n3 != 0) {
                if(n2%2 == 0) n3 = n2 / 2;
                else idx2 = random.nextInt(4);
            }
            op = get_result(n2 , n3 , idx2) + n1;
        } else if(idx1 == 1 && idx2 == 0) {
            op = (n1 - n2) + n3;
            while(op < 0) {
                n1*=2;
                op = (n1 - n2) + 3;
            }
        } else if(idx1 == 1 && idx2 == 1) {
            op = (n1 - n2 - n3);
            while(op < 0) {
                n1*=2;
                op = (n1 - n2 - n3);
            }
        } else if(idx1 == 1 && idx2 == 2) {
            op = n1 - (n2 * n3);
            while(op < 0) {
                n1*=2;
                op = (n1 - (n2*n3));
            }
        } else if(idx1 == 1 && idx2 == 3) {
            while(idx2 == 3 && n1%n2 != 0) {
                if(n2%2 == 0) n3 = n2 / 2;
                else idx2 = random.nextInt(4);
            }
            int ans = get_result(n2 , n3 , idx2);
            op = n1 - ans;
            while(op < 0) {
                n1*=2;
                op = n1 - ans;
            }
        } else if(idx1 == 2 && idx2 == 0) {
            op = (n1 * n2) + n3;
        } else if(idx1 == 2 && idx2 == 1) {
            op = (n1 * n2) - n3;
            while(op < 0) {
                n1*=2;
                op = (n1*n2) - n3;
            }
        } else if(idx1 == 2 && idx2 == 2) {
            op = n1 * n2 * n3;
        } else if(idx1 == 2 && idx2 == 3) {
            op = (n1 * n2);
            while(idx2 == 3 && op%n3 != 0) {
                if(op % 2 == 0) n3 = op / 2;
                else idx2 = random.nextInt(4);
            }
            op = get_result(op , n3 , idx2);
        } else if(idx1 == 3 && idx2 == 0) {
            while(idx1 == 3 && n1%n2!=0) {
                if(n1 % 2 == 0) n2 = n1 / 2;
                else idx1 = random.nextInt(4);
            }
            op = get_result(n1 , n2 , idx1) + n3;
        } else if(idx1 == 3 && idx2 == 1) {
            while(idx1 == 3 && n1%n2!=0) {
                if(n1 % 2 == 0) n2 = n1 / 2;
                else idx1 = random.nextInt(4);
            }
            op = get_result(n1 , n2 , idx1) - n3;
        } else if (idx1 == 3 && idx2 == 2) {
            while(idx1 == 3 && n1%n2!=0) {
                if(n1 % 2 == 0) n2 = n1 / 2;
                else idx1 = random.nextInt(4);
            }
            if(idx1 == 0 || idx1 == 1) {
                op = get_result(n2,n3,idx2);
                if(idx1 == 0) op = op + n1;
                else op = n1 - op;
            } else {
                op = get_result(n1 , n2 , idx1);
                op = get_result(op , n3 , idx2);
            }

        } else {
            int mul = n2 * n3;
            if(n1%mul != 0) {
                n1 = mul * 2;
            }

            op = n1 / mul;
        }


        tv_hard_opr1.clearAnimation();
        tv_hard_opr2.clearAnimation();


        tv_hard_num1.setText(String.valueOf(n1));
        tv_hard_num2.setText(String.valueOf(n2));
        tv_hard_num3.setText(String.valueOf(n3));

        tv_hard_opr1.setText("?");
        tv_hard_opr2.setText("?");
        tv_hard_op.setText(String.valueOf(op));

        btn_hard_plus.setText("+");
        btn_hard_minus.setText("-");
        btn_hard_multiply.setText("*");
        btn_hard_divide.setText("/");

        startAnimation(tv_hard_opr1);

    }

    int get_result(int n1 , int n2 , int c) {
        if(c == 0) return n1 + n2;
        else if(c == 1) return n1 - n2;
        else if(c == 2) return n1 * n2;
        else return n1 / n2;
    }

    public void startAnimation(TextView tv) {
        Animation anim = new AlphaAnimation(0.0f , 1.0f);
        anim.setDuration(1000); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        tv.startAnimation(anim);
    }

    public void GotoMainActivity(View view) {
        Intent intent = new Intent(hard_timer.this , MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();

    }
}