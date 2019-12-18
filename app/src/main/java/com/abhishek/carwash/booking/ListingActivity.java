package com.abhishek.carwash.booking;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.abhishek.carwash.R;

public class ListingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Spinner spinner;
    private Button sumit;
    SpinnerAdapter spinnerAdapter;
    private String[] carList = {"Sedan", "Hatchback", "SUV"};
    String select_car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spackage_detail);

        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,carList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);
        sumit = findViewById(R.id.list_submit);
        sumit.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        select_car = carList[position];

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

        if (v==sumit){

            if (select_car == null){

                Toast.makeText(getApplicationContext(), "Select your car", Toast.LENGTH_SHORT).show();

            }else {
                Intent intent = new Intent(ListingActivity.this, PackageDetailActivity.class);
                intent.putExtra("select_car", select_car);
                startActivity(intent);
            }
        }

    }
}
