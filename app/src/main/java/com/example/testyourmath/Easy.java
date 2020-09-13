package com.example.testyourmath;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

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

        Random random = new Random();
        int n1 = random.nextInt(10);
        int n2 = random.nextInt(10);

        if(n2 > n1) {
            int temp = n1;
            n1 = n2;
            n2 = temp;
        }

        tv_num1.setText(String.valueOf(n1));
        tv_num2.setText(String.valueOf(n2));

        char[] opr = new char[4];
        opr[0] = '+';
        opr[1] = '-';
        opr[2] = '*';
        opr[3] = '/';

        int idx = random.nextInt(4);
        Log.d("here", "onCreate: " + idx + " " + opr[idx]);


        while(opr[idx] == '/' || n2 == 0) {
            Log.d("here", "onCreate: " + n1 + " " + n2 + " " + idx + " " + opr[idx]);
            if(n2 == 0 && opr[idx] == '/') {
                n2 = random.nextInt(10);
            } else if(n1 % n2 != 0) {
                idx = random.nextInt(4);
            } else break;
        }
        Log.d("here", "onCreate: below" + n1 + " " + n2 + " " + idx + " " + opr[idx]);
        int op = 1;
        switch (idx) {
            case 0 : op = n1 + n2;break;
            case 1 : op = n1 - n2;break;
            case 2 : op = n1 * n2;break;
            case 3 : op = n1 / n2;break;
        }

        edt_opr.setText(String.valueOf(opr[idx]));
        tv_op.setText(String.valueOf(op));

    }

    public void OnPlusClick(View view) {

    }
}