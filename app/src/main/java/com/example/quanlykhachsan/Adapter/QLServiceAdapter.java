package com.example.quanlykhachsan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.quanlykhachsan.Class.Services;
import com.example.quanlykhachsan.R;

import java.util.List;

public class QLServiceAdapter extends ArrayAdapter<Services> {
    private Context context;
    private List<Services> servicesList;

    public QLServiceAdapter(Context context, List<Services> servicesList) {
        super(context, 0, servicesList);
        this.context = context;
        this.servicesList = servicesList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.service_item_layout, parent, false);
        }


        Services service = getItem(position);


        if (service != null) {
            TextView idTextView = convertView.findViewById(R.id.serviceId);
            TextView nameTextView = convertView.findViewById(R.id.serviceName);
            TextView priceTextView = convertView.findViewById(R.id.servicePrice);


            idTextView.setText(String.valueOf(service.getId()));
            nameTextView.setText(service.getName());
            priceTextView.setText(String.format("$%.2f", service.getPrice()));
        }

        return convertView;
    }


    public void updateData(List<Services> newServiceList) {
        servicesList.clear();
        servicesList.addAll(newServiceList);
        notifyDataSetChanged();
    }
}
