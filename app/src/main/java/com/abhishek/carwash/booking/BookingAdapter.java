package com.abhishek.carwash.booking;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abhishek.carwash.R;

import java.util.List;

public class BookingAdapter extends BaseAdapter {

    Context context;
    List<BookingModel> models;
    LayoutInflater layoutInflater;

    public BookingAdapter(Context context, List<BookingModel> models) {
        this.context = context;
        this.models = models;
        layoutInflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.booking_list, null);
        TextView bookingTitle = convertView.findViewById(R.id.bookingListTitle);
        TextView bookingDescript = convertView.findViewById(R.id.bookingListDescription);
        TextView bookDate = convertView.findViewById(R.id.bookingListdate);
        TextView bookTime = convertView.findViewById(R.id.bookingListtime);
        TextView bookPirce = convertView.findViewById(R.id.bookingListPrice);
        TextView bookStatus = convertView.findViewById(R.id.bookingListStatus);
        TextView address = convertView.findViewById(R.id.address);
        LinearLayout layout = convertView.findViewById(R.id.layout_status);

        BookingModel model = models.get(position);
        bookingTitle.setText(model.getPackageName() + " / " + model.getCar());
        address.setText("Address: " + model.getAddress());
        bookingDescript.setText(model.getBookingDescription());

        String date_time = model.getBookingDate();
        bookDate.setText(date_time.substring(0, 10));

        String timeFormat= date_time.substring(11,19);

      /* SimpleDateFormat date12 = new SimpleDateFormat("hh:mm a");
       // SimpleDateFormat date24 = new SimpleDateFormat("hh:mm");

        bookTime.setText(date12.format(timeFormat));
*/
       /* if (date_time.substring(11,16).equals("12:00") || date_time.substring(11,16).equals("24:59")){
            bookDate.setText(date_time.substring(11, 16)+"PM");
        }else  if (date_time.substring(11,16).equals("01:00") || date_time.substring(11,16).equals("11:59")){
            bookDate.setText(date_time.substring(11,16)+"AM");
        }*/

       bookTime.setText(timeFormat);

        bookPirce.setText(model.getBookingPrice());
        String status = (model.getBookingStatus());
        if (status.equals("0")) {
            layout.setBackgroundColor(Color.parseColor("#ff0000"));
            bookStatus.setText("Pending");
        } else if (status.equals("1")) {
            layout.setBackgroundColor(Color.parseColor("#00ff00"));
            bookStatus.setText("Confirm");
        }


        return convertView;
    }
}
