package com.abhishek.carwash.loginregister;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.abhishek.carwash.R;

public class OTPNumberActivity extends AppCompatActivity implements View.OnClickListener {
EditText editmobile;
Button submitotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpnumber);
        find();
    }


    public void find(){
        editmobile=findViewById(R.id.editmobile);
        submitotp=findViewById(R.id.submitotpnum);
        submitotp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view==submitotp){
            startActivity(new Intent(OTPNumberActivity.this,OTPVerifyActivity.class));

        }


    }
}
