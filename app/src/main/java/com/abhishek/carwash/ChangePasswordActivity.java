package com.abhishek.carwash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.abhishek.carwash.loginregister.LoginActivity;
import com.abhishek.carwash.utils.AppPreferences;
import com.abhishek.carwash.utils.ModalLogin;
import com.abhishek.carwash.utils.Urls;
import com.abhishek.carwash.utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {
    protected ProgressDialog progressDialog;

    EditText oldPassword, newPassword, confirmPassword;
    Button btnSubmit;
    ModalLogin modalLogin;
    String pass;
    private String password_confirm;
    private String password;
    private String changePasswordUrl = new Urls().CarWashChangePassword;
    private String status;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        modalLogin = AppPreferences.getSavedUser(ChangePasswordActivity.this);
        email = modalLogin.getEmail();

        oldPassword = findViewById(R.id.old_password);
        newPassword = findViewById(R.id.new_Password);
        confirmPassword = findViewById(R.id.confirm_Password);
        btnSubmit = findViewById(R.id.submitBtn);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = newPassword.getText().toString();
                password_confirm = confirmPassword.getText().toString();
                if (TextUtils.isEmpty(password)) {
                    newPassword.setError("Please enter your password");
                    newPassword.requestFocus();
                    return;
                }else if (TextUtils.isEmpty(password_confirm)){
                    confirmPassword.setError("Please enter your Confirm password");
                    confirmPassword.requestFocus();
                    return;
                }else if (!password.equals(password_confirm)) {
                    Toast.makeText(ChangePasswordActivity.this, "Password Not matching", Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.equals(password_confirm)) {
                    userLogin();

                }
            }
        });

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

        Log.d("Login URL is:", changePasswordUrl);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, changePasswordUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("reponse", response);
                progressDialog.dismiss();


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    status = jsonObject.getString("status");

                    if (status.equals("1")) {
                       // AppPreferences.clearPrefsdata(ChangePasswordActivity.this);
                        finish();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                    } else if (status.equals("0")) {
                        Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

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
               // params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

}
