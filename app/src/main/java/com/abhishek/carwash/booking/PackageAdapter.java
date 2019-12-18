package com.abhishek.carwash.booking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.abhishek.carwash.R;
import com.abhishek.carwash.utils.Urls;

import java.util.List;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.ViewHolder> {

    public Context context;
    private List<ModalPackage> list;

    public PackageAdapter(Context context, List<ModalPackage> list) {
        this.context = context;
        this.list = list;


    }

    @NonNull
    @Override
    public PackageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.package_list_datails, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageAdapter.ViewHolder viewHolder, int i) {

        final ModalPackage modal = list.get(i);

        String imgUrl =modal.getImage();
        Log.d("iconimage", imgUrl);
        final String imageurl = new Urls().imgUrl + imgUrl;
        Picasso.with(context).load(imageurl).into(viewHolder.pkImage);

        viewHolder.pkPrice.setText(modal.getPrice());
        viewHolder.pkDescription.setText(modal.getDescription());
        viewHolder.packageName.setText(modal.getName()+" / "+ modal.getCarType());

        viewHolder.btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,BookingInsertActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();

                bundle.putString("id_k",modal.getId());
                bundle.putString("package_name_k",modal.getName());
                bundle.putString("carType_k",modal.getCarType());
                bundle.putString("price_k",modal.getPrice());
                bundle.putString("des_k",modal.getDescription());
                bundle.putString("img_k",imageurl);
                bundle.putString("status_k",modal.getStatus());

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView pkImage;
        private TextView pkPrice,pkDescription, packageName;
        private Button btnBook,btnBack;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pkImage = (ImageView)itemView.findViewById(R.id.packageImage);
            pkPrice = (TextView)itemView.findViewById(R.id.packagePirce);
            pkDescription = (TextView)itemView.findViewById(R.id.packageDescription);
            packageName = (TextView)itemView.findViewById(R.id.packageName);
            btnBook = (Button)itemView.findViewById(R.id.bookBtn);
          //  btnBack = (Button)itemView.findViewById(R.id.backBtn);
        }
    }
}
