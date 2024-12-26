package com.example.quanlykhachsan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlykhachsan.Class.yeucau;
import com.example.quanlykhachsan.R;

import java.util.List;

public class yeucauAdapter_Cus extends RecyclerView.Adapter<yeucauAdapter_Cus.ViewHolder> {

    private final Context context;
    private final List<yeucau> yeucauList;

    // Constructor
    public yeucauAdapter_Cus(Context context, List<yeucau> yeucauList) {
        this.context = context;
        this.yeucauList = yeucauList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout item
        View view = LayoutInflater.from(context).inflate(R.layout.item_yeucau_kh, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data to item views
        yeucau request = yeucauList.get(position);
        holder.txtCustomer.setText(request.getCustomer());
        holder.txtRoom.setText(request.getRoom());
        holder.txtService.setText(request.getService());
        holder.txtDate.setText(request.getDate());
        holder.txtPrice.setText(String.format("%.0f VND", request.getPrice()));
        holder.txtStatus.setText(request.getStatus());
    }

    @Override
    public int getItemCount() {
        return yeucauList.size();
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCustomer, txtRoom, txtService, txtDate, txtPrice, txtStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCustomer = itemView.findViewById(R.id.txtKhachHang);
            txtRoom = itemView.findViewById(R.id.txtPhong);
            txtService = itemView.findViewById(R.id.txtDichVu);
            txtDate = itemView.findViewById(R.id.txtNgay);
            txtPrice = itemView.findViewById(R.id.txtGia);
            txtStatus = itemView.findViewById(R.id.txtstatus);
        }
    }
}
