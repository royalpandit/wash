package com.abhishek.carwash.utils;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.abhishek.carwash.R;
import com.abhishek.carwash.booking.BookingHistoryActivity;
import com.abhishek.carwash.demo.SListingActivity;

public class ThankYouActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThankYouActivity.this, SListingActivity.class));

                /*startActivity(new Intent(ThankYouActivity.this, BookingHistoryActivity.class));*/
                finish();
            }
        });
    }
}
