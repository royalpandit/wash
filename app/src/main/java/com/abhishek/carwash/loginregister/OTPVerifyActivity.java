package com.abhishek.carwash.loginregister;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.abhishek.carwash.demo.SListingActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.abhishek.carwash.R;
import com.abhishek.carwash.otpscred.OtpEditText;
import com.abhishek.carwash.utils.AppPreferences;
import com.abhishek.carwash.utils.ModalLogin;
import com.abhishek.carwash.utils.Urls;
import com.abhishek.carwash.utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class OTPVerifyActivity extends AppCompatActivity implements View.OnClickListener {

    protected ProgressDialog progressDialog;
    private String signupUrl = new Urls().CarWashSignup;
    private String signsmsUrl = new Urls().CarWashSmsSignup;
    String status;
String statuss;
    Button btnOtpVerify;
    String pNumber;
    TextView resend;
String opt;
    OtpEditText otp_view;

    //to=9314497070&sender=JAINVV&message=Hello

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String str_status;
    String str_first_name;
    String str_last_name;
    String str_s_id;
    String str_email;
TextView sendotpdialo;

    int otpNumber = 123456;
    int randomNumber;
    int range = 9;  // to generate a single number with this range, by default its 0..9
    int length = 6; // by default length is 6

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverify);
        getPrfData();
        pNumber = getIntent().getStringExtra("number");
        Log.d("pNumber", pNumber);

        // numberSignUp();
        otp_view = findViewById(R.id.otp_view);
        resend = findViewById(R.id.resend);

        generateRandomNumber();
        sendotpdialo=findViewById(R.id.sendotpdialo);

        sendotpdialo.setText("We have send the otp on " + pNumber + " will apply auto to the fields");
        btnOtpVerify = (Button) findViewById(R.id.submitotp);
        btnOtpVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int getotp_edit = Integer.parseInt(otp_view.getEditableText().toString());
                Log.d("getotp_edit", String.valueOf(getotp_edit));
                // if (randomNumber == getotp_edit)
                if (getotp_edit == randomNumber) {
                    // startActivity(new Intent(OTPVerifyActivity.this, ListingActivity.class));
                    numberSignUp();
                    // Toast.makeText(getApplicationContext(),"OTP is Right",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "OTP is wronge", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public int generateRandomNumber() {

        SecureRandom secureRandom = new SecureRandom();
        String s = "";
        for (int i = 0; i < length; i++) {
            int number = secureRandom.nextInt(range);
            if (number == 0 && i == 0) {
                i = -1;
                continue;
            }
            s = s + number;
        }

        randomNumber = Integer.parseInt(s);
        Log.d("randomNumber", String.valueOf(randomNumber));
        //registerUsergoogle();
        sendOTP();
        return randomNumber;
    }

    private void registerUsergoogle() {
        opt = "CarWash OTP " + randomNumber;
        try{
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }catch (WindowManager.BadTokenException e)
        {
            e.addSuppressed(null);
            e.fillInStackTrace();
        }

        Log.d("SignUp URL is:", signsmsUrl);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, signsmsUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("reponse1", response);
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            statuss = jsonObject.getString("success");

                            //if no error in response

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("mobile", pNumber);
                params.put("msg",opt);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


    private void sendOTP() {
        String opt = "WhizzWash OTP " + randomNumber;

        //String url="http://sms.itwebsolution.in/sendsms.jsp?user=achrya&password=972d0f4315XX&&senderid=achrya&mobiles=+918107116566&sms=008462";
       // String url ="http://sms.itwebsolution.in/sendsms.jsp?user=achrya&password=972d0f4315XX&&senderid=achrya&mobiles=+919314497070&sms=test";
        //String url = Urls.smsapi+user=achrya&password=972d0f4315XX&&senderid=achrya&mobiles=+919314497070&sms=test;
        String url="http://alerts.prioritysms.com/api/web2sms.php?workingkey=A32955b81f43e3be680df44f4c6aaacd0"+"&to="+pNumber+"&sender=JAINVV&message="+opt;
//      String url = "http://sms.itwebsolution.in/sendsms.jsp?user=achrya&password=972d0f4315XX&senderid=achrya" + "&mobiles=" + pNumber + "&sms=" + opt;
        Log.d("url", url);
      url.replace("","%20").trim();
        try {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        } catch (WindowManager.BadTokenException e) {
            e.addSuppressed(null);
            e.fillInStackTrace();
        }

        // Log.d("SignUp URL is:", signupUrl);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("reponse", response);
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    status = jsonObject.getString("status");

                    //if no error in response
                    if (status.equals("1")) {
                        ModalLogin user = new ModalLogin();

                        // Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                    } else if (status.equals("0")) {
                        // Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                      // progressDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                //params.put("phone", "1234567890");
                params.put("password", "123456");
                params.put("phone", pNumber);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void numberSignUp() {
        try {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        } catch (WindowManager.BadTokenException e) {
            e.addSuppressed(null);
            e.fillInStackTrace();
        }

        Log.d("SignUp URL is:", signupUrl);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, signupUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("reponse", response);
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    status = jsonObject.getString("status");

                    //if no error in response
                    if (status.equals("1")) {
                        ModalLogin user = new ModalLogin();

                        user.setU_id(jsonObject.getString("u_id"));
                        user.setFid(jsonObject.getString("fId"));
                        user.setGid(jsonObject.getString("gId"));
                        user.setEmail(jsonObject.getString("email"));
                        user.setFirst_name(jsonObject.getString("first_name"));
                        user.setLast_name(jsonObject.getString("last_name"));
                        user.setfPhoto(jsonObject.getString("image"));
                        //  user.setPhone_number(jsonObject.getString("phone_number"));
                        // String pass = jsonObject.getString("password");

                        String fn = jsonObject.getString("first_name");
                        String ln = jsonObject.getString("last_name");
                        // Log.d("pass",pass);


                        AppPreferences.SaveUserdetail(OTPVerifyActivity.this, user);
                        //  Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();


                        // finish();
                        startActivity(new Intent(OTPVerifyActivity.this, SListingActivity.class));

/*                        startActivity(new Intent(OTPVerifyActivity.this, ListingActivity.class));*/
                        finish();
                        Toast.makeText(getApplicationContext(), "Successful login", Toast.LENGTH_SHORT).show();
                    } else if (status.equals("0")) {
                        Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();


                if (str_status.equals("1")) {
                    //params.put("phone", "1234567890");
                    params.put("password", "123456");
                    params.put("phone", pNumber);

                } else if (str_status.equals("2")) {
                    params.put("fId", str_s_id);
                    params.put("first_name", str_first_name);
                    params.put("last_name", str_last_name);
                    params.put("email", str_email);
                    params.put("phone", pNumber);
                    // params.put("image",imageurl);
                } else if (str_status.equals("3")) {
                    params.put("gId", str_s_id);
                    params.put("first_name", str_first_name);
                    params.put("last_name", str_last_name);
                    params.put("email", str_email);
                    params.put("phone", pNumber);
                }
                Log.d("params", params.toString());
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void getPrfData() {
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        str_status = sharedpreferences.getString("getStatus", "");
        str_first_name = sharedpreferences.getString("first_name", "");
        str_last_name = sharedpreferences.getString("last_name", "");
        str_email = sharedpreferences.getString("email", "");
        str_s_id = sharedpreferences.getString("s_id", "");

    }

    @Override
    public void onClick(View v) {
        if (v == resend) {
        }
    }
}
