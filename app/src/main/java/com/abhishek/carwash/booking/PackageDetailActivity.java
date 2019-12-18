package com.abhishek.carwash.booking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.abhishek.carwash.ActivityTermCondition;
import com.abhishek.carwash.ChangePasswordActivity;
import com.abhishek.carwash.PrivacyActivity;
import com.abhishek.carwash.R;
import com.abhishek.carwash.loginregister.ProfileActivity;
import com.abhishek.carwash.utils.AppPreferences;
import com.abhishek.carwash.utils.ModalLogin;
import com.abhishek.carwash.utils.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PackageDetailActivity extends FragmentActivity implements View.OnClickListener {

    protected ProgressDialog progressDialog;

    ImageView pkImage;
    TextView pkPrice,pkDescription,toolbarTitle;
    Button btnBook,btnBack;
    private TextView user_name, userfirstWords,profile_drawer, booking_history_drawer, change_passward_drawer, privacy_drawer, term_contition_drawer, sign_out;
    CircularImageView userPicture;
    ModalLogin modalLogin;


    ImageView imageleft;
    DrawerLayout main_drawer_layout;
    LinearLayout left_drawer_left;
    RelativeLayout dr_rl;
    String name_f, name_l;



    String status;
    //  private String url = "http://hc.phpsupport.in/wsapis/getFleetBooking?type=2";
    FragmentManager manager = getSupportFragmentManager();
    private RecyclerView mList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<ModalPackage> modalList;
    private PackageAdapter recyclerViewAdapter;

    String getSelectedCar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_detail);
        find();
        getData();

        getSelectedCar = getIntent().getStringExtra("select_car");
        Log.d("getSelectedCar",""+ getSelectedCar);

        modalLogin = AppPreferences.getSavedUser(PackageDetailActivity.this);
        name_f = modalLogin.getFirst_name();
        name_l = modalLogin.getLast_name();
        String email = modalLogin.getEmail();
        String gpic = modalLogin.getGphoto();
        String fpic= modalLogin.getfPhoto();
        Log.d("qqq",name_f+" "+name_l);
        Log.d("email",email);
        user_name = findViewById(R.id.user_name);
        user_name.setText(name_f+" "+name_l);
        userPicture = findViewById(R.id.userPicture);
        Picasso.with(this).load(gpic).into(userPicture);
        userfirstWords = findViewById(R.id.userfirstWords);

            try {
                userfirstWords.setText(name_f.substring(0, 1));
            } catch (StringIndexOutOfBoundsException v) {
                v.fillInStackTrace();
            }


        mList = findViewById(R.id.recycler_view_package);
        modalList = new ArrayList<>();


        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);

        toolbarTitle = (TextView)findViewById(R.id.tool_bar);
        toolbarTitle.setText("PACKAGE DETAIL");

    }
    public void find() {
        main_drawer_layout = findViewById(R.id.menu_layout_item);
        left_drawer_left = findViewById(R.id.left_drawer_layout);
        imageleft = findViewById(R.id.imageleft);
        imageleft.setOnClickListener(this);
        profile_drawer=findViewById(R.id.profile_drawer);
        dr_rl = findViewById(R.id.dr_rl);
        dr_rl.setOnClickListener(this);



        booking_history_drawer = findViewById(R.id.booking_history_drawer);
        change_passward_drawer = findViewById(R.id.change_passward_drawer);
        privacy_drawer = findViewById(R.id.privacy_drawer);
        term_contition_drawer = findViewById(R.id.term_contition_drawer);
        sign_out = findViewById(R.id.sign_out);
        booking_history_drawer.setOnClickListener(this);
        profile_drawer.setOnClickListener(this);
        change_passward_drawer.setOnClickListener(this);
        privacy_drawer.setOnClickListener(this);
        term_contition_drawer.setOnClickListener(this);
        sign_out.setOnClickListener(this);


    }


    private void getData() {



        try{
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }catch (WindowManager.BadTokenException e)
        {
            e.addSuppressed(null);
            e.fillInStackTrace();
        }


        String packageUrl = new Urls().CarWashPackageList;

        Log.d("urlrespone", packageUrl);

        StringRequest request = new StringRequest(Request.Method.GET, packageUrl, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.e("reposne", response);

                progressDialog.dismiss();
                modalList.clear();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    status = jsonObject.getString("status");
                    Log.i("Show value:",status);

                    if (status.equals("1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        Log.e("data:", jsonObject.getJSONArray("data").toString());

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject dataObj = jsonArray.getJSONObject(i);

                            final ModalPackage modal = new ModalPackage();

                            String carType = dataObj.getString("carType");
                            if (getSelectedCar.equals(carType)) {


                                modal.setId(dataObj.getString("id"));
                                // modal.setName(IMAGE_URL1 + dataObj.getString("logo"));
                                modal.setName(dataObj.getString("name"));
                                modal.setCarType(dataObj.getString("carType"));
                                modal.setPrice(dataObj.getString("price"));
                                modal.setDescription(dataObj.getString("description"));
                                modal.setImage(dataObj.getString("image"));
                                modal.setStatus(dataObj.getString("status"));
                                modal.setCreated(dataObj.getString("created"));

                                modalList.add(modal);
                            }else {

                            }

                        }

                        recyclerViewAdapter = new PackageAdapter(getApplicationContext(), modalList);
                        mList.setAdapter(recyclerViewAdapter);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Please Connect Your Internet", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }




    public void drawer_slide() {

        if (!main_drawer_layout.isDrawerOpen(left_drawer_left)) {
            main_drawer_layout.openDrawer(Gravity.LEFT);
        } else {
            main_drawer_layout.closeDrawer(Gravity.LEFT);
        }

    }


    @Override
    public void onClick(View view) {
        if (view == imageleft) {
            drawer_slide();

        }if (view==profile_drawer){
            drawer_slide();
            startActivity(new Intent(PackageDetailActivity.this, ProfileActivity.class));
        }
        if (view == dr_rl) {
            drawer_slide();

        }
        if (view == booking_history_drawer){
            drawer_slide();
            startActivity(new Intent(PackageDetailActivity.this,BookingHistoryActivity.class));
        }
        if (view == change_passward_drawer){
            drawer_slide();
            startActivity(new Intent(PackageDetailActivity.this, ChangePasswordActivity.class));
        }
        if (view == privacy_drawer){
            drawer_slide();
            startActivity(new Intent(PackageDetailActivity.this, PrivacyActivity.class));
        }
        if (view == term_contition_drawer){
            drawer_slide();
            startActivity(new Intent(PackageDetailActivity.this, ActivityTermCondition.class));
        }
        if (view == sign_out){

            AppPreferences.clearPrefsdata(this);
           // LoginManager.getInstance().logOut();
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        if (main_drawer_layout.isDrawerOpen(left_drawer_left)) {
            drawer_slide();
        } else {
            super.onBackPressed();
        }
    }

}
