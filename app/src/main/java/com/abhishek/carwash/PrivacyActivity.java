package com.abhishek.carwash;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class PrivacyActivity extends AppCompatActivity implements View.OnClickListener {
    WebView mWebView;
    ImageView image;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_condition);
        text=findViewById(R.id.text);
        image=findViewById(R.id.image);
        text.setText("Privacy Policy");
        mWebView = (WebView) findViewById(R.id.webviewHelp);
        mWebView.loadUrl("file:///android_asset/privacypolicy.html");
        image.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==image){
            onBackPressed();
        }
    }
}
