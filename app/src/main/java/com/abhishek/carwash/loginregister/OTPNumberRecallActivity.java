package com.abhishek.carwash.loginregister;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.abhishek.carwash.R;

public class OTPNumberRecallActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editmobile;
    private Button login_recall;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpnumber_recall);

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        find();
    }

    private void find() {
        editmobile = findViewById(R.id.editmobile_recall);
        login_recall = findViewById(R.id.login_recall);
        login_recall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==login_recall){
            String pNumber = editmobile.getText().toString().trim();
            if (TextUtils.isEmpty(pNumber)){
                editmobile.setError("Please enter your mobile number");
                editmobile.requestFocus();
                return;
            }else if (pNumber.length()!=10){
                editmobile.setError("Invalid mobile number");
                editmobile.requestFocus();
                return;
            }
            //SharedPreferences.Editor editor = sharedpreferences.edit();
            //editor.putString("getStatus","1"); // Storing string
            //editor.putString("search_url", ); // Storing integer
          //  editor.commit();
            Intent intent = new Intent(OTPNumberRecallActivity.this, OTPVerifyActivity.class);
            intent.putExtra("number", pNumber);
            startActivity(intent);
        }

    }
}
