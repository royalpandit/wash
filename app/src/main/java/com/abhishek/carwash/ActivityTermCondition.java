package com.abhishek.carwash;


import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityTermCondition extends AppCompatActivity implements View.OnClickListener {
    WebView mWebView;
    TextView text;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_condition);
        image = findViewById(R.id.image);

        text = findViewById(R.id.text);
        text.setText("Privacy Policy");
        mWebView = (WebView) findViewById(R.id.webviewHelp);
        mWebView.loadUrl("file:///android_asset/termcondition.html");
        image.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == image) {
            onBackPressed();
        }
    }
}
