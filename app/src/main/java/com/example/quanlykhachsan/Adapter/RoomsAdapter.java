package com.example.quanlykhachsan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlykhachsan.Class.Rooms;
import com.example.quanlykhachsan.R;
import com.example.quanlykhachsan.Views.BillActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.RoomViewHolder> {

    private Context context;
    private List<Rooms> roomList;

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

        // Xử lý sự kiện click vào một item phòng
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BillActivity.class);
            intent.putExtra("room_id", room.getRoomid());
            intent.putExtra("room_type", room.getRoomtype());
            intent.putExtra("room_price", room.getPrice());
            context.startActivity(intent);
        });
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
