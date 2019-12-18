package com.abhishek.carwash.loginregister;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

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
import com.abhishek.carwash.PasswordForgetActivity;
import com.abhishek.carwash.R;
import com.abhishek.carwash.booking.PackageDetailActivity;
import com.abhishek.carwash.utils.AppPreferences;
import com.abhishek.carwash.utils.ModalLogin;
import com.abhishek.carwash.utils.Urls;
import com.abhishek.carwash.utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,  GoogleApiClient.OnConnectionFailedListener  {
    protected ProgressDialog progressDialog;
    private static final String EMAIL = "email";
    private AccessTokenTracker accessTokenTracker;
    EditText editloginpassword;
    EditText editloginemail;
    EditText editmobile;
    Button login;
    TextView signupnow;
    private TextView forget_password;
    private ProgressBar progressBar;

    String loginUrl = new Urls().CarWashLogin;
    String status;

   // LoginButton loginButton;
    CallbackManager callbackManager;

    String signupUrl = new Urls().CarWashSignup;
    private String statusgoogle;
    static String first_name ;
    static String last_name ;
    static String email1;
    static String f_id ;
    String imageurl ;

    private LoginButton loginButton;




    String personId;
    String personName;
    String personPhotoUrl;
    String email;
    private SignInButton btnSignIn;
    AccessTokenTracker accessTokenTr;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;

    private GoogleApiClient mGoogleApiClient;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       /* if (SharedPrefmanager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, PackageDetailActivity.class));
        }*/
        find();
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        callbackManager = CallbackManager.Factory.create();
        //loginButton.setReadPermissions(Arrays.asList(EMAIL));
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
         accessTokenTr = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                // currentAccessToken is null if the user is logged out
                getUserProfile(currentAccessToken);

                /*if (currentAccessToken != null) {
                    // AccessToken is not null implies user is logged in and hence we sen the GraphRequest
                    getUserProfile(currentAccessToken);
                }else{
                    // displayName.setText("Not Logged In");
                }*/
            }
        };
/*----------------------------*/

        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
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
           // personPhotoUrl = acct.getPhotoUrl().toString();
            email = acct.getEmail();

            Log.e(TAG, "Name: " + personName + ", email: " + email
                    + ", Image: " + personPhotoUrl);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("getStatus","3"); // Storing string
            editor.putString("first_name",personName );
            editor.putString("last_name","" );
            editor.putString("email",email );
            editor.putString("s_id",personId );
            editor.commit();

            startActivity(new Intent(LoginActivity.this, OTPNumberRecallActivity.class));
            finish();

           // registerUsergoogle();

        }
    }
   /* @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {

            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {

            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }*/


