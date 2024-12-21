package com.example.quanlykhachsan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlykhachsan.Class.Rooms;
import com.example.quanlykhachsan.R;
import com.example.quanlykhachsan.View_Customers.BillActivity;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.RoomViewHolder> {

    private Context context;
    private List<Rooms> roomList;
    private Set<Integer> selectedRoomIds = new HashSet<>();

    public RoomsAdapter(Context context, List<Rooms> roomList) {
        this.context = context;
        this.roomList = roomList;
    }

    // Tạo ViewHolder để chứa các view của mỗi item trong RecyclerView
    public static class RoomViewHolder extends RecyclerView.ViewHolder {

        public TextView tvRoomType, tvStatus, tvPrice;
        public ImageView ivRoomImage;

        public RoomViewHolder(View itemView) {
            super(itemView);
            tvRoomType = itemView.findViewById(R.id.tvRoomType);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ivRoomImage = itemView.findViewById(R.id.ivRoomImage);
        }
    }
    public Set<Integer> getSelectedRooms() {
        return selectedRoomIds; // Trả về tập hợp các phòng đã chọn
    }
    @Override
    public RoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate layout cho mỗi item trong RecyclerView
        View view = LayoutInflater.from(context).inflate(R.layout.list_rooms, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomViewHolder holder, int position) {
        // Lấy dữ liệu cho phòng từ danh sách
        Rooms room = roomList.get(position);

        // Gắn dữ liệu vào các view trong ViewHolder
        holder.tvRoomType.setText(room.getRoomtype());
        holder.tvStatus.setText(room.getStatus());
        holder.tvPrice.setText(String.format("%,.0f VND", room.getPrice()));

        // Tải hình ảnh vào ImageView nếu có URL
        if (!room.getImage().isEmpty()) {
            Picasso.get().load(room.getImage()).into(holder.ivRoomImage);
        } else {
            holder.ivRoomImage.setImageResource(R.drawable.defaultroom);  // Hình ảnh mặc định
        }

        // Kiểm tra trạng thái phòng
        if (room.getStatus().equalsIgnoreCase("Bận")) {
            // Làm mờ giao diện và vô hiệu hóa click
            holder.itemView.setAlpha(0.5f); // Làm mờ item
            holder.itemView.setEnabled(false); // Vô hiệu hóa click
        } else {
            // Trạng thái phòng trống, cho phép click
            holder.itemView.setAlpha(1.0f); // Không làm mờ
            holder.itemView.setEnabled(true); // Cho phép click

            // Kiểm tra và hiển thị trạng thái đã chọn
            if (selectedRoomIds.contains(room.getRoomid())) {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.selected_color)); // Màu khi được chọn
            } else {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.default_color)); // Màu nền mặc định
            }

            // Xử lý click để chọn hoặc bỏ chọn phòng
            holder.itemView.setOnClickListener(v -> {
                if (selectedRoomIds.contains(room.getRoomid())) {
                    selectedRoomIds.remove(room.getRoomid());
                } else {
                    selectedRoomIds.add(room.getRoomid());
                }
                notifyItemChanged(position); // Cập nhật giao diện
            });
        }

    }

    @Override
    public int getItemCount() {
        return roomList.size();  // Số lượng item trong RecyclerView
    }

    // Cập nhật danh sách các phòng
    public void updateRooms(List<Rooms> newRoomList) {
        this.roomList = newRoomList;
        notifyDataSetChanged();  // Cập nhật lại dữ liệu trong RecyclerView
    }
}
