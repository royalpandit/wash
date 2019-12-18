package com.abhishek.carwash.booking;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;
import com.abhishek.carwash.R;
import com.abhishek.carwash.utils.AppPreferences;
import com.abhishek.carwash.utils.ModalLogin;
import com.abhishek.carwash.utils.ThankYouActivity;
import com.abhishek.carwash.utils.Urls;
import com.abhishek.carwash.utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BookingInsertActivity extends AppCompatActivity {
    protected ProgressDialog progressDialog;
    int dayOfMonth, month, year;
    String InsertUrl = new Urls().CarWashBookingInsert;
    String ApplYPromoCOde = new Urls().CarPromoCode;
    String Str_U_ID;
    String car_id;
    EditText appliPromoCode;
    String Str_Apply_Promo;
    Button applyPromoBtn;
    String package_name;
    String carType;
    String car_price;
    ModalLogin modalLogin;
    String date;
    TextView DisplayTime;
    String format;
    Calendar calendarTime;
    TimePickerDialog timepickerdialog;
    String times;
    String date_time1;
    private ImageView pkImage;
    private TextView pkPrice, pkDescription, packageName;
    private Button btnSubmit;
    private Calendar calendar;
    private TextView _calender;
    private String dates;
    private EditText address;
    private String status;
    String StrDiscount = "0";
    private String name_f, name_l, fullName;
    private String userId, phoneNum, email;
    private int CalendarHour, CalendarMinute;

    private RadioGroup radio_group_pay;
    private RadioButton radio_payButton;
    String Str_Radio_Cash;
    String Str_Radio_phone_pay;
    String Str_Radio_Upi;
    RadioButton Radio_cash;
    RadioButton Radio_phonePay;
    RadioButton Radio_upi;
    String str_address;
    String Radfio_Pay;
    String str_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_insert);
        Str_U_ID = AppPreferences.getSavedUser(this).getU_id();

        appliPromoCode = findViewById(R.id.appliPromoCode);
        applyPromoBtn = findViewById(R.id.applyPromoBtn);

        pkImage = findViewById(R.id.packageImage);
        pkPrice = findViewById(R.id.packagePirce);
        pkDescription = findViewById(R.id.packageDescription);
        packageName = findViewById(R.id.packageName);
        btnSubmit = findViewById(R.id.bookBtn);
        Radio_cash = findViewById(R.id.cash);
        Radio_phonePay = findViewById(R.id.phonePay);
        Radio_upi = findViewById(R.id.upi);

        radio_group_pay = (RadioGroup) findViewById(R.id.radio_group_pay);

        modalLogin = AppPreferences.getSavedUser(BookingInsertActivity.this);
        name_f = modalLogin.getFirst_name();
        name_l = modalLogin.getLast_name();
        fullName = name_f + " " + name_l;
        userId = modalLogin.getU_id();
        phoneNum = modalLogin.getPhone_number();
        email = modalLogin.getEmail();


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            car_id = bundle.getString("id_k");
            package_name = bundle.getString("package_name_k");
            carType = bundle.getString("carType_k");
            car_price = bundle.getString("price_k");
            String des = bundle.getString("des_k");
            String carImg = bundle.getString("img_k");
            String status = bundle.getString("status_k");

            packageName.setText(package_name + " / " + carType);
            pkPrice.setText(car_price);
            pkDescription.setText(des);

            // String imgUrl =modal.getImage();
            Log.d("carIcon", carImg);
            // String imageurl = new Urls().imgUrl + carImg;
            Picasso.with(this).load(carImg).into(pkImage);
        }

        DisplayTime = findViewById(R.id.date_text);
       /* SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        DisplayTime.setText(currentDateandTime);*/
        DisplayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarTime = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);

                timepickerdialog = new TimePickerDialog(BookingInsertActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                       /* if (hourOfDay == 0) {

                            hourOfDay += 12;

                            format = "AM";
                        }
                        else if (hourOfDay == 12) {

                            format = "PM";

                        }
                        else if (hourOfDay > 12) {

                            hourOfDay -= 12;

                            format = "PM";

                        }
                        else {

                            format = "AM";
                        }*/

                        times = hourOfDay + ":" + minute + ":" + "00";
                        Log.d("times", times);
                        DisplayTime.setText(times);
                        Log.d("DisplayTime", DisplayTime.toString());
                    }
                }, CalendarHour, CalendarMinute, true);
                timepickerdialog.show();


            }
        });

        applyPromoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Str_Apply_Promo = appliPromoCode.getText().toString().trim();
                ApplyPromoCcode();
            }
        });
        _calender = findViewById(R.id.calender);
        calendar = Calendar.getInstance();
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        //  date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        //  Date currentTime = Calendar.getInstance().getTime();
        //  _calender.setText(date);
        _calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(BookingInsertActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        dates = year + "-" + month + "-" + dayOfMonth;
                        Log.d("dates", dates);
                        _calender.setText(dates);
                        Log.d("_calender", _calender.toString());
                    }
                }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        address = findViewById(R.id.address);
        btnSubmit = findViewById(R.id.btn_ok);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_time1 = dates + " " + times;
                str_date = date_time1;
                Log.d("str_date", str_date);
                Str_Radio_Cash = Radio_cash.getText().toString();
                Str_Radio_phone_pay = Radio_phonePay.getText().toString();
                Str_Radio_Upi = Radio_upi.getText().toString();
                Log.d("str)radio_cash", Str_Radio_Cash);
                Log.d("str)radio_phon", Str_Radio_phone_pay);
                Log.d("str)radio_upi", Str_Radio_Upi);

                str_address = address.getText().toString().trim();
                int selectedId = radio_group_pay.getCheckedRadioButtonId();
                radio_payButton = (RadioButton) findViewById(selectedId);
                Log.d("str)radio_cashdd", String.valueOf(radio_payButton));
                Radfio_Pay = radio_payButton.getText().toString();
                if (dates == null) {
                    Toast.makeText(getApplicationContext(), "Please Choose your Date", Toast.LENGTH_LONG).show();
                    return;
                } else if (times == null) {
                    Toast.makeText(getApplicationContext(), "Please Choose your Time", Toast.LENGTH_LONG).show();
                    return;
                } else if (radio_payButton == null) {
                    Toast.makeText(getApplicationContext(), "Selected your payment mode", Toast.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(str_address)) {
                    address.setError("Please enter your Address");
                    address.requestFocus();
                    return;
                } else {
                    userLogin();
                }

            }
        });
    }

    private void ApplyPromoCcode() {
        try {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        } catch (WindowManager.BadTokenException e) {
            e.addSuppressed(null);
            e.fillInStackTrace();
        }
        Log.d("Insert URL is:", ApplYPromoCOde);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApplYPromoCOde, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("reponseBook", response);
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    status = jsonObject.getString("status");

                    if (status.equals("1")) {

                        StrDiscount = jsonObject.getString("dicount");
                        //   finish();
                        //     startActivity(new Intent(getApplicationContext(), ThankYouActivity.class));
                        Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                    } else if (status.equals("0")) {
                        Toast.makeText(getApplicationContext(), jsonObject.getString("msg") + "\n" + "Please Choose Your Date or Time", Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("u_id", Str_U_ID);
                params.put("code", Str_Apply_Promo);

                Log.d("ggg", params.toString());
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void userLogin() {
        try {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        } catch (WindowManager.BadTokenException e) {
            e.addSuppressed(null);
            e.fillInStackTrace();
        }
        Log.d("Insert URL is:", InsertUrl);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, InsertUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("reponseBook", response);
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    status = jsonObject.getString("status");

                    if (status.equals("1")) {


                        finish();
                        startActivity(new Intent(getApplicationContext(), ThankYouActivity.class));
                        Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                    } else if (status.equals("0")) {
                        Toast.makeText(getApplicationContext(), jsonObject.getString("msg") + "\n" + "Please Choose Your Date or Time", Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("bookingDate", str_date);
                params.put("address", str_address);
                params.put("uId", userId);
                params.put("name", fullName);
                params.put("phoneNo", phoneNum);
                params.put("email", email);
                params.put("packageName", package_name);
                params.put("car", carType);
                params.put("amount", car_price);
                params.put("payment", Radfio_Pay);
                params.put("discount", StrDiscount);

                Log.d("ggg11", params.toString());
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
}
