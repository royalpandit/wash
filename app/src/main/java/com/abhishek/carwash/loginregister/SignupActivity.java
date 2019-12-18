package com.abhishek.carwash.loginregister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.abhishek.carwash.MainActivity;
import com.abhishek.carwash.R;
import com.abhishek.carwash.booking.PackageDetailActivity;
import com.abhishek.carwash.utils.AppPreferences;
import com.abhishek.carwash.utils.ModalLogin;
import com.abhishek.carwash.utils.Urls;
import com.abhishek.carwash.utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener ,  GoogleApiClient.OnConnectionFailedListener {
    protected ProgressDialog progressDialog;
    private EditText userName;
    private EditText phone_number;
    EditText editregemail;
    EditText editregispassword;
    EditText editregconfirmpassword;
    TextView editregforget_password;
    Button btnregister;
    Button regfacebook;
    Button btngoogle;
    TextView LoginBtn;
    private ProgressBar progressBar;


    String signupUrl = new Urls().CarWashSignup;
    String status;
    private String statusgoogle;

    private LoginButton loginButton;
    private Button loginButton1;
    CallbackManager callbackManager;
    TextView txtUsername, txtEmail;


    static String first_name ;
    static String last_name ;
    static String email1;
    static String f_id ;
    String imageurl ;

    String personId;
    String personName;
    String personPhotoUrl;
    String email;
    private SignInButton btnSignIn;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;

    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activvitysign);
        find();
        loginButton = findViewById(R.id.login_button1);
        loginButton.setOnClickListener(this);


        Log.d("AllData", first_name+last_name+email1+f_id+imageurl);

        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("email","public_profile"));
      //  checkLoginStatus();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("AllData", first_name+last_name+email1+f_id+imageurl);
                Toast.makeText(getApplicationContext(),"Login Succesful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"Login Cancel", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {

                Toast.makeText(getApplicationContext(),"Login Error!"+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in1);
        btnSignIn.setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Customizing G+ button
        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setScopes(gso.getScopeArray());

    }

    private void signIn() {


        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            personId = acct.getId();

            Log.e(TAG, "display name: " + acct.getDisplayName());

            personName = acct.getDisplayName();
            personPhotoUrl = acct.getPhotoUrl().toString();
            email = acct.getEmail();

            Log.e(TAG, "Name: " + personName + ", email: " + email
                    + ", Image: " + personPhotoUrl);


            registerUsergoogle();

        }
    }
