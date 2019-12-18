package com.abhishek.carwash.loginregister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.abhishek.carwash.demo.SListingActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.abhishek.carwash.R;
import com.abhishek.carwash.booking.PackageDetailActivity;
import com.abhishek.carwash.utils.AppPreferences;
import com.abhishek.carwash.utils.ModalLogin;
import com.abhishek.carwash.utils.Urls;
import com.abhishek.carwash.utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editname;
    EditText editlastname;
    EditText editemailid;
    EditText editphonenum;
    protected ProgressDialog progressDialog;
    String ProfileUpdateUrl = new Urls().CarWashUpdateProfile;
    String GETUSE = new Urls().CarGetUser;
    String status;
    Button btnprofileinformation;
    String id;
    String FirstName;
    String LastName;
    String Email;
    String sFirstName;
    String sLastName;
    String sEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        id = AppPreferences.getSavedUser(this).getU_id();
        Log.d("stdid", id);
        sFirstName = AppPreferences.getSavedUser(this).getFirst_name();
        Log.d("sFirstName", sFirstName);
        sLastName = AppPreferences.getSavedUser(this).getLast_name();
        Log.d("sLastName", sLastName);
        sEmail = AppPreferences.getSavedUser(this).getEmail();
        Log.d("sEmail", sEmail);
        find();
       // GETUSER();

        //KK00958

    }

    public void find() {
        editname = findViewById(R.id.editname);
        editlastname = findViewById(R.id.editlastname);
        editemailid = findViewById(R.id.editemailid);
        //   editphonenum=findViewById(R.id.editphonenum);
        btnprofileinformation = findViewById(R.id.btnprofileinformation);
        btnprofileinformation.setOnClickListener(this);
        editname.setText(sFirstName);
        editlastname.setText(sLastName);
        editemailid.setText(sEmail);
    }

    @Override
    public void onClick(View view) {
        if (view == btnprofileinformation) {
            ProfileIpdate();
            //startActivity(new Intent(ProfileActivity.this,OTPNumberActivity.class));
        }
    }

    private void GETUSER() {
        try {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        } catch (WindowManager.BadTokenException e) {
            e.addSuppressed(null);
            e.fillInStackTrace();
        }


        Log.d("ProfileUpdatURL is:", GETUSE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GETUSE,
                new Response.Listener<String>() {
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
                                user.setEmail(jsonObject.getString("email"));
                                user.setFirst_name(jsonObject.getString("first_name"));
                                user.setLast_name(jsonObject.getString("last_name"));

                                editname.setText(jsonObject.getString("first_name"));
                                editlastname.setText(jsonObject.getString("last_name"));
                                editemailid.setText(jsonObject.getString("email"));

                                //  Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();


                                //  finish();
                                //    startActivity(new Intent(ProfileActivity.this, SplashActivity.class));
                                Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
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
                params.put("u_id", id);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


    private void ProfileIpdate() {
        try {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        } catch (WindowManager.BadTokenException e) {
            e.addSuppressed(null);
            e.fillInStackTrace();
        }

        FirstName = editname.getText().toString().trim();
        LastName = editlastname.getText().toString().trim();
        Email = editemailid.getText().toString().trim();
        //  final String password_metch = editregconfirmpassword.getText().toString().trim();


        //first we will do the validations

        /*if (TextUtils.isEmpty(user_name)) {
            userName.setError("Please enter username");
            userName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editregemail.setError("Please enter your email");
            editregemail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editregemail.setError("Enter a valid email");
            editregemail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phone_num)) {
            phone_number.setError("Please enter your phone");
            phone_number.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editregispassword.setError("Enter a password");
            editregispassword.requestFocus();
            return;
        }*/

      /*  if (TextUtils.isEmpty(password_metch)) {
            editregconfirmpassword.setError("Enter a password");
            editregconfirmpassword.requestFocus();
            return;
        }*/

       /* if (TextUtils.isEmpty(password) != TextUtils.isEmpty(password_metch )){
            editregconfirmpassword.setError("password not Metch");
            editregconfirmpassword.requestFocus();
            return;
        }*/

        Log.d("ProfileUpdatURL is:", ProfileUpdateUrl);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ProfileUpdateUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("reponse", response);
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            status = jsonObject.getString("status");

                            //if no error in response
                            if (status.equals("1")) {
                                //  Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();


                                 finish();
                                startActivity(new Intent(ProfileActivity.this, SListingActivity.class));

                                 /*startActivity(new Intent(ProfileActivity.this, PackageDetailActivity.class));*/
                                Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
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
                params.put("u_id", id);
                params.put("firstname", FirstName);
                params.put("lastname", LastName);
                params.put("email", Email);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
}
