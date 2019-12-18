package com.abhishek.carwash.booking;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.abhishek.carwash.R;
import com.abhishek.carwash.utils.AppPreferences;
import com.abhishek.carwash.utils.ModalLogin;
import com.abhishek.carwash.utils.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookingHistoryActivity extends AppCompatActivity {
    protected ProgressDialog progressDialog;
    private String status;

    TextView toolbarTitle;
    ModalLogin modalLogin;
    private String uId;

    ListView listView;
    List<BookingModel> booking;
    ImageView imageleft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        toolbarTitle = (TextView)findViewById(R.id.tool_bar);
        toolbarTitle.setText("Booking History");
        imageleft = findViewById(R.id.imageleft);
        imageleft.setVisibility(View.GONE);

        modalLogin = AppPreferences.getSavedUser(BookingHistoryActivity.this);
        uId = modalLogin.getU_id();

        booking = new ArrayList<>();
        listView = (ListView)findViewById(R.id.listView);

        getData();


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


        String packageUrl = new Urls().CarWashBookingListviaUser +"uId="+uId;

        Log.d("urlrespone", packageUrl);

        StringRequest request = new StringRequest(Request.Method.GET, packageUrl, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.e("reposne", response);

                progressDialog.dismiss();
                booking.clear();

                try {
                    JSONObject object = new JSONObject(response);
                    status = (object.getString("status"));
                    Log.i("Show value:", object.getString("status"));

                    if (status.equals("1")) {
                        JSONArray jsonArray = object.getJSONArray("data");
                        Log.e("data:", object.getJSONArray("data").toString());

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject dataObj = jsonArray.getJSONObject(i);

                            final BookingModel modal = new BookingModel();

                            modal.setuId(dataObj.getString("id"));
                            // modal.setName(IMAGE_URL1 + dataObj.getString("logo"));
                            modal.setCar(dataObj.getString("car"));
                            modal.setPackageName(dataObj.getString("packageName"));
                            modal.setBookingPrice(dataObj.getString("amount"));
                            modal.setBookingDescription(("description not found"));
                            modal.setBookingDate(dataObj.getString("bookingDate"));
                            modal.setAddress(dataObj.getString("address"));
                            modal.setBookingStatus(dataObj.getString("status"));


                            booking.add(modal);

                        }

                        BookingAdapter bookingAdapter = new BookingAdapter(getApplicationContext(),booking);
                        listView.setAdapter(bookingAdapter);


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

}