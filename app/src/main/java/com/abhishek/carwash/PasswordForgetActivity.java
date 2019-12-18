package com.abhishek.carwash;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.abhishek.carwash.loginregister.LoginActivity;
import com.abhishek.carwash.utils.Urls;
import com.abhishek.carwash.utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PasswordForgetActivity extends AppCompatActivity implements View.OnClickListener {
    protected ProgressDialog progressDialog;
    private TextView toolbarTitle;
    private EditText editText;
    private Button cancel, submit;
    private ImageView imageleft;
    private String username;
    private String status;
    private String changeForgetUrl = new Urls().CarWashForgatePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_forget);

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("getEmail");

        toolbarTitle = (TextView)findViewById(R.id.tool_bar);
        toolbarTitle.setText("Forget Password");
        editText = findViewById(R.id.forget_email);
        editText.setText(message);
        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);
        imageleft = findViewById(R.id.imageleft);
        imageleft.setVisibility(View.GONE);
    }

    private void userLogin() {
        //first getting the values
        try{
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }catch (WindowManager.BadTokenException e)
        {
            e.addSuppressed(null);
            e.fillInStackTrace();
        }









        // String strLoginUrl= url+"username="+username+"&password="+password+"&type="+userType;
        Log.d("Login URL is:", changeForgetUrl);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, changeForgetUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("reponse", response);
                progressDialog.dismiss();


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    status = jsonObject.getString("status");

                    if (status.equals("1")) {


                        finish();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                    } else if (status.equals("0")){
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
                params.put("email", username);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    @Override
    public void onClick(View v) {
        if (v == cancel){
           startActivity(new Intent(PasswordForgetActivity.this, LoginActivity.class));
           finish();
        }
        if (v == submit){
            username = editText.getText().toString().trim();
            //validating inputs
            if (TextUtils.isEmpty(username)) {
                editText.setError("Please enter your username");
                editText.requestFocus();
                return;
            }else {
                userLogin();
            }
        }

    }
}