/*-------------------------------*/

    public void find() {
        forget_password = findViewById(R.id.forget_password);
        forget_password.setOnClickListener(this);
       // loginButton = findViewById(R.id.login_button);
        progressBar = findViewById(R.id.progressBar);
        editloginpassword = findViewById(R.id.editloginpassword);
        editloginemail = findViewById(R.id.editloginemail);
        login = findViewById(R.id.login);
        signupnow = findViewById(R.id.signupnow);
        signupnow.setOnClickListener(this);
        login.setOnClickListener(this);
        editmobile = findViewById(R.id.editmobile);

    }

    @Override
    public void onClick(View view) {
        if (view == login) {

            String pNumber = editmobile.getText().toString().trim();
            if (TextUtils.isEmpty(pNumber)){
                editmobile.setError("Please enter your mobile number");
                editmobile.requestFocus();
                return;
            }else if (pNumber.length()!=10){
                editmobile.setError("Invalid mobile number");
                editmobile.requestFocus();
                return;
            }
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("getStatus","1"); // Storing string
            //editor.putString("search_url", ); // Storing integer
            editor.commit();
            Intent intent = new Intent(LoginActivity.this, OTPVerifyActivity.class);
            intent.putExtra("number", pNumber);
            startActivity(intent);
           // userLogin();
        }
        if (view == signupnow) {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        }
        if (view == forget_password){
            if (editloginemail != null){
            String getEmail = editloginemail.getText().toString();
            Intent intent = new Intent(LoginActivity.this, PasswordForgetActivity.class);
            intent.putExtra("getEmail", getEmail);
            startActivity(intent);
            }else {startActivity(new Intent(LoginActivity.this, PasswordForgetActivity.class));}
        }
        if (view == btnSignIn){
            signIn();
        }
        if(view == loginButton){
            registerUserfb();
        }
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
        final String username = editloginemail.getText().toString();
        final String password = editloginpassword.getText().toString();


        //validating inputs
        if (TextUtils.isEmpty(username)) {
            editloginemail.setError("Please enter your username");
            editloginemail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editloginpassword.setError("Please enter your password");
            editloginpassword.requestFocus();
            return;
        }


        // String strLoginUrl= url+"username="+username+"&password="+password+"&type="+userType;
        Log.d("Login URL is:", loginUrl);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("reponse", response);
                progressDialog.dismiss();


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    status = jsonObject.getString("status");

                    if (status.equals("1")) {

                        ModalLogin user = new ModalLogin();

                        user.setU_id(jsonObject.getString("u_id"));
                        user.setEmail(jsonObject.getString("email"));
                        user.setFirst_name(jsonObject.getString("first_name"));
                        user.setLast_name(jsonObject.getString("last_name"));
                        user.setPhone_number(jsonObject.getString("phone_number"));

                      // String pass = jsonObject.getString("password");

                        String fn = jsonObject.getString("first_name");
                        String ln = jsonObject.getString("last_name");
                       // Log.d("pass",pass);


                        AppPreferences.SaveUserdetail(LoginActivity.this,user);

                        finish();
                        startActivity(new Intent(getApplicationContext(), PackageDetailActivity.class));
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
                params.put("password", password);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
    /*----------------------------*/

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


                                AppPreferences.SaveUserdetail(LoginActivity.this,user);
                                //  Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();


                                finish();
                                startActivity(new Intent(LoginActivity.this, OTPNumberRecallActivity.class));
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
                 //       Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

    /*-------------------*/
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
                                user.setfPhoto(jsonObject.getString("image"));
                                //  user.setPhone_number(jsonObject.getString("phone_number"));
                                // String pass = jsonObject.getString("password");

                                String fn = jsonObject.getString("first_name");
                                String ln = jsonObject.getString("last_name");
                                // Log.d("pass",pass);


                                AppPreferences.SaveUserdetail(LoginActivity.this,user);
                                //  Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(LoginActivity.this, OTPNumberRecallActivity.class));

                                finish();
                           //     Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            } else if (status.equals("0")){
                          //      Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    //    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fId", f_id);
                params.put("first_name", first_name);
                params.put("last_name", last_name);
                params.put("email", email1);
                params.put("image",imageurl);
                // params.put("phone", phone_num);
                // params.put("password", password);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }





   /* AccessTokenTracker tokenTracker =new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            loaduserProfile(currentAccessToken);
        }
    };*/

    //////////////////////
    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        //Log.d("TAG", object.toString());
                        try {
                            first_name = object.getString("first_name");
                            last_name = object.getString("last_name");
                            email1 = object.getString("email");
                            f_id = object.getString("id");

                            imageurl = "https://graph.facebook.com/"+f_id+ "/picture?type=normal";

                            Log.d("AllData", first_name+last_name+email1+f_id+imageurl);

                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("getStatus","2"); // Storing string
                            editor.putString("first_name",first_name );
                            editor.putString("last_name",last_name );
                            editor.putString("email",email1 );
                            editor.putString("s_id",f_id );
                            editor.commit();
                            finish();
                            startActivity(new Intent(LoginActivity.this, OTPNumberRecallActivity.class));

                            //  txtUsername.setText(first_name+" "+last_name);
                            //  txtEmail.setText(email1);
                            RequestOptions requestOptions = new RequestOptions();
                            requestOptions.dontAnimate();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();



    }
    /////////////////////
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

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("getStatus","2"); // Storing string
                    editor.putString("first_name",first_name );
                    editor.putString("last_name",last_name );
                    editor.putString("email",email1 );
                    editor.putString("s_id",f_id );
                    editor.commit();

                    startActivity(new Intent(LoginActivity.this, OTPNumberRecallActivity.class));
                    finish();
                    //  txtUsername.setText(first_name+" "+last_name);
                    //  txtEmail.setText(email1);
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();
                   // registerUserfb();


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
}
