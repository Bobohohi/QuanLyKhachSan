package com.example.quanlykhachsan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlykhachsan.Class.Services;
import com.example.quanlykhachsan.R;

import java.util.ArrayList;
import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServiceViewHolder>{
    private Context context;
    private List<Services> serviceList;
    private List<Services> selectedServices = new ArrayList<>(); // Danh sách dịch vụ đã chọn

    public ServicesAdapter(Context context, List<Services> serviceList) {
        this.context = context;
        this.serviceList = serviceList;
    }
    public List<Services> getSelectedServices() {
        return selectedServices;
    }
    // Tạo ViewHolder để chứa các view của mỗi item trong RecyclerView
    public static class ServiceViewHolder extends RecyclerView.ViewHolder {

        public CheckBox rdService;

        public ServiceViewHolder(View itemView) {
            super(itemView);
            rdService = itemView.findViewById(R.id.rdService);
        }
    }

    @Override
    public ServicesAdapter.ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate layout cho mỗi item trong RecyclerView
        View view = LayoutInflater.from(context).inflate(R.layout.list_service, parent, false);
        return new ServicesAdapter.ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServicesAdapter.ServiceViewHolder holder, int position) {
        // Lấy dữ liệu dịch vụ từ danh sách
        Services s = serviceList.get(position);

        // Gắn tên dịch vụ vào CheckBox
        holder.rdService.setText(s.getName());

        // Đặt trạng thái CheckBox khi RecyclerView vẽ lại
        holder.rdService.setOnCheckedChangeListener(null); // Đảm bảo không bị lặp listener
        holder.rdService.setChecked(selectedServices.contains(s));

        // Xử lý sự kiện chọn hoặc bỏ chọn CheckBox
        holder.rdService.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedServices.add(s); // Thêm dịch vụ đã chọn
                Toast.makeText(context, s.getName() + " được chọn", Toast.LENGTH_SHORT).show();
            } else {
                selectedServices.remove(s); // Bỏ dịch vụ khi bị bỏ chọn
                Toast.makeText(context, s.getName() + " bị bỏ chọn", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceList.size();  // Trả về số lượng mục trong danh sách
    }

    // Cập nhật danh sách các phòng
    public void updateServices(List<Services> newServiceList) {
        this.serviceList = newServiceList;
        notifyDataSetChanged();  // Thông báo RecyclerView cập nhật lại dữ liệu
    }
}
