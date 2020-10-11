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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class ClassicGame extends AppCompatActivity {

    TextView tv_classic_num1 , tv_classic_num2  , tv_classic_op , tv_classic_score , tv_classic_lives_left , tv_classic_high_score;
    TextView tv_classic_opr , tv_classic_high_score_text , tv_classic_score_text , tv_classic_lives_left_text;

    Button btn_classic_plus , btn_classic_minus , btn_classic_divide , btn_classic_multiply;

    Handler handler = new Handler();
    int flag = -1;
    DatabaseHelper db;
    int sz = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_game);

        getSupportActionBar().hide();

        tv_classic_num1 = findViewById(R.id.tv_classic_easy_num1);
        tv_classic_num2 = findViewById(R.id.tv_classic_easy_num2);
        tv_classic_op = findViewById(R.id.tv_classic_easy_op);
        tv_classic_score = findViewById(R.id.tv_classic_score);
        tv_classic_high_score = findViewById(R.id.tv_classic_high_score);
        tv_classic_lives_left = findViewById(R.id.tv_classic_lives_left);
        tv_classic_opr = findViewById(R.id.tv_classic_easy_opr1);
        tv_classic_score_text = findViewById(R.id.tv_classic_score_text);
        tv_classic_high_score_text = findViewById(R.id.tv_classic_high_score_text);
        tv_classic_lives_left_text = findViewById(R.id.tv_classic_lives_left_text);

        btn_classic_divide = findViewById(R.id.btn_classic_divide);
        btn_classic_minus = findViewById(R.id.btn_classic_minus);
        btn_classic_multiply = findViewById(R.id.btn_classic_multiply);
        btn_classic_plus = findViewById(R.id.btn_classic_plus);

        tv_classic_num1.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_classic_num2.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_classic_op.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_classic_score.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_classic_high_score.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_classic_lives_left.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_classic_opr.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_classic_lives_left_text.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_classic_high_score_text.setTextSize(sz * getResources().getDisplayMetrics().density);
        tv_classic_score_text.setTextSize(sz * getResources().getDisplayMetrics().density);

        btn_classic_plus.setTextSize(sz * getResources().getDisplayMetrics().density);
        btn_classic_minus.setTextSize(sz * getResources().getDisplayMetrics().density);
        btn_classic_multiply.setTextSize(sz * getResources().getDisplayMetrics().density);
        btn_classic_divide.setTextSize(sz * getResources().getDisplayMetrics().density);

        db = new DatabaseHelper(this);
        int lives = 3;
        String lives_left = String.valueOf(lives);

        String high_score = db.getData("classic" , "easy");
        Log.d("here", "onCreate: highhhhscoreeee " + high_score);
        tv_classic_high_score.setText(high_score);
        tv_classic_lives_left.setText(lives_left);

        show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void correct_answer(Button btn , String operation) {
        int score = Integer.parseInt(tv_classic_score.getText().toString().trim());

        score++;
        tv_classic_score.setText(String.valueOf(score));
        tv_classic_opr.setText("");

        Drawable d = getResources().getDrawable(R.drawable.green_button);
        btn.setBackground(d);
        DelayButtonColor(btn , operation);
        show();
    }

    public void wrong_answer(Button btn , String operation) {
        Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();


        String lives_left = tv_classic_lives_left.getText().toString().trim();


        int lives = Integer.parseInt(lives_left);

        lives--;

        lives_left = String.valueOf(lives);

        tv_classic_lives_left.setText(lives_left);

        Drawable d = getResources().getDrawable(R.drawable.red_button);
        btn.setBackground(d);
        DelayButtonColor(btn , operation);

        if(lives == 0) {
            endgame();
            return;
        }

        show();
    }

    public void check(Button btn , String operation) {
        int n1 = Integer.parseInt(tv_classic_num1.getText().toString().trim());
        int op = Integer.parseInt(tv_classic_op.getText().toString().trim());
        String operator = tv_classic_opr.getText().toString().trim();
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



    private void endgame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ClassicGame.this);
        builder.setTitle("Opps...All lives over!").setMessage("Your Score is : " + tv_classic_score.getText().toString().trim())
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(ClassicGame.this , MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                });
        AlertDialog alertDialog = builder.show();

        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Intent intent = new Intent(ClassicGame.this , MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

        db.insert("classic" , "easy" , Integer.parseInt(tv_classic_score.getText().toString().trim()));

    }

    public void OnClassicPlusClick(View view) {

        if(flag <= 5) {

            tv_classic_opr.setText(String.valueOf('+'));
            int n1 = Integer.parseInt(tv_classic_num1.getText().toString().trim());
            int n2 = Integer.parseInt(tv_classic_num2.getText().toString().trim());
            int op = Integer.parseInt(tv_classic_op.getText().toString().trim());

            Log.d("here " ,"OnPlusClick: " + n1 + " " + n2 + " " + op);

            if(n1 + n2 == op) {
                correct_answer(btn_classic_plus , "plus");
            }
            else {
                wrong_answer(btn_classic_plus , "plus");
            }
        } else check(btn_classic_plus , "plus");

    }


    public void OnClassicMinusClick(View view) {

        if(flag<=5) {
            tv_classic_opr.setText(String.valueOf('-'));
            int n1 = Integer.parseInt(tv_classic_num1.getText().toString().trim());
            int n2 = Integer.parseInt(tv_classic_num2.getText().toString().trim());
            int op = Integer.parseInt(tv_classic_op.getText().toString().trim());

            Log.d("here", "OnMinusClick: " + n1 + " " + n2 + " " + op);

            if(n1 - n2 == op) {
                correct_answer(btn_classic_minus , "minus");
            }
            else {
                wrong_answer(btn_classic_minus , "minus");
            }
        } else check(btn_classic_minus , "minus");

    }

    public void OnClassicMultiplyClick(View view) {

        if(flag <= 5) {
            tv_classic_opr.setText(String.valueOf('*'));

            int n1 = Integer.parseInt(tv_classic_num1.getText().toString().trim());
            int n2 = Integer.parseInt(tv_classic_num2.getText().toString().trim());
            int op = Integer.parseInt(tv_classic_op.getText().toString().trim());

            Log.d("here", "OnMultiplyClick: " + n1 + " " + n2 + " " + op);

            if(n1 * n2 == op) {
                correct_answer(btn_classic_multiply , "multiply");
            }
            else {
                wrong_answer(btn_classic_multiply , "multiply");
            }
        } else check(btn_classic_multiply , "multiply");

    }

    public void OnClassicDivideClick(View view) {

        if(flag <= 5) {
            Log.d("here", "OnDivideClick: aaaa");
            tv_classic_opr.setText(String.valueOf('/'));

            Log.d("here", "OnDivideClick: bbbb");
            int n1 = Integer.parseInt(tv_classic_num1.getText().toString().trim());
            int n2 = Integer.parseInt(tv_classic_num2.getText().toString().trim());
            int op = Integer.parseInt(tv_classic_op.getText().toString().trim());

            Log.d("here", "OnDivideClick: " + n1 + " " + n2 + " " + op);

            if(n1 / n2 == op && n1%n2 == 0) {
                correct_answer(btn_classic_divide , "divide");
            }
            else {
                wrong_answer(btn_classic_divide , "divide");
            }
        } else check(btn_classic_divide , "divide");

    }

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

        while(idx == 3 && n1 % n2 !=0) {
            idx = random.nextInt(4);
        }

        int op = 1;

        if(n2 > n1) {
            int temp = n1;
            n1 = n2;
            n2 = temp;
        }

        switch (idx) {
            case 0 : op = n1 + n2;break;
            case 1 : op = n1 - n2;break;
            case 2 : op = n1 * n2;break;
            case 3 : op = n1 / n2;break;
        }


        Animation anim = new AlphaAnimation(0.0f , 1.0f);
        anim.setDuration(1000); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        tv_classic_opr.clearAnimation();
        tv_classic_num2.clearAnimation();

        if(flag <= 5 ) {

            tv_classic_num1.setText(String.valueOf(n1));
            tv_classic_num2.setText(String.valueOf(n2));

            tv_classic_opr.setText("?");
            tv_classic_op.setText(String.valueOf(op));

            btn_classic_plus.setText("+");
            btn_classic_minus.setText("-");
            btn_classic_multiply.setText("*");
            btn_classic_divide.setText("/");

            tv_classic_opr.startAnimation(anim);
        } else {

            int button_index = random.nextInt(4);

            if(button_index == 0) {
                btn_classic_plus.setText(String.valueOf(n2));
                btn_classic_minus.setText(String.valueOf(n2+1));
                btn_classic_multiply.setText(String.valueOf(n2-1));
                btn_classic_divide.setText(String.valueOf(n2+2));
            } else if(button_index == 1) {
                btn_classic_plus.setText(String.valueOf(n2-1));
                btn_classic_minus.setText(String.valueOf(n2));
                btn_classic_multiply.setText(String.valueOf(n2)+1);
                btn_classic_divide.setText(String.valueOf(n2+2));
            } else if(button_index == 2) {
                btn_classic_plus.setText(String.valueOf(n2+1));
                btn_classic_minus.setText(String.valueOf(n2+2));
                btn_classic_multiply.setText(String.valueOf(n2));
                btn_classic_divide.setText(String.valueOf(n2+4));
            } else {
                btn_classic_plus.setText(String.valueOf(n2+2));
                btn_classic_minus.setText(String.valueOf(n2+1));
                btn_classic_multiply.setText(String.valueOf(n2-2));
                btn_classic_divide.setText(String.valueOf(n2));
            }

            tv_classic_num1.setText(String.valueOf(n1));
            tv_classic_num2.setText("?");

            tv_classic_opr.setText(String.valueOf(opr[idx]));

            tv_classic_op.setText(String.valueOf(op));

            tv_classic_num2.startAnimation(anim);
        }

    }

    public void GotoMainActivity(View view) {
        Intent intent = new Intent(ClassicGame.this , MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}