/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }*/

    public void find() {
        userName = findViewById(R.id.editregname);
        phone_number = findViewById(R.id.editregphone);
        editregemail = findViewById(R.id.editregemail);
        editregispassword = findViewById(R.id.editregispassword);
       editregconfirmpassword = findViewById(R.id.editregconfirmpassword);
        progressBar = findViewById(R.id.progressBar_sinUp);

        editregforget_password = findViewById(R.id.editregforget_password);
        btnregister = findViewById(R.id.btnregisterlogin);
        LoginBtn = findViewById(R.id.signupnow);

        btnregister.setOnClickListener(this);
        LoginBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if (view == LoginBtn) {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
          //  startActivity(new Intent(SignupActivity.this, ProfileActivity.class));

        }
        if (view == btnregister) {
            registerUser();

        }
       /* if (view == loginButton){
            loginButton.performClick();
        }*/

        if (view == btnSignIn){
            signIn();
        }

    }

    private void registerUser() {
        try{
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }catch (WindowManager.BadTokenException e)
        {
            e.addSuppressed(null);
            e.fillInStackTrace();
        }

        final String user_name = userName.getText().toString().trim();
        final String email = editregemail.getText().toString().trim();
        final String phone_num = phone_number.getText().toString().trim();
        final String password = editregispassword.getText().toString().trim();
      //  final String password_metch = editregconfirmpassword.getText().toString().trim();


        //first we will do the validations

        if (TextUtils.isEmpty(user_name)) {
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
        }

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

        Log.d("SignUp URL is:", signupUrl);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, signupUrl,
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
                                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            } else if (status.equals("0")){
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
                params.put("first_name", user_name);
                params.put("email", email);
                params.put("phone", phone_num);
                params.put("password", password);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    AccessTokenTracker tokenTracker =new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

           /* if (currentAccessToken == null){
                txtUsername.setText("");
                txtEmail.setText("");
                //circleImageView.setImageResource(0);
                Toast.makeText(getApplicationContext(),"User LogOut", Toast.LENGTH_SHORT).show();

            }else {*/
                loaduserProfile(currentAccessToken);

        }
    };

    private void loaduserProfile(AccessToken newAccessToken){
        GraphRequest graphRequest = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                try {
                    first_name = object.getString("first_name");
                    last_name = object.getString("last_name");
                    email1 = object.getString("email");
                    f_id = object.getString("id");

                   imageurl = "https://graph.facebook.com/"+f_id+ "/picture?type=normal";

                    Log.d("AllData", first_name+last_name+email1+f_id+imageurl);


                    //  txtUsername.setText(first_name+" "+last_name);
                  //  txtEmail.setText(email1);
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();
                    registerUserfb();


                 //   Glide.with(MainActivity.this).load(imageurl).into(circleImageView);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (NullPointerException e){
                    e.printStackTrace();
                }

            }
        });

        Bundle bundle = new Bundle();
        bundle.putString("fields","first_name,last_name,email,id");
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }


    private void checkLoginStatus(){
        if (AccessToken.getCurrentAccessToken() != null){
            loaduserProfile(AccessToken.getCurrentAccessToken());
        }
    }

    /*----------------------------------*/

    private void registerUserfb() {
        try{
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }catch (WindowManager.BadTokenException e)
        {
            e.addSuppressed(null);
            e.fillInStackTrace();
        }

        Log.d("SignUp URL is:", signupUrl);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, signupUrl,
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
                                user.setFid(jsonObject.getString("fId"));
                                user.setEmail(jsonObject.getString("email"));
                                user.setFirst_name(jsonObject.getString("first_name"));
                                user.setLast_name(jsonObject.getString("last_name"));
                              //  user.setPhone_number(jsonObject.getString("phone_number"));
                                // String pass = jsonObject.getString("password");

                                String fn = jsonObject.getString("first_name");
                                String ln = jsonObject.getString("last_name");
                                // Log.d("pass",pass);


                                AppPreferences.SaveUserdetail(SignupActivity.this,user);
                                //  Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();


                                finish();
                                startActivity(new Intent(SignupActivity.this, PackageDetailActivity.class));
                                Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            } else if (status.equals("0")){
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
                params.put("fId", f_id);
                params.put("first_name", first_name);
                params.put("last_name", last_name);
                params.put("email", email1);
               // params.put("phone", phone_num);
               // params.put("password", password);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
/*---------------------------------*/
    private void registerUsergoogle() {
        try{
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }catch (WindowManager.BadTokenException e)
        {
            e.addSuppressed(null);
            e.fillInStackTrace();
        }

        Log.d("SignUp URL is:", signupUrl);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, signupUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("reponse", response);
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            statusgoogle = jsonObject.getString("status");

                            //if no error in response
                            if (statusgoogle.equals("1")) {
                                ModalLogin user = new ModalLogin();

                                user.setU_id(jsonObject.getString("u_id"));
                                user.setFid(jsonObject.getString("gId"));
                                user.setEmail(jsonObject.getString("email"));
                                user.setFirst_name(jsonObject.getString("first_name"));
                                user.setLast_name(jsonObject.getString("last_name"));
                                user.setGphoto(jsonObject.getString("image"));
                                //  user.setPhone_number(jsonObject.getString("phone_number"));
                                // String pass = jsonObject.getString("password");

                                String fn = jsonObject.getString("first_name");
                                String ln = jsonObject.getString("last_name");
                                // Log.d("pass",pass);


                                AppPreferences.SaveUserdetail(SignupActivity.this,user);
                                //  Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();


                                finish();
                                startActivity(new Intent(SignupActivity.this, PackageDetailActivity.class));
                                Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            } else if (status.equals("0")){
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
                params.put("fId", personId);
                params.put("first_name", personName);

                //  params.put("last_name", last_name);
                params.put("email", email);
                params.put("image",personPhotoUrl);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}
