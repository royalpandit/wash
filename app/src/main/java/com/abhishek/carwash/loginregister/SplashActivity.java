package com.abhishek.carwash.loginregister;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.abhishek.carwash.R;
import com.abhishek.carwash.demo.SListingActivity;
import com.abhishek.carwash.utils.AppPreferences;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SplashActivity extends AppCompatActivity {

    private static final int STOPSPLASH = 0;
    private static int SPLASH_TIME_OUT = 6000;
    String id;
    PackageInfo packageInfo;
    String key = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        id = AppPreferences.getSavedUser(this).getU_id();
        try {
            //getting application package name, as defined in manifest
            String packageName = getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));
                /*DTF3VvCFaiCN7YN5sE66D6c5h0Q=*/
                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (id.equals("-1")) {
                    Intent intent = new Intent(SplashActivity.this, VideoPlayerActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, SListingActivity.class);
                    startActivity(intent);
                    finish();
                }


            }        }, SPLASH_TIME_OUT);

    }


}

