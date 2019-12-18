package com.abhishek.carwash.demo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abhishek.carwash.R;
import com.abhishek.carwash.booking.ModalPackage;
import com.abhishek.carwash.booking.PackageAdapter;
import com.abhishek.carwash.utils.Urls;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SPackageDetailActivity extends AppCompatActivity  {
    String status;
    //  private String url = "http://hc.phpsupport.in/wsapis/getFleetBooking?type=2";
    FragmentManager manager = getSupportFragmentManager();
    private RecyclerView mList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<ModalPackage> modalList;
    private PackageAdapter recyclerViewAdapter;
    protected ProgressDialog progressDialog;
    String getSelectedCar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_detail);

        mList = findViewById(R.id.recycler_view_package);
        modalList = new ArrayList<>();


        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
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
}
