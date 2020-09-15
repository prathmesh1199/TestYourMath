package com.example.testyourmath;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Easy extends AppCompatActivity {

    TextView tv_num1 , tv_num2  , tv_op , tv_score , tv_time_left , tv_high_score;
    EditText edt_opr;

    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 60000;
    private boolean timerrunning;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);

        getSupportActionBar().hide();

        tv_num1 = findViewById(R.id.tv_easy_num1);
        tv_num2 = findViewById(R.id.tv_easy_num2);
        edt_opr = findViewById(R.id.edt_easy_opr1);
        tv_op = findViewById(R.id.tv_easy_op);
        tv_score = findViewById(R.id.tv_score);
        tv_time_left = findViewById(R.id.tv_time_left);
        tv_high_score = findViewById(R.id.tv_high_score);

        db = new DatabaseHelper(Easy.this);
        String high_score = "High Score : ";
        high_score += db.getData();

        tv_high_score.setText(high_score);

        countDownTimer = new CountDownTimer(6000 , 1000) {
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

                builder.create();
                builder.show();

                Log.d("here", "onFinish: easyyyy 1");
                db.insert("easy" , "level1" , Integer.parseInt(tv_score.getText().toString().trim()));
                Log.d("here", "onFinish: easyyyy 2");
            }
        };

        countDownTimer.start();
        show();

    }

    public void OnPlusClick(View view) {
        edt_opr.setText(String.valueOf('+'));
        int n1 = Integer.parseInt(tv_num1.getText().toString().trim());
        int n2 = Integer.parseInt(tv_num2.getText().toString().trim());
        int op = Integer.parseInt(tv_op.getText().toString().trim());

        Log.d("here " ,"OnPlusClick: " + n1 + " " + n2 + " " + op);

        if(n1 + n2 == op) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            int score = Integer.parseInt(tv_score.getText().toString().trim());

            score++;
            tv_score.setText(String.valueOf(score));
            edt_opr.setText("");
            show();
        }
        else Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
    }

    public void OnMinusClick(View view) {
        edt_opr.setText(String.valueOf('-'));
        int n1 = Integer.parseInt(tv_num1.getText().toString().trim());
        int n2 = Integer.parseInt(tv_num2.getText().toString().trim());
        int op = Integer.parseInt(tv_op.getText().toString().trim());

        Log.d("here", "OnMinusClick: " + n1 + " " + n2 + " " + op);

        if(n1 - n2 == op) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();

            int score = Integer.parseInt(tv_score.getText().toString().trim());

            score++;
            tv_score.setText(String.valueOf(score));
            edt_opr.setText("");
            show();
        }
        else Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();

    }

    public void OnMultiplyClick(View view) {
        edt_opr.setText(String.valueOf('*'));

        int n1 = Integer.parseInt(tv_num1.getText().toString().trim());
        int n2 = Integer.parseInt(tv_num2.getText().toString().trim());
        int op = Integer.parseInt(tv_op.getText().toString().trim());

        Log.d("here", "OnMultiplyClick: " + n1 + " " + n2 + " " + op);

        if(n1 * n2 == op) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();

            int score = Integer.parseInt(tv_score.getText().toString().trim());

            score++;
            tv_score.setText(String.valueOf(score));
            edt_opr.setText("");
            show();
        }
        else Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();

    }

    public void OnDivideClick(View view) {
        edt_opr.setText(String.valueOf('/'));

        int n1 = Integer.parseInt(tv_num1.getText().toString().trim());
        int n2 = Integer.parseInt(tv_num2.getText().toString().trim());
        int op = Integer.parseInt(tv_op.getText().toString().trim());

        Log.d("here", "OnDivideClick: " + n1 + " " + n2 + " " + op);

        if(n1 / n2 == op) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            int score = Integer.parseInt(tv_score.getText().toString().trim());

            score++;
            tv_score.setText(String.valueOf(score));
            edt_opr.setText("");

            show();
        }
        else Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();

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
        tv_num1.setText(String.valueOf(n1));
        tv_num2.setText(String.valueOf(n2));

        //edt_opr.setText(String.valueOf(opr[idx]));
        tv_op.setText(String.valueOf(op));
    }
}