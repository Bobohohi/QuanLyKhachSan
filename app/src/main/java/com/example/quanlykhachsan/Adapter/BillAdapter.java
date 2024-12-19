package com.example.quanlykhachsan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quanlykhachsan.Class.Bill;
import com.example.quanlykhachsan.R;

import java.util.List;

public class BillAdapter extends BaseAdapter {

    private Context context;
    private List<Bill> billList;
    private LayoutInflater inflater;

    public BillAdapter(Context context, List<Bill> billList) {
        this.context = context;
        this.billList = billList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return billList.size();
    }

    @Override
    public Object getItem(int position) {
        return billList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_bill, parent, false);
        }

        Bill bill = billList.get(position);

        TextView txtBookingDetailId = convertView.findViewById(R.id.txtBookingDetailId);  // Thêm TextView cho Booking_detail_id
        TextView txtBookingId = convertView.findViewById(R.id.txtBookingId);
        TextView txtRoomId = convertView.findViewById(R.id.txtRoomId);
        TextView txtPrice = convertView.findViewById(R.id.txtPrice);
        TextView txtNumberOfNights = convertView.findViewById(R.id.txtNumberOfNights);
        TextView txtTotalPrice = convertView.findViewById(R.id.txtTotalPrice);

        txtBookingDetailId.setText(String.valueOf(bill.getBookingDetailId()));  // Hiển thị Booking_detail_id
        txtBookingId.setText(String.valueOf(bill.getBookingId()));
        txtRoomId.setText(String.valueOf(bill.getRoomId()));
        txtPrice.setText(String.format("%,.2f", bill.getPrice()));
        txtNumberOfNights.setText(String.valueOf(bill.getNumberOfNight()));
        txtTotalPrice.setText(String.format("%,.2f", bill.getTotalPrice()));

        return convertView;
    }

    public void updateData(List<Bill> updatedList) {
        this.billList = updatedList;
        notifyDataSetChanged();
    }
}
