package com.abhishek.carwash.demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abhishek.carwash.ActivityTermCondition;
import com.abhishek.carwash.ChangePasswordActivity;
import com.abhishek.carwash.PrivacyActivity;
import com.abhishek.carwash.R;
import com.abhishek.carwash.booking.BookingHistoryActivity;

import com.abhishek.carwash.booking.ModalPackage;
import com.abhishek.carwash.booking.PackageAdapter;
import com.abhishek.carwash.booking.PackageDetailActivity;
import com.abhishek.carwash.loginregister.ProfileActivity;
import com.abhishek.carwash.utils.AppPreferences;
import com.abhishek.carwash.utils.ModalLogin;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SListingActivity extends FragmentActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private Spinner spinner;
    private Button sumit;
    SpinnerAdapter spinnerAdapter;
    private String[] carList = {"Sedan", "Hatchback", "SUV"};
    String select_car;


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
        setContentView(R.layout.activity_spackage_detail);
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,carList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);
        sumit = findViewById(R.id.list_submit);
        sumit.setOnClickListener(this);
        getSelectedCar = getIntent().getStringExtra("select_car");
        Log.d("getSelectedCar",""+ getSelectedCar);

        modalLogin = AppPreferences.getSavedUser(SListingActivity.this);
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
        toolbarTitle = (TextView)findViewById(R.id.tool_bar);
        toolbarTitle.setText("PACKAGE DETAIL");
        find();

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
            startActivity(new Intent(SListingActivity.this, ProfileActivity.class));
        }
        if (view == dr_rl) {
            drawer_slide();

        }
        if (view == booking_history_drawer){
            drawer_slide();
            startActivity(new Intent(SListingActivity.this, BookingHistoryActivity.class));
        }
        if (view == change_passward_drawer){
            drawer_slide();
            startActivity(new Intent(SListingActivity.this, ChangePasswordActivity.class));
        }
        if (view == privacy_drawer){
            drawer_slide();
            startActivity(new Intent(SListingActivity.this, PrivacyActivity.class));
        }
        if (view == term_contition_drawer){
            drawer_slide();
            startActivity(new Intent(SListingActivity.this, ActivityTermCondition.class));
        }if (view==sumit){

            if (select_car == null){

                Toast.makeText(getApplicationContext(), "Select your car", Toast.LENGTH_SHORT).show();

            }else {
                Intent intent = new Intent(SListingActivity.this, PackageDetailActivity.class);
                intent.putExtra("select_car", select_car);
                startActivity(intent);
            }
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        select_car = carList[position];

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*@Override
    public void onClick(View v) {

        if (v==sumit){

            if (select_car == null){

                Toast.makeText(getApplicationContext(), "Select your car", Toast.LENGTH_SHORT).show();

            }else {
                Intent intent = new Intent(SListingActivity.this, PackageDetailActivity.class);
                intent.putExtra("select_car", select_car);
                startActivity(intent);
            }
        }

    }*/

}
