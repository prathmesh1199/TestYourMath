package com.example.testyourmath;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Easy extends AppCompatActivity {

    TextView tv_num1 , tv_num2  , tv_op;
    EditText edt_opr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);

        tv_num1 = findViewById(R.id.tv_easy_num1);
        tv_num2 = findViewById(R.id.tv_easy_num2);
        edt_opr = findViewById(R.id.edt_easy_opr1);
        tv_op = findViewById(R.id.tv_easy_op);

        show();

    }


    public void OnPlusClick(View view) {
        edt_opr.setText(String.valueOf('+'));
        int n1 = Integer.parseInt(tv_num1.getText().toString().trim());
        int n2 = Integer.parseInt(tv_num2.getText().toString().trim());
        int op = Integer.parseInt(tv_op.getText().toString().trim());

        if(n1 + n2 == op) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();

            //edt_opr.setText("");
            show();
        }
        else Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
    }

    public void OnMinusClick(View view) {
        edt_opr.setText(String.valueOf('-'));
        int n1 = Integer.parseInt(tv_num1.getText().toString().trim());
        int n2 = Integer.parseInt(tv_num2.getText().toString().trim());
        int op = Integer.parseInt(tv_op.getText().toString().trim());

        if(n1 - n2 == op) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();

            //edt_opr.setText("");
            show();
        }
        else Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();

    }

    public void OnMultiplyClick(View view) {
        edt_opr.setText(String.valueOf('*'));

        int n1 = Integer.parseInt(tv_num1.getText().toString().trim());
        int n2 = Integer.parseInt(tv_num2.getText().toString().trim());
        int op = Integer.parseInt(tv_op.getText().toString().trim());

        if(n1 * n2 == op) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();


            //edt_opr.setText("");
            show();
        }
        else Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();

    }

    public void OnDivideClick(View view) {
        edt_opr.setText(String.valueOf('/'));

        int n1 = Integer.parseInt(tv_num1.getText().toString().trim());
        int n2 = Integer.parseInt(tv_num2.getText().toString().trim());
        int op = Integer.parseInt(tv_op.getText().toString().trim());

        if(n1 / n2 == op) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();

            //edt_opr.setText("");

            show();
        }
        else Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();

    }


    public void show() {
        Random random = new Random();
        int n1 = random.nextInt(10) ;
        int n2 = random.nextInt(10);

        char[] opr = new char[4];
        opr[0] = '+';
        opr[1] = '-';
        opr[2] = '*';
        opr[3] = '/';

        int idx = random.nextInt(4);
        Log.d("here", "onCreate: " + idx + " " + opr[idx]);

        if(n2 > n1) {
            int temp = n1;
            n1 = n2;
            n2 = temp;
        }


        while(opr[idx] == '/' || n2 == 0) {
            Log.d("here", "onCreate: " + n1 + " " + n2 + " " + idx + " " + opr[idx]);
            if(n2 == 0 && opr[idx] == '/') {
                n2 = random.nextInt(10);
                if(n2 > n1) {
                    int temp = n1;
                    n1 = n2;
                    n2 = temp;
                }
            } else if(n1 % n2 != 0) {
                idx = random.nextInt(4);
            } else break;
        }
        Log.d("here", "onCreate: below" + n1 + " " + n2 + " " + idx + " " + opr[idx]);
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

        tv_num1.setText(String.valueOf(n1));
        tv_num2.setText(String.valueOf(n2));

        //edt_opr.setText(String.valueOf(opr[idx]));
        tv_op.setText(String.valueOf(op));
    }
}